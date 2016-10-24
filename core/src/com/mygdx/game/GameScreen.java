package com.mygdx.game; /**
 * Created by User on 9/29/2016.
 */



//package com.badlogic.drop;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector3;


public class GameScreen implements Screen{


    private BoardController boardController;
    private GameRenderer gameRenderer;
    final MatchThree game;
    private Music music = Gdx.audio.newMusic(Gdx.files.internal("gamemusic.mp3"));






    private OrthographicCamera camera;
    //private SpriteBatch batch;
    private boolean test = true;

    public GameScreen(final MatchThree game){
        this.game = game;
        Gdx.app.log("Game Screen", "Inside gamescreen");
        boardController = new BoardController();
        music.setLooping(true);
        music.play();
        gameRenderer = new GameRenderer(boardController, this.game.batch);

        //Fill our board array full of random stones

        camera = new OrthographicCamera();
        //camera.setToOrtho(false, 800, 480);
        camera.setToOrtho(false, 480, 800);
        //batch = new SpriteBatch();
        Gdx.input.setInputProcessor(new InputHandler(gameRenderer));
    }



    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Gdx.graphics.requestRendering();
        if(boardController.isGameOver())
        {
            game.setScreen(new GameOverScreen(game, boardController.getHighScore()));
            dispose();
        }
        gameRenderer.render();


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

