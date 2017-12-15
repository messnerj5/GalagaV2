package com.messner.patel.galaga;

import android.graphics.Canvas;

/**
 * Created by Kishan on 12/4/2017.
 */

public abstract class GameObject {
    public abstract void init();

    public abstract void onUpdate();

    public abstract void onDraw(Canvas canvas);

}
