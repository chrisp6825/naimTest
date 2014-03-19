package com.chrisp6825.naim.controllers;

import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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

	private Room curRoom;
	
	Array<StaticTiledMapTile> flowerFrameTiles;
	Array<StaticTiledMapTile> grassFrameTiles;
	
	public int currentGrassFrame = 0;
	public Thread t = null;

	public SpecialTileController(OverWorld overWorld) {
	}

	public void dispose() {
		for(TiledMapTile tile : flowerFrameTiles) {
			tile.getTextureRegion().getTexture().dispose();
		}
		for(TiledMapTile tile : grassFrameTiles) {
			tile.getTextureRegion().getTexture().dispose();
		}
		
	}

	public void newRoom(Room curRoom) {
		this.curRoom = curRoom;
		getAnimationTiles();
		setFlowerTiles();
		setGrassTiles();
	}
	
	// parse tileset for any tiles with an "animation" property
	public void getAnimationTiles() {
		flowerFrameTiles = new Array<StaticTiledMapTile>(0);
		grassFrameTiles = new Array<StaticTiledMapTile>(0);
		// getting the frame tiles
		Iterator<TiledMapTile> tiles = curRoom.getMap().getTileSets().getTileSet(0).iterator();
		while(tiles.hasNext()) {
			TiledMapTile tile = tiles.next();
			if (tile.getProperties().containsKey("animation")) {
				if (tile.getProperties().get("animation", String.class).equals("flower")) {
					loadFlowerTiles(tile);
				} else if (tile.getProperties().get("animation", String.class).equals("grass")) {
					loadGrassTiles(tile);
				}
				
			}
		}
	}
	
	// load frames for grass animation
	public void loadGrassTiles(TiledMapTile tile) {
		Texture t = new Texture(Gdx.files.internal("maps/common-tiles/animation/grass.png"));
		
		for (int i = 0; i < 6; i++) {
			grassFrameTiles.add(new StaticTiledMapTile(new TextureRegion(t,16*i,0,16,16)));
			grassFrameTiles.get(grassFrameTiles.size-1).getProperties().put("animation", "grass");
		}
	}
	
	// load frames for flower animation
	public void loadFlowerTiles(TiledMapTile tile) {
		Texture t = new Texture(Gdx.files.internal("maps/common-tiles/animation/flower.png"));
		
		for (int i = 0; i < 5; i++) {
			flowerFrameTiles.add(new StaticTiledMapTile(new TextureRegion(t,16*i, 0, 16, 16)));
			flowerFrameTiles.get(flowerFrameTiles.size-1).getProperties().put("animation", "flower");
		}
	}
	
	// parse through map layer, replacing flower tiles
	public void setFlowerTiles() {
		AnimatedTiledMapTile animatedTile = new AnimatedTiledMapTile(.275f * 1, flowerFrameTiles);
		
		for(TiledMapTile tile : flowerFrameTiles) {
			// copy over properties from old tiles to new
			animatedTile.getProperties().putAll(tile.getProperties());
		}
		
		// replace old tiles with new animated tile
		TiledMapTileLayer layer;
		for(int i = 0; i < curRoom.getMap().getLayers().getCount(); i++) {
			layer = curRoom.getTmtLayer(i);
			if (layer==null) break;
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
	}
	
	// parse through map layer ("grass"), replacing grass tiles
	public void setGrassTiles() {
		TiledMapTileLayer layer = curRoom.getTmtLayer("grass");
		for(int x = 0; x < layer.getWidth(); x++) {
			for(int y = 0; y < layer.getHeight(); y++) {
				Cell cell = layer.getCell(x, y);
				if (cell != null)
					if (cell.getTile().getProperties().containsKey("animation") && cell.getTile().getProperties().get("animation", String.class).equals("grass")) {
						TriggeredTiledMapTile triggeredTile = new TriggeredTiledMapTile(.15f * 1, grassFrameTiles);
						for(TiledMapTile tile : grassFrameTiles) {
							// combine properties in all grass tiles, into triggerTile
							triggeredTile.getProperties().putAll(tile.getProperties());
						}
						cell.setTile(new TriggeredTiledMapTile(.15f * 1, grassFrameTiles));
						cell.getTile().getProperties().putAll(triggeredTile.getProperties());
//						cell.getTile().getProperties().put("animation", "grass");
					}
			}
		}
	}

}
