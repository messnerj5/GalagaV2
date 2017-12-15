package com.messner.patel.galaga;



import android.app.Activity;
import android.graphics.Point;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceHolder;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    GameView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
/**
        FrameLayout game = new FrameLayout(this);
        LinearLayout gameWidgets = new LinearLayout(this);
        Button endGameButton = new Button(this);

        endGameButton.setWidth(300);
        endGameButton.setX(200);
        endGameButton.setY(200);
        endGameButton.setText("Start Game");
        gameWidgets.addView(endGameButton);
**/

        gameView = new GameView(this,point);

     //   game.addView(gameView);
      //  game.addView(gameWidgets);

        setContentView(gameView);


    }

    @Override
    protected void onResume() {
        gameView.resume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        gameView.pause();
        super.onPause();
    }
}
