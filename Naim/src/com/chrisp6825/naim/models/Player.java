package com.chrisp6825.naim.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.chrisp6825.naim.extendables.Person;
import com.chrisp6825.naim.screens.OverWorld;

public class Player extends Person {
	
	public Player(Sprite sprite, OverWorld overWorld) {
		super(sprite, overWorld);
	}
	
	public void dispose() {
		super.getTexture().dispose();
	}
	
	
}
