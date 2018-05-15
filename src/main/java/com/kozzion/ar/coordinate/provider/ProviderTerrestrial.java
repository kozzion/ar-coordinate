package com.kozzion.ar.coordinate.provider;

import com.kozzion.ar.coordinate.CoordinateWGS84;

/**
 * Created by jaapo on 14-5-2018.
 */

public class ProviderTerrestrial {

    public static CoordinateWGS84 getCoordinate(String location)
    {
        switch (location){
            case "Medellin":
                return new CoordinateWGS84( 6.210646, -75.573583, 1400);
            default:
                throw  new RuntimeException("Unknown location: " + location);
        }
    }
}
