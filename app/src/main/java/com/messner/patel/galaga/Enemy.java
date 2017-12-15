package com.messner.patel.galaga;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.GradientDrawable;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Joseph on 12/8/2017.
 */

public class Enemy extends GameObject {

    GameGrid grid;

   // ArrayList<ArrayList<EnemyCharacter>> enemyRestPositions = new ArrayList<>();
    public static EnemyCharacter[][] enemyRestPositions = new EnemyCharacter[5][10];
    public static ArrayList<EnemyCharacter> movingEnemies = new ArrayList<>();
   // private ArrayList<EnemyCharacter> hittableRestEnemies = new ArrayList<>();


    private final int startLocGreen = 3 + GameGrid.shiftSpace; //This is the amount of grids the green guys are from the LEFT side
    private final int startLocRed = 1 + GameGrid.shiftSpace; //From left the amount of grid in the red guys start
    private final int startLocBlue = 0 + GameGrid.shiftSpace;

    private  int quantGreenGalaga = 4;
    private  int quantRedGalaga = 16;
    private  int quantBlueGalaga = 20;

    private int quantGreenLayers = 1;
    private int quantRedLayers = 2;
    private int quantBlueLayers = 2;
    private int totalEnemyLayers = quantBlueLayers + quantGreenLayers + quantRedLayers;

    private int currentLayer = 0;

    int count= 0;
    Random random = new Random();
    int randCount = 10;

    public Enemy(GameGrid grid) {
        this.grid = grid;
        setStartPositions();
        //chooseCharToMove();
    }

    public void setStartPositions(){
        int temp[] = new int[2];
        //boolean isHittable = false;
        for(int i = 0; i< quantGreenGalaga;i++){
            temp[0] = 0;
            temp[1] = i;
            enemyRestPositions[0][i] = new EnemyCharacter("green",grid.getBoard()[startLocGreen + i][0].getCornerCoord(),temp);
        }
        for(int layers = 0; layers < quantRedLayers; layers++) {
            for (int i = 0; i <= quantRedGalaga/2 - 1; i++) {
                temp[0] = 1+layers;
                temp[1] = i;
                enemyRestPositions[1+layers][i] = new EnemyCharacter("red", grid.getBoard()[startLocRed + i][1+layers].getCornerCoord(),temp);
            }
        }
        for(int layers = 0; layers < quantBlueLayers; layers++) {
            for (int i = 0; i < quantBlueGalaga/2; i++) {
                temp[0] = 3+layers;
                temp[1] = i;
                enemyRestPositions[temp[0]][temp[1]] = new EnemyCharacter("blue", grid.getBoard()[startLocBlue + i][3+layers].getCornerCoord(),temp);
            }

        }

    }




    private void chooseCharToMove(){
        boolean isEmpty = true;

        while(isEmpty) {
            int randx = random.nextInt(enemyRestPositions[0].length);
            int randy = random.nextInt(enemyRestPositions.length);
            EnemyCharacter temp = enemyRestPositions[randy][randx];
            if(temp != null){
               enemyRestPositions[randy][randx] = null;
               movingEnemies.add(temp);
                //temp.setHittable(true);
                //change hittable status
                isEmpty = false;
            }
        }
    }


    private void moveCharacter(){
        //--------------------------------
        //instead of having a movingEnemies datastructure
        //there can be a field within the enemy that states whether it is moving
        //or not. THis way it limits the amount of times the datastructure needs
        //to be looped through

        //Essentially, all logic would stay the same except another datastructure
        //is not needed.
        //
        //still need a hittable datastructure potentially...for speed
        //-----------------------------------
        for (int i = 0;i< movingEnemies.size();i++) {
            EnemyCharacter e = movingEnemies.get(i);
            int currPos[] = e.getCurrPos();
            e.setCurrPos(grid.moveVertical(currPos, 1));
            if(currPos[1] > grid.getPlayableHeight()){
                currPos[1] = 0;
                e.setCurrPos(currPos);
            }
            if(currPos[1] == e.getRestLocation()[1]){
                movingEnemies.remove(e);
                currPos = e.getGridPos();
                enemyRestPositions[currPos[0]][currPos[1]] = e;
            }
        }
    }

//    public void setHittableRestEnemies(){
//        EnemyCharacter e;
//        for(int i = enemyRestPositions.length - 2; i >= 0; i--){
//            for(int j = 0; j < enemyRestPositions[0].length; j++){
//                e = enemyRestPositions[i][j];
//                if(e != null) {
//                    if(enemyRestPositions[i+1][j] == null){
//                        e.setHittable(true);
//                        hittableRestEnemies.add(e);
//                    }else{
//                        hittableRestEnemies.remove(e);
//                    }
//                }
//            }
//        }
//    }



    public EnemyCharacter[][] getEnemyStructure(){
        return enemyRestPositions;

    }

    public static ArrayList<EnemyCharacter> getMovingEnemies(){
        return movingEnemies;
    }



    public void destroy(EnemyCharacter e){
        //find by gridLocation
        int[] temp = e.getGridPos();
       if(enemyRestPositions[temp[0]][temp[1]] == null) {
           for(int i = 0; i < movingEnemies.size();i++) {
                if(e == movingEnemies.get(i)){
                    movingEnemies.remove(e);
                }
           }
       }else{
           enemyRestPositions[temp[0]][temp[1]] = null;
       }
    }

    @Override
    public void init() {
    }

    @Override
    public void onUpdate() {
    }

    @Override
    public void onDraw(Canvas canvas) {
        int[] arr = new int[2];
        //setHittableEnemies();
        for(int i = 0; i < enemyRestPositions.length - 1; i++){
            for(int j = 0; j < enemyRestPositions[0].length - 1; j++){
                EnemyCharacter temp = enemyRestPositions[i][j];
                if(temp != null){
                    arr = temp.getRestLocation();
                    if(temp.getIsDestroy()){
                        canvas.drawBitmap(grid.getImage("explosion"),arr[0],arr[1],null);
                        enemyRestPositions[i][j] = null;
                    }else {
                        canvas.drawBitmap(grid.getImage(temp.getEnemyType()), arr[0], arr[1], null);
                    }
                }
            }
        }
        if(count % 2 == 0) {
            moveCharacter();

        }
        if(count % randCount == 0){
            chooseCharToMove();
            randCount = random.nextInt(20) + 1;
        }
        count++;
        ArrayList<EnemyCharacter> e = new ArrayList<>();
        e.addAll(movingEnemies);
        for (EnemyCharacter c:e) {
            arr = c.getCurrPos();
            if(c.getIsDestroy()){
                canvas.drawBitmap(grid.getImage("explosion"),arr[0],arr[1],null);
                movingEnemies.remove(c);
            }else{
                canvas.drawBitmap(grid.getImage(c.getEnemyType()),arr[0],arr[1],null);

            }
        }
    }

}

