package client;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class Client {

    public static void main(String[] args) throws Exception { // IOException, ClassNotFoundException, NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
        Scanner scan = new Scanner(System.in);
   
        Socket socket = new Socket("localhost", 5555);
        System.out.println("Connecting to localhost on port 5555");
        // Output stream socket.
        OutputStream outputStream = socket.getOutputStream();
        // Create object output stream from the output stream to send an object through it
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        InputStream inputStream = socket.getInputStream();
        // create a ObjectInputStream so we can read data from it.
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);

        //DIFFIE
        System.out.println("Generating DH key pair:");
        KeyPairGenerator clientKeyPairGen = KeyPairGenerator.getInstance("DH");
        clientKeyPairGen.initialize(1024);
        KeyPair clientKeyPair = clientKeyPairGen.generateKeyPair();
        
        // Client creates and initializes DH KeyAgreement object
        System.out.println("Initizalizing DH object ...");
        KeyAgreement clientKeyAgree = KeyAgreement.getInstance("DH");
        clientKeyAgree.init(clientKeyPair.getPrivate());
    
        // Client encodes  public key, and sends it Server
        byte[] clientPublicKeyEncoded = clientKeyPair.getPublic().getEncoded();
        objectOutputStream.writeObject(clientPublicKeyEncoded);

        //* Client instantiates a DH public key from encoded Server material
        // Client uses Server public key
        
        byte[] serverPublicKeyEnc =  (byte[]) objectInputStream.readObject();
        
        
        KeyFactory clientKeyFac = KeyFactory.getInstance("DH");
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(serverPublicKeyEnc);
        PublicKey serverPubKey = clientKeyFac.generatePublic(x509KeySpec);
        System.out.println("Client:  PHASE1 ...");
        clientKeyAgree.doPhase(serverPubKey, true);

      
            byte[] clientSharedSecret = clientKeyAgree.generateSecret();
            objectOutputStream.writeObject(clientSharedSecret);
          
        System.out.println("Client Length" + clientSharedSecret.length);
    
        System.out.println("Use shared secret as SecretKey object ...");
        SecretKeySpec clientAesKey = new SecretKeySpec(clientSharedSecret, 0, 16, "AES");
        
        Cipher clientCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        clientCipher.init(Cipher.ENCRYPT_MODE, clientAesKey);
  
        byte[] encodedParams = clientCipher.getParameters().getEncoded();
        objectOutputStream.writeObject(encodedParams);
        
    	Login userlogin = new Login(objectOutputStream, objectInputStream, clientCipher);
		userlogin.login();
		
		
		
		

		//TABLE GUI HERE
		//
		//GAME LOGIC IN GUI
		//BOOLEAN:loggedin 
		/*while(loggedin)
        	  GameMessage gameMessage =  objectInputStream.readObject();
        	  if(gameMessage.getType() == "ACT")
        	  		//SEND MESSAGE BASED ON ACTION INPUT      
        */
		
		
		
        	System.out.println("Closing socket");
        	//socket.close();
    }
    
    public static byte[] oneWayHash(String username, String password) throws NoSuchAlgorithmException
    {
    	
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String salted = username + "/" + password; 
        return digest.digest(salted.getBytes(StandardCharsets.UTF_8));
    }
    
    
    public static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
        String hex = Integer.toHexString(0xff & hash[i]);
        if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    
    
    
    
    
    private static byte[] encrypt(byte[] cleartext, Cipher cipher)
    {
    	byte[] ciphertext = null;
    	 try {
			ciphertext = cipher.doFinal(cleartext);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return ciphertext;
    }
    
    private static byte[] decrypt(byte[] ciphertext, Cipher cipher)
    {
    	byte[] cleartext = null;
    	 try {
    		 cleartext = cipher.doFinal(ciphertext);
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return cleartext;
    }
    
    
    
    
}

