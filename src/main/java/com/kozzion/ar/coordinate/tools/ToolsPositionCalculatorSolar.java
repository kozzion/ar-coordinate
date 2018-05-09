// Copyright 2008 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.kozzion.ar.coordinate.tools;

import com.kozzion.ar.coordinate.CoordinateHEEQ;
import com.kozzion.ar.coordinate.CoordinateRAD;
import com.kozzion.ar.coordinate.provider.ProviderPlanet;

import java.util.Date;

/**
 * Calculate the position of the Sun in RA and Dec
 * 
 * TODO(johntaylor): get rid of this class once the provider
 * framework is refactored.  This duplicates functionality from elsewhere,
 * but the current ephemeris/provider code is a bit too tangled up for easy reuse.
 *
 */
public class ToolsPositionCalculatorSolar {
  public static CoordinateRAD getSolarPosition(Date time) {

    CoordinateHEEQ planetCoordinates = new CoordinateHEEQ(0,0, 0);
    return planetCoordinates.convertToRAD(time);
  }

  public static CoordinateRAD getUranusPosition(Date time) {

    CoordinateHEEQ planetCoordinates = ProviderPlanet.getOrbitalElements(ProviderPlanet.Planet.Uranus, time).convertToHEEQ();
    return planetCoordinates.convertToRAD(time);

  }
}