package com.kozzion.ar.coordinate;

/**
 * Created by jaapo on 29-4-2018.
 */

public class CoordinateAE {
    public double mAzimuthRadians;
    public double mElevationRadians;

    public CoordinateAE(double azimuthRadians, double elevationRadians){
        mAzimuthRadians = azimuthRadians;
        mElevationRadians = elevationRadians;
    }

    public CoordinateENU convertToENU()
    {
        double east = Math.sin(mAzimuthRadians);
        double north = Math.cos(mAzimuthRadians);
        double up = Math.tan(mElevationRadians);
        return new CoordinateENU(east, north, up);
    }

    public String toString() {
        return "Azimuth: " + mAzimuthRadians + " Elevation: " + mElevationRadians;
    }
}
