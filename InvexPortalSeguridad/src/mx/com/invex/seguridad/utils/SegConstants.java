package mx.com.invex.seguridad.utils;

public class SegConstants {
public static final int TIEMPO_EXP_SESION_MINS_MOVIL =10;
public static final int TIEMPO_EXP_SESION_MINS_WEB =15;
//desarollo
//public static final String URL_SERVICIO_VALIDA_CUENTA_ACTIVA ="http://172.16.19.13:9080/TsysWAR/Product.jsp?Usuario={0}";
//public static final String URL_SERVICIO_GET_WEB_USR ="http://172.16.19.13:9080/TsysWAR/GetWebUser.jsp?TARJETA={0}";
//public static final String URL_SERVICIO_GET_USR_BY_EMAIL ="http://172.16.19.13:9080/TsysWAR/GetUserByEmail?EMAIL={0}";
//public static final String WEB_SERVICE_INVEX_MOVIL= "http://172.16.19.13:9080/BancaMovilServices/ServiciosPortalImplService";
//public static final String URL_SERVICIO_REMOVE_USUARIO_WEB= "http://172.16.19.13:9080/TsysWAR/RemoveWebUser.jsp?Usuario={0}";
//public static final String WEB_SERVICE_BANCA_MOVIL = "http://172.16.19.13:9080/BancaMovilServices/ServiciosBancaMovilImplService";
//public static final String LSWS_END_POINT="http://172.16.19.13:9080/TsysWS/services/LSWS";
//public static final String SI02WS="http://172.16.19.13:9080/TsysWS/services/SI02WS";
//produccion
public static final String URL_SERVICIO_VALIDA_CUENTA_ACTIVA ="http://172.16.18.185:9083/TsysWAR/Product.jsp?Usuario={0}";
public static final String URL_SERVICIO_GET_WEB_USR ="http://172.16.18.185:9083/TsysWAR/GetWebUser.jsp?TARJETA={0}";
public static final String URL_SERVICIO_GET_USR_BY_EMAIL ="http://172.16.18.185:9083/TsysWAR/GetUserByEmail?EMAIL={0}";
public static final String WEB_SERVICE_INVEX_MOVIL= "http://172.16.18.185:9083/BancaMovilServices/ServiciosPortalImplService";
public static final String URL_SERVICIO_REMOVE_USUARIO_WEB= "http://172.16.18.185:9083/TsysWAR/RemoveWebUser.jsp?Usuario={0}";
public static final String WEB_SERVICE_BANCA_MOVIL = "http://172.16.18.185:9083/BancaMovilServices/ServiciosBancaMovilImplService";
public static final String LSWS_END_POINT="http://172.16.18.185:9083/TsysWS/services/LSWS";
public static final String SI02WS="http://172.16.18.185:9083/TsysWS/services/SI02WS";
public static final String HSM_KEY_USR="RDKEYHSMTRAN01";
public static final String HSM_KEY_PSW="RPKEYHSMTRAN01";

public static final int MINS_PSW_BOLQUEADO =20;
public static final int DIAS_EXPIRA_CONTRASENIA =30;

}
