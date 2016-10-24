package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;



public class MainMenuScreen implements Screen {

    final MatchThree game;

    private Sprite backGround = new Sprite(new Texture(Gdx.files.internal("cloudBG.png")));

    OrthographicCamera camera;

    public MainMenuScreen(final MatchThree gam) {
        game = gam;

        camera = new OrthographicCamera();

        //camera.setToOrtho(false, 800, 480);
        camera.setToOrtho(false, 480, 800);

    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(backGround.getTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        game.font.draw(game.batch, "Welcome to A Generic Match Three Game!!! ", 100, 500);
        game.font.draw(game.batch, "Tap anywhere to begin!", 125, 400);

        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
