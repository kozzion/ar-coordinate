// Copyright 2008 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.kozzion.ar.coordinate;

import com.kozzion.ar.coordinate.provider.ProviderPlanet;
import com.kozzion.ar.coordinate.tools.Geometry;
import com.kozzion.ar.coordinate.tools.MathUtil;
import com.kozzion.ar.coordinate.tools.Vector3;

import java.util.Date;

//Im asuming this is HEEQ coordinate system
public class CoordinateHEEQ extends Vector3 {

  // Value of the obliquity of the ecliptic for J2000
  private static final float OBLIQUITY = 23.439281f * Geometry.DEGREES_TO_RADIANS;

  public CoordinateHEEQ(float xh, float yh, float zh) {
    super(xh, yh, zh);
  }

  public CoordinateHEEQ Subtract(CoordinateHEEQ other) {
    return new CoordinateHEEQ(x - other.x, y - other.y, z - other.z);
  }

  public CoordinateRAD convertToRAD()
  {
    float ra = Geometry.mod2pi(MathUtil.atan2(y, x));
    float dec = MathUtil.atan(z / MathUtil.sqrt(x * x + y * y));

    return new CoordinateRAD(ra, dec);
  }


  public CoordinateRAD convertToRAD(Date time)
  {
    CoordinateHEEQ earthHEEQ = ProviderPlanet.getOrbitalElements(ProviderPlanet.Planet.Earth, time).convertToHEEQ();
    CoordinateHEEQ coords = Subtract(earthHEEQ);
    CoordinateHEEQ equ = coords.CalculateEquatorialCoordinates();
    return equ.convertToRAD();
  }

  public CoordinateHEEQ CalculateEquatorialCoordinates() {
    return new CoordinateHEEQ(this.x,
        this.y * MathUtil.cos(OBLIQUITY) - this.z * MathUtil.sin(OBLIQUITY),
        this.y * MathUtil.sin(OBLIQUITY) + this.z * MathUtil.cos(OBLIQUITY));
  }

  public float DistanceFrom(CoordinateHEEQ other) {
    float dx = this.x - other.x;
    float dy = this.y - other.y;
    float dz = this.z - other.z;
    return MathUtil.sqrt(dx * dx + dy * dy + dz * dz);
  }


  @Override public String toString() {
    return String.format("(%f, %f, %f)", x, y, z);
  }

}
