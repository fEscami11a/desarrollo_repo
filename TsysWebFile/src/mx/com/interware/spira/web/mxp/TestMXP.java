package mx.com.interware.spira.web.mxp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;


public class TestMXP {
	private static Logger log = Logger.getLogger(TestMXP.class);
	public static void main(String[] args) {
		System.out.println("test mxp");
		MXPWS mxpws = new MXPWS();
		 int promosEnviadas = 0;
		String infile = "C:/Users/fernando.escamillaa/Documents/promos/MSI_pendientes_20092012.txt";
		File entrada = new File(infile);
		if(!entrada.exists()){
			log.error("El archivo "+infile+" no existe");
			return;
		}
		 String linea;
		 try{
		 BufferedReader fd_in = new BufferedReader(new FileReader(infile));
		  MXPWS ws = new MXPWS();
		 do{
      	   linea = fd_in.readLine();
      	   if(linea!= null){
      		StringTokenizer tokens=new StringTokenizer(linea, "|");
            	 int nDatos=tokens.countTokens();
            	 if(nDatos==4){
                   String[] datos=new String[nDatos];
                   int i=0;
                   while(tokens.hasMoreTokens()){
                       datos[i]=tokens.nextToken();
                    //   System.out.println(datos[i]);
                       i++;
                   }
                	 MXPParam param = new MXPParam();
                	 param.setAccount(datos[0]);
                	 param.setBalanceId(datos[1]);
                	 param.setPromoId(datos[2]);
                	 param.setAmount(datos[3]);
                	 param.setUserName("usrPromos");
                	 System.out.println("'"+param.getAccount()+"',");
                	 //llamar al aservicio de mxp
                	 String res =ws.updateMXP(param);
                	 if("OK".equalsIgnoreCase(res)){
                		 ++promosEnviadas;
                	 }
            	 }//fin if ndato
            	 
                //out.println(linea);
      	   }//fin if
      	   }//fin do
         while (linea != null);
		 }
			catch(Exception e){
				log.error(e);
			}
	
/*
		param.setBalanceId("CP");
		param.setPromoId("91200070");
		param.setAmount("000238000");
		param.setAccount("4631000000000000");
		String resp = null;
		try{
			 resp = mxpws.updateMXP(param);
			 log.info("Respuesta :"+resp);
		}
		catch(Exception e){
			log.error(e);
		}*/
	}
}
