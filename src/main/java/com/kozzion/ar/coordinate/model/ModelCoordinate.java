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

    public String mKey;
    public String mName;
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



}
