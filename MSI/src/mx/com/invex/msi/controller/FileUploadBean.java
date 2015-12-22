package mx.com.invex.msi.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import mx.com.invex.msi.model.ArchivoDetalle;

import org.apache.log4j.Logger;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;
 
/**
 * @author Ilya Shaikovsky
 */
@ManagedBean
@SessionScoped
public class FileUploadBean implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<UploadedFile> files = new ArrayList<UploadedFile>();
	
	private static final Logger logger = Logger.getLogger(FileUploadBean.class);
	 private List<ArchivoDetalle> detalles;
	 private List<String> infoArchivo;
	 private String nombreArchivo;

	 
	 
	 
	 
	 public List<String> getInfoArchivo() {
		return infoArchivo;
	}

	public void setInfoArchivo(List<String> infoArchivo) {
		this.infoArchivo = infoArchivo;
	}

	public String init(){
		 detalles= new ArrayList<ArchivoDetalle>();
		 infoArchivo = new ArrayList<String>();
		 FacesContext context = FacesContext.getCurrentInstance(); 
			
			context.getExternalContext().getSessionMap().remove("cuentasCampSegBean"); 
			
		 return "/admin/UploadFileCampSeg";
	 }
	 
	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public void paint(OutputStream stream, Object object) throws IOException {
        stream.write(getFiles().get((Integer) object).getData());
        stream.close();
    }
 
    public void listener(FileUploadEvent event) throws Exception {
    	nombreArchivo=event.getUploadedFile().getName();
    	InputStreamReader isr = new InputStreamReader(event.getUploadedFile().getInputStream());
    	BufferedReader bf = new BufferedReader(isr);
    	int i=0;
    	infoArchivo = new ArrayList<String>();
 		detalles= new ArrayList<ArchivoDetalle>(); 
 		ArchivoDetalle archivo;
 		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
 		logger.info("header "+bf.readLine());
    	for (String x = bf.readLine(); x != null ; x = bf.readLine()) {
    		i++;
    		logger.info("read: "+x);
    		 String[] campos=x.split("\t");
    		 archivo = new ArchivoDetalle(); 
    		 if(campos.length<4){
    			 infoArchivo.add("Error en fila "+i+":"+x+". Los registros deben tener 4 campos");
    			 detalles.clear();
	     		break;
	     	}
	     	archivo.setShot(new Integer(campos[0]));
 		
			archivo.setCuenta(campos[1]);
			 Pattern p = Pattern.compile("^\\d{16}$");
		     Matcher m = p.matcher(archivo.getCuenta());
		     
			if(!m.matches()){
				 detalles.clear();
				 infoArchivo.add("Error en fila "+i+":"+x+". La cuenta debe ser de 16 dígitos");
	     		break;
			}
		
			try {
				archivo.setFechaInicio(formateador.parse(campos[2]));
				archivo.setFechaFin(formateador.parse(campos[3]));
			} catch (ParseException e) {
				 detalles.clear();
				 infoArchivo.add("Fecha no valida");
				 infoArchivo.add("Error en fila "+i+":"+x +". La fecha debe estar en formato dd\\mm\\yyyy");
	     		break;
			}
			//archivo.setMontoMinimo(new Double(campos[4]));
		
			detalles.add(archivo);
    	}
    	infoArchivo.add("Numero de registros leidos "+i);
    	infoArchivo.add("Registros obtenidos exitosamente "+detalles.size());
        files.add(event.getUploadedFile());
    }
 
    public String clearUploadData() {
        files.clear();
        return null;
    }
 
    public int getSize() {
        if (getFiles().size() > 0) {
            return getFiles().size();
        } else {
            return 0;
        }
    }
 
    public ArrayList<UploadedFile> getFiles() {
        return files;
    }
 
    public void setFiles(ArrayList<UploadedFile> files) {
        this.files = files;
    }
 
    public long getTimeStamp() {
        return System.currentTimeMillis();
    }

	public List<ArchivoDetalle> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<ArchivoDetalle> detalles) {
		this.detalles = detalles;
	}
    
    
}
