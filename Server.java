package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import project.Message;

public class Server {
	   private static List<UserInfo> users = new ArrayList<>();
	   
	   public static void main(String[] args) throws Exception {
		   load();
			users.forEach(user -> 
			{
				System.out.println(user.getName());
				System.out.println(user.getPass());
				System.out.println(user.getBalance());
				
			}
			);
	        try(var socketVar = new ServerSocket(5555)) {
	        	System.out.println("Awaiting connections on port 5555...");
	        	var pool = Executors.newFixedThreadPool(50);
	        	while (true) {
	        		pool.execute(new ThreadServer(socketVar.accept()));
            		}
	        	}
	   		}
	   
	   public static void load() {
			String userInfo;
			try {
			File dataFile = new File("userdatabase");
			if (dataFile.createNewFile()) {
				return;
				}
			else {
				Scanner file = new Scanner (dataFile);
				while (file.hasNextLine())
					{
						userInfo = file.nextLine();
						String[] splitUser = userInfo.split("/");
						if (splitUser.length == 3) {
							users.add(new UserInfo(splitUser[0],splitUser[1],splitUser[2]));
							}
					}
				file.close();
				 }
			}
			catch (Exception e) {
				System.out.print("Error with file processing");
			 	}
			}
	   
private static class ThreadServer implements Runnable {
    private Socket socket;

    ThreadServer(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
    	// .accept blocks until an in-bound connection on this port is attempted
        System.out.println("Connection recieved on socket!");

        // get the input stream from the connected socket
        try {
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        
        
        //DIFFIE
        
        byte[] clientPublicKeyEnc =  (byte[]) objectInputStream.readObject();
        //Instantiates a DH public key from the encoded key material.
       KeyFactory serverKeyFac = KeyFactory.getInstance("DH");
       X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(clientPublicKeyEnc);

       PublicKey clientPublicKey = serverKeyFac.generatePublic(x509KeySpec);
     
       //Server gets the DH parameters associated with Client's public key.
       DHParameterSpec dhParamFromClientPubKey = ((DHPublicKey)clientPublicKey).getParams();

       // Server creates own DH key pair
       System.out.println("Server: Generateing DH keypair ...");
       KeyPairGenerator serverKeyPairGen = KeyPairGenerator.getInstance("DH");
       serverKeyPairGen.initialize(dhParamFromClientPubKey);
       KeyPair serverKeyPair = serverKeyPairGen.generateKeyPair();

       // Create and initialize DH KeyAgreement object
       System.out.println("Initializing DH Agreement Object ...");
       KeyAgreement serverKeyAgree = KeyAgreement.getInstance("DH");
       serverKeyAgree.init(serverKeyPair.getPrivate());

       // Server encodes  public key, and sends it to Client.
       byte[] serverPublicKeyEncoded = serverKeyPair.getPublic().getEncoded();
       objectOutputStream.writeObject(serverPublicKeyEncoded);

       System.out.println("Server: Execute PHASE1 ...");
       serverKeyAgree.doPhase(clientPublicKey, true);

       
       byte[] clientSharedSecret =  (byte[]) objectInputStream.readObject();
       int clientLength = clientSharedSecret.length;
      
       byte[] serverSharedSecret = new byte[clientLength];
       int serverLength = serverKeyAgree.generateSecret(serverSharedSecret, 0);
       
     
       SecretKeySpec serverAesKey = new SecretKeySpec(serverSharedSecret, 0, 16, "AES");
     
       AlgorithmParameters aesParams = AlgorithmParameters.getInstance("AES");
       byte[] encodedParams =  (byte[]) objectInputStream.readObject();
       aesParams.init(encodedParams);
       
       Cipher serverCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
       serverCipher.init(Cipher.DECRYPT_MODE, serverAesKey, aesParams);
       
       
       byte[] ciphertext =  (byte[]) objectInputStream.readObject();
       byte[] recovered = decrypt(ciphertext, serverCipher);
       System.out.println(new String(recovered));
       
       //Shared secret now available to server and client, all messages
       //from this point on will now be encrypted using AES using the shared secret.

        // for every message, call printMessage(message);
 
       List<Message> listOfMessages = (List<Message>) objectInputStream.readObject();
       System.out.println("Received [" + listOfMessages.size() + "] messages from: " + socket);
      
       listOfMessages.forEach(msg -> 
       			{
					try {
						System.out.println(new String(serverCipher.doFinal(msg.getStatus())));
					} catch (IllegalBlockSizeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (BadPaddingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
    		   
    		   );
       
        }
        catch (Exception e) {
        	System.out.println("Error with sockets");
        }
        finally {
            try {
                socket.close();
            } catch (IOException e) {
            }
            System.out.println("Closed: " + socket);
    }
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


}

