package com.chrisp6825.naim.models.specialTiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTile.BlendMode;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class TriggeredTiledMapTile implements TiledMapTile {
	
	private long lastTiledMapRenderTime = 0;

	private int id;

	private BlendMode blendMode = BlendMode.ALPHA;

	private MapProperties properties;

	private Array<StaticTiledMapTile> frameTiles;

	private float animationInterval;
	private long frameCount = 0;
	private long initialTimeOffset = TimeUtils.millis();
	
	private long currentFrame = 0;
	private boolean triggered = false;
	private boolean reset = false;

	private float oldAnimationInterval;

	@Override
	public int getId () {
		return id;
	}

	@Override
	public void setId (int id) {
		this.id = id;
	}

	@Override
	public BlendMode getBlendMode () {
		return blendMode;
	}

	@Override
	public void setBlendMode (BlendMode blendMode) {
		this.blendMode = blendMode;
	}

	@Override
	public TextureRegion getTextureRegion () {
		lastTiledMapRenderTime = TimeUtils.millis() - initialTimeOffset;
		
		if (!triggered)
			return frameTiles.get(0).getTextureRegion();
		
		if (currentFrame <= frameCount-1) {
			if (currentFrame >= frameCount-1) {
				currentFrame = frameCount-1;
				if (reset) {
					currentFrame = 0;
					triggered = false;
				}
			} else 
				currentFrame = (lastTiledMapRenderTime / (long)(animationInterval * 1000f)) % frameCount;
		} else {
			//currentFrame = 0;
		}
		return frameTiles.get((int)currentFrame).getTextureRegion();
	}

	@Override
	public MapProperties getProperties () {
		if (properties == null) {
			properties = new MapProperties();
		}
		return properties;
	}

	/** Function is called by BatchTiledMapRenderer render(), lastTiledMapRenderTime is used to keep all of the tiles in lock-step
	 * animation and avoids having to call TimeUtils.millis() in getTextureRegion() */
//	public static void updateAnimationBaseTime () {
//		lastTiledMapRenderTime = TimeUtils.millis() - initialTimeOffset;
//	}

	public TriggeredTiledMapTile (float interval, Array<StaticTiledMapTile> frameTiles) {
		this.frameTiles = frameTiles;
		this.animationInterval = interval;
		this.oldAnimationInterval = interval;
		this.frameCount = frameTiles.size;
	}
	
	public void trigger() {
		triggered = true;
		initialTimeOffset = TimeUtils.millis();
		reset = false;
		animationInterval = oldAnimationInterval;
	}
	
	public void reset() {
		reset = true;
		oldAnimationInterval = animationInterval;
		animationInterval = (float) (animationInterval / 0.9);
	}
	
	// tweaked for grass animation
	public boolean isPlaying() {
		if (currentFrame >= 1)
			return true;
		return false;
	}

}
