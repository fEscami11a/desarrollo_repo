package mx.com.interware.spira.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import mx.com.interware.spira.dto.CuentaAD;
import mx.com.interware.spira.web.util.ConnectionFactoryDWH;

import org.apache.log4j.Logger;

public class DWHDAO {
	
	private static Logger log=Logger.getLogger(DWHDAO.class);

	

	private static String getDiaHabilAnterior() throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
			String sql = "select max(to_char(t.calendar_date,'YYMMDD')) " +
					"from dim_time t where  t.isweekend = 'N' " +
					"and t.isholiday is null " +
					"and  t.calendar_date < trunc( sysdate )";
		log.info(sql);
		String fecha= null;
		try {
			con = ConnectionFactoryDWH.getConnection();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if(rs.next()){
				fecha = rs.getString(1);
			}
		}
		finally {
			if (con != null) {
				rs.close();
				ps.close();
				ConnectionFactoryDWH.closeConnection(con);
			}
		}
		return fecha;
	}
	
	/**
	 * Obtener cuentas relacionadas con ese nombre y rfc
	 * @param longName nombre largo
	 * @param rfc rfc
	 * @return datos de cuentas relacionadas
	 * @throws SQLException
	 */
	public static Set<CuentaAD> getCuentasByUserId(String longName, String rfc)throws SQLException{
		Connection con = null;
		PreparedStatement stm = null;
		//obtiene cuenta, tipo de cuenta, uc3 para ver porducto y si esta activada, nombre largo y cuenta cba y
		//campos para ver si la cuenta esta transcodificada
		String sql = "select acct_num,MJV_ACCT_TYP,u_code3,CR_RATING,LONG_NAME,OTH_ACCT_NUM " +			
				"from dwh_base_daily where long_name like ? and tax_id=? and business_dt = to_date(?,'YYMMDD') and " +
				//quitar tarns y facturadoras
				"(nvl(acct_trans_stat,'X') = 'F' or (acct_trans_stat is null and acct_trans_reas is null)) " +
				//"and nvl(MJV_ACCT_TYP,'X') != 'B' " +
				"order by oth_acct_num,mjv_acct_typ";
		//obtener la ultima fecha de carga
		String fecha = getDiaHabilAnterior();
		log.info("sql "+sql);
		log.info("fecha "+fecha );
		Set<CuentaAD>  cuentas = new LinkedHashSet<CuentaAD>();
		try{
		
			con =  ConnectionFactoryDWH.getConnection();
			stm = con.prepareStatement(sql);
			stm.setString(1, longName);
			stm.setString(2, rfc);
			stm.setString(3, fecha);
			ResultSet rs = stm.executeQuery();
			List<String> cbas = new ArrayList<String>();
			while(rs.next()){
				String tipoCuenta = rs.getString(2);
//				if("B".equals(tipoCuenta.trim())){
//					continue;
//				}
				if("P".equalsIgnoreCase(tipoCuenta)){
					cbas.add(rs.getString(6));

				}else{
					CuentaAD cad = new CuentaAD();
					cad.setNumCuenta(rs.getString(1));
					cad.setTipoCuenta(tipoCuenta);
					cad.setUcode3(rs.getString(3));
					cad.setClasificacion(rs.getString(4));
					cad.setNombreCompleto(rs.getString(5).replace('*', ' ').replace('\\', ' '));
					
					cuentas.add(cad);
				}
			}//fin while
			if(!cbas.isEmpty()){
				String sql2="select acct_num,MJV_ACCT_TYP,u_code3,CR_RATING,LONG_NAME from dwh_base_daily " +
				"where OTH_ACCT_NUM=? and business_dt=to_date(?,'YYMMDD') "+
				"order by oth_acct_num,mjv_acct_typ";
				log.info("sql2 "+sql2);
				PreparedStatement stm2 = con.prepareStatement(sql2);
				for (String cba : cbas) {
					log.info("buscar P y S por cba "+cba);
					
					stm2.setString(1, cba);
					stm2.setString(2, fecha);
					ResultSet rs2 = stm2.executeQuery();
					while(rs2.next()){
						CuentaAD cad = new CuentaAD();
						cad.setNumCuenta(rs2.getString(1));
						cad.setTipoCuenta(rs2.getString(2));
						cad.setUcode3(rs2.getString(3));
						cad.setClasificacion(rs2.getString(4));
						cad.setNombreCompleto(rs2.getString(5).replace('*', ' ').replace('\\', ' '));
						
						cuentas.add(cad);
						
					}
				}
			}
		}catch(SQLException e){
			log.error("ERROR "+e.getMessage());
			e.printStackTrace();
		} finally {
			if (stm != null) {
				try {
					stm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				
					ConnectionFactoryDWH.closeConnection(con);
				
			}
		}
		return cuentas;
	}
	
}

	
	