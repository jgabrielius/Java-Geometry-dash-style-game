package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class Map {
	TiledMap tiledMap;
	OrthogonalTiledMapRenderer tiledMapRenderer;
	public static final int TILE_SIZE = 32;
	Player player;
	boolean dead = false;
	boolean nextLevel = false;
	
	public Map (String name) {
		tiledMap = new TmxMapLoader().load(name);
		tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
		player = new Player();
		player.create(new Vector2(32,128), this);
	}
	public void render (OrthographicCamera camera, SpriteBatch batch) {
		tiledMapRenderer.setView(camera);
		tiledMapRenderer.render();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		player.render(batch);
		batch.end();
	}
	public void update (float delta) {
		player.update(delta, -150f);
	}
	public void dispose () {
		tiledMap.dispose();
	}
	public boolean doesRectCollideWithMap(float x, float y, int width, int height) {
		for (int row = (int) (y / TILE_SIZE); row < Math.ceil((y + height) / TILE_SIZE); row++) {
			for (int col = (int) (x / TILE_SIZE); col < Math.ceil((x + width) / TILE_SIZE); col++) {
				for (int layer = 0; layer < getLayers(); layer++) {
					TiledMapTile type = getTileTypeByCoordinate(layer, col, row);
					if (type != null) {
						if(layer==1) {
							dead = true;
						} else if(layer==2) {
							nextLevel = true;
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	public int getWidth () {
		return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getWidth() * TILE_SIZE;
	}

	public int getHeight () {
		return ((TiledMapTileLayer) tiledMap.getLayers().get(0)).getHeight()*TILE_SIZE;
	}

	public int getLayers() {
		return tiledMap.getLayers().getCount();
	}
	public TiledMapTile getTileTypeByCoordinate (int layer, int col, int row) {
		Cell cell = ((TiledMapTileLayer) tiledMap.getLayers().get(layer)).getCell(col, row);
		if (cell != null) {
			TiledMapTile tile = cell.getTile();
			return tile;
		}
		return null;
	}
	
}
