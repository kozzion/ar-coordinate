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

package com.kozzion.ar.coordinate;


import com.kozzion.ar.coordinate.tools.Geometry;
import com.kozzion.ar.coordinate.tools.MathUtil;
import com.kozzion.ar.coordinate.model.ModelPerspective;

/**
 * This class wraps the six parameters which define the path an object takes as
 * it orbits the sun.
 *
 * The equations come from JPL's Solar System Dynamics site:
 * http://ssd.jpl.nasa.gov/?planet_pos
 * 
 * The original source for the calculations is based on the approximations described in:
 * Van Flandern T. C., Pulkkinen, K. F. (1979): "Low-Precision Formulae for
 * Planetary Positions", 1979, Astrophysical Journal Supplement Series, Vol. 41,
 * pp. 391-411.
 * 
 * 
 * @author Kevin Serafini
 * @author Brent Bryan
 */

//Helio Centric Orbital elements
public class CoordinateHCOE {
  // calculation error
  private final static float EPSILON = 1.0e-6f;

  public final float mDistance;       // Mean mDistance (AU)
  public final float eccentricity;   // Eccentricity of orbit
  public final float inclination;    // Inclination of orbit (AngleUtils.RADIANS)
  public final float ascendingNode;  // Longitude of ascending node (AngleUtils.RADIANS)
  public final float perihelion;     // Longitude of perihelion (AngleUtils.RADIANS)
  public final float mMeanLongitude;  // Mean longitude (AngleUtils.RADIANS)

  public CoordinateHCOE(float distance, float eccentricity, float inclination, float ascendingNode, float perihelion, float meanLongitude) {
    this.mDistance = distance;
    this.eccentricity = eccentricity;
    this.inclination = inclination;
    this.ascendingNode = ascendingNode;
    this.perihelion = perihelion;
    this.mMeanLongitude = meanLongitude;
  }

  public float getAnomaly() {
    return calculateTrueAnomaly(mMeanLongitude - perihelion, eccentricity);
  }
  
  // compute the true anomaly from mean anomaly using iteration
  // m - mean anomaly in radians
  // e - orbit eccentricity
  // Return value is in radians.
  private static float calculateTrueAnomaly(float m, float e) {
    // initial approximation of eccentric anomaly
    float e0 = m + e * MathUtil.sin(m) * (1.0f + e * MathUtil.cos(m));
    float e1;

    // iterate to improve accuracy
    int counter = 0;
    do {
      e1 = e0;
      e0 = e1 - (e1 - e * MathUtil.sin(e1) - m) / (1.0f - e * MathUtil.cos(e1));
      if (counter++ > 100) {
        break;
      }
    } while (MathUtil.abs(e0 - e1) > EPSILON);

    // convert eccentric anomaly to true anomaly
    float v =
        2f * MathUtil.atan(MathUtil.sqrt((1 + e) / (1 - e))
            * MathUtil.tan(0.5f * e0));
    return Geometry.mod2pi(v);
  }

  public CoordinateHEEQ convertToHEEQ()
  {
    float anomaly = getAnomaly();
    float ecc = eccentricity;
    float radius = mDistance * (1 - ecc * ecc) / (1 + ecc * MathUtil.cos(anomaly));

    // heliocentric rectangular coordinates of planet
    float per = perihelion;
    float asc = ascendingNode;
    float inc = inclination;
    float xh = radius *
            (MathUtil.cos(asc) * MathUtil.cos(anomaly + per - asc) -
                    MathUtil.sin(asc) * MathUtil.sin(anomaly + per - asc) *
                            MathUtil.cos(inc));
    float yh = radius *
            (MathUtil.sin(asc) * MathUtil.cos(anomaly + per - asc) +
                    MathUtil.cos(asc) * MathUtil.sin(anomaly + per - asc) *
                            MathUtil.cos(inc));
    float zh = radius * (MathUtil.sin(anomaly + per - asc) * MathUtil.sin(inc));

    return new CoordinateHEEQ( xh, yh, zh);
  }

  public CoordinateScreen convertToScreen(ModelPerspective perspective)
  {
    return convertToHEEQ()
            .convertToRAD(perspective.mDate)
            .convertToAE(perspective.mLocation, perspective.mDate)
            .convertToENU()
            .convertToCamera(perspective.mRotatedProjectionMatrix)
            .convertToScreen(perspective.mGridWidth, perspective.mGridWidth);
  }

  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("Mean Distance: " + mDistance + " (AU)\n");
    sb.append("Eccentricity: " + eccentricity + "\n");
    sb.append("Inclination: " + inclination + " (AngleUtils.RADIANS)\n");
    sb.append("Ascending Node: " + ascendingNode + " (AngleUtils.RADIANS)\n");
    sb.append("Perihelion: " + perihelion + " (AngleUtils.RADIANS)\n");
    sb.append("Mean Longitude: " + mMeanLongitude + " (AngleUtils.RADIANS)\n");

    return sb.toString();
  }
}
