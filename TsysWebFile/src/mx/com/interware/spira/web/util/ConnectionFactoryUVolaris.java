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
public class ConnectionFactoryUVolaris {

	private static Logger log = Logger.getLogger(ConnectionFactoryUVolaris.class);
	private final static String LOADER = "oracle.jdbc.driver.OracleDriver";

	private final static String DATASOURCE = "jboss/datasources/plealtad";

	private static Connection conn = null;



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
		
			return dataSource.getConnection();
		
		
	}

	public static Connection getPooledConnection() throws SQLException {
		if (conn == null || conn.isClosed()) {
			
				conn = dataSource.getConnection();
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
