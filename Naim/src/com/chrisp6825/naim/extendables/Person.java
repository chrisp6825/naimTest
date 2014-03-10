package com.chrisp6825.naim.extendables;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.chrisp6825.naim.screens.OverWorld;

public class Person extends Sprite {
	
	private OverWorld overWorld;
	private TiledMapTileLayer collisionLayer;
	
	public enum State {
		IDLE, STARTWALK, WALKING, RUNNING
	}
	public enum AniState {
		IDLE, STEPL, STEPR
	}
	public enum Direction {
		NORTH, EAST, SOUTH, WEST
	}
	private State state = State.IDLE;
	private AniState aniState = AniState.STEPL;
	private Direction direction = Direction.SOUTH;
	
	private Vector2 curCell = new Vector2(0,0);
	private Vector2 destCell = new Vector2(0,0);
	private Vector2 nextCell = new Vector2(0,0);
	
	private String name = "noname";
	
	private float speed = 1f;
	private int aniCount = 0, aniCountMax = 20;
	
	public Person(Sprite sprite, OverWorld overWorld) {
		super(sprite);
		super.setX(this.curCell.x * 16);
		super.setY(this.curCell.y * 16);
		this.overWorld = overWorld;
		this.collisionLayer = overWorld.getRoomController().getCurRoom().getTmtLayer("collision");
	}
	
	public void animate(boolean finish) {
		
		if (this.aniCount <= aniCountMax*.12) {
			
			
			if (this.direction == Direction.NORTH)
				this.setRegion(0,0,21,32);
			else if (this.direction == Direction.EAST)
				this.setRegion(0,32,21,32);
			else if (this.direction == Direction.SOUTH)
				this.setRegion(0,64,21,32);
			else if (this.direction == Direction.WEST) {
				this.setRegion(0,32,21,32);
				this.flip(true, false);
			}
		} else if (this.aniCount <= aniCountMax*.50) {
			if (this.aniState == AniState.STEPL) {
				if (this.direction == Direction.NORTH)
					this.setRegion(21,0,21,32);
				else if (this.direction == Direction.EAST)
					this.setRegion(21,32,21,32);
				else if (this.direction == Direction.SOUTH)
					this.setRegion(21,64,21,32);
				else if (this.direction == Direction.WEST) {
					this.setRegion(21,32,21,32);
					this.flip(true, false);
				}
			} else if (this.aniState == AniState.STEPR) {
				if (this.direction == Direction.NORTH)
					this.setRegion(42,0,21,32);
				else if (this.direction == Direction.EAST)
					this.setRegion(42,32,21,32);
				else if (this.direction == Direction.SOUTH)
					this.setRegion(42,64,21,32);
				else if (this.direction == Direction.WEST) {
					this.setRegion(42,32,21,32);
					this.flip(true, false);
				}
			}
		} else if (this.aniCount <= aniCountMax) {
			if (this.direction == Direction.NORTH)
				this.setRegion(0,0,21,32);
			else if (this.direction == Direction.EAST)
				this.setRegion(0,32,21,32);
			else if (this.direction == Direction.SOUTH)
				this.setRegion(0,64,21,32);
			else if (this.direction == Direction.WEST) {
				this.setRegion(0,32,21,32);
				this.flip(true, false);
			}
		}
		
		this.setSize(21, 32);
		
		if (this.aniCount < this.aniCountMax) this.aniCount++;
		
		if (finish) {
			this.aniCount = 0;
			if (this.aniState == AniState.STEPL)
				this.aniState = AniState.STEPR;
			else
				this.aniState = AniState.STEPL;
			
			if (this.direction == Direction.NORTH)
				this.setRegion(0,0,21,32);
			else if (this.direction == Direction.EAST)
				this.setRegion(0,32,21,32);
			else if (this.direction == Direction.SOUTH)
				this.setRegion(0,64,21,32);
			else if (this.direction == Direction.WEST) {
				this.setRegion(0,32,21,32);
				this.flip(true, false);
			}
		}
	}
	
	public void attemptMove(Direction dir) {
		setDirection(dir);
		int nextX = (int)getCurCell().x;
		int nextY = (int)getCurCell().y;
		
		if (dir == Direction.NORTH) {
			nextX = (int)getCurCell().x;
			nextY = (int)getCurCell().y+1;
		} else if (dir == Direction.EAST) {
			nextX = (int)getCurCell().x+1;
			nextY = (int)getCurCell().y;
		} else if (dir == Direction.SOUTH) {
			nextX = (int)getCurCell().x;
			nextY = (int)getCurCell().y-1;
		} else if (dir == Direction.WEST) {
			nextX = (int)getCurCell().x-1;
			nextY = (int)getCurCell().y;
		}
		
		if (!checkCollision(nextX,nextY)) {
			setNextSpot(nextX,nextY);
			setState(Person.State.WALKING);
			overWorld.getRoomController().triggerCell(nextX,nextY);
			overWorld.getRoomController().untriggerCell((int)getCurCell().x, (int)getCurCell().y);
		} else {
			System.out.println("blocked");
			animate(true);
		}
		
		//animate(true);
	}
	
	public void move() {

		// quit if not moving
		if (getState() == Person.State.IDLE)
			return;

		if (getState() == Person.State.WALKING) {
			if (getX() != getNextCell().x*16) {
				setX(getX() + (getNextCell().x-getCurCell().x)*getSpeed());
				animate(false);
			} else if (getY() != getNextCell().y*16) {
				setY(getY() + (getNextCell().y-getCurCell().y)*getSpeed());
				animate(false);
			} else {
				// move finished
				setState(Person.State.IDLE);
				setLocation((int)getNextCell().x, (int)getNextCell().y);
				animate(true);
				// temp fix for more fluid movement
				//update(0);
			}
			// player overstepped, fixing position
			if (Math.abs(getCurCell().x - getX()/16) > 1 || 
					Math.abs(getCurCell().y - getY()/16) > 1) {
				System.out.println("overstep");
				setState(Person.State.IDLE);
				setLocation((int)getNextCell().x, (int)getNextCell().y);
				animate(true);
			}
		}
	}
	
	public boolean checkCollision(int x, int y) {
		if (collisionLayer.getCell(x, y) != null)
			return true;
		return false;
	}
	
	
	
	// get and set
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public AniState getAniState() {
		return aniState;
	}

	public void setAniState(AniState aniState) {
		this.aniState = aniState;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public Vector2 getCurCell() {
		return curCell;
	}

	public void setCurCell(Vector2 curCell) {
		this.curCell = curCell;
	}

	public Vector2 getDestCell() {
		return destCell;
	}

	public void setDestCell(Vector2 destCell) {
		this.destCell = destCell;
	}

	public Vector2 getNextCell() {
		return nextCell;
	}

	public void setNextCell(Vector2 nextCell) {
		this.nextCell = nextCell;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void setNextSpot(int x, int y) {
		this.nextCell.x = x;
		this.nextCell.y = y;
		this.state = State.STARTWALK;
	}
	
	public void setLocation(int x, int y) {
		this.curCell.set(x,y);
		this.nextCell.set(x,y);
		this.destCell.set(x,y);
		this.setX(x*16);
		this.setY(y*16);
		
	}
}
