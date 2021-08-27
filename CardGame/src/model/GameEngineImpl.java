package model;

import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import model.interfaces.PlayingCard.Suit;
import model.interfaces.PlayingCard.Value;
import view.interfaces.GameEngineCallback;

public class GameEngineImpl implements GameEngine {
	private static final int DECK = 28;
	private Collection<Player> players;
	private Collection<PlayingCard> deck;
	private Collection<GameEngineCallback> callbacks;
	private int houseInt;
	private LinkedList<PlayingCard> playingDeck;
	private int counter = 0;

	
	public GameEngineImpl() {
		deck = new LinkedList<>();
		players = new LinkedList<>();
		callbacks = new LinkedList<>();
		populateHalfDeck();
	}
	
	/**I populate the deck up here because I thought it'd be cooler for the game Engine to create a deck
	each time it is created rather than having the shuffle create it */
	private void populateHalfDeck() {
		PlayingCard niceCard;
		for(Suit suit : Suit.values()) {
			for(Value value : Value.values()) {
				//For some reason I would get a null pointer on the first iteration, added to fix that
				if(value == null)
					continue;
				niceCard = new PlayingCardImpl(suit, value);
				deck.add(niceCard);
			}
		}
		/* this statement was added because I would usually just create a new deck every time
		 * I dealt to players, instead, it should now shuffle a playing deck and only re-shuffle that
		 * deck once the deck has run out of cards! (it still however uses the deck collection :) )
		 */
		playingDeck = (LinkedList<PlayingCard>) getShuffledHalfDeck();
	}
	
	/* My deal player method checks to ensure the variables are sufficient before using my counting
	 * cards method in order to set the result to the player (I thought this looked cleaner :) )
	 */
	@Override
	public void dealPlayer(Player player, int delay) throws IllegalArgumentException {
		if(delay < 0 || delay > 1000)
			throw new IllegalArgumentException("Illegal value for delay! :)");
		else if(player == null)
			throw new IllegalArgumentException("Player cannot be null! :)");
		player.setResult(countingCards(delay, false, player));
	}
	
	/* my deal house method uses the same counting Cards method however it passes through a specific
	 * set of variables including null in order to allow my single method to work with both players
	 * as well as the house! :)
	 */
	@Override
	public void dealHouse(int delay) throws IllegalArgumentException {
		if(delay < 0)
			throw new IllegalArgumentException("Illegal value for delay! :)");
		int housePoints = countingCards(delay, true, null);
		for(Player player : players) 
			applyWinLoss(player, housePoints);
		printCallbacks(houseInt, null, null);
		//As per the trace.PDF the reset bet has to be called after the callback but prior to reset bet()
		for(Player player : players)
		{
			player.resetBet();
		}
	}
	
	/** This is my inclusive looping system for both dealPlayer and DealHouse, uses player as a checksum
	rather than something like boolean checker because I need to parse player to the call backs */
	private int countingCards(int wait, boolean house, Player player) {
		int points = 0;
		int holder;
		PlayingCard simple;
		
		while(points <= GameEngine.BUST_LEVEL) 
		{
			/* To ensure my deck is replaced when the upper limit is reached, I added this
			 * component to replace the deck with a new one! while also reseting the counter
			 * This means I can run as many players as I want while the deck will always replenish :)
			 */
			if(counter == (DECK -1)) {
				playingDeck = (LinkedList<PlayingCard>) getShuffledHalfDeck();
				counter = 0;
			}
			simple = playingDeck.get(counter);
			holder = simple.getScore();
			if((points + holder) > GameEngine.BUST_LEVEL && house == false) {
				printCallbacks(2, player, simple);
				break;
			}
			//Had to ensure that my checking system (boolean) still worked on the house
			else if((points + holder) > GameEngine.BUST_LEVEL && house == true){
				printCallbacks(2, null, simple);
				break;
			}	
			points += holder;

			//This uses my player check to either call the next card or next house card callback
			if(!house) 
				printCallbacks(1, player, simple);
			else
				printCallbacks(1, null, simple);
			
			counter++;
			//Go to sleep baby 
			try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				System.out.println("Cannot sleep! :(");
			}
		}
		//final segment to ensure that result is called correctly for each player / house
		if(!house) 
			printCallbacks(points, player, null);
		else
			houseInt = points;
		return points;
	}
	
	
	/*I used to use like 3 overloaded methods here but I figured if I did some weird thing like this
	  it would be a nicer implementation (Excusing all the possible null pointer exceptions :) ) 
	  hopefully this isn't some form of software engineering sin!
	 */
	private void printCallbacks(int checker, Player player, PlayingCard card) {
		for(GameEngineCallback engine : callbacks) {	
			switch(checker) {
			case 1:
				if(player == null) {
					engine.nextHouseCard(card, this);
					break;
				}
				engine.nextCard(player, card, this);
				break;
			case 2:
				if(player == null) {
					engine.houseBustCard(card, this);
					break;
				}
				engine.bustCard(player, card, this);
				break;
			}
			
			if(checker > 2) {
				if(player != null) 
					engine.result(player, checker, this);
				else
					engine.houseResult(checker, this);
			}
		}
	}
	
	/** I didn't need to add an option in case the player lost because the bet was already pulled from the 
	chips and it gets wiped by using resetBet() */
	@Override
	public void applyWinLoss(Player player, int houseResult) {
		if(player.getResult() == houseResult) 
			player.setPoints((player.getPoints() + player.getBet()));
		else if(player.getResult() > houseResult) 
			player.setPoints((player.getPoints() + player.getBet()*2));
	}
	
	/** My addPlayer checks the size of the player array and then increments through it checking if the player
	ID matches before replacing the player in that position (like if you took a certain set at the table!) */
	@Override
	public void addPlayer(Player player) {
		if(player == null)
			throw new IllegalArgumentException("Player cannot be null!");
		for(int x = 0; x < players.size(); x++) {
			if(player.getPlayerId().equals(((LinkedList<Player>) players).get(x).getPlayerId())) {
				((LinkedList<Player>)players).remove(x);
				((LinkedList<Player>)players).add(x, player);
				return;
			}
		}
		players.add(player);
	}
	
	/* I use a lot of linked list parses but it works! and I am able to add as many players 
	 * as I would like as well as remove and override players that have the same ID :)
	 */
	
	@Override
	public Player getPlayer(String id) {
		for(int x = 0; x < players.size(); x++) {
			if(((LinkedList<Player>)players).get(x).getPlayerId().equals(id))
				return ((LinkedList<Player>)players).get(x);
		}
		return null;
	}
	
	@Override
	public boolean removePlayer(Player player) {
		for(int x = 0; x < players.size(); x++) {
			if(player.getPlayerId().equals(((LinkedList<Player>)players).get(x).getPlayerId())) {
				((LinkedList<Player>)players).remove(x);
				return true;
			}
		}
		return false;
	}

	/* one of my harder function implementations */
	
	@Override
	public boolean placeBet(Player player, int bet) {
		if(player.setBet(bet))
			return true;
		return false;
	}
	
	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		callbacks.add(gameEngineCallback);
	}

	@Override
	public boolean removeGameEngineCallback(GameEngineCallback gameEngineCallback) {
		if(callbacks.contains(gameEngineCallback)){
			callbacks.remove(gameEngineCallback);
			return true;
		}
		return false;
	}

	/**creates a linkedList of players and sorts them (via the compareTo()  toString() I think) and then
	 * returns them as an unmodifiable collection as per the assignment sheet 
	 */
	
	@Override
	public Collection<Player> getAllPlayers() {
		LinkedList<Player> playersTemp = (LinkedList<Player>) players;
		Collections.sort(playersTemp);
		Collection<Player> unmodified = Collections.unmodifiableCollection(playersTemp);
		return unmodified;
	}
	
	/* This function uses my collection and while I know my playing Deck implementation is probably 
	 * the wrong way to go around it, shuffling the deck will always use the collection so hopefully 
	 * this keeps up the idea of the assignment! :)
	 */
	@Override
	public Deque<PlayingCard> getShuffledHalfDeck() {
		Collections.shuffle((List<PlayingCard>) deck);
		return (Deque<PlayingCard>) deck;
	}

}
