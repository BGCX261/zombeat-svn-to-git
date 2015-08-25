package states;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import main.PlayerInfo;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import tools.Menu;
import tools.ResourceManager;
import utils.FontLoader;
 
public class EndingGameState extends BasicGameState {
 
	Image exitOption=null;	
	float exitScale = 1;
	Menu menu;
	int menuX=480;
	Image background = null;
	Image zombie = null;
	Image human = null;
	
 
	
    int menuY=280;
    float scaleStep = 0.0001f;
    
    int stateID = 5;
    float completion = 0;
    
    EndingGameState( int stateID ) 
    {
       this.stateID = stateID;
    }
 
    @Override
    public int getID() {
        return stateID;
    }
 
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	menu = new Menu();
    	try {
			ResourceManager.getInstance().loadResourcesIntoMenu(new FileInputStream("xml/pauseMenu.xml"), menu);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	background = new Image("data/mappemonde_findujeu.jpg");
    	zombie = new Image("data/tete_zombie.png");
    	human = new Image("data/humanHead.png");
    	Image menuOptions = menu.getImageId("OPTIONS");
    	exitOption = menuOptions.getSubImage(0, 71, 377, 71);
    }
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        completion = (((float) PlayerInfo.getCurrentPlayerInfo().getScore()/PlayerInfo.SCOREMAX)) * 100;
    	background.draw(0,0, new Color(0.95f, 0.9f, 0.9f));
    	exitOption.draw(menuX, menuY+80, exitScale);
    	FontLoader.font26.drawString(200, 100, "Completion : " + completion + "%");
    	if (completion >= 100)
    	{
        	FontLoader.font60.drawString(160, 0, "Fin du monde");
        	zombie.draw(330,450);
        	zombie.draw(190,310);
        	zombie.draw(345,295);
        	zombie.draw(475,225);
        	zombie.draw(320,220);
    	}
    	else
    	{
        	FontLoader.font60.drawString(30, 0, "Les humains ont survecu");
        	if (PlayerInfo.getCurrentPlayerInfo().getAccomplissement(2) != true) human.draw(190,310); else zombie.draw(190,310);
        	if (PlayerInfo.getCurrentPlayerInfo().getAccomplissement(3) != true) human.draw(345,295); else zombie.draw(345,295);
        	if (PlayerInfo.getCurrentPlayerInfo().getAccomplissement(4) != true) human.draw(475,225); else zombie.draw(475,225);
        	if (PlayerInfo.getCurrentPlayerInfo().getAccomplissement(5) != true) human.draw(320,220); else zombie.draw(320,220);
        	if (PlayerInfo.getCurrentPlayerInfo().getScore()==PlayerInfo.SCOREMAX) zombie.draw(330,450); else human.draw(330,450);
//        	zombie.draw(345,295);
//        	zombie.draw(475,225);
//        	zombie.draw(320,220);
//        	human.draw(330,450);
    	}
    }
 
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	Input input = gc.getInput();
   	 
    	int mouseX = input.getMouseX();
    	int mouseY = input.getMouseY();
    	 
    	boolean insideExit = false;
    	 
    	if( ( mouseX >= menuX && mouseX <= menuX+ exitOption.getWidth()) &&
    	          ( mouseY >= menuY+80 && mouseY <= menuY+80 + exitOption.getHeight()) ){
    	    insideExit = true;
    	}
    	 
    	if(insideExit)
    	{
    	   if(exitScale < 1.05f)
    	     exitScale +=  scaleStep * delta;
    	   
    	   if ( input.isMousePressed(Input.MOUSE_LEFT_BUTTON) )
    		   sbg.enterState(ZombEatGame.MAINMENUSTATE,new FadeOutTransition(new Color(0,0,0)), new FadeInTransition(new Color(0,0,0)));
    	   
    	}else{
    	  if(exitScale > 1.0f)
    	    exitScale -= scaleStep * delta;
    	}
    }
    
}