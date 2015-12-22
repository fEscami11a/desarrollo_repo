package mx.com.interware.spira.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import org.apache.log4j.BasicConfigurator;
import org.w3c.dom.Element;

import mx.com.interware.xml.XMLConfig;
import mx.com.interware.xml.XMLString;
import org.apache.log4j.Logger;

public class Main {
	private static Logger log = Logger.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();
		XMLString xml=new XMLString(new File("XML/test.xml"));
		XMLConfig conf=new XMLConfig();
		conf.initWithRoot((Element)xml.getDocument().getFirstChild());
		CompoundMessageElement message = new StructMessageElement();
		message.init(conf.getElement("message/struct"));
		
		log.info(message.getElementNames());
		
		log.info(message.getElement("CH-INT-TERMS-STATUS"));
		
		message.set("ACCOUNT-CYCLE",new Integer(3));
		message.set("TRANSFER-STATUS","G");
		message.set("TRANSFER-DATE","AB");
	
		ArrayMessageElement transSegmeng=(ArrayMessageElement)message.getElement("TRANS-SEGMENT");
		transSegmeng.fixSize();
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		message.writeTo(baos);
		log.info("["+baos.toString()+"]");
		transSegmeng.get(0).getElement("BILL-FEDX-CARD-DE").setValue("I");
		baos=new ByteArrayOutputStream();
		message.writeTo(baos);
		log.info("["+baos.toString()+"]");
		transSegmeng.get(1).getElement("BILL-FEDX-CARD-DE").setValue("J");
		baos=new ByteArrayOutputStream();
		message.writeTo(baos);
		log.info("["+baos.toString()+"]");
		transSegmeng.get(2).getElement("BILL-FEDX-CARD-DE").setValue("K");
		baos=new ByteArrayOutputStream();
		message.writeTo(baos);
		log.info("["+baos.toString()+"]");
		
		
		
		((CompoundMessageElement)message.getElement("CH-INT-TERMS-STATUS")).set("RATE-CODE-CH-COMPL","X");
		((CompoundMessageElement)message.getElement("CH-INT-TERMS-STATUS")).set("CAN-CHG-OVRLMT-FEE","Y");
		
		
		//TRANS-SEGMENT
		
		
		
		CompoundMessageElement message2=new StructMessageElement();
		message2.init(conf.getElement("message/struct"));
		
		message2.readFrom(new ByteArrayInputStream(baos.toString().getBytes()));
		log.info(message2.getElement("ACCOUNT-CYCLE"));
		
		MessageElement me=message2.getElement("ACCOUNT-CYCLE");		
		log.info(me.getValue().getClass()+"\t"+((MessageElement)message2.getElement("ACCOUNT-CYCLE")).getValue());
		log.info(((MessageElement)message2.getElement("TRANSFER-STATUS")).getValue().getClass()+"\t"+((MessageElement)message2.getElement("TRANSFER-STATUS")).getValue());
		log.info(((MessageElement)message2.getElement("TRANSFER-DATE")).getValue().getClass()+"\t"+((MessageElement)message2.getElement("TRANSFER-DATE")).getValue());
		
		log.info(((CompoundMessageElement)message2.getElement("CH-INT-TERMS-STATUS")).getElement("RATE-CODE-CH-COMPL").getValue().getClass()+"\t"+((CompoundMessageElement)message2.getElement("CH-INT-TERMS-STATUS")).getElement("RATE-CODE-CH-COMPL").getValue());
		log.info(((CompoundMessageElement)message2.getElement("CH-INT-TERMS-STATUS")).getElement("CAN-CHG-MBRSHIP-FE").getValue().getClass()+"\t"+((CompoundMessageElement)message2.getElement("CH-INT-TERMS-STATUS")).getElement("CAN-CHG-MBRSHIP-FE").getValue());
		log.info(((CompoundMessageElement)message2.getElement("CH-INT-TERMS-STATUS")).getElement("CAN-CHG-OVRLMT-FEE").getValue().getClass()+"\t"+((CompoundMessageElement)message2.getElement("CH-INT-TERMS-STATUS")).getElement("CAN-CHG-OVRLMT-FEE").getValue());

		ArrayMessageElement transSegmeng2=(ArrayMessageElement)message2.getElement("TRANS-SEGMENT");
		log.info(transSegmeng.get(0).getElement("BILL-FEDX-CARD-DE").getValue());
		log.info(transSegmeng.get(1).getElement("BILL-FEDX-CARD-DE").getValue());
		log.info(transSegmeng.get(2).getElement("BILL-FEDX-CARD-DE").getValue());
		baos=new ByteArrayOutputStream();
		message.writeTo(baos);
		log.info("["+baos.toString()+"]");

		
		baos=new ByteArrayOutputStream();
		message2.writeTo(baos);
		log.info("["+baos.toString()+"]");
		
	}
}
