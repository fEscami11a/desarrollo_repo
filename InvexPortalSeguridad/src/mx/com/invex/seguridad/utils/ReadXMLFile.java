package mx.com.invex.seguridad.utils;


public class ReadXMLFile {

//	public static Document readFile(String servicioURL) throws ParserConfigurationException, IOException, SAXException {
//		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//		URL url = new URL(servicioURL);
//		InputStream stream = url.openStream();
//		Document doc = dBuilder.parse(stream);
//		doc.getDocumentElement().normalize();
//		return doc;
//	}
//
//	public static boolean getValidaAutenticacion(String username) throws ParserConfigurationException, IOException, SAXException {
//		
//		boolean respuesta = false;
//		String servicio = MessageFormat.format("http://172.16.18.13:9080/TsysWAR/Product.jsp?Usuario={0}" , username);
//		if(servicio == null) 
//			return respuesta;
//
//    	Document doc = ReadXMLFile.readFile(servicio);
////		User user = new User();		
//		if(doc.getDocumentElement().getNodeName().equals("OUT")) {
//			NodeList nList = doc.getElementsByTagName("user");
//			if(nList.getLength() > 0) {
//				for (int temp = 0; temp < nList.getLength(); temp++) {
//					Node nNode = nList.item(temp);
//					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
//						Element eElement = (Element) nNode;
//						eElement.getElementsByTagName("status").item(0).getNodeValue();
//					}
//				}
//			}
//		}
//		
//        return respuesta;
//	}
//
//	
}
