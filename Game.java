package server;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable
{
    private Deck deck;
    private Dealer dealer;
    private ArrayList<User> players = new ArrayList<User>();
    private ArrayList<User> waitingPlayers = new ArrayList<User>();
    private User activePlayer;
    private User lastPlayer;
    private boolean newRound;
    private boolean finishRound;
    private int updates = 0;
    
    public Game(User player)
    {
    	activePlayer = player;
    	addPlayer(player);
    	newRound =true;
    	finishRound = false;
    	deck = new Deck();
    	dealer = new Dealer();
    	dealer.hit(deck.randomDrawCard());
    	lastPlayer = players.get(players.size()-1);
    //	newRoundSetup();
    }
    
    public Deck getDeck()
    {
    	return this.deck;
    }
    
    public Dealer getDealer()
    {
    	return this.dealer;
    }
    
    public ArrayList<User> getPlayers()
    {
    	return this.players;
    }
    //ADDED: MM
    //IS FULL
    
    public boolean isFull()
    {
    	return ((players.size() + waitingPlayers.size()) >= 4);
    }
    
    public void addPlayerToWait(User user)
    {
    	waitingPlayers.add(user);
    	return;
    }
    
    public void addPlayer(User user)
    {
    	players.add(user);
    	lastPlayer = players.get(players.size()-1);
    	return;
    }
    
    public User getActivePlayer() {
    	return activePlayer;
    }
    
    public void setActivePlayer(User user)
    {
    	activePlayer = user;
    }
    
    public void setNewRound(boolean status)
    {
    	newRound = status;
    }
    
    public boolean getNewRound()
    {
    	return newRound;
    }
    
    public void addWaitList()
    {
    	waitingPlayers.forEach(player-> {
    	addPlayer(player);
    	});
    	waitingPlayers.clear();
    }
    
    public boolean waitList()
    {
    	return (waitingPlayers.size() > 0);
    }
    
    public void newRoundSetup()
    {
    	deck = new Deck();
    	setFinishRound(false);
    	players.forEach(user ->
    	{
    		user.getHand().emptyHand();   
    		user.getHand().resetBet();
    	});
    	dealer.getHand().emptyHand();
    	dealer.getHand().hit(deck.randomDrawCard());
    	if(waitList())
    	addWaitList();
    	activePlayer = players.get(0);
    	incrementUpdate();
    }
    
    public String toString() {
    	String state = "Dealer: " + dealer.getHand().toString() + '\n';
    	StringBuilder build = new StringBuilder();
    	build.append(state);
    	players.forEach(user ->
    	{
    		build.append(user.getName() + ": " + user.getHand().toString() + '\n');
    	});
    	return build.toString();
    }
    
    public void nextActivePlayer()
    {
    	int newIndex = players.indexOf(activePlayer)+1;
    	activePlayer = players.get(newIndex);
    }
    
    public User getLastPlayer()
    {
    	return lastPlayer;
    }
    
    public void setFinishRound(boolean bool)
    {
    	finishRound = bool;
    }
    
    public boolean getFinishRound()
    {
    	return finishRound;
    }
    
    public int getUpdate() {
    	return updates;
    }
    public void incrementUpdate() {
    	updates = updates + 1;
    }
}


