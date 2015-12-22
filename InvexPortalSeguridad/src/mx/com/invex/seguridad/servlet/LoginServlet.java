package mx.com.invex.seguridad.servlet;

import java.io.IOException;
import java.util.logging.Logger;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 

/**
* http://www.technicaladvices.com/2012/07/08/the-effective-java-logout-servlet-code/
*
* The servlet must be put into <security-constraint> <web-resource-collection> in web.xml, if not, request.getUserPrincipal() will be null!
*
* com.ybxiang.forum.jsfmbean.JSFHelper.printFacesExternalContext()
*/
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static final Logger logger = Logger.getLogger(LoginServlet.class.getName());
 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user=request.getParameter("j_username");
		String pass=request.getParameter("j_password");
		logger.info("loginServlet");
		
		//request.getRequestDispatcher("j_security_check").forward(request, response);
		response.sendRedirect("/InvexPortalSeguridad/j_security_check?j_username=" + user    + "&j_password=" + pass);	
	}
}