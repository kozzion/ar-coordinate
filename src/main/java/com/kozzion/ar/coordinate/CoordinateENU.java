package com.kozzion.ar.coordinate;

import android.opengl.Matrix;

/**
 * Created by jaapo on 27-4-2018.
 */

public class CoordinateENU {
    public double mEast;
    public double mNorth;
    public double mUp;

    public CoordinateENU(double east, double  north, double up) {
        mEast = east;
        mNorth = north;
        mUp = up;
    }

    public CoordinateCamera convertToCamera(float[] rotatedProjectionMatrix) {
        float [] pointInENU = new float[]{(float) mEast,(float) mNorth, (float)mUp, 1};
        float [] cameraCoordinateVector = new float[4];
        Matrix.multiplyMV(cameraCoordinateVector, 0, rotatedProjectionMatrix, 0, pointInENU, 0);//Depricated
        return new CoordinateCamera(cameraCoordinateVector[0],cameraCoordinateVector[1], cameraCoordinateVector[2], cameraCoordinateVector[3]);

    }

    public String toString() {
        return "east: " + mEast + " north: " + mNorth + " up: " + mUp;
    }
}
