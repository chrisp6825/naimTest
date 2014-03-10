package com.chrisp6825.naim;

import com.badlogic.gdx.Game;
import com.chrisp6825.naim.screens.OverWorld;

public class GameClass extends Game {
	
	public float GAMESPEED = 1;
	
	@Override
	public void create() {
		setScreen(new OverWorld(this));
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
