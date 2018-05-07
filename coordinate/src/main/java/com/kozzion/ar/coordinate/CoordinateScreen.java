package com.kozzion.ar.coordinate;

/**
 * Created by jaapo on 27-4-2018.
 */

public class CoordinateScreen {
    public double mX;
    public double mY;
    public double mZ;

    public CoordinateScreen(double x, double y, double z) {
        mX = x;
        mY = y;
        mZ = z;
    }

    public boolean isFront(){
        return mZ < 0;
    }

}
