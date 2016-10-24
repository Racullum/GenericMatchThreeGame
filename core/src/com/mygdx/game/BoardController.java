package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;



public class BoardController {


    private Texture stone;
    private Sound sound;
    private Sprite active_sprite = null;
    private int index_i, index_j;
    boolean horizontal = false;
    private Texture[] textures_array = new Texture[6];
    private boolean animationNotDone = true;
    private int animationCount = 0;
    private BitmapFont font;
    private Sprite[][] boardArray = new Sprite[4][6];
    private int count = 0;
    private int moveCount = 30;
    private Sprite background = new Sprite(new Texture(Gdx.files.internal("cloudBG.png")));


    private Sprite gridImage = new Sprite(new Texture(Gdx.files.internal("tile.png")));

    public BoardController(){

        font = new BitmapFont();
        font.getData().setScale(1.5f, 1.5f);

        gridImage.setScale(1.5f, 1.5f);
        initializeBoard();
    }

    public int getHighScore()
    {
        return count;
    }

    public boolean isGameOver(){
        if(moveCount <= 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void swap(Sprite a, int i, int j)
    {
        if((Math.abs(i - index_i)) < 2 && (Math.abs(j - index_j)) < 2)  {
            moveCount = moveCount - 1;
            float temp_x = 0;
            float temp_y = 0;
            Sprite temp = boardArray[i][j];
            temp_x = boardArray[i][j].getX();
            temp_y = boardArray[i][j].getY();
            boardArray[i][j].setPosition(a.getX(), a.getY());
            a.setPosition(temp_x, temp_y);
            boardArray[i][j] = boardArray[index_i][index_j];
            boardArray[index_i][index_j] = temp;
            active_sprite = null;
        }


    }


    public void initializeBoard()
    {
        sound = Gdx.audio.newSound(Gdx.files.internal("complete.mp3"));
        //Populating the array sprites_array with the stone textures
        textures_array[0] = new Texture(Gdx.files.internal("PurpleHeart.png"));
        textures_array[1] = new Texture(Gdx.files.internal("OrangePentagon.png"));
        textures_array[2] = new Texture(Gdx.files.internal("GreenSquare.png"));
        textures_array[3] = new Texture(Gdx.files.internal("BlueStar.png"));
        textures_array[4] = new Texture(Gdx.files.internal("RedDiamond.png"));
        textures_array[5] = new Texture(Gdx.files.internal("YellowTriangle.png"));
        //gridImage.setScale(50f, 50f);
        for(int i = 0; i < boardArray.length; i++)
        {
            for(int j = 0; j < boardArray[i].length; j++)
            {
                boardArray[i][j] = randomSprite(0, 0);
            }
        }



        //Put stones in position
        for(int i = 0; i < boardArray.length; i++)
            for(int j = 0; j < boardArray[i].length; j++)
            {
                boardArray[i][j].setPosition((100 + 80 * i), (610 - 80 * j));
                //boardArray[i][j].draw(batch);

            }
    }



    Sprite randomSprite(float x, float y)
    {
        int random = 0;
        random = 0 + (int)(Math.random() * ((5 - 0) + 1));
        Sprite temp =  (new Sprite(textures_array[random]));
        temp.setPosition(x, y);
        return temp;
    }

    void buildBoard(SpriteBatch batch)
    {
        findCollisions();

        batch.draw(background.getTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 6; j++) {
                gridImage.setPosition(90 + 80 * i, 600 - 80 * j);
                gridImage.draw(batch);
            }
        //Put stones in position
        for(int i = 0; i < boardArray.length; i++)
            for(int j = 0; j < boardArray[i].length; j++)
            {
                boardArray[i][j].setPosition(boardArray[i][j].getX(), boardArray[i][j].getY());
                boardArray[i][j].draw(batch);
            }
        font.draw(batch, "Score: " + count, 200f, 780f);
        font.draw(batch, "Moves left: " + moveCount, 25f, 780f);
    }

    public void setActiveSprite(int x, int y, OrthographicCamera camera) {
        Vector3 touchPos = new Vector3(x, y, 0);
        camera.unproject(touchPos);
        for (int i = 0; i < boardArray.length; i++)
            for (int j = 0; j < boardArray[i].length; j++) {
                if ((touchPos.x - 30 <= boardArray[i][j].getX()) && (boardArray[i][j].getX() <= touchPos.x + 30))
                    if ((touchPos.y - 30 <= boardArray[i][j].getY()) && (boardArray[i][j].getY() <= touchPos.y + 30)) {
                        if (active_sprite == null) {
                            active_sprite = boardArray[i][j];
                            index_i = i;
                            index_j = j;
                            return;
                        }

                        if (active_sprite != null) {
                                swap(active_sprite, i, j);
                                return;
                        }
                    }
            }
    }

    private void matched(Sprite x, Sprite y, Sprite z)
    {


        x.setScale((x.getScaleX() / 1.05f), x.getScaleY() / 1.05f);


        y.setScale(y.getScaleX() / 1.05f, y.getScaleY() / 1.05f);



        z.setScale(z.getScaleX() / 1.05f, z.getScaleY() / 1.05f);

    }
    
    private boolean validMatch(int i, int j, Texture tex) {
        boolean temp = false;
        if (boardArray[i][j].getTexture() == tex) {
            if (i >= 2) {
                if ((boardArray[i - 1][j].getTexture() == tex) && (boardArray[i - 2][j].getTexture() == tex)) {

                    temp = true;
                    horizontal = true;
                }
            }
            if (j >= 2) {
                Gdx.app.log("findCollisions", "Inside for loop");
                if ((boardArray[i][j - 1].getTexture() == tex) && (boardArray[i][j - 2].getTexture() == tex)) {
                    temp = true;
                    horizontal = false;
                }
            }

        }
        return temp;

    }
        private void matchHandler(int i, int j) {

            //put the array location of this stone in the location array
            if (horizontal) {
                if (animationNotDone) {

                    if (animationCount < 50) {
                        matched(boardArray[i - 2][j], boardArray[i - 1][j], boardArray[i][j]);
                        animationCount++;

                    } else {
                        sound.play(3f);
                        animationNotDone = false;
                        animationCount = 0;
                    }

                }
                else {
                    count += 10;
                    float x = boardArray[i - 2][j].getX();
                    float y = boardArray[i - 2][j].getY();

                    boardArray[i - 2][j] = randomSprite(x, y);
                    x = boardArray[i - 1][j].getX();
                    y = boardArray[i - 1][j].getY();
                    boardArray[i - 1][j] = randomSprite(x, y);
                    x = boardArray[i][j].getX();
                    y = boardArray[i][j].getY();
                    boardArray[i][j] = randomSprite(x, y);
                    animationNotDone = true;

                }
                horizontal = false;
            }
            else {
                Gdx.app.log("matchHandler", "inside");
                if (animationNotDone) {
                    if (animationCount < 50) {
                        matched(boardArray[i][j - 2], boardArray[i][j - 1], boardArray[i][j]);
                        animationCount++;

                    } else {
                        sound.play(3f);
                        animationNotDone = false;
                        animationCount = 0;
                    }
                }
                else {
                    count += 10;
                    float x = boardArray[i][j - 2].getX();
                    float y = boardArray[i][j - 2].getY();

                    boardArray[i][j - 2] = randomSprite(x, y);
                    x = boardArray[i][j - 1].getX();
                    y = boardArray[i][j - 1].getY();
                    boardArray[i][j - 1] = randomSprite(x, y);
                    x = boardArray[i][j].getX();
                    y = boardArray[i][j].getY();
                    boardArray[i][j] = randomSprite(x, y);
                    animationNotDone = true;
                }


            }
            horizontal = false;
        }



    void findCollisions()
    {   int count_blue = 0;
        int count_green = 0;
        int count_orange = 0;
        int count_purple = 0;
        int count_red = 0;
        int count_yellow = 0;

        //Array list to keep track of stones that need to be removed from the game screen
        for(int i = 0; i < boardArray.length; i++)
            for (int j = 0; j < boardArray[i].length; j++) {
                for(int k = 0; k < textures_array.length; k++)
                    if (validMatch(i, j, textures_array[k])) {

                        matchHandler(i, j);
                    }

            }

    }
}


