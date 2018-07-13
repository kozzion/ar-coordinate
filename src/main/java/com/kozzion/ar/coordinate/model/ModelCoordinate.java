package com.kozzion.ar.coordinate.model;

import com.kozzion.ar.coordinate.CoordinateECOE;
import com.kozzion.ar.coordinate.CoordinateHCOE;
import com.kozzion.ar.coordinate.CoordinateRAD;
import com.kozzion.ar.coordinate.CoordinateScreen;
import com.kozzion.ar.coordinate.CoordinateWGS84;

/**
 * Created by jaapo on 14-5-2018.
 */

public class ModelCoordinate {

    public static final int TYPE_WGS84 = 0;
    public static final int TYPE_ECOE = 1;
    public static final int TYPE_HCOE = 2;
    public static final int TYPE_RAD = 3;



    public String mKey;
    public String mName;
    public int mType;


    public CoordinateWGS84 mCoordinateTerrestrial;
    public CoordinateECOE mCoordinateEarthCentric;
    public CoordinateHCOE mCoordinateHelioCentric;
    public CoordinateRAD mCoordinateStellar;


    public CoordinateScreen convertToScreen(ModelPerspective perspective)
    {
        if(mCoordinateTerrestrial != null){
            return mCoordinateTerrestrial.convertToScreen(perspective);
        } else if (mCoordinateEarthCentric != null) {
            return mCoordinateEarthCentric.convertToScreen(perspective);
        } else if (mCoordinateHelioCentric != null){
            return mCoordinateHelioCentric.convertToScreen(perspective);
        } else {
            return mCoordinateStellar.convertToScreen(perspective);
        }
    }
    public int getType() {
        return mType;
    }



}
