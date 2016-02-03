package mx.com.interware.spira.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mx.com.interware.spira.web.util.ConFactoryUsrCatalogos;
import mx.com.interware.spira.web.util.ConnectionFactory;
import mx.com.interware.spira.web.util.ConnectionFactoryPLealtad;
import mx.com.interware.spira.web.util.ConnectionFactoryUPTARJETAS;

import org.apache.log4j.Logger;

public class CommonDAO {
	
	private static Logger logger=Logger.getLogger(CommonDAO.class);

	private static final String queryBillAcc = "select NumCuentaFacturadora,idJerarquiaCuenta from ods_CuentaTarjeta where NumCuentaTarjeta=?";
	
	private static final String queryJerCta = "select idJerarquiaCuenta from ods_CuentaTarjeta where NumCuentaTarjeta=?"; // IAL (JVTYPE)

	private static final String queryBillingData = 
		"select cuenta.idpersona as r1, cuenta.numcuentatarjeta as r2, cuenta.idjerarquiacuenta as r3,"
			+" cuenta.IDSTATUSCUENTA as r4, persona.nombrecompleto as r5, persona.RFC as r6,"
			+" direccion.CALLEYNUMERO as r7, direccion.COLONIA as r8, direccion.CIUDAD as r9, direccion.CODIGOPOSTAL as r10"
			+" from ods_cuentatarjeta cuenta, ods_persona persona,ods_direccion direccion"
			+" where cuenta.idpersona = persona.idpersona and  cuenta.idpersona = direccion.idpersona"
			+" and cuenta.numcuentatarjeta = ?";
			
	private static final String queryAdditionalBillingData =
	"select cuenta.idpersona as r1, cuenta.numcuentatarjeta as r2, cuenta.idjerarquiacuenta as r3,"
		+" cuenta.IDSTATUSCUENTA as r4, persona.nombrecompleto as r5, persona.RFC as r6,"
		+" direccion.CALLEYNUMERO as r7, direccion.COLONIA as r8, direccion.CIUDAD as r9, direccion.CODIGOPOSTAL as r10"
		+" from ods_cuentatarjeta cuenta, ods_persona persona,ods_direccion direccion"
		+" where cuenta.idpersona = persona.idpersona and  cuenta.idpersona = direccion.idpersona"
		+" and cuenta.numcuentafacturadora = ? and cuenta.numcuentatarjeta <> ?";

	private static final String queryPhone = 
		"select numerolocal as r1 from ods_telefono where idpersona = ? and  idtipotelefono = ?";			
		

	public static String getBillCheckAcc(String account) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		account = account.trim();
		try {
			con = ConnectionFactory.getConnection();
			ps = con.prepareStatement(queryBillAcc);
			ps.setString(1, account);
			rs = ps.executeQuery();
			//ps.setString(1, account);
			String billCheckAcc = account;
			String idJerarquiaCuenta = "NA";
			
			if (rs.next()) {
				billCheckAcc = rs.getString(1);
				idJerarquiaCuenta= rs.getString(2);
			}

			if (billCheckAcc == null)
			{
				billCheckAcc = account;
				return billCheckAcc;                
			}
			if (billCheckAcc != null && !idJerarquiaCuenta.equals("B")){
				return billCheckAcc;
			}
			if ( billCheckAcc != null && idJerarquiaCuenta.equals("B")) {
			    billCheckAcc = account;
			    return billCheckAcc;
			}
					
			rs.close();
			ps.close();
			return billCheckAcc;
		}
		finally {
			if (con != null) {
				ConnectionFactory.closeConnection(con);
			}
		}
	}
	
	public static String getJerCuenta(String account, boolean isclosed) throws SQLException { // IAL (JVTYPE)
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		account = account.trim();
		try {
			con = ConnectionFactory.getPooledConnection(); // Se cambió el tipo de conexión
			ps = con.prepareStatement(queryJerCta);
			ps.setString(1, account);
			rs = ps.executeQuery();
			String idJerarquiaCuenta = "NA";
			
			if (rs.next()) {
				//billCheckAcc = rs.getString(1);
				idJerarquiaCuenta= rs.getString(1).trim();
			}
			
			// Equivalencias de los datos con la pantalla IAL (JVTYPE)
			rs.close();
			ps.close();
			return idJerarquiaCuenta;
		}
		finally {
			if (isclosed) // Se valida si se cierra la conexión
			{
				if (con != null) {
					ConnectionFactory.closePooledConnection(); 
				}
			}
		}
	}
	
	public static Map getBillCheckAccData(String account) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;		
		account = account.trim();
		try {
			con = ConnectionFactory.getConnection();
			ps = con.prepareStatement(queryBillingData);
			ps.setString(1, account);
			rs = ps.executeQuery();
			
			String idPersona = "";
			String numCuenta = "";
			String tipoCuenta = "";
			String clasificacion = "";
			String nombreCompleto = "";
			
			String telefonoCasa = "";
			String telefonoOficina = "";
			
			String rfc = "";
			String calle = "";
			String colonia = "";
			String municipioestado = "";
			String codigoPostal = "";
			
			if (rs.next()) {
				idPersona = rs.getString("r1").trim()!=null?rs.getString("r1").trim():"";
				numCuenta = rs.getString("r2").trim()!=null?rs.getString("r2").trim():"";
				tipoCuenta = rs.getString("r3").trim()!=null?rs.getString("r3").trim():"";
				clasificacion = rs.getString("r4").trim()!=null?rs.getString("r4").trim():"";
				nombreCompleto = rs.getString("r5").trim()!=null?rs.getString("r5").trim():"";
				rfc = rs.getString("r6").trim()!=null?rs.getString("r6").trim():"";
				calle = rs.getString("r7").trim()!=null?rs.getString("r7").trim():"";
				colonia = rs.getString("r8").trim()!=null?rs.getString("r8").trim():"";
				municipioestado = rs.getString("r9").trim()!=null?rs.getString("r9").trim():""; // DKDKFKFKF, DK
				codigoPostal = rs.getString("r10").trim()!=null?rs.getString("r10").trim():"";
				if (!idPersona.equals("")) {
					ps2 = con.prepareStatement(queryPhone);
					ps2.setString(1, idPersona);
					ps2.setInt(2, 1);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						telefonoCasa = rs2.getString("r1")!=null?rs2.getString("r1").trim():"";
					}
					ps2 = con.prepareStatement(queryPhone);
					ps2.setString(1, idPersona);
					ps2.setInt(2, 2);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						telefonoOficina = rs2.getString("r1")!=null?rs2.getString("r1").trim():"";
					}
					rs2.close();
					ps2.close();															
				}
			}
			else {
				idPersona = "NO DISPONIBLE";
				//numCuenta = "";
				numCuenta = account;
				tipoCuenta = "NO DISPONIBLE";
				clasificacion = "NO DISPONIBLE";
				nombreCompleto = "NO DISPONIBLE";
				telefonoCasa = "NO DISPONIBLE";
				telefonoOficina = "NO DISPONIBLE";
				rfc = "NO DISPONIBLE";
				calle = "NO DISPONIBLE";
				colonia = "NO DISPONIBLE";
				municipioestado = "NO DISPONIBLE";
				codigoPostal = "NO DISPONIBLE";				
			}
			
			rs.close();
			ps.close();
			
			Map map = null;
			map = new HashMap();
			
			map.put("idPersona", idPersona);
			map.put("numCuenta", numCuenta);
			map.put("tipoCuenta", tipoCuenta);
			map.put("clasificacion", clasificacion);
			map.put("nombreCompleto", nombreCompleto);
			map.put("telefonoCasa", telefonoCasa);
			map.put("telefonoOficina", telefonoOficina);
			map.put("rfc", rfc);
			map.put("calle", calle);
			map.put("colonia", colonia);
			map.put("municipioestado", municipioestado);
			map.put("codigoPostal", codigoPostal);
			
			
			return map;
		}
		finally {
			if (con != null) {
				ConnectionFactory.closeConnection(con);
			}
		}
	}
	
	public static List getBillCheckAdditionalData(String account) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;		
		account = account.trim();
		try {
			con = ConnectionFactory.getConnection();
			ps = con.prepareStatement(queryAdditionalBillingData);
			ps.setString(1, account);
			ps.setString(2, account);			
			rs = ps.executeQuery();
			
			String idPersona = "";
			String numCuenta = "";
			String tipoCuenta = "";
			String clasificacion = "";
			String nombreCompleto = "";
			
			String telefonoCasa = "";
			String telefonoOficina = "";
			
			String rfc = "";
			String calle = "";
			String colonia = "";
			String municipioestado = "";
			String codigoPostal = "";
			
			List list = null;
			list = new ArrayList();
			
			while (rs.next()) {
				idPersona = rs.getString("r1").trim()!=null?rs.getString("r1").trim():"";
				numCuenta = rs.getString("r2").trim()!=null?rs.getString("r2").trim():"";
				tipoCuenta = rs.getString("r3").trim()!=null?rs.getString("r3").trim():"";
				clasificacion = rs.getString("r4").trim()!=null?rs.getString("r4").trim():"";
				nombreCompleto = rs.getString("r5").trim()!=null?rs.getString("r5").trim():"";
				rfc = rs.getString("r6").trim()!=null?rs.getString("r6").trim():"";
				calle = rs.getString("r7").trim()!=null?rs.getString("r7").trim():"";
				colonia = rs.getString("r8").trim()!=null?rs.getString("r8").trim():"";
				municipioestado = rs.getString("r9").trim()!=null?rs.getString("r9").trim():""; // DKDKFKFKF, DK
				codigoPostal = rs.getString("r10").trim()!=null?rs.getString("r10").trim():"";
				if (!idPersona.equals("")) {
					ps2 = con.prepareStatement(queryPhone);
					ps2.setString(1, idPersona);
					ps2.setInt(2, 1);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						telefonoCasa = rs2.getString("r1")!=null?rs2.getString("r1").trim():"";
					}
					ps2 = con.prepareStatement(queryPhone);
					ps2.setString(1, idPersona);
					ps2.setInt(2, 2);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						telefonoOficina = rs2.getString("r1")!=null?rs2.getString("r1").trim():"";
					}
					rs2.close();
					ps2.close();															
				}
				Map map = null;
				map = new HashMap();
			
				map.put("idPersona", idPersona);
				map.put("numCuenta", numCuenta);
				map.put("tipoCuenta", tipoCuenta);
				map.put("clasificacion", clasificacion);
				map.put("nombreCompleto", nombreCompleto);
				map.put("telefonoCasa", telefonoCasa);
				map.put("telefonoOficina", telefonoOficina);
				map.put("rfc", rfc);
				map.put("calle", calle);
				map.put("colonia", colonia);
				map.put("municipioestado", municipioestado);
				map.put("codigoPostal", codigoPostal);
				
				list.add(map);		
			}
			
			rs.close();
			ps.close();
			
			return list;
		}
		finally {
			if (con != null) {
				ConnectionFactory.closeConnection(con);
			}
		}
	}		
	
	public static String getErrorDesc(String processID,String Code) throws SQLException {
			Connection con = null;
			Statement stm = null;
			ResultSet rs = null;
			String sql= null;
			String desc="";
			try {
				con = ConnectionFactory.getConnection();
				sql = "select descripcion from MQ_ERROR where servicio = '"+processID.trim()+"' and codigo = '"+Code.trim()+"' ";
				stm = con.createStatement();
				rs  = stm.executeQuery(sql);

				if (rs.next()) {
					desc = rs.getString("descripcion");
				}
				else{
					desc = "Codigo no encontrado";
				}
				stm.close();		
				rs.close();
				return desc;
			}
			finally {
				if (con != null) {
					ConnectionFactory.closeConnection(con);
				}
			}
		}

	public static String[] getPoints(String account) throws SQLException {
				Connection con = null;
				Statement stm = null;
				ResultSet rs = null;
				String sql= null;
				String[] desc=new String[4];
				
				try {
					con = ConnectionFactory.getConnection();
					account = getBillCheckAcc(account);
					sql = "select npuntos_acumulados, nsaldo_actual, nsaldo_anterior,npuntos_redimidos from ecm_testado_cuenta where vnumero_tarjeta='"+account+"'";
					stm = con.createStatement();
					rs  = stm.executeQuery(sql);

					desc[0] = "0";
					desc[1] = "0";
					desc[2] = "0";
					desc[3] = "0";

					if (rs.next()) {
						desc[0] = rs.getString("npuntos_acumulados")!=null?rs.getString("npuntos_acumulados"):"0";
						desc[1] = rs.getString("nsaldo_actual")!=null?rs.getString("nsaldo_actual"):"0";
						desc[2] = rs.getString("nsaldo_anterior")!=null?rs.getString("nsaldo_anterior"):"0";
						desc[3] = rs.getString("npuntos_redimidos")!=null?rs.getString("npuntos_redimidos"):"0";
						
					}
					stm.close();		
					rs.close();
					return desc;
				}
				finally {

					if (con != null) {
						ConnectionFactory.closeConnection(con);
					}
				}
			}

	public static String getLastStmntDate(String account) throws SQLException {
				Connection con = null;
				Statement stm = null;
				ResultSet rs = null;
				String sql= null;
				Date fecha= null;
				String strDate = null;
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
				try {
					con = ConnectionFactory.getConnection();
					sql = "select fechaultimoestadocuenta from ods_cuentatarjeta where numcuentatarjeta = '"+account+"'"; 
					stm = con.createStatement();
					rs  = stm.executeQuery(sql);

					if (rs.next()) {
						fecha = rs.getDate("fechaultimoestadocuenta");
						strDate = dateFormat.format(fecha);						
					}
					else{
						// No cambiar este String, si se cambia; la longitud debe de ser diferente de 8
						strDate = "NOSEENCONTRO";
					}
					stm.close();		
					rs.close();
					return strDate;
				}
				finally {
					
					if (con != null) {
						ConnectionFactory.closeConnection(con);
					}
				}
			}
	
	public void setBitacora(String processID,String userName,String msg) throws SQLException {
				Connection con = null;
				Statement stm = null;
				String sql= null;
				try {
					con = ConnectionFactory.getConnection();
					sql = "insert into MQ_BITACORAACTUALIZA(proceso,fecha,mensaje,usuario) " +
						    "values('"+processID+"' , sysdate ,  '"+msg+"', '"+userName+"' )";
					logger.info(sql);
					stm = con.createStatement();
					stm.executeUpdate(sql);
					stm.close();		
				}
				finally {
					
					if (con != null) {
						ConnectionFactory.closeConnection(con);
					}
				}
			}
	
	public static String getSiguienteDiaHabil(String fecha)throws SQLException{
		String sql= "select min(to_char(t.calendar_date,'YYYYMMDD')) " +
				"from   dim_time t " +
				"where  t.isweekend = 'N' " +
				"and    t.isholiday     is null " +
				"and    t.calendar_date >= to_date('"+fecha+"','yyyymmdd')";
		String result= null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = ConnectionFactory.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if (rs.next()) {
				result = rs.getString(1);
			}
			rs.close();
			ps.close();
			
		}
		finally {
			ConnectionFactory.closeConnection(con);
		}
		return result;
	}
	
	public static String getCuentaByWebUser(String encUsr) throws SQLException{
		logger.info("getCuentaByWebUser "+encUsr);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String res= null;
		try{
			String sql= "Select DATA4U1 from USUARIO where NOMBREUSUARIO = ?";
			con = ConnectionFactoryUPTARJETAS.getConnection();
			ps= con.prepareStatement(sql);
			ps.setString(1, encUsr);
			rs = ps.executeQuery();
			if(rs.next()){
				res = rs.getString(1);
			}
		}finally {
			if(rs!= null){
				rs.close();
			}
			if(ps!= null){
				ps.close();
			}
			if (con != null) {
				ConnectionFactoryUPTARJETAS.closeConnection(con);
			}
		}
		return res;
	}
	
	public static boolean isCuentaTs2(String cuenta) throws SQLException{
		logger.info("isCuentaTs2 "+cuenta );
		if(cuenta== null){
			return false;
		}
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			String sql="Select bin " +
					"from producto_ts2 " +
					"where bin= '"+cuenta.substring(0, 6)+"'";
			con = ConFactoryUsrCatalogos.getConnection();
			ps= con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			return (rs.next());
		}finally {
			if(rs!= null){
				rs.close();
			}
			if(ps!= null){
				ps.close();
			}
			if (con != null) {
				ConFactoryUsrCatalogos.closeConnection(con);
			}
		}
		
	}
	
	public static String getProductoTs2(String cuenta,String tpc, String cpc) throws SQLException{
		if(cuenta== null){
			return null;
		}
		logger.info("getProductoCuenta "+cuenta +" tpc "+tpc +" cpc "+cpc);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String prod=null;
		try{
			String sql="Select producto " +
					"from producto_ts2 " +
					"where bin= '"+cuenta.substring(0, 6)+"' and tpc='"+tpc.toUpperCase()+"' and cpc='"+cpc.toUpperCase()+ "'";
			logger.info("sql "+sql);
			con = ConFactoryUsrCatalogos.getConnection();
			ps= con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			if(rs.next()){
				prod=rs.getString(1);
			}
		}finally {
			if(rs!= null){
				rs.close();
			}
			if(ps!= null){
				ps.close();
			}
			if (con != null) {
				ConFactoryUsrCatalogos.closeConnection(con);
			}
		}
		return prod;
	}
	
	public static String getProductoCuenta(String cuenta,String ucode3) throws SQLException{
		logger.info("getProductoCuenta "+cuenta + " ucode3 "+ucode3);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String res= null;
		try{
			String sql="Select distinct campania " +
					"from matriz_productos " +
					"where bin_banco like '"+cuenta.substring(0, 6)+"%' " +
							"and u_code3_act like '"+ucode3.charAt(0)+"%'";
			con = ConFactoryUsrCatalogos.getConnection();
			ps= con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			if(rs.next()){
				res = rs.getString(1);
			}
		}finally {
			if(rs!= null){
				rs.close();
			}
			if(ps!= null){
				ps.close();
			}
			if (con != null) {
				ConFactoryUsrCatalogos.closeConnection(con);
			}
		}
		return res;
	}
	
	
	public static Map<String,String> getStatusBloqueoTs2() throws SQLException{
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		Map<String,String> statuses = new HashMap<String,String>();
		try{
			String sql="Select llave,descripcion from parametro " +
					"where valor ='RS_SC'";
			con = ConFactoryUsrCatalogos.getConnection();
			ps= con.prepareStatement(sql);
			
			rs = ps.executeQuery();
			if(rs.next()){
				statuses.put(rs.getString(1),rs.getString(2));
			}
		}finally {
			if(rs!= null){
				rs.close();
			}
			if(ps!= null){
				ps.close();
			}
			if (con != null) {
				ConFactoryUsrCatalogos.closeConnection(con);
			}
		}
		return statuses;
	}
	/**
	 * Consulta el estado de cuenta en puntos tsl
	 * @param numCliente
	 * @return puntos acreditados|puntos redimidos|puntos actuales
	 * @throws SQLException
	 */
	public static String getPuntosEdoCuenta(String numCliente) throws SQLException{
		logger.info("getPuntosEdoCuenta "+numCliente);
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String res= null;
		numCliente = numCliente.trim();
		try{
			
			String sql ="SELECT PUNTOS_ACREDITADOS,PUNTOS_REDIMIDOS,PUNTOS_ACTUALES, fecha " +
					"FROM PL_ESTADOS_CUENTA edo " +
					"WHERE NUM_CLIENTE=? " +
					"and fecha=(select max(fecha) from PL_ESTADOS_CUENTA where NUM_CLIENTE=edo.NUM_CLIENTE)";
			logger.info(sql);
			con = ConnectionFactoryPLealtad.getConnection();
			ps= con.prepareStatement(sql);
			ps.setString(1, numCliente);
			rs = ps.executeQuery();
			if(rs.next()){
				res = rs.getString(1)+"|"+rs.getString(2)+"|"+rs.getString(3);
			}
		}finally {
			if(rs!= null){
				rs.close();
			}
			if(ps!= null){
				ps.close();
			}
			if (con != null) {
				ConnectionFactoryPLealtad.closeConnection(con);
			}
		}
		return res;
	}

}
	

	
	