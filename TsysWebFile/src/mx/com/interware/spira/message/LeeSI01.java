package mx.com.interware.spira.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import mx.com.interware.xml.XMLString;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;

import com.ibm.mq.jms.JMSC;
import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnectionFactory;

public class LeeSI01 {

	private static Logger log = Logger.getLogger(LeeSI01.class);

	public static final String QM_HOST = "172.16.60.183";
//	public static final String QM_HOST = "172.16.18.138";
	public static final String QM_NAME = "SPIRAPROD";
	public static final String Q_PREGUNTA = "PREGUNTA";

	public static final String Q_RESPUESTA = "RESPUESTA"; //"5988RESPUESTA";

	public static final String CHANNEL = "JAVA.CHANNEL";
	public static final int PORT = 1414;

	private static byte[] sendMessage(byte[] msgbytes) throws Exception {
		Queue preguntaQueue = null;
		Queue respuestaQueue = null;
		QueueSession session = null;
		QueueConnection connection = null;
		QueueConnectionFactory factory = null;
		QueueSender queueSender = null;
		QueueReceiver queueReceiver = null;

		try {
			factory = new MQQueueConnectionFactory();

			((MQQueueConnectionFactory) factory).setHostName(QM_HOST);
			((MQQueueConnectionFactory) factory).setChannel(CHANNEL);
			((MQQueueConnectionFactory) factory).setPort(PORT);
			((MQQueueConnectionFactory) factory).setTransportType(JMSC.MQJMS_CLIENT_NONJMS_MQ);

			connection = factory.createQueueConnection();

			log.info("Starting the Connection");
			connection.start();

			// We now create a QueueSession from the connection. Here we
			// specify that it shouldn't be transacted, and that it should
			// automatically acknowledge received messages
			log.info("Creating a Session");
			boolean transacted = true;
			session = connection.createQueueSession(transacted, Session.CLIENT_ACKNOWLEDGE);
			//Session.AUTO_ACKNOWLEDGE);

			// Get the Queue (the specification of where to send our message)
			preguntaQueue = session.createQueue("queue://" + QM_NAME + "/" + Q_PREGUNTA + "?persistence=1");
			((MQQueue) preguntaQueue).setTargetClient(JMSC.MQJMS_CLIENT_NONJMS_MQ);

			// The session is used to create messages, so create an empty
			// TextMessage and fill it with some data
			log.info("Creating a TextMessage");
			TextMessage outMessage = session.createTextMessage();

			respuestaQueue = session.createQueue("queue://" + QM_NAME + "/" + Q_RESPUESTA);
			((MQQueue) respuestaQueue).setTargetClient(JMSC.MQJMS_CLIENT_NONJMS_MQ);

			outMessage.setJMSReplyTo(respuestaQueue);
			//outMessage.setStringProperty("JMS_IBM_Format","MQSTR");

			log.info("Adding Text");
			outMessage.setText(new String(msgbytes));
			outMessage.setJMSExpiration(6000000);

			//      outMessage.setStringProperty("MQFormat","MQSTR");
			log.info("Creating a QueueSender");
			queueSender = session.createSender(preguntaQueue);

			// Ask the QueueSender to send the message we have created
			log.info("Sending the message to " + preguntaQueue.getQueueName());
			queueSender.send(outMessage);
			session.commit();
			// Closing QueueSender
			log.info("Closing QueueSender");
			queueSender.setTimeToLive(60000);
while (1==1) {
			// Create a QueueReceiver in the same way
			log.info("Creating a QueueReceiver");
			queueReceiver = session.createReceiver(respuestaQueue);//, "JMSCorrelationID='" + outMessage.getJMSCorrelationID() + "'");
			// Now use the QueueReceiver to retrieve the message, blocking
			// for a maximum of 1000ms. The receive call returns when the
			// message arrives, or after 1000ms, whichever is sooner

			log.info("Reading the message back again");
			Message inMessage = queueReceiver.receive(30000);
			session.commit();
			// Check to see if the receive call has actually returned a
			// message. If it hasn't, report this and throw an exception...
			if (inMessage == null) {
				log.info("The attempt to read the message back again " + "failed, apparently because it wasn't there");
//				throw new JMSException("Failed to get message back again");
        continue;
			}

			// ...otherwise display the message
			log.info("\n" + "Got message" + ": " + inMessage);

			// Check that the message received (a) is of the correct type,
			// and (b) contains the same text as the one sent, reporting the
			// result of these two checks
			if (inMessage instanceof TextMessage) {
				log.info("Reply message was a TextMessage");
				// Extract the message content with getText()
				String replyString = ((TextMessage) inMessage).getText();

				// Test its equality with the message text sent
				log.info("REPLY[" + replyString.length() + "]\n" + replyString);
//				return ((TextMessage) inMessage).getText().getBytes();
			}
			else {
				log.info("Reply message was not a TextMessage");
				BytesMessage bm = (BytesMessage) inMessage;
				byte[] buff = new byte[8192];
				int n = bm.readBytes(buff);
				byte[] result = new byte[n];
				for (int i = 0; i < n; i++) {
					result[i] = buff[i];
				}
				String replyString = new String(result);
				log.info("REPLY[" + replyString.length() + "]\n" + replyString);
//				return result;
				//throw new JMSException("Retrieved the wrong type of message");
			}
}
			// Remember to call the close() method on all of the objects
			// used, to ensure proper clean-up of resources

			// Closing QueueReceiver

		}
		catch (JMSException je) {
			log.error(je);
			// check for a linked exception that provides more detail
			Exception le = je.getLinkedException();
			if (le != null)
				log.info("linked exception: " + le);
		  throw je;
		}
		catch (Exception e) {
			// This catches any exception thrown in the main body of
			// the program, displaying it on screen
			log.error("Caught exception: " + e);
			throw e;
		}
		finally {
			// A finally block is a good place to ensure that we don't forget
			// to close the most important JMS objects
			try {
				if (queueSender != null) {
					queueSender.close();
				}
				if (queueReceiver != null) {
					queueReceiver.close();
				}
				if (session != null) {
					log.info("closing session");
					session.close();
				}
				if (connection != null) {
					log.info("closing connection");
					connection.close();
				}
			}
			catch (JMSException je) {
				log.error("failed with " + je);
			}
			log.info("finished");
		}
	}

	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		File confFile = new File("XML/packet.config.xml");
		XMLString xml = new XMLString(confFile);
		TPacketImpl.init((Element) xml.getDocument().getFirstChild());
		FileInputStream fis = new FileInputStream(new File("XML/SI01_01_REQ.txt"));
		

		log.info("---------------------------------------------------------- INICIO ==> HEADER REQUEST");
		TPacket header = new TPacketImpl();
		header.readRequestFrom(fis);
		header.writeRequestTo(System.out);
		StructMessageElement struct = (StructMessageElement) header.getElementRequest();
		log.debug("struct:" + struct);
		log.debug(struct.getElementNames());
		log.info("---------------------------------------------------------- FINAL ==> HEADER REQUEST");
		
		log.info("---------------------------------------------------------- INICIO ==> MENSAJE REQUEST");
		TPacket packet = new TPacketImpl();
		packet.readRequestFrom(fis);
		packet.writeRequestTo(System.out);
		log.info("---------------------------------------------------------- FINAL ==> MENSAJE REQUEST");
		log.debug("struct:" + struct);
		log.debug(struct.getElementNames());
		log.info("---------------------------------------------------------- INICIO ==> TEND REQUEST");
		TPacket end = new TPacketImpl();
		end.readRequestFrom(fis);
		end.writeRequestTo(System.out);
		struct = (StructMessageElement) end.getElementRequest();
		log.debug("struct:" + struct);
		log.debug(struct.getElementNames());
		log.info("---------------------------------------------------------- FINAL ==> TEND REQUEST");
		//linea de abajo dejar comentada
		//header.getElementRequest().getElement("accountID").setValue("1234567891234567");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// Envio del mensaje hacia el main frame		
		header.writeRequestTo(baos);
		packet.writeRequestTo(baos);
		end.writeRequestTo(baos);
		baos.close();		
		log.info("ENVIANDO MENSAJE:\n" + baos.toString());
		
		byte[] result = sendMessage(baos.toByteArray());
		
		//Solo esta de prueba
		//FileInputStream file = new FileInputStream("XML/SI04_01_RES.txt");
		
		// Regreso del mensaje del mainframe
		ByteArrayInputStream bais = new ByteArrayInputStream(result);
		
		log.info("---------------------------------------------------------- INICIO ==> HEADER RESPONSE");
		header.readResponseFrom(bais);
		header.writeResponseTo(System.out);
		//log.info("El status es:"+header.getElementResponse().getElement("passFail").getValue());
		log.info("---------------------------------------------------------- INICIO ==> HEADER RESPONSE");
		log.info("---------------------------------------------------------- INICIO ==> MENSAJE RESPONSE");
		packet.readResponseFrom(bais);
		packet.writeResponseTo(System.out);
		log.info("---------------------------------------------------------- INICIO ==> MENSAJE RESPONSE");
		log.info("---------------------------------------------------------- INICIO ==> TEND RESPONSE");
		//end.readResponseFrom(file);
		end.readResponseFrom(bais);
		end.writeResponseTo(System.out);
		log.info("---------------------------------------------------------- INICIO ==> TEND RESPONSE");
		log.info("\n\nVersion:"+packet.getElementResponse().getElement("version").getValue());
	}
}
