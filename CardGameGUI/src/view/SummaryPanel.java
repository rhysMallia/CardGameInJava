package view;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.Constants;
import model.ContentHolder;
import model.interfaces.GameEngine;
import model.interfaces.Player;

@SuppressWarnings("serial")
public class SummaryPanel extends JPanel implements Constants
{
	private ContentHolder content;
	private GameEngine gameEngine;
	private JTable Table;
	String[] data; 
	DefaultTableModel model;
	String[] columns = {"ID", "Player Name", "Points", "Bet", "Result", "Outcome"};
	
	public SummaryPanel(GameEngine g, ContentHolder content)
	{
		this.content = content;
		this.gameEngine = g;
		
		model = new DefaultTableModel();
		for(String data : columns)
		{
			model.addColumn(data);
		}
		addPanel(model);
	}
	
	public void update()
	{
		model.setRowCount(0);
		addHouse(model);
		for(Player player : gameEngine.getAllPlayers())
		{
			model.addRow(new Object[] {player.getPlayerId(), player.getPlayerName(),
			player.getPoints(), player.getBet(), player.getResult(), 
			content.getPlayerFromMap(player.getPlayerId()).getOutcome()});
		}
		addPanel(model);
		
	}
	
	/** this code, while being a duplicate, was nessicary as I could not update my summary from the 
	 * GUI threads and I didn't have enough time to properly fix this issue, fortunately they work in combination
	 */
	public void updateTable()
	{
		model.setRowCount(0);
		addHouse(model);
		for(Player player : gameEngine.getAllPlayers())
		{
			model.addRow(new Object[] {player.getPlayerId(), player.getPlayerName(),
			player.getPoints(), player.getBet(), player.getResult(), 
			content.getPlayerFromMap(player.getPlayerId()).getOutcome()});
		}
		Table.setPreferredScrollableViewportSize(Table.getPreferredSize());
	}
	
	public void addHouse(DefaultTableModel model)
	{
		model.addRow(new Object[] { HI, HO, null, null, content.getPlayerFromMap("H").getResult(), 
				content.getPlayerFromMap("H").getOutcome()} );
	}
	
	public void addPanel(DefaultTableModel model) 
	{
		removeAll();
		Table = new JTable(model);
		Table.setPreferredScrollableViewportSize(Table.getPreferredSize());
		Table.revalidate();
		add(new JScrollPane(Table));
	}
}
