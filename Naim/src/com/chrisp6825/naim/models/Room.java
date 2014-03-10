package com.chrisp6825.naim.models;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.chrisp6825.naim.models.specialTiles.TriggeredTiledMapTile;

public class Room {
	
	private TiledMap map;
	private Vector2 size = new Vector2(10,10);
	public Array<TriggeredTiledMapTile> grassList = new Array<TriggeredTiledMapTile>(0);
	
	public Room() {
		map = new TiledMap();
	}



	public void dispose() {
		map.dispose();
	}
	
	// get and set
	
	public TiledMap getMap() {
		return map;
	}


	public void setMap(TiledMap map) {
		this.map = map;
	}


	public Vector2 getSize() {
		//return size;
		return new Vector2(((TiledMapTileLayer)map.getLayers().get(0)).getWidth(),((TiledMapTileLayer)map.getLayers().get(0)).getHeight());
	}


	public void setSize(Vector2 size) {
		this.size = size;
	}
	
	public TiledMapTileLayer getTmtLayer(int n) {
		return (TiledMapTileLayer)this.getMap().getLayers().get(n);
	}
	public TiledMapTileLayer getTmtLayer(String s) {
		return (TiledMapTileLayer)this.getMap().getLayers().get(s);
	}

}
