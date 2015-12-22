package mx.com.invex.msi.util;
/**
 * Clase para la constantes y parametros de configuracion
 * @author fernando.escamillaa
 *
 */
public class MSIConstants {
public static final String TSYSWS_SECURITY_USERNAME = "LineaSpira";
public static final String TSYSWS_ACCESS_KEY = "prodLS615#";
public static final String TRANS_CODIGO_COMPRAS_NACIONALES = "7146";
public static final String TRANS_CODIGO_COMPRAS_INTER_10001 = "1001";
public static final String TRANS_CODIGO_COMPRAS_INTER_30001 = "3001";
public static final String TRANS_CODIGO_PRG0_COMPRAS_NAL = "7140";

public static final boolean desa = true;

//public static final String TSYSWS_ENDPOINT = desa?"http://172.16.18.13:9080/TsysWS/services/LSWS":"http://172.16.18.185:9083/TsysWS/services/LSWS";
//public static final String TSYSWS_SI02_ENDPOINT = desa?"http://172.16.18.13:9080/TsysWS/services/SI02WS":"http://172.16.18.185:9083/TsysWS/services/SI02WS";
//public static final String SERVICIOS_MSIMOVS_ENDPOINT = desa?"http://172.16.18.13:9080/InvexServicesWeb/ServiciosMSIImplService":"http://172.16.18.185:9083/InvexServicesWeb/ServiciosMSIImplService";
//public static final String MXP_SERVICE_ENDPOINT = desa?"http://172.16.18.13:9080/InvexServicesWeb/MXPWSService":"http://172.16.18.185:9083/InvexServicesWeb/MXPWSService";

public static final String BALANCE_ID_CURRENT_PURCHASE="CP";
public static final String ONE_CYCLE_OLD_PURCHASE="PP"; //PP – 1 Cycle Old Purchase
public static final String ID_PROM_6_MSI="90600149";
public static final String ID_PROM_12_MSI="91200036";

public static final String ID_PROM_PROG0_6_MSI="90600179";
public static final String ID_PROM_PROG0_12_MSI="91200067";
public static final String ID_PROM_18_MSI="91800112";

public static final String MAIL_SMTP_HOST = "172.16.90.128";
public static final String MAIL_SMTP_PORT = "25";
public static final String SENDER_ADDRESS = "boletin@invextarjetas.com.mx";
public static final String SUBJECT = "Promoción MSI Aplicada";
public static final String INVEX_BANCO = "INVEX Banco ";
public static final String MENSAJE_CORREO = "agradece su preferencia,<br> se han registrado sus compras a <br> meses sin intereses con el folio: ";

public static final String PROM_ESTATUS_PENDIENTE="P";
public static final String PROM_ESTATUS_ENVIADO="E";
public static final String PROM_ESTATUS_APLICADO="A";

public static final String MQ_APLICADAS ="ITAA";
public static final String MQ_PENDIENTES ="ITAP";
public static final String MQ_ENVIADAS ="ITAE";
public static final String TRANSACCIONES_MQ ="ITA";
public static final String TRANSACCIONES_DRAFT ="IPS";
public static final String DRAFT_APLICADAS ="IPSA";
public static final String DRAFT_PENDIENTES ="IPSP";
public static final String TODAS_TRANSACCIONES ="ALL";

public static final String ACTIVACION ="AC112";
public static final String REACTIVACION ="RCTV4";
public static final String TSYSWS_ENDPOINT = "TSYSWS_ENDPOINT";
public static final String TSYSWS_SI02_ENDPOINT = "TSYSWS_SI02_ENDPOINT";
public static final String SERVICIOS_MSIMOVS_ENDPOINT = "SERVICIOS_MSIMOVS_ENDPOINT";
public static final String MXP_SERVICE_ENDPOINT = "MXP_SERVICE_ENDPOINT";
public static final String ITH_SERVICE_ENDPOINT = "ITH_SERVICE_ENDPOINT";
public static final String MSI_PPNGI_SERVICE_ENDPOINT = "MSI_PPNGI_SERVICE_ENDPOINT";
public static final String TS2_MSG_ENDPOINT="TS2_MSG_ENDPOINT";
}
