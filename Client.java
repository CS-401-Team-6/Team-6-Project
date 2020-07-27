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

        //DIFFIE HELLMAN initialization
        System.out.println("Generating DH key pair:");
        KeyPairGenerator clientKeyPairGen = KeyPairGenerator.getInstance("DH");
        clientKeyPairGen.initialize(1024);
        KeyPair clientKeyPair = clientKeyPairGen.generateKeyPair();
        
        // Client creates and initializes DH KeyAgreement object
        System.out.println("Initizalizing DH object ...");
        KeyAgreement clientKeyAgree = KeyAgreement.getInstance("DH");
        clientKeyAgree.init(clientKeyPair.getPrivate());
    
        // Client encodes  public key and sends it Server
        byte[] clientPublicKeyEncoded = clientKeyPair.getPublic().getEncoded();
        objectOutputStream.writeObject(clientPublicKeyEncoded);

        // Client instantiates a DH public key from encoded Server material
        // Client uses Server public key
        
        byte[] serverPublicKeyEnc =  (byte[]) objectInputStream.readObject();
        
        //After doPhase(), key ready to use
	KeyFactory clientKeyFac = KeyFactory.getInstance("DH");
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(serverPublicKeyEnc);
        PublicKey serverPubKey = clientKeyFac.generatePublic(x509KeySpec);
        System.out.println("Client:  PHASE1 ...");
        clientKeyAgree.doPhase(serverPubKey, true);

      	//Generate a shared secret
        byte[] clientSharedSecret = clientKeyAgree.generateSecret();
        objectOutputStream.writeObject(clientSharedSecret);
          
        System.out.println("Client Length" + clientSharedSecret.length);
    
	//Use the shared secret to instantiate an AES key
        System.out.println("Use shared secret as SecretKey object ...");
        SecretKeySpec clientAesKey = new SecretKeySpec(clientSharedSecret, 0, 16, "AES");
     	//Initialize cipher
        Cipher clientCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        clientCipher.init(Cipher.ENCRYPT_MODE, clientAesKey);
  	//Send cipher parameters to server
        byte[] encodedParams = clientCipher.getParameters().getEncoded();
        objectOutputStream.writeObject(encodedParams);
	//Call to user login GUI 
    	Login userlogin = new Login(objectOutputStream, objectInputStream, clientCipher);
	userlogin.login();
		
		
	System.out.println("Connection confirmed");
    }
    
	//Helper function that takes two strings, combines them and returns the byte array of the SHA-256 one way hash
    public static byte[] oneWayHash(String username, String password) throws NoSuchAlgorithmException
    {
    	
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String salted = username + "/" + password; 
        return digest.digest(salted.getBytes(StandardCharsets.UTF_8));
    }
    
 
    //Helper function that encrypts a cleartext byte array given a predfiened cipher.
    
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
    
    
}

