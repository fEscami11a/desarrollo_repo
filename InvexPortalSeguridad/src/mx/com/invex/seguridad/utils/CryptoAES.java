package mx.com.invex.seguridad.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;  

public class CryptoAES {

    private static byte[] linebreak = {}; 
    
    private static String secret = "#GS?(/$6¡1M@7(K";  
	    
    private static SecretKey key;
    private static Cipher cipher;
    private static Base64 coder;
    private static String secret2 = "MG#/)?7$¡9MX7)@";
	private static SecretKey key2;
	
    
    static {
	try {
	    key = new SecretKeySpec(secret.getBytes("UTF-8"), "AES");
	    key2 = new SecretKeySpec(secret2.getBytes("UTF-8"), "AES");
	    cipher = Cipher.getInstance("AES/ECB/PKCS5Padding", "SunJCE");
	    coder = new Base64(32, linebreak, true);
	} catch (Throwable t) {
	    t.printStackTrace();
	}
    }

    /**
     * 
     * @param plainText
     * @return
     * @throws Exception
     */
    public static synchronized String encrypt(String plainText)
	    throws Exception {
	cipher.init(Cipher.ENCRYPT_MODE, key);
	byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF-8"));
	return new String(coder.encode(cipherText));
    }
    
	public static synchronized String encrypt2(String plainText)
			throws Exception {
		cipher.init(Cipher.ENCRYPT_MODE, key2);
		byte[] cipherText = cipher.doFinal(plainText.getBytes("UTF-8"));
		return new String(coder.encode(cipherText));
	}


    /**
     * 
     * @param codedText
     * @return
     * @throws Exception
     */
    public static synchronized String decrypt(String codedText)
	    throws Exception {
	byte[] encypted = coder.decode(codedText.getBytes("UTF-8"));
	cipher.init(Cipher.DECRYPT_MODE, key);
	byte[] decrypted = cipher.doFinal(encypted);
	return new String(decrypted);
    }


	public static synchronized String decrypt2(String codedText)
			throws Exception {
		byte[] encypted = coder.decode(codedText.getBytes("UTF-8"));
		cipher.init(Cipher.DECRYPT_MODE, key2);
		byte[] decrypted = cipher.doFinal(encypted);
		return new String(decrypted);
	}


    
    /**
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
    	//=== original: adminUsr
    	//=== cifrado: q7gWckUbcUNH8P95ln7bLw
	String str = "<info><nombre>JESUS AZAEL GONZALEZ CASTILLO</nombre><tarjeta>4461389000594045</tarjeta><fhcorte>20/5/13</fhcorte><fhlimitepago>10/6/13</fhlimitepago><saldoactual>22155.20</saldoactual><saldoalcorte>20731.98</saldoalcorte><pagominimo>606.6</pagominimo><pagominimongi>17695</pagominimongi><aleatorio>000102</aleatorio></info>";
    	//String str = "salsamendi5";
	System.out.println("=== original: " + str);

	String cifrado = encrypt(str);
	System.out.println("=== cifrado: " + cifrado);
//
	String decifrado = decrypt("oPaPDOiAbKf-xM6dVDu7i7Fnik_ya8viuesYxUkIdRFPITTf6rvDFB4G7qGF1HuqFKKmhXwUomFTHYS29Tmq4NDKpQBfE45oGvyrJcjcN79p1YDZlPZBeKtzJfPnFgXiPXD3lCOzCZxhXoD8G_UFq0GLYXjNIS6cMN0NEZABqk0WP5wQJ9kwLA3J0JXWOV9-LPSUwOlTa1lJo5IOUWlxKxUrxRGP2GCBzfT4gQq62ZJ4o4lTj2gtYPf__X6woZ41qYyr_f0gHH6n1DdEDeNOBwR33NvkBcWOCXWeGpbS-vBKSIIrlqMfCnkJJWTKbpfT06RY3NjsD986vP3fX4HX41tEd9-6ewe3m5CiMglCHEgh1HmB0o4Rg2SGzvYtZtK7w0P7kVKJgVX3QcDstWUzUTVJtwXe68O2NRI9JauVTgmT16VzsCpvq4xt8nugce0M");
	System.out.println("=== decifrado: >" + decifrado+"<");
	
	System.out.println("cuenta "+ decrypt2("Atd-saO2TE-wSKGfk1IMJeLH65N5X7UX6wf9oqyW104"));
	System.out.println("cuenta encr "+encrypt2("4631860004262933"));
    }
}
