package model;

import java.util.ArrayList;
import model.interfaces.PlayingCard;

public class PlayerDetails 
{
	private String id;
	private String name;
	private String outcome;
	private int result;
	private ArrayList<PlayingCard>cardArray;
	private PlayingCard bust;
	/** an extra class that is used to carry data 
	 * for the player and his drawn cards, including one
	 * for the house, this is important as most UI components
	 * rely on this data rather than the engines pure data
	 */
	public PlayerDetails(String i, String n)
	{
		id = i;
		name = n;
		cardArray = new ArrayList<PlayingCard>();
	}
	
	public void setId(String input) 
	{
		id = input;
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setName(String input) 
	{
		name = input;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setOutcome(int player, int house)
	{
		if(player > house)
		{
			outcome = "Win!";
		}
		else if(player == house)
		{
			outcome = "Draw!";
		}
		else
		{
			outcome = "Loss!";
		}
	}
	
	public void resetOutcome()
	{
		outcome = null;
	}
	
	public String getOutcome()
	{
		return outcome;
	}
	
	public PlayingCard returnCard(int id)
	{
		
		return cardArray.get(id);
	}
	
	public void addCard(PlayingCard card)
	{
		cardArray.add(card);
	}
	
	public ArrayList<PlayingCard> getArray()
	{
		return cardArray;
	}
	
	public void cleanArray()
	{
		cardArray.clear();
	}
	public void addBust(PlayingCard card)
	{
		bust = card;
	}
	
	public PlayingCard getBust()
	{
		return bust;
	}
	
	public void setResult(int r)
	{
		result = r;
	}
	
	public int getResult()
	{
		return result;
	}
}
