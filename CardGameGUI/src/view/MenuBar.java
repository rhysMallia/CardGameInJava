package view;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.ButtonListener;
import controller.ListListener;
import model.Constants;
import model.ContentHolder;
import model.interfaces.GameEngine;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar implements Constants {
	private GameEngine gameEngine;
	private ContentHolder content;
	private JMenuItem NewPlayer;
	private JMenuItem DeletePlayer;
	private JMenuItem Bet;
	private JMenuItem ClearBet;
	private JMenuItem Deal;
	private JMenuItem Exit;
	private final ListListener listL;
	private final ButtonListener listener;
	public MenuBar(GameEngine g, ContentHolder c)
	{
		gameEngine = g;
		content = c;
		listener = new ButtonListener(gameEngine, content);
		listL = new ListListener(gameEngine, content);
		JMenu menu = new JMenu("Menu");
		
		NewPlayer = new JMenuItem(PLAYER);
		NewPlayer.addActionListener(listener);
		
		DeletePlayer = new JMenuItem(DELETE);
		DeletePlayer.addActionListener(listL);
		DeletePlayer.setEnabled(false);
		
		Bet = new JMenuItem(BET);
		Bet.addActionListener(listener);
		Bet.setEnabled(false);
		
		ClearBet = new JMenuItem(CLEAR);
		ClearBet.addActionListener(listL);
		ClearBet.setEnabled(false);
		
		Deal = new JMenuItem(DEAL);
		Deal.addActionListener(listener);
		Deal.setEnabled(false);
		
		Exit = new JMenuItem(EXIT);
		Exit.addActionListener(listL);
		
		menu.add(NewPlayer);
		menu.add(DeletePlayer);
		menu.add(Bet);
		menu.add(ClearBet);
		menu.add(Deal);
		menu.add(Exit);
		
		add(menu);
	}
	
	public void setAdd(boolean check)
	{
		NewPlayer.setEnabled(check);
	}
	
	public void setBet(boolean check)
	{
		Bet.setEnabled(check);
	}
	
	public void setDeal(boolean check)
	{
		Deal.setEnabled(check);
	}
	
	public void setDelete(boolean check)
	{
		DeletePlayer.setEnabled(check);
	}
	
	public void setClear(boolean check)
	{
		ClearBet.setEnabled(check);
	}
	
}
