package com.chrisp6825.naim.controllers;

import java.util.Iterator;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.chrisp6825.naim.models.Room;
import com.chrisp6825.naim.models.specialTiles.TriggeredTiledMapTile;
import com.chrisp6825.naim.screens.OverWorld;

public class SpecialTileController {

	private OverWorld overWorld;
	private Room curRoom;
	
	Array<StaticTiledMapTile> flowerFrameTiles;
	Array<StaticTiledMapTile> grassFrameTiles;
	
	public int currentGrassFrame = 0;
	public Thread t = null;

	public SpecialTileController(OverWorld overWorld) {
		this.overWorld = overWorld;
		//curRoom = overWorld.getRoomController().getCurRoom();
	}

	public void dispose() {
		
	}

	public void newRoom(Room curRoom) {
		this.curRoom = curRoom;
		getAnimationTiles();
		setFlowerTiles();
		setGrassTiles();
	}
	
	public void getAnimationTiles() {
		flowerFrameTiles = new Array<StaticTiledMapTile>(0);
		grassFrameTiles = new Array<StaticTiledMapTile>(0);
		// getting the frame tiles
		Iterator<TiledMapTile> tiles = curRoom.getMap().getTileSets().getTileSet(0).iterator();
		while(tiles.hasNext()) {
			TiledMapTile tile = tiles.next();
			if (tile.getProperties().containsKey("animation")) {
				if (tile.getProperties().get("animation", String.class).equals("flower")) {
					flowerFrameTiles.add((StaticTiledMapTile) tile);
				} else if (tile.getProperties().get("animation", String.class).equals("grass")) {
					grassFrameTiles.add((StaticTiledMapTile) tile);
				}
				
			}
		}
		
		int numOfFlowerFrames = flowerFrameTiles.size;
		int numOfGrassFrames = grassFrameTiles.size;
	}
	
	public void setFlowerTiles() {
		AnimatedTiledMapTile animatedTile = new AnimatedTiledMapTile(.275f * 1, flowerFrameTiles);
		// copy over properties from old tiles to new
		for(TiledMapTile tile : flowerFrameTiles) {
			animatedTile.getProperties().putAll(tile.getProperties());
		}
		// replace old tiles with new animated tile
		TiledMapTileLayer layer = curRoom.getTmtLayer(1);
		for(int x = 0; x < layer.getWidth(); x++) {
			for(int y = 0; y < layer.getHeight(); y++) {
				Cell cell = layer.getCell(x, y);
				if (cell != null)
					if (cell.getTile().getProperties().containsKey("animation") && cell.getTile().getProperties().get("animation", String.class).equals("flower")) {
						cell.setTile(animatedTile);
				}
			}
		}
	}
	
	public void setGrassTiles() {
//		TriggeredTiledMapTile triggeredTile = new TriggeredTiledMapTile(.1f * 1, grassFrameTiles);
//		// copy over properties from old tiles to new
//		for(TiledMapTile tile : grassFrameTiles) {
//			triggeredTile.getProperties().putAll(tile.getProperties());
//		}
		
		TiledMapTileLayer layer = curRoom.getTmtLayer(1);
		for(int x = 0; x < layer.getWidth(); x++) {
			for(int y = 0; y < layer.getHeight(); y++) {
				Cell cell = layer.getCell(x, y);
				if (cell != null)
					if (cell.getTile().getProperties().containsKey("animation") && cell.getTile().getProperties().get("animation", String.class).equals("grass")) {
						TriggeredTiledMapTile triggeredTile = new TriggeredTiledMapTile(.15f * 1, grassFrameTiles);
						for(TiledMapTile tile : grassFrameTiles) {
							triggeredTile.getProperties().putAll(cell.getTile().getProperties());
						}
						cell.setTile(new TriggeredTiledMapTile(.15f * 1, grassFrameTiles));
						cell.getTile().getProperties().putAll(triggeredTile.getProperties());
						System.out.println(x+","+y);
						System.out.println(cell.getTile().getProperties().get("animation"));
					}
			}
		}
	}

}
