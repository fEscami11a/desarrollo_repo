package mx.com.interware.spira.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;
import mx.com.interware.spira.message.util.Formatter;
import mx.com.interware.spira.web.Msg;
import mx.com.interware.util.Throwable2String;
import mx.com.interware.xml.XMLConfig;
import mx.com.interware.xml.XMLString;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class TPacketImpl implements TPacket {

	private static Logger log = Logger.getLogger(TPacketImpl.class);
	public static String BANK_ID = "5988";
	private static XMLConfig config;
	private static Map packetConfigMapRequests;
	private static Map packetConfigMapResponses;
	StructMessageElement structElementRequest, structElementResponse;

	public TPacketImpl() {
		if (packetConfigMapRequests == null
			|| packetConfigMapResponses == null) {
			synchronized (TPacketImpl.class) {
				if (packetConfigMapRequests == null
					|| packetConfigMapResponses == null) {
					//log.error("problemas muy importantes de configuracion, no esta inicializado el sistema con el xml de mensajes TSYS ej:packet.config.xml, inicializando con default");
					File defaultXML = new File(Msg.DEFAULT_CONFIG_FILE);
					log.info(
						"cargando configuracion de:"
							+ defaultXML.getAbsolutePath());
					try {
						XMLString xml = new XMLString(defaultXML);
						init((Element) xml.getDocument().getFirstChild());
					} catch (Exception e) {
						log.error(
							"problemas al tratar de usar configuracion default",
							e);
						throw new RuntimeException(
							"problemas al tratar de usar configuracion default \n"
								+ Throwable2String.throwable2String(e));
					}
				}
			}
		}
	}

	private static void initStruct(String packetID, Element childElement) {
		if (childElement
			.getAttribute("name")
			.equalsIgnoreCase("REQUEST-DATA")) {
			packetConfigMapRequests.put(packetID, childElement);
		} else if (
			childElement.getAttribute("name").equalsIgnoreCase(
				"RESPONSE-DATA")) {
			packetConfigMapResponses.put(packetID, childElement);
		} else {
			throw new RuntimeException("Formato invalido en el xml, el nombre de los structs dentro de un packet debe ser REQUEST-DATA o RESPONSE-DATA");
		}
	}

	public static synchronized void init(Element confElem)
		throws
			TransformerConfigurationException,
			ParserConfigurationException,
			TransformerException,
			IOException,
			SAXException {
		//log.debug("Entrando al init de TPacketImpl");
		config = new XMLConfig();
		config.initWithRoot(confElem);
		packetConfigMapRequests = new HashMap();
		packetConfigMapResponses = new HashMap();
		for (Iterator packetIter =
			config.getChildElementIterator("packets", "packet");
			packetIter.hasNext();
			) {
			Element packetElem = (Element) packetIter.next();
			Node child = packetElem.getFirstChild();
			while (child != null) {
				if (child instanceof Element) {
					Element childElement = (Element) child;
					if (child.getNodeName().equals("struct")) {
						//log.debug("Adding info for:"+packetElem.getAttribute("id"));
						initStruct(packetElem.getAttribute("id"), childElement);
					} else if (child.getNodeName().equals("include")) {
						File file =
							new File(childElement.getAttribute("location"));
						XMLString xml = new XMLString(file);
						//log.debug("Adding info for(include):"+packetElem.getAttribute("id"));
						initStruct(
							packetElem.getAttribute("id"),
							(Element) xml.getDocument().getFirstChild());
					}
				}
				child = child.getNextSibling();
			}
		}
		//log.info("Saliendo al init de TPacketImpl");
	}

	public CompoundMessageElement getElementRequest() {
		return structElementRequest;
	}

	public CompoundMessageElement getElementResponse() {
		return structElementResponse;
	}

	public void readRequestFrom(InputStream in)
		throws
			IOException,
			InvalidFormatException,
			InstantiationException,
			IllegalAccessException {
		structElementRequest = readStructFrom(packetConfigMapRequests, in);
	}

	public void readResponseFrom(InputStream in)
		throws
			IOException,
			InvalidFormatException,
			InstantiationException,
			IllegalAccessException {
		structElementResponse = readStructFrom(packetConfigMapResponses, in);
	}

	private StructMessageElement readStructFrom(Map map, InputStream in)
		throws
			IOException,
			InvalidFormatException,
			InstantiationException,
			IllegalAccessException {
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		byte[] tmp = new byte[1024];
		in.read(tmp, 0, 8);
		//log.debug("tmp:[" + new String(tmp, 0, 8) + "]");
		int len = Integer.parseInt(new String(tmp, 0, 8));
		buf.write(tmp, 0, 8);
		in.read(tmp, 0, 4);
		String packetID = new String(tmp, 0, 4);
		buf.write(tmp, 0, 4);
		/*if (packetID.equalsIgnoreCase("FLDC")){
			len = len-4;
		}
		else {
			len = len - 12;
		}
		*/
		len = len - 12;
		while (len > 0) {
			int max = len > 1024 ? 1024 : len;
			int n = in.read(tmp, 0, max);
			buf.write(tmp, 0, n);
			len -= n;
		}
		buf.close();
		//log.info("readStructFrom(Map, InputStream) 1: "+Thread.currentThread()+" :: "+new String(buf.toByteArray()));

		StructMessageElement structElement = new StructMessageElement();
		if (map == null) {
			log.error("map is null " + (map == null));
		}
		//log.debug("el map para "+packetID+" en null:"+(map.get(packetID)==null));
		structElement.init((Element) map.get(packetID));
		structElement.setPacketID(packetID);
		structElement.readFrom(new ByteArrayInputStream(buf.toByteArray()));
		//		ByteArrayOutputStream baos = new ByteArrayOutputStream(); // ELIMINAR		
		//		structElement.writeTo(baos); // ELIMINAR
		//		log.info("readStructFrom(Map, InputStream) 2: "+Thread.currentThread()+" :: "+baos.toString()); // ELIMINAR En este paso al 2do., truena!!!
		return structElement;
	}

	public void writeRequestTo(OutputStream out) throws IOException {
		structElementRequest.writeTo(out);
	}

	public void writeResponseTo(OutputStream out) throws IOException {
		structElementResponse.writeTo(out);
	}

	public void setBankId(String bankID) throws IOException {
		BANK_ID = bankID;
	}

	public void convertToDefaultHeader()
		throws
			InvalidFormatException,
			IOException,
			InstantiationException,
			IllegalAccessException {
		String defaultHeaderInfo = null;
		defaultHeaderInfo =
			"00000250TSYS"
				+ BANK_ID
				+ "                                    CAT1                    00000000N00000000000000                                                                       0001                                                            0000            ";
		log.debug(
			"convertToDefaultHeader(): "
				+ Thread.currentThread()
				+ " :: "
				+ defaultHeaderInfo);
		//"00000250TSYS5988                                    CAT1                    00000000N00000000000000                                                                       0001                                                            0000            ";		
		ByteArrayInputStream bis =
			new ByteArrayInputStream(defaultHeaderInfo.getBytes());
		readRequestFrom(bis);
	}

	public void convertToDefaultMaintHeader(String vars, String resp)
		throws
			InvalidFormatException,
			IOException,
			InstantiationException,
			IllegalAccessException {
		String defaultHeaderInfo = null;
		defaultHeaderInfo =
			"00000291BOSS                          "
				+ vars
				+ "          "
				+ resp
				+ "                                                                             RESPUESTA                                       SPIRAPROD                                                                                                    ";
		ByteArrayInputStream bis =
			new ByteArrayInputStream(defaultHeaderInfo.getBytes());
		readRequestFrom(bis);
	}

	//	public void convertToDefaultMaintHeader(String vars, String resp,String approvingOfficer) throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException {
	//		String defaultHeaderInfo =
	//		"00000291BOSS                          "+vars+"     "+approvingOfficer+" "+resp+"                                                                             RESPUESTA                                       SPIRAPROD                                                                                                    ";             
	//		ByteArrayInputStream bis = new ByteArrayInputStream(defaultHeaderInfo.getBytes());
	//		readRequestFrom(bis);
	//	}

	public void convertToDefaultTEnd()
		throws
			InvalidFormatException,
			IOException,
			InstantiationException,
			IllegalAccessException {
		String defaultEndInfo = null;
		defaultEndInfo = "00000012TEND";
		log.debug(
			"convertToDefaultTEnd(): "
				+ Thread.currentThread()
				+ " :: "
				+ defaultEndInfo);
		ByteArrayInputStream bis =
			new ByteArrayInputStream(defaultEndInfo.getBytes());
		readRequestFrom(bis);
	}

	public void convertToDefaultMEnd()
		throws
			InvalidFormatException,
			IOException,
			InstantiationException,
			IllegalAccessException {
		String defaultEndInfo = null;
		defaultEndInfo = "00000012MEND";
		log.debug(
			"convertToDefaultMEnd(): "
				+ Thread.currentThread()
				+ " :: "
				+ defaultEndInfo);
		ByteArrayInputStream bis =
			new ByteArrayInputStream(defaultEndInfo.getBytes());
		readRequestFrom(bis);
	}

	public void convertToDefaultMsg(String msg)
		throws
			InvalidFormatException,
			IOException,
			InstantiationException,
			IllegalAccessException {
		String defaultMsgInfo = null;
		defaultMsgInfo = "00000022" + msg + "1.00      ";
		log.debug(
			"convertToDefaultMsg(String): "
				+ Thread.currentThread()
				+ " :: "
				+ defaultMsgInfo);
		//int,msg,extra F
		ByteArrayInputStream bis =
			new ByteArrayInputStream(defaultMsgInfo.getBytes());
		readRequestFrom(bis);
	}

	// colocarle 19 en lengh
	public void convertToDefaultMsg(String msg, String extra)
		throws
			InvalidFormatException,
			IOException,
			InstantiationException,
			IllegalAccessException {
		String len = null;
		String defaultMsgInfo = null;
		len = "" + (22 + extra.length());
		defaultMsgInfo =
			"00000000".substring(0, 8 - len.length())
				+ len
				+ msg
				+ "     ".substring(0, 4 - msg.length())
				+ "1.00      "
				+ extra;
		log.debug(
			"convertToDefaultMsg(String, String): "
				+ Thread.currentThread()
				+ " :: "
				+ defaultMsgInfo);
		//int,msg,extra F
		ByteArrayInputStream bis =
			new ByteArrayInputStream(defaultMsgInfo.getBytes());
		readRequestFrom(bis);
	}

	public void convertToDefaultFLDC()
		throws
			InvalidFormatException,
			IOException,
			InstantiationException,
			IllegalAccessException {
		String msg = null;
		msg = "00000059FLDC          00000000000001                       ";
		ByteArrayInputStream bis = new ByteArrayInputStream(msg.getBytes());
		readRequestFrom(bis);
		//		String str=formatter.asString(value);
		//		String msg=""+(57+str.length()*2);
		//		String fld=""+field;
		//		fld="000000".substring(0,6-fld.length())+fld;
		//		msg="00000000".substring(0,8-msg.length())+msg+"FLDC          "+fld+"0000"+"0000".substring(0,4-str.length())+str.length()+"                     "+str+XFormatter.BLANCOS.substring(0,str.length());		
	}

	public void convertToFLDC(String field, Object value, Formatter formatter)
		throws
			InvalidFormatException,
			IOException,
			InstantiationException,
			IllegalAccessException {
		convertToDefaultFLDC();
		String val =
			formatter != null ? formatter.asString(value) : value.toString();
		getElementRequest().getElement("length").setValue(
			new Long(57 + val.length()));
		getElementRequest().getElement("fieldIdentifier").setValue(field);
		getElementRequest().getElement("newFieldLength").setValue(
			new Long(val.length()));
		getElementRequest().getElement("newFieldValue").setValue(val);
	}

	public void convertToFLDCCurrentValue(
		String field,
		Object currentValue,
		Object newValue,
		Formatter formatter)
		throws
			InvalidFormatException,
			IOException,
			InstantiationException,
			IllegalAccessException {
		convertToDefaultFLDC();
		String currval =
			formatter != null
				? formatter.asString(currentValue)
				: currentValue.toString();
		String newval =
			formatter != null
				? formatter.asString(newValue)
				: newValue.toString();
		getElementRequest().getElement("length").setValue(
			new Long(57 + currval.length() + newval.length()));
		getElementRequest().getElement("fieldIdentifier").setValue(field);
		getElementRequest().getElement("newFieldLength").setValue(
			new Long(newval.length()));
		getElementRequest().getElement("newFieldValue").setValue(newval);
		getElementRequest().getElement("currentFieldLength").setValue(
			new Long(currval.length()));
		getElementRequest().getElement("currentFieldValue").setValue(currval);
	}

	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		int k = Integer.parseInt("00000250");
		log.debug("" + k);
		File confFile = new File("WebContent/WEB-INF/XML/packet.config.xml");
		XMLString xml = new XMLString(confFile);
		TPacketImpl.init((Element) xml.getDocument().getFirstChild());
		String info =
			"00000250TSYS5988                                    CAT1                    00000000N00000000000000                                                                       0001                                                            0000            ";
		//00000250TSYS5988                                    CAT1                    00000000N00021130000000                                                                       0001                                                            0000            
		ByteArrayInputStream in = new ByteArrayInputStream(info.getBytes());
		TPacketImpl tp = new TPacketImpl();
		tp.readRequestFrom(in);
	}

}
