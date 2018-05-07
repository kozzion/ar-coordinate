package com.kozzion.ar.coordinate;

import android.graphics.Canvas;

/**
 * Created by jaapo on 27-4-2018.
 */

public class CoordinateCamera {
    double mX;
    double mY;
    double mZ;
    double mS;

    public CoordinateCamera(double x, double y, double z, double s){
        mX = x;
        mY = y;
        mZ = z;
        mS = s;
    }

    public CoordinateScreen convertToScreen(Canvas canvas) {
        double x  = (0.5f + mX/mS) * canvas.getWidth();
        double y = (0.5f - mY/mS) * canvas.getHeight();
        return new CoordinateScreen(x,y,mZ);
    }
}
