package com.chrisp6825.naim.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.chrisp6825.naim.GameClass;
import com.chrisp6825.naim.controllers.PlayerController;
import com.chrisp6825.naim.controllers.RoomController;
import com.chrisp6825.naim.inputManagers.OverWorldInputProcessor;
import com.chrisp6825.naim.inputManagers.OverWorldKeys;
import com.chrisp6825.naim.renderers.PlayerRenderer;
import com.chrisp6825.naim.renderers.RoomRenderer;

public class OverWorld implements Screen {
	
	private GameClass game;
	private RoomRenderer roomRenderer;
	private RoomController roomController;
	private PlayerController playerController;
	private PlayerRenderer playerRenderer;
	
	private SpriteBatch spriteBatch;
	private BitmapFont font = new BitmapFont();
	private OrthographicCamera camera;
	
	public OverWorld(GameClass gameClass) {
		this.game = gameClass;
	}

	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		if (OverWorldKeys.isPressed(OverWorldKeys.B)) {
		}
		if (OverWorldKeys.isPressed(OverWorldKeys.A)) {
		}
		
		roomController.update(delta);
		playerController.update(delta);
		
		roomRenderer.render(delta);
		
		spriteBatch.setProjectionMatrix(roomRenderer.getCamera().combined);
		spriteBatch.begin();
		playerRenderer.render(delta);
		//TODO render NPCs
		
		// print FPS on screen
		spriteBatch.setProjectionMatrix(camera.combined);
		font.draw(spriteBatch, "FPS : " + Gdx.graphics.getFramesPerSecond(), 5f, 320f);
		spriteBatch.end();
		
		roomRenderer.renderOverPersons(delta);
		
		OverWorldKeys.update();
	}

	@Override
	public void show() {
		
		Gdx.input.setInputProcessor(new OverWorldInputProcessor(this));

		roomController = new RoomController(this);
		playerController = new PlayerController(this);
		
		roomRenderer = new RoomRenderer(this);
		playerRenderer = new PlayerRenderer(this);
		
//		spriteBatch = roomRenderer.getRenderer().getSpriteBatch();
		spriteBatch = new SpriteBatch();
		camera = new OrthographicCamera();
		
	}

	@Override
	public void resize(int width, int height) {
		roomRenderer.resize(width, height);
		camera.setToOrtho(false, width, height);
		camera.update();
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		playerController.dispose();
		roomController.dispose();
		roomRenderer.dispose();
		playerRenderer.dispose();
		font.dispose();
		spriteBatch.dispose();
	}
	
	// get and set

	public GameClass getGame() {
		return game;
	}

	public void setGame(GameClass game) {
		this.game = game;
	}

	public RoomRenderer getRoomRenderer() {
		return roomRenderer;
	}

	public void setRoomRenderer(RoomRenderer roomRenderer) {
		this.roomRenderer = roomRenderer;
	}

	public RoomController getRoomController() {
		return this.roomController;
	}

	public void setRoomController(RoomController roomController) {
		this.roomController = roomController;
	}

	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}

	public void setSpriteBatch(SpriteBatch spriteBatch) {
		this.spriteBatch = spriteBatch;
	}

	public PlayerController getPlayerController() {
		return playerController;
	}

	public void setPlayerController(PlayerController playerController) {
		this.playerController = playerController;
	}

}
