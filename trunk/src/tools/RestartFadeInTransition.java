package tools;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;

import states.GameplayState;

public class RestartFadeInTransition extends FadeInTransition{

	public RestartFadeInTransition(Color color) {
		super(color);
	}

	public void preRender(StateBasedGame game, GameContainer container, Graphics g){
		GameplayState.setLevelNb(GameplayState.levelNb);
		super.preRender(game, container, g);
	}
}
