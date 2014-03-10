package com.chrisp6825.naim.inputManagers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.chrisp6825.naim.screens.OverWorld;

public class OverWorldInputProcessor extends InputAdapter{
	
	private OverWorld overWorld;

	public OverWorldInputProcessor(OverWorld overWorld) {
		this.overWorld = overWorld;
	}
	
	public boolean keyDown(int k) {
		if(k == Keys.UP) OverWorldKeys.setKey(OverWorldKeys.UP, true);
		if(k == Keys.RIGHT) OverWorldKeys.setKey(OverWorldKeys.RIGHT, true);
		if(k == Keys.DOWN) OverWorldKeys.setKey(OverWorldKeys.DOWN, true);
		if(k == Keys.LEFT) OverWorldKeys.setKey(OverWorldKeys.LEFT, true);
		if(k == Keys.ESCAPE) OverWorldKeys.setKey(OverWorldKeys.ESCAPE, true);
		if(k == Keys.ENTER) OverWorldKeys.setKey(OverWorldKeys.ENTER, true);
		if(k == Keys.A) OverWorldKeys.setKey(OverWorldKeys.A, true);
		if(k == Keys.B) OverWorldKeys.setKey(OverWorldKeys.B, true);
		if(k == Keys.M) OverWorldKeys.setKey(OverWorldKeys.M, true);
		return true;
	}
	
	public boolean keyUp(int k) {
		if(k == Keys.UP) OverWorldKeys.setKey(OverWorldKeys.UP, false);
		if(k == Keys.RIGHT) OverWorldKeys.setKey(OverWorldKeys.RIGHT, false);
		if(k == Keys.DOWN) OverWorldKeys.setKey(OverWorldKeys.DOWN, false);
		if(k == Keys.LEFT) OverWorldKeys.setKey(OverWorldKeys.LEFT, false);
		if(k == Keys.ESCAPE) OverWorldKeys.setKey(OverWorldKeys.ESCAPE, false);
		if(k == Keys.ENTER) OverWorldKeys.setKey(OverWorldKeys.ENTER, false);
		if(k == Keys.A) OverWorldKeys.setKey(OverWorldKeys.A, false);
		if(k == Keys.B) OverWorldKeys.setKey(OverWorldKeys.B, false);
		if(k == Keys.M) OverWorldKeys.setKey(OverWorldKeys.M, false);
		return true;
	}

}
