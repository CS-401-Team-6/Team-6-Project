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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import client.Client;
import client.ConnectionMessage;

//Server Class, Michael Maddux
//Contains the main method for the server machine
//Listens for connections on a set port, establishes a new thread for Clients connecting
//A Diffie-Hellman Key Exchange is used to establish secret shared information.
//Shared information is used to encrypt and decrypt a SHA-256 hashed username + password with AES

public class Server {
	//users is a Map which holds all registered users, maps the username to a User object corresponding to the username   
	private static Map<String, User> users = new HashMap<String, User>();
	//games holds the instances of current active games.
	private static List<Game> games = new ArrayList<>();
	//dataFile is the name of the Security Log file.
	private static String dataFile = "secLog";
	//userFile is the name of the User Database file.
	private static String  userDataBase = "userdatabase";
	
	//Main method for server function.
	public static void main(String[] args) throws Exception {
		//Call load(), loads the Users from the user database into the users Map.   
		load();
		//Print user names and balances in Server console for verification of load.
		users.forEach((name, user) -> 
		{
			System.out.println(name);
			System.out.println(user.getBalance());	
		});
		//Create a new Socket and listen, creating a new thread upon a Client connection
	    try(var socketVar = new ServerSocket(5555)) {
	    	System.out.println("Awaiting connections on port 5555...");
	    	//Create THread Pool allowing 50 Client connections
	        var pool = Executors.newFixedThreadPool(50);
	        while (true) {
	        		//Execute ThreadServer run, which is the Server method unique to each thread
	        		pool.execute(new ThreadServer(socketVar.accept()));
            	}
	        }
	   }
	   
	//Method: load()
	//Pre-conditions:Users have not been loaded
	//Post-conditions:User information held in file "userdatabase" is loaded into
	//		users Map, using username as key for each User object.	
	public static void load() {
		//userinfo holds the raw input for each line from userdatabase
		String userInfo;
		//Attempt to open userdatabase. If it does not exist, create it.
		try {
		File dataFile = new File(userDataBase);
			if (dataFile.createNewFile()) {
				return;
			}
			else {
				//Read the file, line by line
				Scanner file = new Scanner (dataFile);
				while (file.hasNextLine())
					{
						//Split the '/' separated User info into its components
						userInfo = file.nextLine();
						String[] splitUser = userInfo.split("/");
						//If the splitUser has the correct form of three parts, add it to the users Map
						if (splitUser.length == 3) {
							users.put(splitUser[0], new User(splitUser[0],splitUser[1],splitUser[2]));
							}
					}
				file.close();
				 }
			}
			catch (Exception e) {
				System.out.print("Error with file processing");
			 	}
	}
	   
	//Method: save()
		//Pre-conditions:User database file exists
		//Post-conditions:User information from Map users is stored in file at userDataBase" in
		//form username/encryptedPassword/balance
	public static void save() throws IOException {
				FileWriter userFile = new FileWriter(userDataBase);
				users.forEach((name, user) ->
					{
						try {
							userFile.write(name + '/' + user.getPass() + '/' + user.getBalance() + '\n');
						} catch (IOException e) {
							e.printStackTrace();
						}
					} );
				userFile.close();
				return;
	}
			
//A new ThreadServer is created and its run executed upon a new thread being created.   
private static class ThreadServer implements Runnable {
    private Socket socket;
    //Static int used to identify unique client sessions
    private static int instance = 0;
    //id represents client session, created using instance variable.
    private int id;
    //Represents the table being utilized in this thread
    private Game gameInstance;
    //User object of logged in User
    private User user;
    
    //Constructor, takes socket, established on creation in Server main, and assigns id based on static instance int.
    ThreadServer(Socket socket) {
        this.socket = socket;
        instance ++;
        id = instance;
    }
    
    //run() is the main executable for an individual thread
    @Override
    public void run() {
    	// Accept blocks until an in-bound connection on this port is attempted
        System.out.println("Connection recieved on socket!");
        //Get the input/output streams from the connected socket
        try {
        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        
        //Begin the Diffie-Hellman exchange
        //Take the encrypted public key information from the Client.
        byte[] clientPublicKeyEnc =  (byte[]) objectInputStream.readObject();
        
        //Instantiates a DH public key from the encoded key material.
        KeyFactory serverKeyFac = KeyFactory.getInstance("DH");
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(clientPublicKeyEnc);
        
        //Generate the client's public key
        PublicKey clientPublicKey = serverKeyFac.generatePublic(x509KeySpec);
        
        //Server gets the DH parameters associated with Client's public key.
        DHParameterSpec dhParamFromClientPubKey = ((DHPublicKey)clientPublicKey).getParams();
        
        //Server creates own DH key pair
        System.out.println("Server: Generating DH keypair ...");
        KeyPairGenerator serverKeyPairGen = KeyPairGenerator.getInstance("DH");
        serverKeyPairGen.initialize(dhParamFromClientPubKey);
        KeyPair serverKeyPair = serverKeyPairGen.generateKeyPair();
        
        //Create and initialize DH KeyAgreement object
        System.out.println("Initializing DH Agreement Object ...");
        KeyAgreement serverKeyAgree = KeyAgreement.getInstance("DH");
        serverKeyAgree.init(serverKeyPair.getPrivate());
        
        //Server encodes  public key, and sends it to Client.
        byte[] serverPublicKeyEncoded = serverKeyPair.getPublic().getEncoded();
        objectOutputStream.writeObject(serverPublicKeyEncoded);
        
        //After serverKeyAgree.doPhase, exchange of information is complete.
        System.out.println("Server: Exchange complete");
        serverKeyAgree.doPhase(clientPublicKey, true);

        //Read shared Secret from client 
        byte[] clientSharedSecret =  (byte[]) objectInputStream.readObject();
        int clientLength = clientSharedSecret.length;
      
        //Create a Shared Secret from the exchanged material
        byte[] serverSharedSecret = new byte[clientLength];
        int serverLength = serverKeyAgree.generateSecret(serverSharedSecret, 0);
       
        //Create AES parameters
        SecretKeySpec serverAesKey = new SecretKeySpec(serverSharedSecret, 0, 16, "AES");
        AlgorithmParameters aesParams = AlgorithmParameters.getInstance("AES");
        byte[] encodedParams =  (byte[]) objectInputStream.readObject();
        aesParams.init(encodedParams);
        //Initialize a Cipher with the AES keye and parameters
        Cipher serverCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        serverCipher.init(Cipher.DECRYPT_MODE, serverAesKey, aesParams);       
       //Shared secret now available to server and client, all messages
       //from this point on will now be encrypted using AES using the shared secret.

       boolean connected = true;
	//Process ConnectionMessages from Client
       while(connected) {
    	   ConnectionMessage msg = (ConnectionMessage) objectInputStream.readObject();
    	   System.out.println("Received message from: " + socket);
					try {
						byte[] encryptedMessage = serverCipher.doFinal(msg.getStatus());
						//Register case
						if(msg.getType().compareTo("register") == 0)
							{
							//User name taken
							if (users.containsKey(msg.getText()))
								{
								objectOutputStream.writeObject(new ConnectionMessage("register", msg.getStatus(), "/FAIL"));
								secWrite("secLog", "USER " +  msg.getText() +  " REGISTRATION FAILED, NAME ALREADY TAKEN");
								}
							else {
							//Add new User to user map and user database
							System.out.println("Current User Size:" + users.size());
							System.out.println("Adding" + msg.getText());
							users.put(msg.getText(), new User(msg.getText(), bytesToHex(encryptedMessage)));
							System.out.println("New User Size:" + users.size());
							Server.save();
							secWrite("secLog", "NEW USER " + msg.getText() + " REGISTERED");
							objectOutputStream.writeObject(new ConnectionMessage("register", msg.getStatus(), "/SUCCESS"));
							}
							}
						//Login case
						if(msg.getType().compareTo("login") == 0)
						{
							//User name does not exist case
							if (!users.containsKey(msg.getText()))
							{
								objectOutputStream.writeObject(new ConnectionMessage("login", msg.getStatus(), "/NO USER"));
								secWrite("secLog","LOGIN FAILED, NO USER " + msg.getText());
							}
							//User name does exist
							else {
								String decrypted = bytesToHex(decrypt(msg.getStatus(), serverCipher));
								System.out.println("ENCRYPTED: " + bytesToHex(msg.getStatus()));
								System.out.println("DECRYPTED: " + decrypted);
								System.out.println("ON FILE: " + users.get(msg.getText()).getPass());
								//Passwords do not match case
								if (!users.get(msg.getText()).getPass().equals(decrypted))
								{
									objectOutputStream.writeObject(new ConnectionMessage("login", msg.getStatus(), "/INVALID PASS"));
									secWrite("secLog","LOGIN FAILED, INVALID PASSWORD FOR " + msg.getText());
								}
								//Login success
								else {
									objectOutputStream.writeObject(new ConnectionMessage("login", msg.getStatus(), "/SUCCESS"));
									secWrite("secLog","LOGIN OF " + msg.getText() + "COMPLETE");
									user = users.get(msg.getText());
									//If no active games, create a new one and add user
									if(games.isEmpty())
										{
										System.out.println("Creating new game");
									 	games.add(new Game(user));
									 	gameInstance = games.get(0);
										}
									//Place a user in an empty game
									else {
										boolean placed = false;
										for(int i = 0; i < games.size(); i++)
										{
											if (!games.get(i).isFull())
											{
												games.get(i).addPlayerToWait(user);
												gameInstance = games.get(i);
												placed = true;
												break;
											}
										}
										//If no empty games exist, create a new Game with the user		
										if (!placed)
											{
											games.add(new Game(user));
											gameInstance = games.get(games.size() -1);
											}
									}
									//Run the game
									System.out.println("GAME RUN");
									gameRun(objectOutputStream, objectInputStream);
								}
							}
						}
					} catch (IllegalBlockSizeException e) {
						e.printStackTrace();
					} catch (BadPaddingException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
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
	   
    //Server side game logic
    //
    private void gameRun(ObjectOutputStream objectOutputStream, ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException, InterruptedException
    {
    	MessageProcessor mp = new MessageProcessor();
    	boolean logout = false;
	//gameUpdate used to determine if a new game state should be sent. GameInstance holds an update int that increases when the game state changes
    	int gameUpdate = -1;

    	User currentUser = gameInstance.getActivePlayer();
    	while (!logout)
    	{
		//Determines activePlayer from game. If equal to the user associated with this thread, sends the ACTIVE message, which 
		//the client will read and begin the active player message sequence
    		if(user.equals(gameInstance.getActivePlayer()) )
    		{
    			objectOutputStream.writeObject(new Message("ACTIVE", "SENT", "SENT", gameInstance));
    			Message userAction = (server.Message) objectInputStream.readObject();
			//Process messages until the Client is Done, either through Stand, Bust, or hitting 21.
    			while(!userAction.getType().contentEquals("DONE")) {
    				System.out.println(userAction.getType());
    				objectOutputStream.reset();
    				objectOutputStream.writeObject(mp.process(userAction, gameInstance));
    				objectOutputStream.reset();
    				System.out.println(gameInstance.toString());
    				userAction = (server.Message) objectInputStream.readObject();
    			}
    			Message done = mp.process(userAction, gameInstance);
			//If the done message is not Finished, that means more players must still play. Set the next active player and continue.
    			if(!done.getType().equals("FINISH"))
    			{
    				gameInstance.nextActivePlayer();
    				objectOutputStream.reset();
    				objectOutputStream.writeObject(done);
    			}
			//A FINISH Type in the done message means that is the last player and last action. Results should be sent out and a new round should begin.
    			else if(done.getType().equals("FINISH"))
    			{
    				objectOutputStream.reset();
    				objectOutputStream.writeObject(done);
    				Thread.sleep(300);
    				userAction = (server.Message) objectInputStream.readObject();
    				objectOutputStream.writeObject(mp.process(userAction, gameInstance));
    				objectOutputStream.reset();
    			}
    		}
		//For clients who are not the active player
    		else if(!gameInstance.getActivePlayer().equals(user))
    		{	
			//If the local gameUpdate number does not equal the gameInstance update number, a new message must be sent
    			if(!(gameUpdate==gameInstance.getUpdate()))
    			{
				//If the gameInstance is in the FinishRound stage, send the RESULTS message, so the client knows to display the results
    				if(gameInstance.getFinishRound())
    						{
    					objectOutputStream.writeObject(new Message("RESULTS", gameInstance));
    	    				objectOutputStream.reset();	
    						}
				//Else, send a WAIT packet with the gameInstance. Client will print new game state.
    				else {
    					objectOutputStream.writeObject(new Message("WAIT", gameInstance));
    					objectOutputStream.reset();	
					//Since a gameInstance was sent, gameUpdate becomes the Update value of the gameInstance we just sent.
					//No longer need to send this gameInstance, until another update is made
    					gameUpdate=gameInstance.getUpdate();
    				}
    			}
    		}

    		Thread.sleep(200);
    }
    }
    
    //Helper function to decrypt encrypted ciphertext, given a predefined cipher.
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
    
    //Helper function to write to the end of the security log
    private void secWrite(String fileName, String textIn) throws IOException
    {
    	FileWriter fileWrite = new FileWriter(fileName, true);
    	BufferedWriter buffWrite = new BufferedWriter(fileWrite);
    	Date time = new Date();
    	buffWrite.append(time.toString() + " Session ID: " + id + " " + textIn + '\n');
    	buffWrite.close();
    }
    
    //Helper function to convert an array of bytes into Hex values for visual processing
    public static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
        String hex = Integer.toHexString(0xff & hash[i]);
        if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
}



