package mx.com.invex.ws;

import javax.xml.ws.Endpoint;

public class WSCallcenterPublisher {
	public static void main(String[] args) {
		   Endpoint.publish("http://localhost:8080/ws/callcenter", new CallcenterWService());
	    }
}
