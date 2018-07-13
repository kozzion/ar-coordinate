package com.kozzion.ar.coordinate.model;

import com.kozzion.ar.coordinate.CoordinateWGS84;

import java.util.Date;

/**
 * Created by jaapo on 14-5-2018.
 */

public class ModelPerspective {
    public Date mDate;
    public CoordinateWGS84 mLocation;
    public float [] mRotatedProjectionMatrix;
    public double mGridWidth;
    public double mGridHeigth;

    public ModelPerspective(Date date, CoordinateWGS84 location, float [] rotatedProjectionMatrix, double gridWidth, double gridHeigth){
        mDate = date;
        mLocation = location;
        mRotatedProjectionMatrix = rotatedProjectionMatrix;
        mGridWidth = gridWidth;
        mGridHeigth = gridHeigth;
    }

    public String toString() {
        return mDate.toString() + "\n" + mLocation.toString() + "\n" + string(mRotatedProjectionMatrix) + "\n" + mGridWidth + " "  + mGridHeigth;
    }

    private String string(float[] mRotatedProjectionMatrix) {
        String s = "";
        for (int i = 0; i < mRotatedProjectionMatrix.length; i++) {
            s = s +  mRotatedProjectionMatrix[i] + "  ";
        }
        return s;
    }
}
