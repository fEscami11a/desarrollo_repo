package mx.com.interware.spira.web;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import mx.com.interware.spira.message.ArrayMessageElement;
import mx.com.interware.spira.message.FieldMessageElement;
import mx.com.interware.spira.message.MessageElement;
import mx.com.interware.spira.message.StructMessageElement;
import mx.com.interware.spira.message.TPacket;
import mx.com.interware.spira.message.TPacketCacheHolder;
import mx.com.interware.spira.message.TPacketImpl;
import mx.com.interware.spira.message.exceptions.ElementNotFoundException;
import mx.com.interware.spira.message.exceptions.InvalidFormatException;
import mx.com.interware.spira.message.exceptions.TotalMessageException;
import mx.com.interware.util.LRUCache2;
import mx.com.interware.util.Throwable2String;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ibm.mq.jms.JMSC;
import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnectionFactory;
public abstract class AbstractTSYSMessage {
	private static Logger log = Logger.getLogger(AbstractTSYSMessage.class);

	// los siguientes son valors default pero deben ser puestos por el servlet de inicializacion
	// de la aplicacion y deben estar almacenados en el web.xml

	public static String QM_HOST = "172.16.18.139";
	private static String QM_NAME = "SPIRAPROD";
	private static String Q_PREGUNTA = "PREGUNTA";
	private static String Q_RESPUESTA = "RESPUESTA";
	private static String CHANNEL = "JAVA.CHANNEL";
	private static int PORT = 1414;
	private static int CACHE_SIZE = 25;
	private static int SENDER_TIME_TO_LIVE = 5000;
	private static int RECEIVER_MAX_WAIT = 6000;

	private static Map messagesCaches = new HashMap();
	private static TPacket TEND, MEND;
	/**
	 * Vacia el cache
	 * @return
	 */
	public synchronized static void clearCache() {
		StringBuffer buf = new StringBuffer();
		buf.append("AbstractTSYSMessage.clearCache invoked.. \nmessagesCaches.size=" + messagesCaches.size());
		buf.append("\n\n");
		for (Iterator iter = messagesCaches.keySet().iterator(); iter.hasNext();) {
			Object k = iter.next();
			LRUCache2 cache = (LRUCache2) messagesCaches.get(k);
			buf.append("Cache para:" + k + " tiene " + cache.size() + " elementos\n");
		}
		log.info(buf.toString());
		messagesCaches.clear();
	}
	/**
	 * Obtiene la secci&oacute;n TEND del mensaje MQ
	 * @return
	 */
	private synchronized TPacket getEND() {
		if (TEND == null) {
			TEND = new TPacketImpl();
			try {
				TEND.convertToDefaultTEnd();
			}
			catch (Exception e) {
				log.error(e);
			}
		}
		return TEND;
	}
	/**
	 * Obtiene la secci&oacute;n MEND del mensaje MQ
	 * @return
	 */
	private synchronized TPacket getMEND() {
		if (MEND == null) {
			MEND = new TPacketImpl();
			try {
				MEND.convertToDefaultMEnd();
			}
			catch (Exception e) {
				log.error(e);
			}
		}
		return TEND;
	}

	//public static SimpleDateFormat avgFormater=new SimpleDateFormat("yyyyMMdd-HH:mm");
	public static SimpleDateFormat avgFormater = new SimpleDateFormat("yyyyMMdd-HH");
	private static AvgPoint currentAvgPoint;
	private static String currentHour;
	private static List avgStats = new LinkedList();
	
	private static synchronized void addPoint(long delta) {
		Date now = new Date();
		if (currentHour == null || !avgFormater.format(now).equals(currentHour)) {
			//nuevaHora
			if (currentHour != null) {
				currentAvgPoint.closeHour();
			}
			currentAvgPoint = new AvgPoint(now);
			currentHour = avgFormater.format(now);
			avgStats.add(currentAvgPoint);
			while (avgStats.size() > 240) {
				avgStats.remove(0);
			}
		}
		currentAvgPoint.getDelta().add(new Long(delta));
	}
	
	
	/***********************Seccin para bitacora**********************/
	
		
	private static ArrayList allMSG = new ArrayList(); 
	
	
	/**
	 * Agrega un punto de estadistica sobre un mensaje MQ transaccionado
	 * @param delta
	 * @param dmsType
	 */
	private static synchronized void addPoint(long delta, String dmsType) {
		String instant = Util.parseFormatFileDate(new Date());
		MsgDescription msg = new MsgDescription();
		msg.setDelta(delta);
		msg.setDmsType(dmsType);
		msg.setInstant(instant);
		allMSG.add(msg);
		addPoint(delta);
	}
	/**
	 * Devuelve los datos almacenados sobre el tiempo puntual en el envi 
	 * y recepcin de mensajes
	 */
	public static synchronized Collection getDataMessage() {
		return allMSG;
	}
	/*************************************************/
	/**
	 * Devuelve los datos almacenados sobre el tiempo promedio por hora en el envi 
	 * y recepcin de mensajes
	 */
	public static synchronized AvgPoint[] getStatistics() {
			if (avgStats.size() <= 0)
				return null;
			AvgPoint[] data = new AvgPoint[avgStats.size()];
			avgStats.toArray(data);
			return data;
	}
	/**
	 * Envia el Mensaje MQ a trav&eacute;s de los parametros establecidos de configuracin
	 * @param msgbytes
	 * @return
	 * @throws JMSException
	 */
	protected byte[] sendMessage(byte[] msgbytes) throws JMSException {
		long t0 = System.currentTimeMillis();
		double initT = System.currentTimeMillis();
		Queue preguntaQueue = null;
		Queue respuestaQueue = null;
		QueueSession session = null;
		QueueConnection connection = null;
		QueueConnectionFactory factory = null;
		QueueSender queueSender = null;
		QueueReceiver queueReceiver = null;

		//String key=new String(msgbytes);
		//byte[] result=(byte[])cache.get(key);
		//if (result!=null) {
		//	log.debug("salio mensaje del cache");
		//	return result;
		//}
		try {
			factory = new MQQueueConnectionFactory();
			
			((MQQueueConnectionFactory) factory).setHostName(QM_HOST);
			((MQQueueConnectionFactory) factory).setChannel(CHANNEL);
			((MQQueueConnectionFactory) factory).setPort(PORT);
			((MQQueueConnectionFactory) factory).setTransportType(JMSC.MQJMS_CLIENT_NONJMS_MQ);

			connection = factory.createQueueConnection();

			//log.info("Starting the Connection to:" + QM_HOST + ":" + PORT + " [" + CHANNEL + "]");
			connection.start();
			boolean transacted = true;
			session = connection.createQueueSession(transacted, Session.CLIENT_ACKNOWLEDGE);
			//Session.AUTO_ACKNOWLEDGE);

			preguntaQueue = session.createQueue("queue://" + QM_NAME + "/" + Q_PREGUNTA + "?persistence=1");
			//log.info("Connecting to queue:" + preguntaQueue);
			 ((MQQueue) preguntaQueue).setTargetClient(JMSC.MQJMS_CLIENT_NONJMS_MQ);

			TextMessage outMessage = session.createTextMessage();
				
			respuestaQueue = session.createQueue("queue://" + QM_NAME + "/" + Q_RESPUESTA);
			((MQQueue) respuestaQueue).setTargetClient(JMSC.MQJMS_CLIENT_NONJMS_MQ);

			outMessage.setJMSReplyTo(respuestaQueue);
			//outMessage.setStringProperty("JMS_IBM_Format","MQSTR");
			outMessage.setText(new String(msgbytes));
			queueSender = session.createSender(preguntaQueue);
			queueSender.setTimeToLive(SENDER_TIME_TO_LIVE);
			log.info("Mandando mensaje a TSYS");
			log.info("Sending message " + getClass().getName() + ": " + outMessage.getText() + "");
			System.out.println("Sendind message "+outMessage.getText());
			double init = System.currentTimeMillis();
			queueSender.send(outMessage);

			session.commit();
			//recibir un mensaje con em miso JMSMessageID del que enviamos !
			// ojo ver si esto es asi o es necesario usar el CorrelationID

			queueReceiver = session.createReceiver(respuestaQueue, "JMSMessageID='" + outMessage.getJMSMessageID() + "'");
			//log.debug("receiving message... waiting max:" + RECEIVER_MAX_WAIT + " id=" + outMessage.getJMSMessageID());
			//log.info("Getting message from :" + respuestaQueue);
			TextMessage inMessage = (TextMessage) queueReceiver.receive(RECEIVER_MAX_WAIT);
			session.commit();
			
			if (inMessage == null) {
				// counter
				//String msg = ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM);
				String msg = ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.GET_MESSAGE_TIMEOUT);
				log.info("EXTRAPOINTINFO - No se logro recibir el mensaje: "+new Date());
				throw new JMSException(msg);
			}
			log.info("Got message" + ": " + inMessage.getText());
			System.out.println("Got message "+inMessage.getText());
			String replyString = inMessage.getText();
			double end = System.currentTimeMillis();
			log.debug("Tunning MQ - SENDMESSAGE: " + ((end - init) / 1000) + " seg."); // ELIMINAR
			//cache.put(key,replyString.getBytes("ISO-8859_1"));
			//return replyString.getBytes();
			double endT = System.currentTimeMillis();
			log.debug("Tunning MQ - sendMessage Method: " + ((endT - initT) / 1000) + " seg."); // ELIMINAR
			
			//mduran 06/04/2011 se corrige para la versión 7 de WAS ya que lanza una excepción
			//return replyString.getBytes("ISO-8859_1");
			return replyString.getBytes("ISO8859-1");
		}
		catch (UnsupportedEncodingException e) {
			throw new JMSException("Problema con el encoding: " + Throwable2String.throwable2String(e));
		}
		finally {
			// A finally block is a good place to ensure that we don't forget
			// to close the most important JMS objects
			long t1 = System.currentTimeMillis();
			long delta = t1 - t0;
			if (delta>999) {
				log.info("EXTRAPOINTINFO - Delta time: "+delta+ " :: Time: "+new Date()); // Retrasos de 1 seg. o mas
			}
			log.info("Tiempo de envio y respuesta: "+delta+ "ms. <---> "+((double)delta)/1000 + " seg.");
			addPoint(delta, getMessageName());
			try {
				if (queueSender != null) {
					queueSender.close();
				}
				if (queueReceiver != null) {
					queueReceiver.close();
				}
				if (session != null) {
					session.close();
				}
				if (connection != null) {
					connection.close();
				}
			}
			catch (JMSException je) {
				log.info("EXTRAPOINTINFO - JMSException0: "+new Date());
				log.error("send message failed", je);
				throw je;
			}
		}
	}
	
	protected String sendMessage(String mensaje) throws JMSException {
		long t0 = System.currentTimeMillis();
		double initT = System.currentTimeMillis();
		Queue preguntaQueue = null;
		Queue respuestaQueue = null;
		QueueSession session = null;
		QueueConnection connection = null;
		QueueConnectionFactory factory = null;
		QueueSender queueSender = null;
		QueueReceiver queueReceiver = null;

		//String key=new String(msgbytes);
		//byte[] result=(byte[])cache.get(key);
		//if (result!=null) {
		//	log.debug("salio mensaje del cache");
		//	return result;
		//}
		try {
			factory = new MQQueueConnectionFactory();
			
			((MQQueueConnectionFactory) factory).setHostName(QM_HOST);
			((MQQueueConnectionFactory) factory).setChannel(CHANNEL);
			((MQQueueConnectionFactory) factory).setPort(PORT);
			((MQQueueConnectionFactory) factory).setTransportType(JMSC.MQJMS_CLIENT_NONJMS_MQ);

			connection = factory.createQueueConnection();

			//log.info("Starting the Connection to:" + QM_HOST + ":" + PORT + " [" + CHANNEL + "]");
			connection.start();
			boolean transacted = true;
			session = connection.createQueueSession(transacted, Session.CLIENT_ACKNOWLEDGE);
			//Session.AUTO_ACKNOWLEDGE);

			preguntaQueue = session.createQueue("queue://" + QM_NAME + "/" + Q_PREGUNTA + "?persistence=1");
			//log.info("Connecting to queue:" + preguntaQueue);
			 ((MQQueue) preguntaQueue).setTargetClient(JMSC.MQJMS_CLIENT_NONJMS_MQ);

			TextMessage outMessage = session.createTextMessage();
				
			respuestaQueue = session.createQueue("queue://" + QM_NAME + "/" + Q_RESPUESTA);
			((MQQueue) respuestaQueue).setTargetClient(JMSC.MQJMS_CLIENT_NONJMS_MQ);

			outMessage.setJMSReplyTo(respuestaQueue);
			//outMessage.setStringProperty("JMS_IBM_Format","MQSTR");
			outMessage.setText(mensaje);
			queueSender = session.createSender(preguntaQueue);
			queueSender.setTimeToLive(SENDER_TIME_TO_LIVE);
			log.info("Mandando mensaje a TSYS");
			log.info("Sending message " + getClass().getName() + ": " + outMessage.getText() + "");
			double init = System.currentTimeMillis();
			queueSender.send(outMessage);

			session.commit();
			//recibir un mensaje con em miso JMSMessageID del que enviamos !
			// ojo ver si esto es asi o es necesario usar el CorrelationID

			queueReceiver = session.createReceiver(respuestaQueue, "JMSMessageID='" + outMessage.getJMSMessageID() + "'");
			//log.debug("receiving message... waiting max:" + RECEIVER_MAX_WAIT + " id=" + outMessage.getJMSMessageID());
			//log.info("Getting message from :" + respuestaQueue);
			TextMessage inMessage = (TextMessage) queueReceiver.receive(RECEIVER_MAX_WAIT);
			session.commit();
			
			if (inMessage == null) {
				// counter
				//String msg = ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM);
				String msg = ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.GET_MESSAGE_TIMEOUT);
				log.info("EXTRAPOINTINFO - No se logro recibir el mensaje: "+new Date());
				throw new JMSException(msg);
			}
			log.info("Got message" + ": " + inMessage.getText());
			String replyString = inMessage.getText();
			double end = System.currentTimeMillis();
			log.debug("Tunning MQ - SENDMESSAGE: " + ((end - init) / 1000) + " seg."); // ELIMINAR
			//cache.put(key,replyString.getBytes("ISO-8859_1"));
			//return replyString.getBytes();
			double endT = System.currentTimeMillis();
			log.debug("Tunning MQ - sendMessage Method: " + ((endT - initT) / 1000) + " seg."); // ELIMINAR
			
			//mduran 06/04/2011 se corrige para la versión 7 de WAS ya que lanza una excepción
			//return replyString.getBytes("ISO-8859_1");
			return replyString;
		}
		
		finally {
			// A finally block is a good place to ensure that we don't forget
			// to close the most important JMS objects
			long t1 = System.currentTimeMillis();
			long delta = t1 - t0;
			if (delta>999) {
				log.info("EXTRAPOINTINFO - Delta time: "+delta+ " :: Time: "+new Date()); // Retrasos de 1 seg. o mas
			}
			log.info("Tiempo de envio y respuesta: "+delta+ "ms. <---> "+((double)delta)/1000 + " seg.");
			addPoint(delta, getMessageName());
			try {
				if (queueSender != null) {
					queueSender.close();
				}
				if (queueReceiver != null) {
					queueReceiver.close();
				}
				if (session != null) {
					session.close();
				}
				if (connection != null) {
					connection.close();
				}
			}
			catch (JMSException je) {
				log.info("EXTRAPOINTINFO - JMSException0: "+new Date());
				log.error("send message failed", je);
				throw je;
			}
		}
	}
	

	protected void sendMessage(TPacket header, TPacket packet)
		throws JMSException, InvalidFormatException, IOException, InstantiationException, IllegalAccessException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		header.writeRequestTo(baos);
		packet.writeRequestTo(baos);
		getEND().writeRequestTo(baos);
		//log.debug("Mandando:" + baos.toString());
		//String test="00000250TSYS59884631860000002846                    CAT1                    00000000N00000000000000                                                                       0001                                                            0000            00000022SI011.00      00000012TEND";
		//log.info("mandando test:\n"+test);
		log.debug("sendMessage(TPacket, TPacket): " + Thread.currentThread() + " :: " + baos.toString()); // ELIMINAR
		byte[] result = sendMessage(baos.toByteArray());
		//byte[] result=sendMessage(test.getBytes());
		double kbytes = result.length;
		log.info("Tamao del Mensaje : " + kbytes + " Bytes -- " + (kbytes / 1024) + " Kb");
		ByteArrayInputStream bais = new ByteArrayInputStream(result);
		header.readResponseFrom(bais);
		packet.readResponseFrom(bais);
	}

	protected void sendMessage(TPacket[] packet) throws JMSException, InvalidFormatException, IOException, InstantiationException, IllegalAccessException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		for (int i = 0; i < packet.length; i++) {
			packet[i].writeRequestTo(baos);
		}
		log.info("Mandando :" + baos.toString());

		byte[] result = sendMessage(baos.toByteArray());
		log.debug("sendMessage(TPacket[]): " + Thread.currentThread() + " :: " + baos.toString()); // ELIMINAR
		ByteArrayInputStream bais = new ByteArrayInputStream(result);
		int i = 0;
		while (bais.available() > 0) {
			packet[i++].readResponseFrom(bais);
		}
	}

	private static synchronized LRUCache2 getCache(Class clazz) {
		LRUCache2 cache = (LRUCache2) messagesCaches.get(clazz.getName());
		if (cache == null) {
			cache = new LRUCache2(CACHE_SIZE);
			messagesCaches.put(clazz.getName(), cache);
		}
		return cache;
	}

	protected void invalidatePacket(String account) {
		LRUCache2 cache = getCache(getClass());
		synchronized (cache) {
			cache.remove(account);
		}
	}

	protected void invalidatePacketAll(Class[] clazz, String account) {
		for (int i = 0; i < clazz.length; i++) {
			LRUCache2 cache = getCache(clazz[i]);
			cache.remove(account);
		}
	}
	/**
	 * ejemplo de uso en mx.com.interware.spira.ls.facade.mna.MNAImpl
	 */
	protected void invalidatePacket(Class[] clazz, String account) {
		for (int i = 0; i < clazz.length; i++) {
			LRUCache2 cache = getCache(clazz[i]);
			if (cache.contains(clazz[i])) {
				cache.remove(account);
			}
		}
	}
	
	//Nuevo metodo 27 Junio 2008 limpia todo el cache
	protected void invalidatePacketLMCR(Class[] clazz, String account) {
		for (int i = 0; i < clazz.length; i++) {
			LRUCache2 cache = getCache(clazz[i]);
			cache.remove(account);
		}
	}
	// Nuevo metodo 27 Junio 2008
	
	// Nuevo metodo 03 Julio 2008 Limpia el cache contenido en una clase
	protected void limpiaCache(Class clazz, String account){
		LRUCache2 cache = getCache(clazz);
		cache.remove(account);
	}
	// Nuevo metodo 03 Julio 2008

	protected abstract String getMessageName(); // ej:SI01

	protected TPacket buildDefaultBodyPacket() throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException {
		TPacket packet = new TPacketImpl();
		packet.convertToDefaultMsg(getMessageName());
		return packet;
	}

	/**
	 * maxCachedTime: max number of SECONDS that cache is considered valid
	 */
	protected TPacket getPacket(String account, long maxCachedTime)
		throws ElementNotFoundException, InvalidFormatException, JMSException, IOException, InstantiationException, IllegalAccessException {
		LRUCache2 cache = getCache(getClass());
		TPacketCacheHolder holder = (TPacketCacheHolder) cache.get(account);
		long Ltime = 1000L;

		if (maxCachedTime < 10) {
			maxCachedTime = 10;
		}
		else if (maxCachedTime == 0) {
			maxCachedTime = 1;
			Ltime = 1L;
		}

		if (holder != null) {
			if ((System.currentTimeMillis() - holder.getCreation()) > maxCachedTime * Ltime) {
				holder = null;
			}
		}
		if (holder == null) {
			TPacket header = new TPacketImpl();
			header.convertToDefaultHeader();
			header.getElementRequest().getElement("accountID").setValue(account);
			TPacket packet = buildDefaultBodyPacket();
			//				ByteArrayOutputStream baos = new ByteArrayOutputStream(); // ELIMINAR
			//				header.writeRequestTo(baos); // ELIMINAR
			//				packet.writeRequestTo(baos); // ELIMINAR
			//				getEND().writeRequestTo(baos); // ELIMINAR
			//				log.debug("getPacket: [header + packet + END]: "+Thread.currentThread()+" :: "+baos.toString()); // ELIMINAR

			if (Msg.DEBUGGING_OFF_LINE) {
				//		log.debug("Sacando mensaje fijo de prueba");
				FileInputStream fis = new FileInputStream(Msg.DEFAULT_SI01_RESPONSE_FILE);
				packet.readResponseFrom(fis);
			}
			else {
				sendMessage(header, packet);
			}

			// verificar el header para ver que no existan errores y si no entonces:
			// metemos al cache el packet resultante de lo contrario mandamos exception !!
			holder = new TPacketCacheHolder(account, packet);
			cache.put(account, holder);

		}
		else {
			holder.refreshCreation();
		}
		return holder.getPacket();
	}
	/**
	 * Obtiene el campo solicitado del mensaje MQ
	 * @param account
	 * @param fieldName
	 * @param cacheTimeOut
	 * @return
	 * @throws TotalMessageException
	 */
	protected String getStringField(String account, String fieldName, long cacheTimeOut) throws TotalMessageException {
		TPacket packet = null;
		StructMessageElement struct = null;
		try {
			packet = getPacket(account, cacheTimeOut);
			struct = (StructMessageElement) packet.getElementResponse();
			return String.valueOf(struct.getElement(fieldName).getValue());
		}
		catch (ElementNotFoundException enfe) {
			if ((packet != null) && (struct != null) && (struct.getPacketID().equalsIgnoreCase("E900"))) {
				//return String.valueOf(struct.getElement("message").getValue());   	
				throw new TotalMessageException(
					String.valueOf(struct.getElement("code").getValue()) + " " + String.valueOf(struct.getElement("message").getValue()));
			}
			else {
				log.debug("Elemento " + fieldName + " no encontrado");
				throw enfe;
			}
		}
		catch (TotalMessageException e) {
			// ESTA ES ALGUNA POSIBLE EXCEPTION NUESTRA
			log.error("Problema en mensaje: " + getClass().getName(), e);
			// esto es imposible por que yo estoy seguro que LONG-NAME si pertenece a SI01
			throw e;
		}
		catch (InstantiationException e) {
			log.error("Problema en mensaje 1: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
		catch (IllegalAccessException e) {
			log.error("Problema en mensaje 2: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
		catch (JMSException e) {
			log.info("EXTRAPOINTINFO - JMSException1: "+new Date());
			log.error("Problema en mensaje 3: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
		catch (IOException e) {
			log.error("Problema en mensaje 4: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
	}

	protected String getOptionStringField(String account, String fieldName, long cacheTimeOut) throws TotalMessageException {
		TPacket packet = null;
		StructMessageElement struct = null;
		try {
			packet = getPacket(account, cacheTimeOut);
			struct = (StructMessageElement) packet.getElementResponse();
			MessageElement elem = struct.getElement(fieldName);
			if (elem instanceof FieldMessageElement) {
				FieldMessageElement fme = (FieldMessageElement) elem;
				return String.valueOf(fme.getOptionValue());
			}
			return String.valueOf(elem.getValue());
		}
		catch (ElementNotFoundException enfe) {
			if ((packet != null) && (struct != null) && (struct.getPacketID().equalsIgnoreCase("E900"))) {
				//return String.valueOf(struct.getElement("message").getValue());   	
				throw new TotalMessageException(
					String.valueOf(struct.getElement("code").getValue()) + " " + String.valueOf(struct.getElement("message").getValue()));
			}
			else {
				log.debug("Elemento " + fieldName + " no encontrado");
				throw enfe;
			}
		}
		catch (TotalMessageException e) {
			// ESTA ES ALGUNA POSIBLE EXCEPTION NUESTRA
			log.error("Problema en mensaje: " + getClass().getName(), e);
			// esto es imposible por que yo estoy seguro que LONG-NAME si pertenece a SI01
			throw e;
		}
		catch (InstantiationException e) {
			log.error("Problema en mensaje 1: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
		catch (IllegalAccessException e) {
			log.error("Problema en mensaje 2: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
		catch (JMSException e) {
			log.info("EXTRAPOINTINFO - JMSException2: "+new Date());
			log.error("Problema en mensaje 3: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
		catch (IOException e) {
			log.error("Problema en mensaje 4: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
	}

	protected Long getLongField(String account, String fieldName, long cacheTimeOut) throws TotalMessageException {
		try {
			TPacket packet = getPacket(account, cacheTimeOut);
			StructMessageElement struct = (StructMessageElement) packet.getElementResponse();
			return (Long) struct.getElement(fieldName).getValue();
		}
		catch (TotalMessageException e) {
			// ESTA ES ALGUNA POSIBLE EXCEPTION NUESTRA
			log.error("Problema en mensaje: " + getClass().getName(), e);
			// esto es imposible por que yo estoy seguro que LONG-NAME si pertenece a SI01
			throw e;
		}
		catch (InstantiationException e) {
			log.error("Problema en mensaje 1: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
		catch (IllegalAccessException e) {
			log.error("Problema en mensaje 2: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
		catch (JMSException e) {
			log.info("EXTRAPOINTINFO - JMSException3: "+new Date());
			log.error("Problema en mensaje 3: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
		catch (IOException e) {
			log.error("Problema en mensaje 4: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
	}

	protected Double getDoubleField(String account, String fieldName, long cacheTimeOut) throws TotalMessageException {
		try {
			TPacket packet = getPacket(account, cacheTimeOut);
			StructMessageElement struct = (StructMessageElement) packet.getElementResponse();
			return new Double(struct.getElement(fieldName).getValue().toString());
		}
		catch (TotalMessageException e) {
			// ESTA ES ALGUNA POSIBLE EXCEPTION NUESTRA
			log.error("Problema en mensaje: " + getClass().getName(), e);
			// esto es imposible por que yo estoy seguro que LONG-NAME si pertenece a SI01
			throw e;
		}
		catch (InstantiationException e) {
			log.error("Problema en mensaje 1: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
		catch (IllegalAccessException e) {
			log.error("Problema en mensaje 2: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
		catch (JMSException e) {
			log.info("EXTRAPOINTINFO - JMSException4: "+new Date());
			log.error("Problema en mensaje 3: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
		catch (IOException e) {
			log.error("Problema en mensaje 4: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
	}

	protected StructMessageElement getStructField(String account, String fieldName, long cacheTimeOut) throws TotalMessageException {
		try {
			TPacket packet = getPacket(account, cacheTimeOut);
			StructMessageElement struct = (StructMessageElement) packet.getElementResponse();
			return (StructMessageElement) struct.getElement(fieldName);
		}
		catch (TotalMessageException e) {
			// ESTA ES ALGUNA POSIBLE EXCEPTION NUESTRA
			log.error("Problema en mensaje: " + getClass().getName(), e);
			// esto es imposible por que yo estoy seguro que LONG-NAME si pertenece a SI01
			throw e;
		}
		catch (InstantiationException e) {
			log.error("Problema en mensaje 1: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
		catch (IllegalAccessException e) {
			log.error("Problema en mensaje 2: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
		catch (JMSException e) {
			log.info("EXTRAPOINTINFO - JMSException5: "+new Date());
			log.error("Problema en mensaje 3: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
		catch (IOException e) {
			log.error("Problema en mensaje 4: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
	}

	protected String[] getArrStringField(String account, String arrayName, String fieldName, long cacheTimeOut) throws TotalMessageException {
		TPacket packet = null;
		StructMessageElement struct = null;
		StructMessageElement structTemp = null;
		ArrayMessageElement array = null;
		String[] element = null;
		try {
			packet = getPacket(account, cacheTimeOut);
			struct = (StructMessageElement) packet.getElementResponse();
			array = (ArrayMessageElement) struct.getElement(arrayName);
			log.info("" + arrayName+" - "+fieldName + ":"+ array.getSize());
			
			if (array.getSize() > 0) {
				element = new String[array.getSize()];
				for (int i = 0; i < element.length; i++) {
					structTemp = (StructMessageElement) array.get(i);
					MessageElement elem = structTemp.getElement(fieldName);
					if (elem != null) {
						element[i] = String.valueOf(((FieldMessageElement) elem).getValue());
					}
				}
			}
			return element;
		}
		catch (ElementNotFoundException enfe) {
			if ((packet != null) && (struct != null) && (struct.getPacketID().equalsIgnoreCase("E900"))) {
				//return String.valueOf(struct.getElement("message").getValue());   	
				throw new TotalMessageException(
					String.valueOf(struct.getElement("code").getValue()) + " " + String.valueOf(struct.getElement("message").getValue()));
			}
			else {
				log.debug("Elemento " + fieldName + " no encontrado");
				throw enfe;
			}
		}
		catch (TotalMessageException e) {
			// ESTA ES ALGUNA POSIBLE EXCEPTION NUESTRA
			log.error("Problema en mensaje: " + getClass().getName(), e);
			// esto es imposible por que yo estoy seguro que LONG-NAME si pertenece a SI01
			throw e;
		}
		catch (InstantiationException e) {
			log.error("Problema en mensaje 1: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
		catch (IllegalAccessException e) {
			log.error("Problema en mensaje 2: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
		catch (JMSException e) {
			log.info("EXTRAPOINTINFO - JMSException6: "+new Date());
			log.error("Problema en mensaje 3: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
		catch (IOException e) {
			log.error("Problema en mensaje 4: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
	}

	public Element getMessageAsXML(Document doc, String account, long cacheTimeOut) throws TotalMessageException {
		try {
			TPacket packet = getPacket(account, cacheTimeOut);
			StructMessageElement struct = (StructMessageElement) packet.getElementResponse();
			return struct.asElement(doc);
		}
		catch (TotalMessageException e) {
			// ESTA ES ALGUNA POSIBLE EXCEPTION NUESTRA
			log.error("Problema en mensaje: " + getClass().getName(), e);
			// esto es imposible por que yo estoy seguro que LONG-NAME si pertenece a SI01
			throw e;
		}
		catch (InstantiationException e) {
			log.error("Problema en mensaje 1: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
		catch (IllegalAccessException e) {
			log.error("Problema en mensaje 2: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
		catch (JMSException e) {
			log.info("EXTRAPOINTINFO - JMSException7: "+new Date());
			log.error("Problema en mensaje 3: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
		catch (IOException e) {
			log.error("Problema en mensaje 4: " + getClass().getName(), e);
			throw new TotalMessageException(ResourceBundle.getBundle(Msg.USR_MSG).getString(Msg.TSYS_PROBLEM));
		}
	}

	public String getArr2StringField(String account, String segment, String field, int timeCache) throws TotalMessageException {
		try {
			StringBuffer buff = new StringBuffer();
			String[] arr = getArrStringField(account, segment, field, timeCache); //24 * 60 * 60);
			if (arr != null) {
				for (int i = 0; i < arr.length; i++) {
					if (i > 0) {
						buff.append(',');
					}
					buff.append(arr[i]);
				}
			}
			return buff.toString();
		}
		catch (TotalMessageException e) {
			log.error(e);
			throw e;
		}
	}

	public static String getCHANNEL() {
		return CHANNEL;
	}

	public static int getPORT() {
		return PORT;
	}

	public static String getQ_PREGUNTA() {
		return Q_PREGUNTA;
	}

	public static String getQ_RESPUESTA() {
		return Q_RESPUESTA;
	}

	public static String getQM_HOST() {
		return QM_HOST;
	}

	public static String getQM_NAME() {
		return QM_NAME;
	}

	public static void setCHANNEL(String string) {
		if (string == null || string.trim().length() == 0)
			string = CHANNEL;
		log.info("setting CHANNEL=" + string);
		CHANNEL = string;
	}

	private static int convertToIntWithDefault(String string, int defaultValue) {
		if (string == null || string.trim().length() == 0)
			string = "" + defaultValue;
		int val = 0;
		try {
			val = Integer.parseInt(string);
		}
		catch (NumberFormatException nfe) {
			log.error("[" + string + "] no es numerico valido");
			val = defaultValue;
		}
		return val;
	}

	public static void setPORT(String string) {
		PORT = convertToIntWithDefault(string, PORT);
		//log.info("setting PORT=" + PORT);
	}

	public static void setQ_PREGUNTA(String string) {
		//log.info("setting Q_PREGUNTA=" + string);
		Q_PREGUNTA = string;
	}

	public static void setQ_RESPUESTA(String string) {
		//log.info("setting Q_RESPUESTA=" + string);
		Q_RESPUESTA = string;
	}

	public static void setQM_HOST(String string) {
		//log.info("setting QM_HOST=" + string);
		QM_HOST = string;
	}

	public static void setQM_NAME(String string) {
		//log.info("setting QM_NAME=" + string);
		QM_NAME = string;
	}

	public static int getCACHE_SIZE() {
		return CACHE_SIZE;
	}

	public static void setCACHE_SIZE(String string) {
		CACHE_SIZE = convertToIntWithDefault(string, CACHE_SIZE);
		//log.info("setting CACHE_SIZE=" + CACHE_SIZE);
	}

	public static int getRECEIVER_MAX_WAIT() {
		return RECEIVER_MAX_WAIT;
	}

	public static int getSENDER_TIME_TO_LIVE() {
		return SENDER_TIME_TO_LIVE;
	}

	public static void setRECEIVER_MAX_WAIT(String string) {
		RECEIVER_MAX_WAIT = convertToIntWithDefault(string, RECEIVER_MAX_WAIT);
		//log.info("setting RECEIVER_MAX_WAIT=" + RECEIVER_MAX_WAIT);
	}

	public static void setSENDER_TIME_TO_LIVE(String string) {
		SENDER_TIME_TO_LIVE = convertToIntWithDefault(string, SENDER_TIME_TO_LIVE);
		//log.info("setting SENDER_TIME_TO_LIVE=" + SENDER_TIME_TO_LIVE);
	}

	protected void showClass() {
		log.info(getClass().getName());
	}

	protected String breakNumber(String number, boolean cents) {
		if (number.indexOf('.') < 0)
			number = number + ".00";
		int idx = number.indexOf('.');
		if (cents) {
			return number.substring(idx + 1);
		}
		else {
			return number.substring(0, idx);
		}
	}

//public static void main(String[] args) {
//	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh:mm:ss");
//	
//	System.out.println(dateFormat.format(new Date()));
//}
}
