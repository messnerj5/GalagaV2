package com.messner.patel.galaga;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Joseph on 12/10/2017.
 */

public class Missile extends GameObject  {

    GameGrid grid;
    public static ArrayList<MissileCharacter> fighterMissile = new ArrayList<>();
    public static ArrayList<MissileCharacter> enemyMissile = new ArrayList<>();
    Random random;
    int count = 0;

    public Missile(GameGrid grid) {
        this.grid = grid;
    }


    public void addFighterMissile(int[] pos, int[] gridPos){
        fighterMissile.add(new MissileCharacter(grid, pos, gridPos));
    }

    public void addEnemyMissile(int[] pos, int[] gridPos){
        enemyMissile.add(new MissileCharacter(grid, pos,gridPos));
    }

    private void updateFighterMissile(){
        MissileCharacter missile;
        for(int i = 0; i < fighterMissile.size();i++){
            missile = fighterMissile.get(i);
            int temp[] = missile.getCurrPos();
            if(temp[1] < 0){
                fighterMissile.remove(missile);
            }
            missile.setCurrPos(grid.moveVertical(temp, -1));

            if(missile.getContact()){
                fighterMissile.remove(missile);
            }
        }
    }

    private void updateEnemyMissile(){
        MissileCharacter missile;
        for(int i = 0; i < enemyMissile.size();i++){
            missile = enemyMissile.get(i);
            int temp[] = missile.getCurrPos();

            missile.setCurrPos(grid.moveVertical(temp, 1));
            if(temp[1] > grid.getPlayableHeight()){
                enemyMissile.remove(missile);
            }

        }
    }

    private void shootEnemyMissiles(){
        ArrayList<EnemyCharacter> e = Enemy.getMovingEnemies();
        int r = e.size() - 1;
       // int r = random.nextInt(e.size());
        addEnemyMissile(e.get(r).getCurrPos(),e.get(r).getGridPos());
    }


    private void checkEnemyCollision(){
        MissileCharacter m;
        EnemyCharacter e;

        for(int j = 0; j < fighterMissile.size();j++) {
            m = fighterMissile.get(j);
            int missile[] = m.getCurrPos();
            for (int i = 0; i < Enemy.movingEnemies.size(); i++) {
                e = Enemy.movingEnemies.get(i);
                int move[] = e.getCurrPos();


                if (move[0] == missile[0] && move[1] == missile[1]){
                    m.setContact(true);
                    e.setIsDestroy(true);
                    break;
                }
            }
        }

        for(int j = 0; j < fighterMissile.size();j++){
            m = fighterMissile.get(j);
            int missile[] = m.getCurrPos();
            boolean isBreak = false;
            for(int i = Enemy.enemyRestPositions.length - 1;i >= 0;i--){
              //  if(m.gridPos[0])

                for(int k = 0; k < Enemy.enemyRestPositions[0].length - 1; k++) {

                    e = Enemy.enemyRestPositions[i][k];
                    if (e != null) {
                        int[] move = e.getCurrPos();
                        if (move[0] == missile[0] && move[1] == missile[1]) {
                            m.setContact(true);
                            e.setIsDestroy(true);
                            isBreak = true;
                            break;
                        }
                    }
                    if(isBreak){
                        break;
                    }
                }

            }
        }
    }


    @Override
    public void init() {

    }

    @Override
    public void onUpdate() {
        updateFighterMissile();
        updateEnemyMissile();
        checkEnemyCollision();
    }

    @Override
    public void onDraw(Canvas canvas) {
        int temp[];

        for(int i = 0; i < fighterMissile.size();i++) {
            temp = fighterMissile.get(i).getCurrPos();

            canvas.drawBitmap(grid.getImage("missile"), temp[0], temp[1], null);
        }
        for(int i = 0; i < enemyMissile.size();i++) {
            temp = enemyMissile.get(i).getCurrPos();

            canvas.drawBitmap(grid.rotateImage("missile",180), temp[0], temp[1], null);
        }

        if(count % 10 == 0){
            shootEnemyMissiles();
        }

        count++;

    }

}
