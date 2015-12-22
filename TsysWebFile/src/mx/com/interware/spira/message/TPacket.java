package mx.com.interware.spira.message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import mx.com.interware.spira.message.exceptions.InvalidFormatException;
import mx.com.interware.spira.message.util.Formatter;

public interface TPacket {

	CompoundMessageElement getElementRequest();
	CompoundMessageElement getElementResponse();
	
	void setBankId(String bankID) throws IOException;
	void convertToDefaultHeader() throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException;
	void convertToDefaultMaintHeader(String vars,String resp) throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException;
	void convertToDefaultTEnd() throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException;
	void convertToDefaultMEnd() throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException;
	void convertToDefaultMsg(String msg) throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException;
	void convertToDefaultMsg(String msg, String extra) throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException;
	void convertToDefaultFLDC() throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException;
	void convertToFLDC(String field,Object value,Formatter formatter) throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException;
	void convertToFLDCCurrentValue(String field,Object currentValue,Object newValue,Formatter formatter) throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException;
	void readRequestFrom(InputStream in) throws IOException, InvalidFormatException, InstantiationException, IllegalAccessException;
	void readResponseFrom(InputStream in) throws IOException, InvalidFormatException, InstantiationException, IllegalAccessException;
	void writeRequestTo(OutputStream out) throws IOException;
	void writeResponseTo(OutputStream out) throws IOException;

}
