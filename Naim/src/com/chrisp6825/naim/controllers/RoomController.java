package com.chrisp6825.naim.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.AtlasTmxMapLoader;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.chrisp6825.naim.LocalFileHandleResolver;
import com.chrisp6825.naim.models.Room;
import com.chrisp6825.naim.models.specialTiles.TriggeredTiledMapTile;
import com.chrisp6825.naim.screens.OverWorld;

public class RoomController {
	
	private OverWorld overWorld;
	private Room curRoom;
	private SpecialTileController specialTileController;
	
	public RoomController(OverWorld overWorld) {
		this.overWorld = overWorld;
		curRoom = new Room();
		loadDemoRoom();
//		loadExternalRoom();
		
		
		specialTileController = new SpecialTileController(overWorld);
		specialTileController.newRoom(curRoom);
	}

	public void update(float delta) {
		
	}

	public void loadDemoRoom() {
		curRoom.setMap(new TmxMapLoader().load("maps/town1/town1.tmx"));
//		curRoom.setMap(new AtlasTmxMapLoader().load("maps/test/town1.tmx"));
		
		
	}
	
	public void loadExternalRoom() {
		//FileHandle file = Gdx.files.local("myfile.txt");
		//file.writeString("My god, it's full of stars", false);
		if (Gdx.files.local("town0.tmx").exists())
			curRoom.setMap(new TmxMapLoader(new LocalFileHandleResolver()).load("town0.tmx"));
		else
			loadDemoRoom();
	}
	
	public boolean checkCollision(int x, int y) {
		if (curRoom.getTmtLayer("collision").getCell(x, y) != null)
			return true;
		return false;
	}
	
	public void dispose() {
		curRoom.dispose();
		specialTileController.dispose();
	}
	
	// get and set
	
	public Room getCurRoom() {
		return this.curRoom;
	}

	public void setCurRoom(Room curRoom) {
		this.curRoom = curRoom;
	}

	public SpecialTileController getSpecialTileController() {
		return specialTileController;
	}

	public void setSpecialTileController(SpecialTileController specialTileController) {
		this.specialTileController = specialTileController;
	}

	public void triggerCell(int x, int y) {
		if (getCurRoom().getTmtLayer(1).getCell(x,y) != null && getCurRoom().getTmtLayer(1).getCell(x,y).getTile().getClass().equals(TriggeredTiledMapTile.class)) {
			((TriggeredTiledMapTile) getCurRoom().getTmtLayer(1).getCell(x,y).getTile()).trigger();
		}
	}

	public void untriggerCell(int x, int y) {
		if (getCurRoom().getTmtLayer(1).getCell(x,y) != null && getCurRoom().getTmtLayer(1).getCell(x,y).getTile().getClass().equals(TriggeredTiledMapTile.class))
			((TriggeredTiledMapTile) getCurRoom().getTmtLayer(1).getCell(x,y).getTile()).reset();
	}

}
