package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import model.Constants;
import model.ContentHolder;
import model.interfaces.GameEngine;

public class CancelListener implements ActionListener, Constants
{
	
	private ContentHolder content;
	private JFrame frame;
	private GameEngine gameEngine;
	boolean check;
	public CancelListener(JFrame f,  GameEngine g, ContentHolder c, boolean chk)
	{
		gameEngine = g;
		content = c;
		frame = f;
		check = chk;
	}
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(check)
		{		
			content.setPlayer(null);	
		}
		else
		{
			content.setNewBet(null);
		}
		
		frame.setVisible(false);
		content.getTopBar().setAdd(true);
		content.getMenuBar().setAdd(true);
		if(gameEngine.getAllPlayers().size() > 0)
		{
			content.getMenuBar().setDelete(true);
			if(content.getTopBar().getBox().getSelectedItem() != HO)
			{
				if(gameEngine.getPlayer(content.getTopBar().returnId()).getBet() > 0)
				{
					content.getMenuBar().setClear(true);
					if(gameEngine.getPlayer(content.getTopBar().returnId()).getResult() == 0)
					{
						content.getTopBar().setBet(true);
						content.getMenuBar().setBet(true);
						content.getTopBar().setDeal(true);
						content.getMenuBar().setDeal(true);
					}
				}
				else
				{
					content.getTopBar().setBet(true);
					content.getMenuBar().setBet(true);
					content.getMenuBar().setClear(false);
				}
			}
		}
	}

}
