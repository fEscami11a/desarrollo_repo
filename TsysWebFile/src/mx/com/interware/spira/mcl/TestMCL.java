
package mx.com.interware.spira.mcl;

import mx.com.interware.spira.message.exceptions.TotalMessageException;

import org.apache.log4j.Logger;

/**
 * @author vbaez
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class TestMCL {
	private static Logger log = Logger.getLogger(TestMCL.class);

	public static void main(String[] args) {
		MCL service = new MCLImpl();
		
		ActualizacionLimiteCreditoRequest actualizacionLimiteCreditoRequest = new ActualizacionLimiteCreditoRequest();

		actualizacionLimiteCreditoRequest.setNumeroCuenta("6046442100140273");
		actualizacionLimiteCreditoRequest.setNumeroOfficer("0605");
		actualizacionLimiteCreditoRequest.setResetDeclines("N");		
//		//actualizacionLimiteCreditoRequest.setLimiteCreditoActual(null); // null, "" o no setear
		actualizacionLimiteCreditoRequest.setLimiteCreditoNuevo("1");
		actualizacionLimiteCreditoRequest.setPorcentajeEfectivoActual(null); // null, "" o no setear	
		actualizacionLimiteCreditoRequest.setPorcentajeEfectivoNuevo("000"); // null, "" o no setear
		//actualizacionLimiteCreditoRequest.setDineroDisponibleActual(null); // null, "" o no setear
		//actualizacionLimiteCreditoRequest.setDineroDisponibleNuevo(null); // null, "" o no setear
		actualizacionLimiteCreditoRequest.setUsuario("LineaSpira");
		actualizacionLimiteCreditoRequest.setContrasenia("prodLS615#");
		
		String resp="";
		try {
			resp=service.updateMCL(actualizacionLimiteCreditoRequest);
			log.info(resp);
		} catch (TotalMessageException e) {
			log.error(e);
	}
	}
}