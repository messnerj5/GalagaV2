package com.messner.patel.galaga;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.widget.ImageView;


/**
 * Created by Kishan on 12/7/2017.
 */

public class Fighter extends GameObject implements Shootable {
    int xPos , yPos;
    int lives = 1;
    public static boolean isAlive;
    int counter = 0;
    private int[] currPos = new int[2];
    private int[] gridPos = new int[2];



    GameGrid grid;

    public Fighter(GameGrid grid,int xPos , int yPos){
        this.xPos = xPos;
        this.yPos = yPos;

        //thisFighter = BitmapFactory.decodeResource(resources , R.drawable.fighter);
        this.grid = grid;

        gridPos[0] = 0;
        gridPos[1] = grid.getGridHeight() - 1;
        currPos = grid.getBoard()[gridPos[0]][gridPos[1]].getCornerCoord();
        isAlive = true;

    }

    public int[] getCurrPos(){
        return currPos;
    }

    public void setCurrPos(int[] temp){
        currPos = temp;
    }

    public void shoot(){

    }

    public int[] getGridPos(){
        return gridPos;
    }

    public void setGridPos(int set){
        if(gridPos[0] >= 0 && gridPos[0] <= grid.getGridWidth()) {
            gridPos[0] += set;
        }
    }

    @Override
    public void init() {
        isAlive = true;
    }

    @Override
    public void onUpdate() {

    }

    public static void setIsAlive(boolean alive){
        isAlive = alive;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if(isAlive){
            canvas.drawBitmap(grid.getImage("fighter"),currPos[0],currPos[1],null);
        }else if(counter<10){
            canvas.drawBitmap(grid.getImage("explosion"),currPos[0],currPos[1],null);
            counter++;
        }else{
            if(Enemy.movingEnemies.size()==0){
                init();
                Enemy.movingCharacters = true;
            }
        }

    }
}
