
package mx.com.interware.spira.web.si01;

import mx.com.interware.spira.dto.SI01ResponseDTO;
import mx.com.interware.spira.message.exceptions.TotalMessageException;

public interface SI01 {
	public SI01ResponseDTO getSI01ResponseDTO(String account) throws TotalMessageException;
}