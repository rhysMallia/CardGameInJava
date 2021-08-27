package view;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import model.Constants;
import model.interfaces.PlayingCard.Suit;
public class IconFactory implements Constants
{

	/** thanks for the code Caspar :) **/
	   private Map<Suit, ImageIcon> imageIconMap;
	   public ImageIcon getImageIcon(Suit suit)
	   {
	      if (imageIconMap == null)
	         createImageIcons();

	      return imageIconMap.get(suit);
	   }
	   
	   private void createImageIcons()
	   {
	      imageIconMap = new HashMap<Suit, ImageIcon>();

	      for (Suit suit : Suit.values())
	         imageIconMap.put(suit, new ImageIcon(getFullPath(suit)));
	   }

	   private String getFullPath(Suit suit)
	   {
	      return String.format("%s%s%s.png", FILE_PATH, FILE_PATH.endsWith(File.separator) ? ""
	         : File.separator, suit.toString().toLowerCase());
	   }
}
