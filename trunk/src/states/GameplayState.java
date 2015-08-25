package states;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import main.Bonus;
import main.Ennemy;
import main.PlayerInfo;
import main.Zombie;
import objects.Teleport;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import tools.Level;
import tools.ResourceManager;
import utils.InGameMessage;


public class GameplayState extends BasicGameState {
 
	static public enum STATES {GAME_OVER_STATE, LEVEL_UP, PAUSE_GAME_STATE,START_GAME_STATE}
	static private Level level;
	public static int levelNb=1;
	private PlayerInfo playerInfo;
	public static Level getLevel(){
    	return level;
    }
	private STATES currentState = null;
	
	private boolean endingGame=false;
	
	private int savedScore=0;
	
	int stateID = 1;

	private static Zombie zombie1;
 
    private static Zombie zombie2;
    
    public static List<InGameMessage> messages = new ArrayList<InGameMessage>();
 
    GameplayState( int stateID ) 
    {
       this.stateID = stateID;
       level = new Level(levelNb);
    }
    
    public void enter(GameContainer gc, StateBasedGame sb) throws SlickException
    {
        super.enter(gc, sb);  
        playerInfo = PlayerInfo.getCurrentPlayerInfo();
        if (currentState == STATES.GAME_OVER_STATE){
        	this.restart();
        }
        
        currentState = STATES.START_GAME_STATE;
        
        if(level.getNumber() != levelNb){
        	level= new Level(levelNb);
        	try {
				ResourceManager.getInstance().loadResourcesIntoLevel(new FileInputStream("xml/level" + levelNb + ".xml"), level);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
        }
        savedScore = playerInfo.getScore();
//        level.getAmbience().play();
//        level.getAmbience().loop();
    }
    
    @Override
    public int getID() {
        return stateID;
    }
 
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
    	try {
			ResourceManager.getInstance().loadResourcesIntoLevel(new FileInputStream("xml/level" + levelNb + ".xml"), level);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		zombie1= new Zombie(Input.KEY_LEFT, Input.KEY_RIGHT, 1);
		zombie2= new Zombie(Input.KEY_Q, Input.KEY_D, 2);
		playerInfo = PlayerInfo.getCurrentPlayerInfo();
    }
 
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
    	level.getBackground().draw(0, 0);
    	level.getMap().tmap.render(0, 0);
    	
		for (Bonus b :level.getBonus()){
			b.draw(g);
		}

		for (Ennemy e : level.getEnnemies()){
			e.draw(g);
		}
		
		zombie1.draw(g);
		zombie2.draw(g);
		
		//Pour l'instant c'est plus facile de faire comme ca qu'avec nonBlockingEntities car seuls les teleports affichent qqchose
		for (Teleport t : level.getTeleport()){
			t.draw(g);
		}
		
//		for (CollidableObject c : level.getMap().getNonBlockingEntities()){
//			c.draw(g);
//		}
		
		playerInfo.draw(g);
		
		for (InGameMessage message : messages){
			message.render(gc, sbg, g);
		}
		
    }
    
    public void restart(){
    	messages.clear();
    	
    	if (levelNb == level.getNumber()) playerInfo.setScore(savedScore);
		level= new Level(levelNb);
		try {
			ResourceManager.getInstance().loadResourcesIntoLevel(new FileInputStream("xml/level" + levelNb + ".xml"), level);
		} catch (SlickException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	zombie1.reset();
    	zombie2.reset();
		
    	System.out.println("level = " + level.getNumber());
    }
 
    public static void setLevelNb(int levelNumber){
    	levelNb = levelNumber;
    	level= new Level(levelNb);
		try {
			ResourceManager.getInstance().loadResourcesIntoLevel(new FileInputStream("xml/level" + levelNb + ".xml"), level);
		} catch (SlickException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		zombie1.reset();
		zombie2.reset();
    }
    
    public void setState(STATES state){
    	currentState = state;
    }
    
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
    	currentState=STATES.START_GAME_STATE;
    	
    	for (InGameMessage message : messages){
			message.update(delta);
		}
    	
    	if (endingGame && !zombie1.getActualAnim().isStopped())return;
    	else if (endingGame) {
    		currentState=STATES.LEVEL_UP;
    	}
    	
    	
    	if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
    		currentState=STATES.PAUSE_GAME_STATE;
    	}
    	
    	if (zombie1.outOfWindow() || zombie2.outOfWindow() || zombie1.isGameOver() || zombie2.isGameOver()){
    		currentState=STATES.GAME_OVER_STATE;
    	}
    	
    	for (Ennemy e : level.getEnnemies()){
			e.update(delta);
		}
		
		for (Bonus b : level.getBonus()){
				b.update(delta);
		}
    	
    	zombie1.update(gc, sbg, delta);  
    	zombie2.update(gc, sbg, delta);
    	
    	switch(currentState)
        {
            case PAUSE_GAME_STATE:
            	sbg.enterState(ZombEatGame.GAMEPAUSESTATE);
            	return;
            case GAME_OVER_STATE:
                sbg.enterState(ZombEatGame.GAMEOVERSTATE,new FadeOutTransition(new Color(0,0,0)), new FadeInTransition(new Color(0,0,0)));
                return;
        
            case LEVEL_UP:
            	level.getAmbience().fade(500, 0, true);
            	endingGame=false;
            	if (level.isFinal()) {
            		setLevelNb(1);
            		sbg.enterState(ZombEatGame.ENDINGGAMESTATE);
            		return;
            	}
            	endingGame=false;
//            	levelNb++;
//            	this.restart();
            	setLevelNb(++levelNb);
            	if (playerInfo.getScore() -savedScore ==PlayerInfo.SCOREMAXLEVEL[levelNb]) playerInfo.setAccomplissement(levelNb, true); else playerInfo.setAccomplissement(levelNb, false);
            	sbg.enterState(ZombEatGame.TEXTSTATE);
            	return;
		default:
			break;
        }
    	
    	if(zombie1.isInFinalPosition() && zombie2.isInFinalPosition()){
			endingGame=true;
			zombie1.setAnimationDos();
			zombie2.setAnimationDos();
			playerInfo.displayAndAddScore(InGameMessage.TEXTS.BONUS_LEVEL, 1000);
			return;
		}
    	
		zombie1.inFinalPosition(false);
		zombie2.inFinalPosition(false);
    }
    
}