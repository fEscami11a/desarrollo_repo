package mx.com.interware.spira.web.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mx.com.interware.spira.dto.InfoLuciActivacionDTO;
import mx.com.interware.spira.web.util.ConnectionFactoryLUCI;

import org.apache.log4j.Logger;

public class LUCIDAO {
	
	private static Logger log=Logger.getLogger(LUCIDAO.class);

	

	public static InfoLuciActivacionDTO getInfoTsys2(String folio) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql= "select DISCLOSUREGROUP,CALLE_EMPLEO,NUMERO_EMPLEO,COLONIA_EMPLEO,CPOSTAL_EMPLEO,ESTADO_EMPLEO,MUNICIPIO_EMPLEO,"+
				"PAIS,GRADOESTUDIO,NACIONALIDAD,BIRTH_NAME,INGRESOMENSUAL,TIPOVIVIENDA,NAME_LINE_1," +
				"CALLE,NOEXTERIOR,NOINTERIOR,COLONIA,MUNICIPIO,ESTADO,CPOSTAL,FECHA_ASIGNACION_TC," +
				"TELEFONO,TELEFONO_EMP,ACQ_STRATEGY,LIMITECREDITO,FECHADECORTE,CICLE,BUREAU,CR,GENERANIP," +
				"STATUSCODE,REASONCODE,BRANCHTS2,CREDIT_SCORE,IDPRODUCTO,BIRTHDATE,FOLIO,NOM_EMB," +
				"APATERNO_CLIENTE,VENTA_FRIO_T5,AMATERNO_CLIENTE,NOMBRE_CLIENTE,VER_ID,INGRESOMENSUAL,EXTENSION_EMP," +
				"PORDISPOSICIONEFECTIVO,MONTOPAGOMINIMO,PERCENT_CASH_LIMIT,TEL_CEL,CURP,REFNAME," +
				"REFTEL,EMAIL,ESTADOCIVIL,NACIONALIDAD,SEXO,GRADOESTUDIO,TIPOVIVIENDA,PAIS,CLAVETOTAL," +
				"PRODUCTO,USER_CODE_3,VALOR,MARCAEI,ACTIVIDADLABORAL,NOMBRE_EMPLEO,CALLE_EMPLEO,SEXO," +
				"NUMERO_EMPLEO,COLONIA_EMPLEO,CPOSTAL_EMPLEO,FOLIO,ESTADO_EMPLEO,MUNICIPIO_EMPLEO," +
				"ESTADOCIVIL,ANTIGUEDAD_EMPLEO,ACTIVIDADLABORAL,COINDIDENCIA_PF,CLIENTE_UNICO,IDGRADOESTUDIO " +
				"from LUCIV2.vwsolicitudests2 WHERE FOLIO = "+folio;
		log.info(sql);
		InfoLuciActivacionDTO infoAct= new InfoLuciActivacionDTO();
		try {
			con = ConnectionFactoryLUCI.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				log.info("resultados? true");
				 infoAct = new InfoLuciActivacionDTO();
				 infoAct.setUcode2(rs.getString("VENTA_FRIO_T5"));
				 infoAct.setClienteUnico(rs.getString("CLIENTE_UNICO"));
				 infoAct.setCoincidenciaPF(rs.getString("COINDIDENCIA_PF"));
				 infoAct.setAntiguedadEmp(rs.getString("ANTIGUEDAD_EMPLEO"));
				 infoAct.setDisclosureGroup(rs.getString("DISCLOSUREGROUP"));
				 infoAct.setEmpCalle(rs.getString("CALLE_EMPLEO"));
				 infoAct.setEmpNum(rs.getString("NUMERO_EMPLEO"));
				 infoAct.setEmpColonia(rs.getString("COLONIA_EMPLEO"));
				 infoAct.setEmpCP(rs.getString("CPOSTAL_EMPLEO"));
				 infoAct.setEmpEdo(rs.getString("ESTADO_EMPLEO"));
				 infoAct.setEmpMun(rs.getString("MUNICIPIO_EMPLEO"));
				 infoAct.setCodigoPais(rs.getString("PAIS"));
				 infoAct.setGradoEstudio(rs.getString("IDGRADOESTUDIO"));
				 infoAct.setNacionalidad(rs.getString("NACIONALIDAD"));
				 infoAct.setBirthname(rs.getString("BIRTH_NAME"));
				 infoAct.setMaritalStatus(rs.getString("ESTADOCIVIL"));
				 infoAct.setIngresoMensual(rs.getString("INGRESOMENSUAL"));
				 infoAct.setHomeType(rs.getString("TIPOVIVIENDA"));
				 infoAct.setActividadLaboral(rs.getString("ACTIVIDADLABORAL"));
				byte[] aux = rs.getBytes("NOM_EMB"); 
				try {
					String cadena = new String(aux,"iso-8859-1");
					log.info("NAme liune 1 "+cadena);
					infoAct.setNameLine1(cadena);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				infoAct.setBureauStatus("Y".equalsIgnoreCase(rs.getString("bureau"))?"N":"Y");
				log.info("En Buro: "+infoAct.getBureauStatus());
				infoAct.setCalle(rs.getString("Calle"));
				infoAct.setNoExterior(rs.getString("noExterior"));
				infoAct.setNoInterior(rs.getString("noInterior"));
				infoAct.setColonia(rs.getString("Colonia"));
				infoAct.setMunicipio(rs.getString("Municipio"));
				infoAct.setEdo(rs.getString("Estado"));
				infoAct.setCpostal(rs.getString("CPostal"));
				infoAct.setTel(rs.getString("Telefono"));
				infoAct.setTelEmp(rs.getString("Telefono_EMP"));
				//String agente= rs.getString("Agente");
				String marcaEI = rs.getString("MarcaEI");
				if(marcaEI == null || "".equals(marcaEI.trim())){
					marcaEI="0";
				}
//				if(agente!= null){
//					infoAct.setAgente(agente.startsWith("9")?agente:marcaEI+agente);
//				}
				infoAct.setAcqStrategy(rs.getString("ACQ_STRATEGY"));
				infoAct.setLimiteCredito(rs.getString("limiteCredito"));
				infoAct.setCicle(rs.getString("CICLE"));
				log.info("ciclo: "+infoAct.getCicle());
				//infoAct.setAnnualFee(rs.getString("ANUALDAD"));
				infoAct.setBranch(rs.getString("BRANCHTS2"));
				log.info("cranch: "+infoAct.getBranch());
				infoAct.setCreditScore(rs.getString("Credit_Score"));
				log.info("cred scor "+infoAct.getCreditScore());
//				infoAct.setOfficer(rs.getString("Officer"));
//				logger.info("officer "+infoAct.getOfficer());
				infoAct.setBirthDate(rs.getString("BirthDate"));
				log.info("birthday "+infoAct.getBirthDate());
				infoAct.setFolio(rs.getString("folio"));
				log.info("foiko "+infoAct.getFolio());
				infoAct.setPlaceOfEmp(rs.getString("NOMBRE_EMPLEO"));
				log.info("plac of emp");
				infoAct.setLongName(rs.getString("NAME_LINE_1"));
				log.info("nsame_loine_1");
				infoAct.setNombre(rs.getString("NOMBRE_CLIENTE"));
				log.info("1");
				infoAct.setApaterno(rs.getString("APATERNO_CLIENTE"));
				log.info("2");
				infoAct.setAmaterno(rs.getString("AMATERNO_CLIENTE"));
				log.info("3");
				infoAct.setRfc(rs.getString("VER_ID"));
				log.info("4");
				infoAct.setExtEmp(rs.getString("Extension_EMP"));
				log.info("5");
				infoAct.setPercentCashLimit(rs.getString("PERCENT_CASH_LIMIT"));
				log.info("6");
				infoAct.setTelCel(rs.getString("TEL_CEL"));
				log.info("7");
				infoAct.setCurp(rs.getString("CURP"));
				log.info("8");
				infoAct.setGuarantor1(rs.getString("refname"));
				log.info("9");
				infoAct.setGuarantor1Tel(rs.getString("reftel"));
				log.info("10");
				infoAct.setEmail(rs.getString("EMAIL"));
				log.info("11");
				infoAct.setProducto(rs.getString("PRODUCTO"));
				log.info("12");
				infoAct.setCreditRaiting(rs.getString("CR"));
				log.info("1");
				infoAct.setGeneraNIP(rs.getString("generanip"));
				log.info("1");
				log.info("Generar NIP: "+infoAct.getGeneraNIP());
				infoAct.setUcode3(rs.getString("USER_CODE_3"));
				infoAct.setStatusCode(rs.getString("STATUSCODE"));
				infoAct.setReasonCode(rs.getString("REASONCODE"));
				infoAct.setFchAsignacion(rs.getString("FECHA_ASIGNACION_TC"));
				infoAct.setSexo(rs.getString("SEXO"));
				//infoAct.setSavAcctNum(rs.getString("SAV_ACCT_NUM"));
				//infoAct.setUsrAcct3(rs.getString("FCH_DECISION")+"00000000");
//				String ucode2 =rs.getString("USER_CODE_2");
//				if(ucode2 != null && !"".equals(ucode2.trim())){
//					infoAct.setUcode2(ucode2);
//				}
				if(rs.next()){
					infoAct.setGuarantor2(rs.getString("refname"));
					infoAct.setGuarantor2Tel(rs.getString("reftel"));
				}
				
				rs.close();
				ps.close();
				//obtener ref gurantors
				log.info("folio "+infoAct.getFolio());
				
		}
		}catch(SQLException e){
			log.error("ERROR "+e.getMessage());
			e.printStackTrace();
		}
		finally {
			if (con != null) {
				try {
					rs.close();
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ConnectionFactoryLUCI.closeConnection(con);
			}
		}
		return infoAct;
	}
//	
//	public static Set<CuentaAD> getCuentasByUserId(String longName, String rfc)throws SQLException{
//		Connection con = null;
//		PreparedStatement stm = null;
//		String sql = "select acct_num,MJV_ACCT_TYP,u_code3,CR_RATING,LONG_NAME,OTH_ACCT_NUM " +
//				"from dwh_base_daily where long_name like ? and tax_id=? and business_dt = to_date(?,'YYMMDD') " +
//				"order by oth_acct_num,mjv_acct_typ";
//		String fecha = getDiaHabilAnterior();
//		log.info("sql "+sql);
//		log.info("fecha "+fecha );
//		Set<CuentaAD>  cuentas = new LinkedHashSet<CuentaAD>();
//		try{
//		
//			con =  ConnectionFactoryDWH.getConnection();
//			stm = con.prepareStatement(sql);
//			stm.setString(1, longName);
//			stm.setString(2, rfc);
//			stm.setString(3, fecha);
//			ResultSet rs = stm.executeQuery();
//			List<String> cbas = new ArrayList<String>();
//			while(rs.next()){
//				String tipoCuenta = rs.getString(2);
//				
//				if("P".equalsIgnoreCase(tipoCuenta)){
//					cbas.add(rs.getString(6));
//
//				}else{
//					CuentaAD cad = new CuentaAD();
//					cad.setNumCuenta(rs.getString(1));
//					cad.setTipoCuenta(tipoCuenta);
//					cad.setUcode3(rs.getString(3));
//					cad.setClasificacion(rs.getString(4));
//					cad.setNombreCompleto(rs.getString(5).replace('*', ' ').replace('\\', ' '));
//					
//					cuentas.add(cad);
//				}
//			}//fin while
//			if(!cbas.isEmpty()){
//				String sql2="select acct_num,MJV_ACCT_TYP,u_code3,CR_RATING,LONG_NAME from dwh_base_daily " +
//				"where OTH_ACCT_NUM=? and business_dt=to_date(?,'YYMMDD') "+
//				"order by oth_acct_num,mjv_acct_typ";
//				log.info("sql2 "+sql2);
//				PreparedStatement stm2 = con.prepareStatement(sql2);
//				for (String cba : cbas) {
//					log.info("buscar P y S por cba "+cba);
//					
//					stm2.setString(1, cba);
//					stm2.setString(2, fecha);
//					ResultSet rs2 = stm2.executeQuery();
//					while(rs2.next()){
//						CuentaAD cad = new CuentaAD();
//						cad.setNumCuenta(rs2.getString(1));
//						cad.setTipoCuenta(rs2.getString(2));
//						cad.setUcode3(rs2.getString(3));
//						cad.setClasificacion(rs2.getString(4));
//						cad.setNombreCompleto(rs2.getString(5).replace('*', ' ').replace('\\', ' '));
//						
//						cuentas.add(cad);
//						
//					}
//				}
//			}
//		}catch(SQLException e){
//			log.error("ERROR "+e.getMessage());
//			e.printStackTrace();
//		} finally {
//			if (stm != null) {
//				try {
//					stm.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			if (con != null) {
//				
//					ConnectionFactoryDWH.closeConnection(con);
//				
//			}
//		}
//		return cuentas;
//	}
	
}

	
	