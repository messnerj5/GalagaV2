package com.messner.patel.galaga;

import android.content.Context;
import android.graphics.Canvas;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.AUDIO_SERVICE;

/**
 * Created by Joseph on 12/10/2017.
 */

public class Missile extends GameObject {


    GameGrid grid;
    public static ArrayList<MissileCharacter> fighterMissile = new ArrayList<>();
    public static ArrayList<MissileCharacter> enemyMissile = new ArrayList<>();
    Context context;
    int count = 0;
    SoundPool soundPool;
    private boolean loaded;
    int defaultShot,enemyDie,fighterDie;
    AudioManager audioManager;
    int xPos,yPos;


    public Missile(Context context, GameGrid grid , int xPos, int yPos) {
        this.grid = grid;
        this.context = context;
        this.xPos = xPos;
        this.yPos = yPos;

    }

    public void setXPos(int xPos){
        this.xPos = xPos;
    }

    public void setYPos(int yPos){
        this.yPos = yPos;
    }


    public void addFighterMissile(int[] pos, int[] gridPos) {
        fighterMissile.add(new MissileCharacter(grid, pos, gridPos));
    }

    public void addEnemyMissile(int[] pos, int[] gridPos) {
        enemyMissile.add(new MissileCharacter(grid, pos, gridPos));
    }


    private void updateFighterMissile() {
        MissileCharacter missile;
       // int originalSize = fighterMissile.size();
        for (int i = 0; i < fighterMissile.size(); i++) {
            missile = fighterMissile.get(i);
            int temp[] = missile.getCurrPos();
            if (temp[1] < 0) {
                fighterMissile.remove(missile);
            }
            missile.setCurrPos(grid.moveVertical(temp, -1));

            if (missile.getContact()) {
                fighterMissile.remove(missile);
            }
        }
    }

    private void updateEnemyMissile() {
        MissileCharacter missile;
        for (int i = 0; i < enemyMissile.size(); i++) {
            missile = enemyMissile.get(i);
            int temp[] = missile.getCurrPos();

            missile.setCurrPos(grid.moveVertical(temp, 1));
            if (temp[1] > grid.getPlayableHeight()) {
                enemyMissile.remove(missile);
            }

        }
    }

    private void shootEnemyMissiles() {
        Random random = new Random();
        ArrayList<EnemyCharacter> e = Enemy.getMovingEnemies();

        // int r = e.size() - 1;
        if(e.size() == 0 || !Fighter.isAlive){
            System.out.println("Made it to before");
          //  addEnemyMissile(e.get(0).getCurrPos(), e.get(0).getGridPos());
            System.out.println("Crashing after");
            //GameView.getPublicSoundPool().play(GameView.getMapOfSounds().get("EnemyShot"), 10, 10,
            //         1, 0, 1f);
           // soundPool.play(defaultShot,100,100,1,0,1f);
        }else {
            int r = random.nextInt(e.size());

            if (e.get(r) == null) {
            } else {
                addEnemyMissile(e.get(r).getCurrPos(), e.get(r).getGridPos());
                //GameView.getPublicSoundPool().play(GameView.getMapOfSounds().get("EnemyShot"), 10, 10,
                //         1, 0, 1f);
                soundPool.play(defaultShot, 100, 100, 2, 0, 1f);


            }
        }
    }


    private void checkEnemyCollision() {
        MissileCharacter m;
        EnemyCharacter e;
        boolean fighterWin = true;
        for (int j = 0; j < fighterMissile.size(); j++) {
            m = fighterMissile.get(j);
            int missile[] = m.getCurrPos();
            for (int i = 0; i < Enemy.movingEnemies.size(); i++) {
                fighterWin = false;
                e = Enemy.movingEnemies.get(i);
                int move[] = e.getCurrPos();
                System.out.println("values in move");
                System.out.println(move[0]);
                System.out.println(move[1]);


                if ((move[0] == missile[0] && move[1] == missile[1]) ||
                        (move[0] == missile[0] && move[1]-grid.getPixelsPerBox()  == missile[1])){

                    m.setContact(true);
                    e.setIsDestroy(true);
                    soundPool.play(enemyDie,100,100,1,0,1f);
                    break;
                }
            }
        }

        for (int j = 0; j < fighterMissile.size(); j++) {
            m = fighterMissile.get(j);
            int missile[] = m.getCurrPos();
            boolean isBreak = false;
            for (int i = Enemy.enemyRestPositions.length-1; i >= 0; i--) {
                //  if(m.gridPos[0])

                for (int k = 0; k < Enemy.enemyRestPositions[0].length; k++) {

                    e = Enemy.enemyRestPositions[i][k];
                    if (e != null) {
                        fighterWin &= false;
                        int[] move = e.getCurrPos();
                        if ((move[0] == missile[0] && move[1] == missile[1] && !e.beingDestroyed )) {
                            m.setContact(true);
                            e.setIsDestroy(true);
                            isBreak = true;
                            soundPool.play(enemyDie,100,100,1,0,1f);
                            break;
                        }
                    }
                    if (isBreak) {
                        break;
                    }
                    GameView.setFighterWin(fighterWin);
                }

            }
        }
    }

    private void checkFighterCollision(){
        for(MissileCharacter missile:enemyMissile){
            int[] temp = missile.getCurrPos();
            if(temp[0] == xPos && temp[1] == yPos && Fighter.isAlive){

                Enemy.setMovingCharacters(false);
                Fighter.setIsAlive(false);

                soundPool.play(fighterDie,100,100,1,0,1f);


            }
        }
    }




    @Override
    public void init() {
        audioManager = (AudioManager) context.getSystemService(AUDIO_SERVICE);
        soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);

        // Current volumn Index of particular stream type.
        float currentVolumeIndex = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        // Get the maximum volume index for a particular stream type.
        float maxVolumeIndex  = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        // Volumn (0 --> 1)
        float volume = currentVolumeIndex / maxVolumeIndex;

        // Suggests an audio stream whose volume should be changed by
        // the hardware volume controls.
        //setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(10,AudioManager.STREAM_MUSIC,50);

        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });
        defaultShot = soundPool.load(context,R.raw.defaultshot,1);
        enemyDie = soundPool.load(context,R.raw.enemydestroyed,1);
        fighterDie = soundPool.load(context,R.raw.fighterdie,1);


    }

    @Override
    public void onUpdate() {
        updateFighterMissile();
        updateEnemyMissile();
        checkEnemyCollision();
        checkFighterCollision();
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
