package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player {
	Vector2 pos;
	float velocityY = 0;
	Map map;
	boolean grounded = true;
	public static final int SPEED = 80;
	public static final int JUMP_VELOCITY =50;
	float weight = 12;
	Texture image;
	
	public void create (Vector2 position, Map map) {
		this.pos = new Vector2(position.x, position.y);
		this.map = map;
		image = new Texture("player.png");
	}
	public void gravity (float deltaTime, float gravity) {
		float newY = pos.y;
		this.velocityY += gravity * deltaTime * weight;
		newY += this.velocityY * deltaTime;
	
		if (map.doesRectCollideWithMap(pos.x, newY, 32, 32)) {
			if (velocityY < 0) {
				this.pos.y = (float) Math.floor(pos.y);
				grounded = true;
			}
			this.velocityY = 0;
		} else {
			this.pos.y = newY;
			grounded = false;
		}
	}
	public void moveX (float amount) {
		float newX = pos.x + amount;
		if (!map.doesRectCollideWithMap(newX, pos.y, 32, 32))
			this.pos.x = newX;
	}
	public void update(float deltaTime, float gravity) {
		if (Gdx.input.isKeyPressed(Keys.SPACE) && grounded) {
			this.velocityY += JUMP_VELOCITY * weight;
		}
		gravity(deltaTime, gravity);//Apply gravity
		moveX(4);//Move to right
	}
	public void render(SpriteBatch batch) {
		batch.draw(image, pos.x, pos.y, 32, 32);
	}
}
