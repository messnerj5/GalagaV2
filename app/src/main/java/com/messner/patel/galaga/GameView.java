package com.messner.patel.galaga;

import android.content.Context;

import android.graphics.Bitmap;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kishan on 12/4/2017.
 */

public class GameView extends SurfaceView implements Runnable , View.OnTouchListener {

    volatile boolean playing = true;
    Thread gameThread;
    SurfaceHolder surfaceHolder;
    private static final int FPS = 30;
    private static final int TBTWF = 1000/FPS;
    private long previousTimeMilliseconds;
    private long currentTimeMilliseconds;
    public static float deltaTime;
    Point screenSize;
   public static int SCREEN_HEIGHT;
    private static int SCREEN_WIDTH;
    private List<GameObject> gameObjects = new ArrayList<GameObject>();
    int i;
    private int numSides = 3;
    private Paint paint;
    GameObject testFighter;
    boolean continuousTouch = false;



    Bitmap thisFighter;


    GameGrid grid;

    public static int getScreenHeight() {
        return SCREEN_HEIGHT;
    }

    public static int getScreenWidth() {
        return SCREEN_WIDTH;
    }

    public GameView(Context context, Point point) {
        super(context);
        /**
        leftButton = new Button(getContext());
        rightButton = new Button(context);
        shootButton = new Button(getContext());
        shootButton.setX(100);
        shootButton.setY(1000);
        shootButton.setText("Fuck you button fuck what yea");
        shootButton.setVisibility(View.VISIBLE);
**/


        surfaceHolder = this.getHolder();
        screenSize = point;
        SCREEN_WIDTH = point.x;
        SCREEN_HEIGHT = point.y;


        grid = new GameGrid(SCREEN_WIDTH,SCREEN_HEIGHT, getContext().getResources());
//

        Enemy enemy = new Enemy(grid);



        //this.setOnTouchListener(this);

      //  gameObjects.add(new StarField(100,30.0f));
        int[] t = grid.getBoard()[0][grid.getGridHeight()-1].getCornerCoord();
        testFighter = new Fighter(grid,t[0] , t[1]);

        gameObjects.add(testFighter);
        gameObjects.add(new StarField(100,30.0f));
        gameObjects.add(enemy);
        Missile missile = new Missile(grid);
        gameObjects.add(missile);
       this.setOnTouchListener(this);

        //thisFighter = BitmapFactory.decodeResource(getContext().getResources(),R.drawable.fighter);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event){
        Fighter tempFighter = (Fighter) gameObjects.get(0);
        int direction = 0;
        int currPos[] = tempFighter.getCurrPos();
        int touchPos = (int)event.getX();
        if(Math.abs(touchPos - currPos[0]) > grid.getPixelsPerBox()/2) {
            if (touchPos > currPos[0]) {
                //move right
                direction = 1;
            } else {
                //move left
                direction = -1;
            }
        }

        currPos = grid.moveSide(currPos,direction);
        ((Fighter) gameObjects.get(0)).setCurrPos(currPos);
        ((Fighter) gameObjects.get(0)).setGridPos(direction);
        //gameObjects.get(0).setxPos(gameObjects.get(0).getxPos() + adjustment);
      //  gameObjects.get(0).setyPos(SCREEN_HEIGHT - 160);


        switch(event.getActionMasked()){
/**
            case MotionEvent.AXIS_ORIENTATION:
                gameObjects.add(new Fighter(getResources(),500,500));
                break;
**/
            case MotionEvent.ACTION_DOWN:

                continuousTouch = true;

                /**
                gameObjects.set(0,new Fighter(getResources(),
                        tempFighter.getxPos() + adjustment,
                        SCREEN_HEIGHT - 160));
                 **/

                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                //gameObjects.add(new MissileCharacter(grid,
                  //      (((Fighter) gameObjects.get(0)).getCurrPos())));

                ((Missile) gameObjects.get(3)).addFighterMissile(((Fighter) gameObjects.get(0)).getCurrPos(), ((Fighter) gameObjects.get(0)).getGridPos());
                break;


            case MotionEvent.ACTION_UP:
               continuousTouch = false;
                break;

            case MotionEvent.ACTION_MOVE:
                /**
                gameObjects.set(0, new Fighter(getResources(),
                        tempFighter.getxPos() + adjustment,
                      //  tempFighter.getxPos() + ((tempFighter.getxPos() + (int)event.getX())/10),
                        SCREEN_HEIGHT - 160));
                break;
                 **/

        }
        return true;

    }

    @Override
    public void run() {

        previousTimeMilliseconds = System.currentTimeMillis();

        init();
        while(playing){
            currentTimeMilliseconds = System.currentTimeMillis();
            deltaTime = (currentTimeMilliseconds - previousTimeMilliseconds)/1000.0f;

            update();
            draw();
            try{
                gameThread.sleep(TBTWF);
            }catch(InterruptedException e){

            }
            previousTimeMilliseconds = currentTimeMilliseconds;

        }
    }

    public void pause(){
        playing = false;
        try {
            gameThread.join();
        }catch(InterruptedException e){

        }
    }
    public void init(){
        if(surfaceHolder.getSurface().isValid()){
            Canvas canvas = surfaceHolder.lockCanvas();
            for(i = 0; i<gameObjects.size();i++){
                gameObjects.get(i).onDraw(canvas);
            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void resume(){
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    void update(){

        for(i = 0; i<gameObjects.size();i++){
            gameObjects.get(i).onUpdate();

        }

    }

    void draw(){

        if(surfaceHolder.getSurface().isValid()){
            Canvas canvas = surfaceHolder.lockCanvas();

            canvas.drawARGB(255,0,0,0);

            for(i = 0; i<gameObjects.size();i++){
                gameObjects.get(i).onDraw(canvas);

            }

            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

}
