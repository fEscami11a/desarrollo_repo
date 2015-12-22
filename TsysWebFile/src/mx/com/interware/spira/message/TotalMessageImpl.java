package mx.com.interware.spira.message;

import org.apache.log4j.Logger;


public class TotalMessageImpl extends StructMessageElement implements TotalMessage {
	
	private static Logger log=Logger.getLogger(TotalMessageImpl.class);
	
//	public Integer getInteger(String name)  throws ElementNotFoundException {
//		Object result = getElement(name).getValue();
//		if (!(result instanceof Number)) {
//			throw new ClassCastException(result.getClass()+" no es un numero");
//		}
//		return new Integer(""+result);
//	}
//
//	public BigInteger getBigInteger(String name) throws ElementNotFoundException {
//		Object result = getElement(name).getValue();
//		if (!(result instanceof Number)) {
//			throw new ClassCastException(result.getClass()+" no es un numero");
//		}
//		return new BigInteger(""+result);
//	}
//
//
//	public StructMessageElement getStructMessageElement(String name) throws ElementNotFoundException {
//		Object result = getElement(name).getValue();
//		if (result==null) {
//			throw new ElementNotFoundException("Elemento:"+name+" no registrado en esta estructura");
//		}
//		if (!(result instanceof StructMessageElement)) {
//			throw new ClassCastException(result.getClass()+" no es un MessageElement");
//		}
//		return (StructMessageElement)result;
//	}
//
	/**
	 *
	 */
//	public Object getValue() {
//		throw new RuntimeException("Meto getValue() no existe para este tipo de objetos");
//	}
//
//
//	public void setValue(Object value) throws InvalidFormatException {
//		throw new RuntimeException("Meto setValue(Object value) no existe para este tipo de objetos");
//	}

}
