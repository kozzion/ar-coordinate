package com.kozzion.ar.coordinate;

import com.kozzion.ar.coordinate.tools.TimeUtil;

import java.util.Date;

/**
 * Created by jaapo on 27-4-2018.
 */

public class CoordinateRAD {

    public double mRightAscensionRadians;
    public double mDeclenationRadians;

    public CoordinateRAD(double rightAscensionRadians, double declenationRadians){
        mRightAscensionRadians = rightAscensionRadians;
        mDeclenationRadians = declenationRadians;
    }


    public CoordinateAE convertToAE(CoordinateWGS84 observerLocation, Date date){
        //http://www.stargazing.net/kepler/altaz.html
        double LSTDegrees = TimeUtil.meanSiderealTime(date,Math.toDegrees(observerLocation.mLongitudeRadian) );
        double HA = Math.toRadians(LSTDegrees) - mRightAscensionRadians;
        if(HA < 0)
        {
            HA += Math.PI * 2;
        }
        double elevationRadians = Math.asin(Math.sin(mDeclenationRadians) * Math.sin(observerLocation.mLatitudeRadian) +
                               Math.cos(mDeclenationRadians) * Math.cos(observerLocation.mLatitudeRadian) * Math.cos(HA));

        double azimuthRadians =  Math.acos(
                (Math.sin(mDeclenationRadians) - Math.sin(elevationRadians) * Math.sin(observerLocation.mLatitudeRadian))
                / (Math.cos(elevationRadians)*Math.cos(observerLocation.mLatitudeRadian)));

        //As sin(HA) is positive, the angle AZ is 2pi - A
        if(0 < Math.sin(HA)) {
            return new CoordinateAE((Math.PI * 2) - azimuthRadians, elevationRadians);
        } else {
            return new CoordinateAE(azimuthRadians, elevationRadians);
        }
    }

    public String toString() {
        return "Ra: " + mRightAscensionRadians + " Dec: " + mDeclenationRadians;
    }

}
