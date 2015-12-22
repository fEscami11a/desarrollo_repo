package mx.com.interware.spira.web.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mx.com.interware.spira.dto.CuentaTs2Info;
import mx.com.interware.spira.web.util.ConnectionFactoryInvexts2;

import org.apache.log4j.Logger;

public class Ts2dwhDAO {
	
	private static Logger log=Logger.getLogger(Ts2dwhDAO.class);

	

	public static List<CuentaTs2Info> getCuentasRelacionadas(String cuenta) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		//obtiene la ultima fecha de carga del CIF
		String sql ="select to_char(max(business_dt),'YYMMDD') from CIF_TYPE6_DAILY";
		log.info(sql);
		
		List<CuentaTs2Info> cuentas = new ArrayList<CuentaTs2Info>();
		try {
			con = ConnectionFactoryInvexts2.getConnection();
			ps = con.prepareStatement(sql);
			//ps.setString(1, cuenta);
			rs = ps.executeQuery();
			if(rs.next()){
				String fecha= rs.getString(1);
				log.info("fecha "+fecha);
				log.info(cuenta);
				//obtener el cliente unico para obtener las todas las cuentas con ese id
				sql="SELECT distinct type5.custom_Data_91,type5.custom_Data_40 " +
					"FROM CIF_TYPE5_DAILY type5 INNER JOIN CIF_TYPE6_DAILY type6 ON type5.account_ID=type6.account_ID "+
					"where trim(type6.account_number)= ? "+
					"and CUSTOM_DATA_91 is not null "+
					"and CUSTOM_DATA_91 != '0' "+
					"and type6.business_dt=type5.business_dt "+
					"and type6.business_dt=to_date(?,'YYMMDD')";
				log.info(sql);
				ps = con.prepareStatement(sql);
				ps.setString(1, cuenta);
				ps.setString(2, fecha);
				rs = ps.executeQuery();
				if(rs.next()){
					String idusr=rs.getString(1);
					String cd40= rs.getString(2);
					log.info("id "+idusr);
					//obtener tipo de cuenta, nombre completo, cuenta, suff para ver si 
					//esta trancodificada, cd80 para ver si esta activa, cpc y tpc por id unico
					sql="select DECODE (customer_type, 0, 'Primary',"+ 
                             "1, 'Co-Applicant', "+
                             "2, 'Authorized User',"+ 
                             "3, 'Business User',"+
                            "4, 'Guarantor',"+
                            "5,'Perpetrator',"+
                            "9,'Dual','Other') tipo,embossed_name,trim(type6.account_number) cuenta, suffix_number, custom_data_80,client_product_code, tsys_product_code "+
							"from CIF_TYPE5_DAILY type5 INNER JOIN CIF_TYPE6_DAILY type6 ON type5.account_ID=type6.account_ID "+
							"where type6.business_dt=to_date(?,'YYMMDD') "+
							"and type6.business_dt=type5.business_dt "+
							"and custom_data_91= ? and custom_data_40= ? order by type6.ACCOUNT_ID,CUSTOMER_TYPE asc";
					log.info(sql);
					ps = con.prepareStatement(sql);
					ps.setString(1, fecha);
					ps.setString(2, idusr);
					ps.setString(3, cd40);
					rs = ps.executeQuery();
					while(rs.next()){
						CuentaTs2Info cuentats2= new CuentaTs2Info();
						cuentats2.setCuenta(rs.getString("cuenta"));
						cuentats2.setTipoCuenta(rs.getString("tipo"));
						String prod=(rs.getString("tsys_product_code")+","+rs.getString("client_product_code"));
						String status=rs.getString("suffix_number");
						//si sufix es 0 o viene vacio status es el cd80, si no la cuenta esta transcod
						if(status== null || "0".equals(status.trim()) || status.trim().length()==0){
							String cust80= rs.getString("custom_data_80");
							cuentats2.setProducto(prod+"|"+cust80);
						}else{
							cuentats2.setProducto(prod+"|TRANSCODIFICADA");
						}
						//nombre completo
						cuentats2.setNombre(rs.getString("embossed_name"));
						cuentas.add(cuentats2);
					}
				}
			}
			
			
			
		}
		finally {
			if (con != null) {
				rs.close();
				ps.close();
				ConnectionFactoryInvexts2.closeConnection(con);
			}
		}
		return cuentas;
	}
	
	
}

	
	