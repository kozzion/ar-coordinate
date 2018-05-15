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
}
