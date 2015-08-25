package states;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import tools.Menu;
import tools.ResourceManager;
import utils.FontLoader;
 
public class InstructionsState extends BasicGameState {
 
    Image background = null;
	Image exitOption = null;
	Image title = null;
	UnicodeFont f = FontLoader.font26;
	
    float exitScale = 1;
    Menu menu;
    int menuX=40;
    int menuY=140;
    float scaleStep = 0.0001f;
 
    int stateID = 7;
    
 
    InstructionsState( int stateID ) 
    {
       this.stateID = stateID;
    }
 
    @Override
    public int getID() {
        return stateID;
    }
 
    public void enter(GameContainer gc, StateBasedGame sb) throws SlickException
    {
        super.enter(gc, sb);  

    }
    
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	try {
    		menu = new Menu();
        	ResourceManager.getInstance().loadResourcesIntoMenu(new FileInputStream("xml/passwordMenu.xml"), menu);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	menu.getAmbience().loop();
    	
		Image menuOptions = menu.getImageId("OPTIONS");
    	title = menu.getImageId("TITLE");
    	exitOption = menuOptions.getSubImage(0, 71, 377, 71);
    	
    	background = menu.getBackground();
    	 
    	// load the menu images
    	
    }
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    	background.draw(0, 0, new Color(0.8f, 0.8f, 0.8f));
    	
    	// Draw menu
    	title.draw();
		f.drawString(0, menuY+30, "Q-D + Fleches directionnelles = deplacer les zombies.");
		f.drawString(0, menuY+60, "Prendre un champignon inverse la gravite de l'autre joueur.");
		f.drawString(0, menuY+90, "les deux zombies doivent rejoindre la sortie.");
    	exitOption.draw(menuX, menuY+200, exitScale);
    }
 
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	
    	Input input = gc.getInput();
    	 
    	int mouseX = input.getMouseX();
    	int mouseY = input.getMouseY();
    	 
    	boolean insideExit = false;
    	 
    	if( ( mouseX >= menuX && mouseX <= menuX+ exitOption.getWidth()) &&
    	          ( mouseY >= menuY+200 && mouseY <= menuY+200 + exitOption.getHeight()) ){
    	    insideExit = true;
    	}
    	 
    	  	 
    	if(insideExit)
    	{
    	   if(exitScale < 1.05f)
    	     exitScale +=  scaleStep * delta;
    	   
    	   if ( input.isMousePressed(Input.MOUSE_LEFT_BUTTON) )
       	    	sbg.enterState(ZombEatGame.MAINMENUSTATE, new FadeOutTransition(new Color(0,0,0)), new FadeInTransition(new Color(0,0,0)));
    	}else{
    	  if(exitScale > 1.0f)
    	    exitScale -= scaleStep * delta;
    	}
    }
 
}