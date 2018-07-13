package com.kozzion.ar.coordinate;

import android.graphics.Canvas;

import com.kozzion.ar.coordinate.model.ModelPerspective;

/**
 * Created by jaapo on 27-4-2018.
 */

public class CoordinateWGS84 {

    private final static double WGS84_A = 6378137.0;                  // WGS 84 semi-major axis constant in meters
    private final static double WGS84_E2 = 0.00669437999014;          // square of WGS 84 eccentricity

    public double mLatitudeRadian;
    public double mLongitudeRadian;
    public double mAltitudeMeter;

    public CoordinateWGS84(double latitudeDegrees, double longitudeDegrees, double altitudeMeter)
    {
        mLatitudeRadian = Math.toRadians(latitudeDegrees);
        mLongitudeRadian = Math.toRadians(longitudeDegrees);
        mAltitudeMeter = altitudeMeter;
    }


    //https://www.mathworks.com/help/aeroblks/llatoecefposition.html
    public CoordinateECEF convertToWCEF()
    {

        float clat = (float) Math.cos(mLatitudeRadian);
        float slat = (float) Math.sin(mLatitudeRadian);
        float clon = (float) Math.cos(mLongitudeRadian);
        float slon = (float) Math.sin(mLongitudeRadian);

        float N = (float) (WGS84_A / Math.sqrt(1.0 - WGS84_E2 * slat * slat));

        float x = (float) ((N + mAltitudeMeter) * clat * clon);
        float y = (float) ((N + mAltitudeMeter) * clat * slon);
        float z = (float) ((N * (1.0 - WGS84_E2) + mAltitudeMeter) * slat);

        return new CoordinateECEF(x , y, z);

    }

    public CoordinateScreen convertToScreen(ModelPerspective perspective) {
        return convertToWCEF()
                .convertToENU(perspective.mLocation)
                .convertToCamera(perspective.mRotatedProjectionMatrix)
                .convertToScreen(perspective.mGridWidth, perspective.mGridHeigth);
    }

    public String toString() {
        return "latitude (deg): " + Math.toDegrees(mLatitudeRadian) + " longitude (deg): " +  Math.toDegrees(mLongitudeRadian) + "  altitudeMeter (meter): " + mAltitudeMeter;
    }
}
