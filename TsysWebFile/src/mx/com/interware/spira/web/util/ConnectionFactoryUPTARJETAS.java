/*
 * Created on Sep 7, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mx.com.interware.spira.web.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.NoInitialContextException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * 
 * Class Pruebas.java
 * Package mx.com.interware.spira.web.util
 * Created on Sep 7, 2005
 * @author blemus
 * @version 1.0
 * 
 * Connection class, try to use de datasource jdbc/spira, if exist , else use
 * a connection manager driver
 * 
 */
public class ConnectionFactoryUPTARJETAS {

	private static Logger log = Logger.getLogger(ConnectionFactoryUPTARJETAS.class);
	private final static String LOADER = "oracle.jdbc.driver.OracleDriver";

	// ## PRODUCCION
//
//	private final static String URL = "jdbc:oracle:thin:@172.16.18.203:1521:spirapro";
//	private final static String USER = "usr_interfaces";
//	private final static String PASS = "interf2006";

	private final static String DATASOURCE = "jboss/datasources/InvextarjetasDS";

	private static Connection conn = null;

	//	## DESARROLLO 10G

//	private final static String URL = "jdbc:oracle:thin:@172.16.18.101:1522:spirapro";
//	private final static String USER = "uwasmq";
//	private final static String PASS = "FRANCIA4";


	//	## DESARROLLO
//
//		private final static String URL = "jdbc:oracle:thin:@172.16.18.204:1521:dbsppstd";
//		private final static String USER = "spiraods";
//		private final static String PASS = "spiraods";
	
//		private final static String DATASOURCE =" "; // "jdbc/desarrollo";

	private static DataSource dataSource;

	static {
		try {
			InitialContext ctx = new InitialContext();
			dataSource = (DataSource) ctx.lookup(DATASOURCE);
			log.info("usando contexto " + DATASOURCE);
		}
		catch (NoInitialContextException e2) {
			log.info("No existe contexto para data source " + DATASOURCE + " :" + e2.fillInStackTrace());
		}
		catch (NamingException e1) {
			log.info("No existe contexto para data source " + DATASOURCE + ": " + e1.fillInStackTrace());
		}
		if (dataSource == null) {
			try {
				Driver driver = (Driver) Class.forName(LOADER).newInstance();
				DriverManager.registerDriver(driver);
			}
			catch (InstantiationException e) {
				log.error(e);
			}
			catch (IllegalAccessException e) {
				log.error(e);
			}
			catch (ClassNotFoundException e) {
				log.error(e);
			}
			catch (SQLException e) {
				log.error(e);
			}
		}
	}

	public static Connection getConnection() throws SQLException {
		if (dataSource != null) {
			return dataSource.getConnection();
		}
		log.warn("CONEXION DIRECTA A LA BASE DE DATOS"); // ELIMINAR
		//return DriverManager.getConnection(URL, USER, PASS);
		return null;
	}

	public static Connection getPooledConnection() throws SQLException {
		if (conn == null || conn.isClosed()) {
			if (dataSource != null) {
				conn = dataSource.getConnection();
				return conn;
			}
		//	conn = DriverManager.getConnection(URL, USER, PASS);
			log.warn("CONEXION POOLED DIRECTA A LA BASE DE DATOS"); // ELIMINAR
			return conn;
		}
		else {
			return conn;
		}
	}

	public static void closeConnection(Connection con) {
		try {
			if (con != null) {
				con.close();
			}
		}
		catch (SQLException cause) {
			log.error("problemas a cerrar la coneccion a base de datos", cause);
		}
	}

	public static void closePooledConnection() {
		try {
			if (conn != null) {
				conn.close();
				log.info("Conexión única a B.D. cerrada");
			}
		}
		catch (SQLException cause) {
			log.error("problemas a cerrar la coneccion a base de datos", cause);
		}
	}

}
