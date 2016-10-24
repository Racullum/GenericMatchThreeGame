package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by User on 10/22/2016.
 */

public class GameRenderer {
    private BoardController boardController;
    private OrthographicCamera camera;
    private SpriteBatch batch;



    public GameRenderer(BoardController bc, SpriteBatch batch){
        boardController = bc;
        //batch = new SpriteBatch();
        this.batch = batch;
        camera = new OrthographicCamera();
        //camera.setToOrtho(false, 800, 480);
        camera.setToOrtho(false, 480, 800);
    }


    public void setActiveSprite(int x, int y){
        boardController.setActiveSprite(x, y, camera);
    }
    public void render(){


        Gdx.gl.glClearColor(0, 0, 0.2f, 1);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        boardController.buildBoard(batch);
        batch.end();


    }
}
