package com.messner.patel.galaga;

/**
 * Created by Joseph on 12/8/2017.
 */

public class EnemyCharacter {
    private int[] restPos = new int[2];
    private int[] currPos = new int[2];
    private int[] gridPos = new int[2];
    private String enemyType;
    private boolean isHittable;
    private boolean isMoving;
    private boolean isDestroy = false;



    public EnemyCharacter(String enemyType, int[] startLoc, int[] gridPos) {
        this.enemyType = enemyType;
        restPos = startLoc;
        currPos[0] = restPos[0];
        currPos[1] = restPos[1];
        this.gridPos[0] = gridPos[0];
        this.gridPos[1] = gridPos[1];
        //this.isHittable = isHittable;
        //this.isMoving = isMoving;
    }


    public int[] getRestLocation(){
        return restPos;
    }

    public int[] getCurrPos(){
        return currPos;
    }

    public String getEnemyType(){
        return enemyType;
    }

    public void setCurrPos(int[] pos){
        currPos = pos;
    }

    public int[] getGridPos(){
        return gridPos;
    }

    public void setHittable(boolean set){
        isHittable = set;
    }

    public boolean getHittable(){
        return isHittable;
    }

    public void setMoving(boolean move){
        isMoving = move;
    }

    public boolean getMoving(){
        return isMoving;
    }

    public boolean getIsDestroy(){
        return isDestroy;
    }

    public void setIsDestroy(boolean set){
        isDestroy = set;
    }
}
