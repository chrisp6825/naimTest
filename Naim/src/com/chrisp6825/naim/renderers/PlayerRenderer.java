package com.chrisp6825.naim.renderers;

import com.chrisp6825.naim.models.Player;
import com.chrisp6825.naim.screens.OverWorld;

public class PlayerRenderer {
	
	private OverWorld overWorld;
	private Player player;
	
	private int rotate = 0;

	public PlayerRenderer(OverWorld overWorld) {
		this.overWorld = overWorld;
		this.player = overWorld.getPlayerController().getPlayer();
		getCurrentPlayerImage();
	}

	public void render(float delta) {
		//player.draw(overWorld.getSpriteBatch());
		
		overWorld.getSpriteBatch().draw(player, player.getX()-3, player.getY());
	}
	
	public void getCurrentPlayerImage() {
		player.setRegion(0, 0, 20, 32);
		player.setSize(20, 32);
	}

	public void dispose() {
		
	}

}
