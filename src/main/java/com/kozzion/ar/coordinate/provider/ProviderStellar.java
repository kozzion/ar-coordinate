package com.kozzion.ar.coordinate.provider;

import com.kozzion.ar.coordinate.CoordinateRAD;
import com.kozzion.ar.coordinate.model.ModelCoordinate;
import com.kozzion.ar.coordinate.tools.MathUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jaapo on 1-5-2018.
 */

public class ProviderStellar {

    public static List<ModelCoordinate> getCoordinateList() {
        List<ModelCoordinate> coordinateList = new ArrayList<>();
        for (String coordinateName : getCoordinateNameList()) {
            getCoordinate(coordinateName);
        }
        return coordinateList;
    }

    public static List<String> getCoordinateNameList() {
        List<String> coordinateNameList = new ArrayList<>();
        coordinateNameList.add("M1");
        coordinateNameList.add("M74");
        coordinateNameList.add("Aldebaran");
        return coordinateNameList;
    }

    public static CoordinateRAD getCoordinate(String coordinateName)
    {
        switch (coordinateName){
            case "M1":
                return new CoordinateRAD(MathUtil.hoursToRadian(5, 34.5, 0), MathUtil.degreesToRadian( 22, 1, 0));
            case "M74":
                return new CoordinateRAD(MathUtil.hoursToRadian(1, 36, 41.8), MathUtil.degreesToRadian( 15, 47, 1));
            case "Aldebaran":
                return new CoordinateRAD(MathUtil.hoursToRadian(4, 36, 2), MathUtil.degreesToRadian( 16, 30, 33));
            default:
                throw new RuntimeException("Unknown stellar coordinateName: "  + coordinateName);
        }

    }



}
