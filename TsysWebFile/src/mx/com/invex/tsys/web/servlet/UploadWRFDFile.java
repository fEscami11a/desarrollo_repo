package mx.com.invex.tsys.web.servlet;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UploadWRFDFile
 */
@WebServlet("/UploadWRFDFile")
@ServletSecurity(
@HttpConstraint(
    rolesAllowed = {"USR_TSYS"}))
public class UploadWRFDFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadWRFDFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ollllaaa");
		 PrintWriter out = response.getWriter();
			try{
				
			 String contentType = request.getContentType();
			
			 if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {
					                 DataInputStream in = new DataInputStream(request.
					 getInputStream());
					               
					                 //we are taking the length of Content type data
					                 int formDataLength = request.getContentLength();
					                 byte dataBytes[] = new byte[formDataLength];
					                 int byteRead = 0;
					                 int totalBytesRead = 0;
					                 //this loop converting the uploaded file into byte code
					                 while (totalBytesRead < formDataLength) {
					                         byteRead = in.read(dataBytes, totalBytesRead, 
					 formDataLength);
					                         totalBytesRead += byteRead;
					                         }
					                                         String file = new String(dataBytes);
					                 //for saving the file name
					                 String saveFile = file.substring(file.indexOf("filename=\"") + 10);
					               
					                 
					                 saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
					                 saveFile = saveFile.substring(saveFile.lastIndexOf("\\")
					  + 1,saveFile.indexOf("\""));
					                 int lastIndex = contentType.lastIndexOf("=");
					                 String boundary = contentType.substring(lastIndex + 1,
					 contentType.length());
					                 int pos;
					                 //extracting the index of file 
					                 pos = file.indexOf("filename=\"");
					                 pos = file.indexOf("\n", pos) + 1;
					                 pos = file.indexOf("\n", pos) + 1;
					                 pos = file.indexOf("\n", pos) + 1;
					                 int boundaryLocation = file.indexOf(boundary, pos) - 4;
					                 int startPos = ((file.substring(0, pos)).getBytes()).length;
					                 int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;
					                 // creating a new file with the same name and writing the content in new file
					                 FileOutputStream fileOut = new FileOutputStream("\\"+saveFile);
					                 fileOut.write(dataBytes, startPos, (endPos - startPos));
					                 fileOut.flush();
					                 fileOut.close();
			 }
			 }catch(Exception e){
				 out.println("Error: "+e.getMessage());
				e.printStackTrace();
			}finally{
				 out.flush();
	             out.close();
			}
			
	}

}
