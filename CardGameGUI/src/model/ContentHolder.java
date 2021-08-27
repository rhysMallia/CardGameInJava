package model;

import java.util.HashMap;
import java.util.Map;

import view.CardDisplay;
import view.IconFactory;
import view.MenuBar;
import view.NewBet;
import view.NewPlayer;
import view.SummaryPanel;
import view.TopToolBar;

public class ContentHolder 
{
	private TopToolBar TopBar;
	private CardDisplay Display;
	private SummaryPanel Summary;
	private NewPlayer newPlayer;
	private NewBet newBet;
	private MenuBar Menu;
	private IconFactory icons;
	private Map<String, PlayerDetails>playerMap;
	private int players;
	
	/** holds all of our UI and extra player data needed to be 
	 * carried around to functions and classes 
	 */
	public ContentHolder() 
	{
		playerMap = new HashMap<String, PlayerDetails>();
		players = 0;
	}
	
	public void setTopBar(TopToolBar top) 
	{
		TopBar = top;
	}
	
	public TopToolBar getTopBar() 
	{
		return TopBar;
	}
	
	public void setDisplay(CardDisplay card)
	{
		Display = card;
	}
	
	public CardDisplay getDisplay()
	{
		return Display;
	}
	
	public void setSummary(SummaryPanel summary)
	{
		Summary = summary;
	}
	
	public SummaryPanel getSummary()
	{
		return Summary;
	}
	
	public void setPlayer(NewPlayer player)
	{
		newPlayer = player;
	}
	
	public NewPlayer getPlayer()
	{
		return newPlayer;
	}
	
	public void setNewBet(NewBet bet) 
	{
		newBet = bet;
	}
	
	public NewBet getNewBet()
	{
		return newBet;
	}
	
	public Map<String, PlayerDetails> getMap() 
	{
		return playerMap;
	}
	
	public PlayerDetails getPlayerFromMap(String id)
	{
		return playerMap.get(id);
	}
	
	public void addPlayerToMap(String id, String name)
	{
		playerMap.put(id, new PlayerDetails(id, name));
	}
	
	public void removePlayerFromMap(String id)
	{
		playerMap.remove(id);
	}
	
	public void printMap()
	{
		for(String key : playerMap.keySet())
		{
			System.out.println(key);
		}
	}
	
	public void setMenuBar(MenuBar m)
	{
		Menu = m;
	}
	
	public MenuBar getMenuBar()
	{
		return Menu;
	}
	
	public int getPlayerInt()
	{
		return players;
	}
	
	public void iteratePlayerInt()
	{
		players++;
	}
	
	public void setIcons(IconFactory i)
	{
		icons = i;
	}
	
	public IconFactory getIcons()
	{
		return icons;
	}
}
