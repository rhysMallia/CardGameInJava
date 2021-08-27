package model;

import model.interfaces.PlayingCard;
import java.util.Map;
import java.util.HashMap;

public class PlayingCardImpl implements PlayingCard {
	private Suit suit;
	private Value value;
	private Map<Value, Integer> valueMap;
	
	public PlayingCardImpl(Suit suit, Value value) {
		valueMap = new HashMap<Value, Integer>();
		populateMap(valueMap);
		this.suit = suit;
		this.value = value;
	}
	
	/** I thought it would be easier to create a map of Strings and ints associated
	 *  with the values rather than having to create a switch statement or something similar!
	 *  This is populated each time a card is created, I thought that might be better coding practice
	 *  (Which really means I stole the idea from the tutes!)
	 */
	private void populateMap(Map<Value, Integer> valueMap) {
		valueMap.put(Value.EIGHT, 8);
		valueMap.put(Value.NINE, 9);
		valueMap.put(Value.TEN, 10);
		valueMap.put(Value.JACK, 10);
		valueMap.put(Value.QUEEN, 10);
		valueMap.put(Value.KING, 10);
		valueMap.put(Value.ACE, 11);
	}

	@Override
	public Suit getSuit() {
		return suit;
	}

	@Override
	public Value getValue() {
		return this.value;
	}
	
	/** Searches through my Map and returns the key (int) value when it correctly 
	 *  finds the associated key, which will be turned into a comparable string via the
	 *  string(value) method!
	 */
	
	@Override
	public int getScore() {
		return(valueMap.get(getValue()));
	}
	

	private String String(Value value) {
		return value.name();
	}
	
	private String String(Suit suit) {
		return suit.name();
	}
	
	/** This method moves the latter part of  the string into a new variable, it then proceeds
	 *  to lower that part of the string, before re-attaching it to the capitialised front!
	 */
	
	 @Override
	 public String toString() {
		 String valueTemp = "";
		 String suitTemp = "";
		 
		 valueTemp = String(value).substring(1, String(value).length()).toLowerCase();
		 suitTemp = String(suit).substring(1, String(suit).length()).toLowerCase();
		 
		 valueTemp = String(value).substring(0, 1) + valueTemp;
		 suitTemp = String(suit).substring(0, 1) + suitTemp;
		 return String.format("Suit: %s, Value: %s, Score: %d", suitTemp, valueTemp, getScore());
	 }
	 
	
	@Override
	public boolean equals(PlayingCard card) {
		return(suit.equals(card.getSuit()) && value.equals(card.getValue()));
	}
	
	@Override
	public boolean equals(Object card) {
		if(card instanceof PlayingCard) {
			PlayingCard niceCard = (PlayingCard) card;
			return(suit.equals(niceCard.getSuit()) && value.equals(niceCard.getValue()));
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return suit.hashCode() + value.hashCode();
	}
}
