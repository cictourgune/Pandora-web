package org.tourgune.pandora.util;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 
		printBytes(hexStringToByteArray("A7AE2EB71F004168B99BA749BAC1CA64")); 
		
	}
	
	public static byte[] hexStringToByteArray(String s) {
	    int len = s.length();
	    byte[] data = new byte[len / 2];
	    for (int i = 0; i < len; i += 2) {
	        data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
	                             + Character.digit(s.charAt(i+1), 16));
	    }
	    return data;
	}
	
	public static void printBytes(byte[] data){
		
		String bytes="";
		for(int i=0;i<data.length;i++){ 
			bytes = bytes +" "+i+":"+String.format("%2X", data[i]);;
			
			
		}
		System.out.println(bytes.toString());
	}

}
