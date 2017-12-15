package com.messner.patel.galaga;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

/**
 * Created by Kishan on 12/9/2017.
 */

public class MissileCharacter{


    int xPos , yPos;
    int[] currPos = new int[2];
    int[] gridPos = new int[2];
    boolean contact = false;
    GameGrid grid;

    public MissileCharacter(GameGrid grid, int[] currPos, int[] gridPos){
        this.xPos = xPos;
        this.yPos = yPos;

        this.currPos[0] = currPos[0];
        this.currPos[1] = currPos[1] - grid.getPixelsPerBox();

        this.gridPos[0] = gridPos[0];
        this.gridPos[1] = gridPos[1];

        this.grid = grid;

    }

    public int[] getCurrPos(){
        return currPos;
    }

    public void setCurrPos(int pos[]){
        currPos = pos;
    }

    //@Override
    public void init() {

    }

   // @Override
    public void onUpdate() {
        currPos[1] -= 10 ;
    }

    //@Override
    public void onDraw(Canvas canvas) {
        canvas.drawBitmap(grid.getImage("missile"),currPos[0],currPos[1],null);

    }


    public void setContact(boolean set){
        contact = set;
    }

    public boolean getContact(){
        return contact;
    }


    public int[] getGridPos(){
        return gridPos;
    }

    public void setGridPos(int[] set){
        gridPos = set;
    }
}
