package mx.com.invex.seguridad.utils;

import org.apache.commons.codec.binary.Base64;

public final class InvexPasswordEncoder {

	public byte[] getBytes(String str) {
		byte[] b = new byte[str.length()];
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) str.charAt(i);
		}
		return b;
	}
	
	public byte[] computeHash(byte[] bytes) throws Exception {
		java.security.MessageDigest d = null;
		d = java.security.MessageDigest.getInstance("SHA-1");
		d.reset();
		d.update(bytes);
		return d.digest();
	}

	public String encode(CharSequence rawPassword) {
		try {
			return Base64.encodeBase64String(computeHash(getBytes(rawPassword.toString())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		  return (encode(rawPassword).equals(encodedPassword));
	}
	public static void main(String[] args) {
		mx.com.invex.seguridad.utils.InvexPasswordEncoder encoder = new InvexPasswordEncoder();
    	String result = encoder.encode("fEscami11a");
    	System.out.println(result);
	}
}
