package view;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToolBar;

import controller.ButtonListener;
import controller.ComboListener;
import model.Constants;
import model.ContentHolder;
import model.interfaces.GameEngine;
import model.interfaces.Player;

@SuppressWarnings("serial")
public class TopToolBar extends JToolBar implements Constants
{
	private GameEngine gameEngine;
	private ContentHolder content;
	private JButton NewPlayer;
	private JButton Bet;
	private JButton Deal;
	private JComboBox<String> Box;
	private ButtonGroup group = new ButtonGroup();
	private final ButtonListener listener;
	
	public TopToolBar(GameEngine g, ContentHolder c)
	{
		gameEngine = g;
		content = c;
		listener = new ButtonListener(gameEngine, content);
		
		/** adding our buttons and listeners! **/
		NewPlayer = new JButton(PLAYER);
		NewPlayer.addActionListener(listener);
		group.add(NewPlayer);
		add(NewPlayer);
		
		Bet = new JButton(BET);
		Bet.addActionListener(listener);
		Bet.setEnabled(false);
		group.add(Bet);
		add(Bet);
		
		Deal = new JButton(DEAL);
		Deal.addActionListener(listener);
		Deal.setEnabled(false);
		group.add(Deal);
		add(Deal);
		
		/** adding our combo box **/
		Box = new JComboBox<String>();
		Box.addActionListener(new ComboListener(gameEngine, content));
		add(Box);
		
		
	}
	
	public void setAllButtons(boolean value)
	{
		NewPlayer.setEnabled(value);
		Bet.setEnabled(value);
		Deal.setEnabled(value);
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
	
	public void refresh()
	{
		/** for the combobox, we have to remove all the previous entries and then 
		 * re-add them via the getAllplayers method or else we get duplication
		 */
		Box.removeAllItems();
		Box.revalidate();
		for(Player player : gameEngine.getAllPlayers())
		{
			Box.addItem(player.getPlayerId() + ". " + player.getPlayerName());
		}
		Box.addItem(HO);
	}
	
	public JComboBox<String> getBox() 
	{
		return Box;
	}
	
	public String returnId() 
	{
		String temp = (String)Box.getSelectedItem();
		return temp.substring(0, temp.indexOf("."));
	}
	
}
