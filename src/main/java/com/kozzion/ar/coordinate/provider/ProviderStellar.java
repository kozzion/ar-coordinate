package com.kozzion.ar.coordinate.provider;

import com.kozzion.ar.coordinate.CoordinateRAD;
import com.kozzion.ar.coordinate.tools.MathUtil;

/**
 * Created by jaapo on 1-5-2018.
 */

public class ProviderStellar {

    public enum StellarObject{
        M1,
        M74,
        ALDEBARAN
    }
    public static CoordinateRAD getCoordinate(StellarObject stellarObject)
    {
        switch (stellarObject)
        {
            case M74:
                return new CoordinateRAD(MathUtil.hoursToRadian(1, 36, 41.8), MathUtil.degreesToRadian( 15, 47, 1));
            case ALDEBARAN:
                return new CoordinateRAD(MathUtil.hoursToRadian(4, 36, 2), MathUtil.degreesToRadian( 16, 30, 33));

            default:
                throw new RuntimeException("Unknown stellar object");
        }

    }


}
