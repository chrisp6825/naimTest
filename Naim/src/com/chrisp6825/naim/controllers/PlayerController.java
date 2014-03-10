package com.chrisp6825.naim.controllers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.chrisp6825.naim.extendables.Person;
import com.chrisp6825.naim.inputManagers.OverWorldKeys;
import com.chrisp6825.naim.models.Player;
import com.chrisp6825.naim.screens.OverWorld;

public class PlayerController {
	
	private OverWorld overWorld;
	private Player player;
	
	public PlayerController(OverWorld overWorld) {
		this.overWorld = overWorld;
		player = new Player(new Sprite(new Texture("img/persons/charBlue.png")), overWorld);
		
		if (overWorld.getRoomController().getCurRoom().getTmtLayer("spawn") != null)
			findSpawn(overWorld.getRoomController().getCurRoom().getTmtLayer("spawn"));
		else 
			player.setLocation(12, 12);
	}
	
	public void update(float delta) {
		
		checkControls();
		player.move();
		// update camera
		overWorld.getRoomRenderer().positionCam(player.getX()+(player.getWidth()/2), 
				player.getY() + (player.getHeight()/2));
	}
	
	public void checkControls() {
		if (player.getState() == Person.State.IDLE) {
			if (OverWorldKeys.isDown(OverWorldKeys.UP)) {
				player.attemptMove(Person.Direction.NORTH);
			}

			if (OverWorldKeys.isDown(OverWorldKeys.RIGHT)) {
				player.attemptMove(Person.Direction.EAST);
			}

			if (OverWorldKeys.isDown(OverWorldKeys.DOWN)) {
				player.attemptMove(Person.Direction.SOUTH);
			}

			if (OverWorldKeys.isDown(OverWorldKeys.LEFT)) {
				player.attemptMove(Person.Direction.WEST);
			}
		}
		
		if (OverWorldKeys.isPressed(OverWorldKeys.ENTER)) {
			System.out.println("Current location cell : " + ((int)player.getCurCell().x) + "," + ((int)player.getCurCell().y));
			System.out.println("Current location abs  : " + player.getX() + "," + player.getY());
			System.out.println("Next location : " + ((int)player.getNextCell().x) + "," + ((int)player.getNextCell().y));
			System.out.println("-------------------------------");
		}
	}
	
	public void findSpawn(TiledMapTileLayer layer) {
		for (int y = 0; y < layer.getHeight(); y++) {
			for (int x = 0; x < layer.getWidth(); x++) {
				if (layer.getCell(x, y) != null) {
					player.setLocation(x,y);
				}
			}
		}
	}
	
	public void dispose() {
		player.dispose();
	}
	
	// get and set

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

}
