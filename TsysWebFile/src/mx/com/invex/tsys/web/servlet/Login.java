package mx.com.invex.tsys.web.servlet;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.naming.*;
import javax.naming.directory.*;
import java.util.Hashtable;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;


/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("esoty en proisces request");
    	final String SUCCESS = "Success.html";
    	final String FAILURE = "Failure.html";
    	String strUrl = "login.html";
    	String username = request.getParameter("username");
    	String password = request.getParameter("password");

    	Hashtable env = new Hashtable(11);

    	boolean b = false;

    	env.put(Context.INITIAL_CONTEXT_FACTORY,
    	"com.sun.jndi.ldap.LdapCtxFactory");
    	env.put(Context.PROVIDER_URL, "ldap://172.16.18.118:389");
    	env.put(Context.SECURITY_AUTHENTICATION, "simple");
    	//env.put(Context.SECURITY_PRINCIPAL, "CN="+ username +",OU=Aplicaciones y Servicios,OU=Administracion,OU=Invex,DC=invexbt,DC=com");
    	env.put(Context.SECURITY_PRINCIPAL, username);
    	
    	env.put(Context.SECURITY_CREDENTIALS, password);

    	try {
    	// Create initial context
    	DirContext ctx = new InitialDirContext(env);

    	// Close the context when we're done
    	b = true;
    	ctx.close();

    	} catch (NamingException e) {
    	b = false;
    	}finally{
    	if(b){
    		System.out.println("success");
    	strUrl = SUCCESS;
    	}else{
    		System.out.println("failure");
    	strUrl = FAILURE;
    	}
    	}
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("estoy en get"); 
    	processRequest(request,response);
    	}

    	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	processRequest(request,response);
    	} 

}
