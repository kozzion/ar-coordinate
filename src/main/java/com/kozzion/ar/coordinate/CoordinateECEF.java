package com.kozzion.ar.coordinate;

/**
 * Created by jaapo on 27-4-2018.
 */

public class CoordinateECEF {

    double mX;
    double mY;
    double mZ;

    public CoordinateECEF(double x, double y, double z){
        mX = x;
        mY = y;
        mZ = z;
    }

    public CoordinateENU convertToENU(CoordinateWGS84 locationObserverWSG84){

        CoordinateECEF locationObserverECEF = locationObserverWSG84.convertToWCEF();

        double clat = (float)Math.cos(locationObserverWSG84.mLatitudeRadian);
        double slat = (float)Math.sin(locationObserverWSG84.mLatitudeRadian);
        double clon = (float)Math.cos(locationObserverWSG84.mLongitudeRadian);
        double slon = (float)Math.sin(locationObserverWSG84.mLongitudeRadian);

        double dx = locationObserverECEF.mX - mX;
        double dy = locationObserverECEF.mY - mY;
        double dz = locationObserverECEF.mZ - mZ;

        double east  = -slon*dx + clon*dy;

        double north = -slat*clon*dx - slat*slon*dy + clat*dz;

        double up    = clat*clon*dx + clat*slon*dy + slat*dz;

        return new CoordinateENU(east , north, up);
    }

    public String toString() {
        return "x: " + mX + " y: " + mY + " z: " + mZ;
    }

}
