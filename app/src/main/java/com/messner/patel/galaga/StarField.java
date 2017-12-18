package com.messner.patel.galaga;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Kishan on 12/4/2017.
 */

public class StarField extends GameObject {
    protected Vector2 points[];
    protected int numberOfPoints;
    protected Paint paint;
    protected float currentAlpha = 255;
    protected float twinkleSpeed = 100.0f;
    protected int direction = 1;
    protected int maxHeight = 0;

    public StarField(int numberOfPoints, float twinkleSpeed, int maxHeight) {
        this.numberOfPoints = numberOfPoints;
        this.twinkleSpeed = twinkleSpeed;
        paint = new Paint();

        this.maxHeight = maxHeight;

        paint.setColor(Color.argb((int)currentAlpha,255,255,255));
        points = new Vector2[numberOfPoints];

        Random rand = new Random();
        for(int i = 0; i<numberOfPoints;i++){
            points[i] = new Vector2(rand.nextInt(GameView.getScreenWidth()),
                    rand.nextInt(GameView.getScreenHeight()));
        }
    }

    @Override
    public void init() {

    }

    @Override
    public void onUpdate() {
       // calculateAlpha();
        scrollStars();

    }

    private void scrollStars(){
        for(int i = 0 ; i<numberOfPoints;i++){

            points[i].setY((points[i].y + 1) % maxHeight );
        }
    }

    private void calculateAlpha(){
        if(direction>0){

            currentAlpha = (currentAlpha + twinkleSpeed *GameView.deltaTime * direction);

            if(currentAlpha >= 255){
                currentAlpha = 255;
                direction*=-1;
            }

        }else{
            currentAlpha = (currentAlpha + twinkleSpeed *GameView.deltaTime * direction);

            if(currentAlpha <= 0){
                currentAlpha = 0;
                direction*=-1;
            }
        }
    }



    @Override
    public void onDraw(Canvas canvas) {

        paint.setColor(Color.argb((int) currentAlpha,255,255,255));
        for(int i = 0; i<numberOfPoints;i++){
            canvas.drawPoint(points[i].x,points[i].y,paint);
        }

    }
}
