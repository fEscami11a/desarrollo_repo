package mx.com.interware.spira.web;


public interface Msg {
	
	public static final boolean DEBUGGING_OFF_LINE=false;
	
	public static final String USR_MSG="mx.com.interware.spira.web.UsrMessages";
	
	public static final String DEFAULT_CONFIG_FILE="WebContent/WEB-INF/XML/packet.config.xml";
	public static final String DEFAULT_SI01_RESPONSE_FILE="/tmp/XML/SI01_RESPONSE.txt";
	
	public static final String GET_MESSAGE_TIMEOUT="GET_MESSAGE_TIMEOUT";
	public static final String TSYS_PROBLEM="TSYS_PROBLEM";
	public static final String SI01_PROBLEM="SI01_PROBLEM";

}
