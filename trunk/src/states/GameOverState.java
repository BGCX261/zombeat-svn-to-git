package states;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


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
import tools.RestartFadeInTransition;
 
public class GameOverState extends BasicGameState {
 
	Image exitOption=null;	
	float exitScale = 1;
	Menu menu;
	int menuX=40;
 
	
    int menuY=150;
    float scaleStep = 0.0001f;
 
    Image restart=null;
    Image gameOver=null;
    float startGameScale = 1;
    
    Image currentGraphics = null; //le background a afficher
    boolean newGameOver = true; //s'il faut changer le background
    
    int stateID = 4;
    
	
    GameOverState( int stateID ) 
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
        menu.getSound("gameOver").play();
    }
 
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	menu = new Menu();
    	try {
			ResourceManager.getInstance().loadResourcesIntoMenu(new FileInputStream("xml/gameOverMenu.xml"), menu);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	Image menuOptions = menu.getImageId("OPTIONS");
   	 
    	restart = menuOptions.getSubImage(0, 137, 270, 71);
    	gameOver = menuOptions.getSubImage(0, 354, 318, 71) ;
    	exitOption = menuOptions.getSubImage(0, 71, 377, 71);
    	currentGraphics = new Image(640,480);
    }
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    	if (newGameOver){
        	sbg.getState(ZombEatGame.GAMEPLAYSTATE).render(gc, sbg, g);
        	g.copyArea(currentGraphics, 0, 0);
        	g.clear();
        	newGameOver=false;
    	}

    	g.drawImage(currentGraphics, 0, 0, new Color(1.0f,0.9f,0.9f));
    	gameOver.draw((gc.getWidth()-gameOver.getWidth())/2, 10, 1.1f);
    	restart.draw(menuX, menuY, startGameScale);
    	exitOption.draw(menuX, menuY+80, exitScale);
    	
    }
 
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	Input input = gc.getInput();
   	 
    	int mouseX = input.getMouseX();
    	int mouseY = input.getMouseY();
    	 
    	boolean insideStartGame = false;
    	boolean insideExit = false;
    	 
    	if( ( mouseX >= menuX && mouseX <= menuX + restart.getWidth()) &&
    	    ( mouseY >= menuY && mouseY <= menuY + restart.getHeight()) ){
    	    insideStartGame = true;
    	}else if( ( mouseX >= menuX && mouseX <= menuX+ exitOption.getWidth()) &&
    	          ( mouseY >= menuY+80 && mouseY <= menuY+80 + exitOption.getHeight()) ){
    	    insideExit = true;
    	}
    	 
    	if(insideStartGame){
    	  if(startGameScale < 1.05f){
    		  startGameScale += scaleStep * delta;}
    	 
    	  if ( input.isMousePressed(Input.MOUSE_LEFT_BUTTON) ){
    		  sbg.enterState(ZombEatGame.GAMEPLAYSTATE, new FadeOutTransition(new Color(0,0,0)), new RestartFadeInTransition(new Color(0,0,0)));
    	  }
    	}else{
    	  if(startGameScale > 1.0f)
    	    startGameScale -= scaleStep * delta;
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
 
 
    public void leave(GameContainer gc, StateBasedGame sb) throws SlickException
    {
        super.leave(gc, sb);
        newGameOver = true;
    }
}