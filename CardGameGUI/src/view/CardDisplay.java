package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.Constants;
import model.ContentHolder;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import model.interfaces.PlayingCard.Value;



@SuppressWarnings("serial")
public class CardDisplay extends JPanel implements Constants
{
	private int squareX;
	private int squareY;
	private int squareW;
	private int squareH;
	private Player player;
	private ContentHolder content;
	private Dimension frameSize;
	private HashMap<Value, String>valueMap;
	private boolean houseCheck;
	
	public CardDisplay(ContentHolder c)
	{
		content = c;
		frameSize = this.getSize();
		valueMap = new HashMap<Value, String>();
		populateMap(valueMap);
	}
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		Graphics2D g2d2 = (Graphics2D) g.create();
		if(player != null || houseCheck != false)
		{
			int count = 0;
			String playerId = "";
			if(houseCheck)
			{
				playerId = "H";
			}
			else
			{
				playerId = player.getPlayerId();
			}
			ArrayList<PlayingCard>array = content.getPlayerFromMap(playerId).getArray();
			/** loops through the card array inside of my player map in order to render the correct
			 * amount of cards as well as having the impression of a live card draw
			 */
			for(int i = 0; i < array.size(); i++)
			{
				PlayingCard card = array.get(i);
				frameSize = this.getSize();
				squareW = (frameSize.width / MAGIC);
				squareH = (int)(squareW*HEIGHT_MOD);
				squareY = (frameSize.height / 4);
				squareX = (BUFFER*2) + (squareW * count) + (BUFFER*count);
				
				/** painting the curved rectangle in the correct position as to
				 * not overlap
				 */
				g2d.setColor(Color.WHITE);
				g2d.drawRoundRect( squareX,
				squareY, squareW, squareH, CURVE, CURVE);
				g2d.fillRoundRect(squareX,
				squareY, squareW, squareH, CURVE, CURVE);
				
				/** painting the image in the centre of the card, adjusts live to your res! **/
				ImageIcon image = content.getIcons().getImageIcon(card.getSuit());
				Image holder = image.getImage().getScaledInstance(squareW / MAGIC, squareH / MAGIC, Image.SCALE_DEFAULT);
				image = new ImageIcon(holder);
				image.paintIcon(this, g2d, squareX + (squareW / 2) - ((squareW / MAGIC)/2), 
						squareY + (squareH / 2) - ((squareH / MAGIC)/2));
				
				/** painting the two strings, this also adjusts its sized based on 
				 * your screen resolution!
				 */
				g2d2.setFont(new Font("SERIF", Font.PLAIN, squareW / 6));
				g2d2.drawString(valueMap.get(card.getValue()), squareX + (BUFFER*2), squareY + (squareW / 6) + (BUFFER));
				g2d2.drawString(valueMap.get(card.getValue()), 
						(squareX + squareW) - (squareW / MAGIC) - BUFFER, 
						(squareY + squareH) - (squareH / MAGIC) + (BUFFER*2));
				count++;
			}
			/** dirty, but paints the bust card if it is available in the player
			 * array
			 */
			if(content.getPlayerFromMap(playerId).getBust() != null)
			{
				PlayingCard card = content.getPlayerFromMap(playerId).getBust();
				squareX = (BUFFER*3) + (squareW * count) + (BUFFER*count);
				
				g2d.setColor(Color.GRAY);
				g2d.drawRoundRect( squareX,
						squareY, squareW, squareH, CURVE, CURVE);
				g2d.fillRoundRect(squareX,
						squareY, squareW, squareH, CURVE, CURVE);
				
				ImageIcon image = content.getIcons().getImageIcon(
				content.getPlayerFromMap(playerId).getBust().getSuit());
				Image holder = image.getImage().getScaledInstance(squareW / MAGIC, squareH / MAGIC, Image.SCALE_DEFAULT);
				image = new ImageIcon(holder);
				image.paintIcon(this, g2d, squareX + (squareW / 2) - ((squareW / MAGIC)/2), 
						squareY + (squareH / 2) - ((squareH / MAGIC)/2));
				
				g2d2.setFont(new Font("SERIF", Font.PLAIN, squareW / 6));
				g2d2.drawString(valueMap.get(card.getValue()), squareX + (BUFFER*2), squareY + (squareW / 6) + (BUFFER));
				g2d2.drawString(valueMap.get(card.getValue()), (squareX + squareW) - (squareW / MAGIC) - BUFFER, 
						(squareY + squareH) - (squareH / MAGIC) + (BUFFER*2));
				count++;
				}
		}
	}
	
	public void setPlayer(Player p)
	{
		player = p;
	}
	
	public void removePlayer()
	{
		player = null;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	private void populateMap(HashMap<Value, String> valueMap) {
		valueMap.put(Value.EIGHT, "8");
		valueMap.put(Value.NINE, "9");
		valueMap.put(Value.TEN, "10");
		valueMap.put(Value.JACK, "J");
		valueMap.put(Value.QUEEN, "Q");
		valueMap.put(Value.KING, "K");
		valueMap.put(Value.ACE, "A");
	}
	
	public void setHouseCheck(boolean c)
	{
		houseCheck = c;
	}
	
}
