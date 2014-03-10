package com.chrisp6825.naim.renderers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.chrisp6825.naim.models.Player;
import com.chrisp6825.naim.models.Room;
import com.chrisp6825.naim.models.specialTiles.TriggeredTiledMapTile;
import com.chrisp6825.naim.screens.OverWorld;

public class RoomRenderer {
	
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	
	private OverWorld overWorld;
	
	private Room curRoom;
	private Player player;
	
	int[] backgroundLayers = { 0 };
	int[] midLayers = { 1,2 };
	int[] tallLayers = { 3 };
	
	
	public RoomRenderer(OverWorld overWorld) {
		this.overWorld = overWorld;
		this.curRoom = overWorld.getRoomController().getCurRoom();
		this.player = overWorld.getPlayerController().getPlayer();
		
		renderer = new OrthogonalTiledMapRenderer(curRoom.getMap());
		camera = new OrthographicCamera();
		positionCam(player.getX(), player.getY());
	}
	
	public void resize(int width, int height) {
		camera.viewportWidth = ((int)(width/32))*16;
		camera.viewportHeight = ((int)(height/32))*16;
//		camera.viewportWidth = width;
//		camera.viewportHeight = height;
//		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
		camera.update();
	}
	
	public void render(float delta) {
		
		renderer.setView(camera);
		//renderer.render();
		
//		TriggeredTiledMapTile.updateAnimationBaseTime();
		
		renderer.getSpriteBatch().disableBlending();
		renderer.render(backgroundLayers);
		renderer.getSpriteBatch().enableBlending();
		renderer.render(midLayers);
		renderer.render(tallLayers);
		
		
//		renderer.getSpriteBatch().begin();
//		renderObject(curRoom.getMap().getLayers().get("objects").getObjects().get(0));
//		renderer.getSpriteBatch().end();
		
	}
	
	public void renderOverPersons(float delta) {
		
		renderer.getSpriteBatch().begin();
		renderer.getSpriteBatch().enableBlending();
		
		if (curRoom.getTmtLayer("tall").getCell((int)player.getCurCell().x, (int)player.getCurCell().y) != null) {
			
			renderer.getSpriteBatch().draw(curRoom.getTmtLayer("tall")
					.getCell((int)player.getCurCell().x, (int)player.getCurCell().y).getTile()
					.getTextureRegion(), player.getCurCell().x*16, player.getCurCell().y*16);
		}
		if (curRoom.getTmtLayer("tall").getCell((int)player.getNextCell().x, (int)player.getNextCell().y) != null) {
			
			renderer.getSpriteBatch().draw(curRoom.getTmtLayer("tall")
					.getCell((int)player.getNextCell().x, (int)player.getNextCell().y).getTile()
					.getTextureRegion(), player.getNextCell().x*16, player.getNextCell().y*16);
		}
		
		
		// tweaked for grass over player
		if (curRoom.getTmtLayer(1).getCell((int)player.getNextCell().x, (int)player.getNextCell().y) != null) {
			if (curRoom.getTmtLayer(1).getCell((int)player.getNextCell().x, (int)player.getNextCell().y).getTile().getProperties().containsKey("animation")) {
				if (curRoom.getTmtLayer(1).getCell((int)player.getNextCell().x, (int)player.getNextCell().y).getTile().getProperties().get("animation", String.class).equals("grass")) {
					if (((TriggeredTiledMapTile) curRoom.getTmtLayer(1).getCell((int)player.getNextCell().x, (int)player.getNextCell().y).getTile()).isPlaying()) {
						renderer.getSpriteBatch().draw(curRoom.getTmtLayer(1)
								.getCell((int)player.getNextCell().x, (int)player.getNextCell().y).getTile()
								.getTextureRegion(), player.getNextCell().x*16, player.getNextCell().y*16);
					}
				}
			}
		}
		
		renderer.getSpriteBatch().end();
		
	}
	
	public void renderObject(MapObject object) {
		if(object instanceof RectangleMapObject) {
			RectangleMapObject rectObj = (RectangleMapObject) object;
			if(rectObj.getProperties().containsKey("gid")) {
				TiledMapTile tile = curRoom.getMap().getTileSets().getTile(rectObj.getProperties().get("gid", Integer.class));
				renderer.getSpriteBatch().draw(tile.getTextureRegion(), rectObj.getRectangle().x, rectObj.getRectangle().y);
			}
		}
	}
	
	public void positionCam(float x, float y) {
		camera.position.set(x, y, 0);
		camera.update();
	}
	
	public void dispose() {
		renderer.dispose();
	}
	
	// get and set
	
	public OrthogonalTiledMapRenderer getRenderer() {
		return this.renderer;
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	public void setCamera(OrthographicCamera camera) {
		this.camera = camera;
	}

}
