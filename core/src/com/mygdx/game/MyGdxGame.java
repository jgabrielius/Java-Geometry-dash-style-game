
//Gabrielius Jokubauskas 1kursas 1 grupe
package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MyGdxGame extends ApplicationAdapter{
	
    OrthographicCamera camera;
    SpriteBatch batch;
    Map map;
    int level = 1;
    
    @Override
    public void create () {
    	batch = new SpriteBatch();
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        
        switch(level) {
        case 1: map= new Map("level1.tmx");
        break;
        case 2: map= new Map("level2.tmx");
        break;
        case 3: map= new Map("level3.tmx");
        break;
        case 4: map= new Map("level4.tmx");
        default:
        map= new Map("level1.tmx");
        level =1;
        break;
        }
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(51, 204, 255, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        camera.translate(4,0);
        camera.update();
        map.update(Gdx.graphics.getDeltaTime());
        map.render(camera, batch);
        if(map.dead) {
        	create();
        }
        if(camera.position.x-Gdx.graphics.getWidth()/2>map.player.pos.x) {
        	create();
        }
        if(map.nextLevel) {
        	level++;
        	create();
        }
    }
    @Override
	public void dispose () {
		batch.dispose();
		map.dispose();
    }
}
