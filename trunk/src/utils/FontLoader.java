package utils;

import org.newdawn.slick.*;
import org.newdawn.slick.font.effects.*;


//It does nothing but to load a True Type Font ... we can use it later
//to load unicode fonts as well.
public class FontLoader
{
	public static UnicodeFont font26 = FontLoader.loadFont("data/font/FeastOfFlesh.TTF", 26);
	public static UnicodeFont font60 = FontLoader.loadFont("data/font/FeastOfFlesh.TTF", 60);
	
    public static UnicodeFont loadFont(String fontLocation, int fontSize)
            
    {
        UnicodeFont tFont = null;
		try {
			tFont = new UnicodeFont("data/font/FeastOfFlesh.TTF", fontSize, false, false);
			tFont.getEffects().add(new ColorEffect(java.awt.Color.red));
		    tFont.addAsciiGlyphs();
		    tFont.loadGlyphs();

		    return tFont;
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tFont;
       
    }

}