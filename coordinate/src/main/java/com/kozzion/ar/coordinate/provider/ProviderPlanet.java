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

package com.kozzion.ar.coordinate.provider;

import com.kozzion.ar.coordinate.CoordinateRAD;
import com.kozzion.ar.coordinate.CoordinateHEEQ;
import com.kozzion.ar.coordinate.CoordinateOE;
import com.kozzion.ar.coordinate.CoordinateWGS84;
import com.kozzion.ar.coordinate.tools.Geometry;
import com.kozzion.ar.coordinate.tools.MathUtil;
import com.kozzion.ar.coordinate.tools.TimeConstants;
import com.kozzion.ar.coordinate.tools.TimeUtil;


import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ProviderPlanet {

  public enum Planet
  {
    Pluto,
    Neptune,
    Uranus,
    Jupiter,
    Saturn,
    Mars,
    Earth,
    Venus,
    Mercury,
  }


  public enum LunarPhase
  {
    NewMoon,
    WaxingQuarter,
    WaxingHalf,
    WaxingThreeQuarters,
    FullMoon,
    WaningThreeQuarters,
    WaningHalf,
    WaningQuarter,
  }




  // Taken from JPL's Planetary Positions page: http://ssd.jpl.nasa.gov/?planet_pos
  // This gives us a good approximation for the years 1800 to 2050 AD. Which, to be honest,
  // is over the expected lifetime of this library, if you live you live in 2050 and are using
  // this code get in your flying car and go find someone who can do a better job
  public static CoordinateOE getOrbitalElements(Planet planet, Date date) {
    // Centuries since J2000
    float jc = (float) TimeUtil.julianCenturies(date);

    switch (planet) {
      case Mercury: {
        float a = 0.38709927f + 0.00000037f * jc;
        float e = 0.20563593f + 0.00001906f * jc;
        float i = (7.00497902f - 0.00594749f * jc) * Geometry.DEGREES_TO_RADIANS;
        float l =
            Geometry.mod2pi((252.25032350f + 149472.67411175f * jc) * Geometry.DEGREES_TO_RADIANS);
        float w = (77.45779628f + 0.16047689f * jc) * Geometry.DEGREES_TO_RADIANS;
        float o = (48.33076593f - 0.12534081f * jc) * Geometry.DEGREES_TO_RADIANS;
        return new CoordinateOE(a, e, i, o, w, l);
      }

      case Venus: {
        float a = 0.72333566f + 0.00000390f * jc;
        float e = 0.00677672f - 0.00004107f * jc;
        float i = (3.39467605f - 0.00078890f * jc) * Geometry.DEGREES_TO_RADIANS;
        float l =
            Geometry.mod2pi((181.97909950f + 58517.81538729f * jc) * Geometry.DEGREES_TO_RADIANS);
        float w = (131.60246718f + 0.00268329f * jc) * Geometry.DEGREES_TO_RADIANS;
        float o = (76.67984255f - 0.27769418f * jc) * Geometry.DEGREES_TO_RADIANS;
        return new CoordinateOE(a, e, i, o, w, l);
      }

      // Note that this is the orbital data for Earth.
      case Earth: {
        float a = 1.00000261f + 0.00000562f * jc;
        float e = 0.01671123f - 0.00004392f * jc;
        float i = (-0.00001531f - 0.01294668f * jc) * Geometry.DEGREES_TO_RADIANS;
        float l =
            Geometry.mod2pi((100.46457166f + 35999.37244981f * jc) * Geometry.DEGREES_TO_RADIANS);
        float w = (102.93768193f + 0.32327364f * jc) * Geometry.DEGREES_TO_RADIANS;
        float o = 0.0f;
        return new CoordinateOE(a, e, i, o, w, l);
      }

      case Mars: {
        float a = 1.52371034f + 0.00001847f * jc;
        float e = 0.09339410f + 0.00007882f * jc;
        float i = (1.84969142f - 0.00813131f * jc) * Geometry.DEGREES_TO_RADIANS;
        float l =
            Geometry.mod2pi((-4.55343205f + 19140.30268499f * jc) * Geometry.DEGREES_TO_RADIANS);
        float w = (-23.94362959f + 0.44441088f * jc) * Geometry.DEGREES_TO_RADIANS;
        float o = (49.55953891f - 0.29257343f * jc) * Geometry.DEGREES_TO_RADIANS;
        return new CoordinateOE(a, e, i, o, w, l);
      }

      case Jupiter: {
        float a = 5.20288700f - 0.00011607f * jc;
        float e = 0.04838624f - 0.00013253f * jc;
        float i = (1.30439695f - 0.00183714f * jc) * Geometry.DEGREES_TO_RADIANS;
        float l =
            Geometry.mod2pi((34.39644051f + 3034.74612775f * jc) * Geometry.DEGREES_TO_RADIANS);
        float w = (14.72847983f + 0.21252668f * jc) * Geometry.DEGREES_TO_RADIANS;
        float o = (100.47390909f + 0.20469106f * jc) * Geometry.DEGREES_TO_RADIANS;
        return new CoordinateOE(a, e, i, o, w, l);
      }

      case Saturn: {
        float a = 9.53667594f - 0.00125060f * jc;
        float e = 0.05386179f - 0.00050991f * jc;
        float i = (2.48599187f + 0.00193609f * jc) * Geometry.DEGREES_TO_RADIANS;
        float l =
            Geometry.mod2pi((49.95424423f + 1222.49362201f * jc) * Geometry.DEGREES_TO_RADIANS);
        float w = (92.59887831f - 0.41897216f * jc) * Geometry.DEGREES_TO_RADIANS;
        float o = (113.66242448f - 0.28867794f * jc) * Geometry.DEGREES_TO_RADIANS;
        return new CoordinateOE(a, e, i, o, w, l);
      }

      case Uranus: {
        float a = 19.18916464f - 0.00196176f * jc;
        float e = 0.04725744f - 0.00004397f * jc;
        float i = (0.77263783f - 0.00242939f * jc) * Geometry.DEGREES_TO_RADIANS;
        float l =
            Geometry.mod2pi((313.23810451f + 428.48202785f * jc) * Geometry.DEGREES_TO_RADIANS);
        float w = (170.95427630f + 0.40805281f * jc) * Geometry.DEGREES_TO_RADIANS;
        float o = (74.01692503f + 0.04240589f * jc) * Geometry.DEGREES_TO_RADIANS;
        return new CoordinateOE(a, e, i, o, w, l);
      }

      case Neptune: {
        float a = 30.06992276f + 0.00026291f * jc;
        float e = 0.00859048f + 0.00005105f * jc;
        float i = (1.77004347f + 0.00035372f * jc) * Geometry.DEGREES_TO_RADIANS;
        float l =
            Geometry.mod2pi((-55.12002969f + 218.45945325f * jc) * Geometry.DEGREES_TO_RADIANS);
        float w = (44.96476227f - 0.32241464f * jc) * Geometry.DEGREES_TO_RADIANS;
        float o = (131.78422574f - 0.00508664f * jc) * Geometry.DEGREES_TO_RADIANS;
        return new CoordinateOE(a, e, i, o, w, l);
      }

      case Pluto: {
        float a = 39.48211675f - 0.00031596f * jc;
        float e = 0.24882730f + 0.00005170f * jc;
        float i = (17.14001206f + 0.00004818f * jc) * Geometry.DEGREES_TO_RADIANS;
        float l =
            Geometry.mod2pi((238.92903833f + 145.20780515f * jc) * Geometry.DEGREES_TO_RADIANS);
        float w = (224.06891629f - 0.04062942f * jc) * Geometry.DEGREES_TO_RADIANS;
        float o = (110.30393684f - 0.01183482f * jc) * Geometry.DEGREES_TO_RADIANS;
        return new CoordinateOE(a, e, i, o, w, l);
      }

      default:
        throw new RuntimeException("Unknown Planet: " + planet);
    }
  }


  // TODO(serafini): We need to correct the Ra/Dec for the user's location. The
  // current calculation is probably accurate to a degree or two, but we can,
  // and should, do better.
  /**
   * Calculate the geocentric right ascension and declination of the moon using
   * an approximation as described on page D22 of the 2008 Astronomical Almanac
   * All of the variables in this method use the same names as those described
   * in the text: lambda = Ecliptic longitude (degrees) beta = Ecliptic latitude
   * (degrees) pi = horizontal parallax (degrees) r = distance (Earth radii)
   *
   * NOTE: The text does not give a specific time period where the approximation
   * is valid, but it should be valid through at least 2009.
   */
  //TODO it now is 2018 :(
  public static CoordinateRAD calculateLunarGeocentricLocation(Date time) {
    // First, calculate the number of Julian centuries from J2000.0.
    float t = (float) ((TimeUtil.calculateJulianDay(time) - 2451545.0f) / 36525.0f);

    // Second, calculate the approximate geocentric orbital elements.
    float lambda =
        218.32f + 481267.881f * t + 6.29f
            * MathUtil.sin((135.0f + 477198.87f * t) * Geometry.DEGREES_TO_RADIANS) - 1.27f
            * MathUtil.sin((259.3f - 413335.36f * t) * Geometry.DEGREES_TO_RADIANS) + 0.66f
            * MathUtil.sin((235.7f + 890534.22f * t) * Geometry.DEGREES_TO_RADIANS) + 0.21f
            * MathUtil.sin((269.9f + 954397.74f * t) * Geometry.DEGREES_TO_RADIANS) - 0.19f
            * MathUtil.sin((357.5f + 35999.05f * t) * Geometry.DEGREES_TO_RADIANS) - 0.11f
            * MathUtil.sin((186.5f + 966404.03f * t) * Geometry.DEGREES_TO_RADIANS);
    float beta =
        5.13f * MathUtil.sin((93.3f + 483202.02f * t) * Geometry.DEGREES_TO_RADIANS) + 0.28f
            * MathUtil.sin((228.2f + 960400.89f * t) * Geometry.DEGREES_TO_RADIANS) - 0.28f
            * MathUtil.sin((318.3f + 6003.15f * t) * Geometry.DEGREES_TO_RADIANS) - 0.17f
            * MathUtil.sin((217.6f - 407332.21f * t) * Geometry.DEGREES_TO_RADIANS);
    //float pi =
    //    0.9508f + 0.0518f * MathUtil.cos((135.0f + 477198.87f * t) * Geometry.DEGREES_TO_RADIANS)
    //        + 0.0095f * MathUtil.cos((259.3f - 413335.36f * t) * Geometry.DEGREES_TO_RADIANS)
    //        + 0.0078f * MathUtil.cos((235.7f + 890534.22f * t) * Geometry.DEGREES_TO_RADIANS)
    //        + 0.0028f * MathUtil.cos((269.9f + 954397.74f * t) * Geometry.DEGREES_TO_RADIANS);
    // float r = 1.0f / MathUtil.sin(pi * Geometry.DEGREES_TO_RADIANS);

    // Third, convert to RA and Dec.
    float l =
        MathUtil.cos(beta * Geometry.DEGREES_TO_RADIANS)
            * MathUtil.cos(lambda * Geometry.DEGREES_TO_RADIANS);
    float m =
        0.9175f * MathUtil.cos(beta * Geometry.DEGREES_TO_RADIANS)
            * MathUtil.sin(lambda * Geometry.DEGREES_TO_RADIANS) - 0.3978f
            * MathUtil.sin(beta * Geometry.DEGREES_TO_RADIANS);
    float n =
        0.3978f * MathUtil.cos(beta * Geometry.DEGREES_TO_RADIANS)
            * MathUtil.sin(lambda * Geometry.DEGREES_TO_RADIANS) + 0.9175f
            * MathUtil.sin(beta * Geometry.DEGREES_TO_RADIANS);
    double ra = (double)Geometry.mod2pi(MathUtil.atan2(m, l)) * Geometry.RADIANS_TO_DEGREES;
    double dec = (double)MathUtil.asin(n) * Geometry.RADIANS_TO_DEGREES;

    return new CoordinateRAD(Math.toRadians(ra), Math.toRadians(dec));
  }



  /**
   * Enum that identifies whether we are interested in rise or set time.
   */
  public enum RiseSetIndicator { RISE, SET }

  // Maximum number of times to calculate rise/set times. If we cannot
  // converge after this many iteretions, we will fail.
  private final static int MAX_ITERATIONS = 25;

  /**
   * Calculates the next rise or set time of this planet from a given observer.
   * Returns null if the planet doesn't rise or set during the next day.
   * 
   * @param now Calendar time from which to calculate next rise / set time.
   * @param loc Location of observer.
   * @param indicator Indicates whether to look for rise or set time.
   * @return New Calendar set to the next rise or set time if within
   *         the next day, otherwise null.
   */
  public static Calendar calcNextRiseSetTime(Planet planet, Calendar now,CoordinateWGS84 loc,
                                      RiseSetIndicator indicator) {
    // Make a copy of the calendar to return.
    Calendar riseSetTime = Calendar.getInstance();
    double riseSetUt = calcRiseSetTime(planet, now.getTime(), loc, indicator);
    // Early out if no nearby rise set time.
    if (riseSetUt < 0) {
      return null;
    }
    
    // Find the start of this day in the local time zone. The (a / b) * b
    // formulation looks weird, it's using the properties of int arithmetic
    // so that (a / b) is really floor(a / b).
    long dayStart = (now.getTimeInMillis() / TimeConstants.MILLISECONDS_PER_DAY)
                    * TimeConstants.MILLISECONDS_PER_DAY - riseSetTime.get(Calendar.ZONE_OFFSET);
    long riseSetUtMillis = (long) (calcRiseSetTime(planet, now.getTime(), loc, indicator)
                                  * TimeConstants.MILLISECONDS_PER_HOUR);
    long newTime = dayStart + riseSetUtMillis + riseSetTime.get(Calendar.ZONE_OFFSET);
    // If the newTime is before the current time, go forward 1 day.
    if (newTime < now.getTimeInMillis()) {
      //Log.d(TAG, "Nearest Rise/Set is in the past. Adding one day.");
      newTime += TimeConstants.MILLISECONDS_PER_DAY;
    }
    riseSetTime.setTimeInMillis(newTime);
    if (!riseSetTime.after(now)) {
      //Log.e(TAG, "Next rise set time (" + riseSetTime.toString()
      //           + ") should be after current time (" + now.toString() + ")");
    }
    return riseSetTime;
  }

  // Internally calculate the rise and set time of an object.
  // Returns a double, the number of hours through the day in UT.
  private static double calcRiseSetTime(Planet planet, Date dateTime, CoordinateWGS84 loc,
                                 RiseSetIndicator indicator) {
    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UT"));
    cal.setTime(dateTime);

    float sign = (indicator == RiseSetIndicator.RISE ? 1.0f : -1.0f);
    float delta = 5.0f;
    float ut = 12.0f;

    int counter = 0;
    while ((Math.abs(delta) > 0.008) && counter < MAX_ITERATIONS) {
      cal.set(Calendar.HOUR_OF_DAY, (int) MathUtil.floor(ut));
      float minutes = (ut - MathUtil.floor(ut)) * 60.0f;
      cal.set(Calendar.MINUTE, (int) minutes);
      cal.set(Calendar.SECOND, (int) ((minutes - MathUtil.floor(minutes)) * 60.f));

      // Calculate the hour angle and declination of the planet.
      // TODO(serafini): Need to fix this for arbitrary RA/Dec locations.
      Date tmp = cal.getTime();
      CoordinateHEEQ sunCoordinates = getOrbitalElements(Planet.Earth, tmp).convertToHEEQ();
      CoordinateRAD raDec = null;//RaDec.getInstance(planet, tmp, sunCoordinates); //TODO check if this even works

      // GHA = GST - RA. (In degrees.)
      float gst = TimeUtil.meanSiderealTime(tmp, 0);
      float gha = gst - (float)Math.toDegrees(raDec.mRightAscensionRadians);

      // The value of -0.83 works for the diameter of the Sun and Moon. We
      // assume that other objects are simply points.
      //float bodySize = (this == ProviderPlanet.Sun || this == ProviderPlanet.Moon) ? -0.83f : 0.0f;
      //TODO the moon is now a satalite
      float bodySize = -0.83f;
      float hourAngle = calculateHourAngle(bodySize, (float) Math.toDegrees(loc.mLatitudeRadian), (float) Math.toDegrees(raDec.mDeclenationRadians));

      delta = (gha + (float)Math.toDegrees(loc.mLongitudeRadian) + (sign * hourAngle)) / 15.0f;
      while (delta < -24.0f) {
        delta = delta + 24.0f;
      }
      while (delta > 24.0f) {
        delta = delta - 24.0f;
      }
      ut = ut - delta;

      // I think we need to normalize UT
      while (ut < 0.0f) {
        ut = ut + 24.0f;
      }
      while (ut > 24.0f) {
        ut = ut - 24.0f;
      }

      ++counter;
    }

    // Return failure if we didn't converge.
    if (counter == MAX_ITERATIONS) {
      return -1.0f;
    }

    // TODO(serafini): Need to handle latitudes above 60
    // At latitudes above 60, we need to calculate the following:
    // sin h = sin phi sin delta + cos phi cos delta cos (gha + lambda)
    return ut;
  }


  // Calculates the hour angle of a given declination for the given location.
  // This is a helper application for the rise and set calculations. Its
  // probably not worth using as a general purpose method.
  // All values are in degrees.
  //
  // This method calculates the hour angle from the meridian using the
  // following equation from the Astronomical Almanac (p487):
  // cos ha = (sin mAltitude - sin lat * sin dec) / (cos lat * cos dec)
  public static float calculateHourAngle(float altitude, float latitude,
                                         float declination) {
    float altRads = altitude * MathUtil.DEGREES_TO_RADIANS;
    float latRads = latitude * MathUtil.DEGREES_TO_RADIANS;
    float decRads = declination * MathUtil.DEGREES_TO_RADIANS;
    float cosHa = (MathUtil.sin(altRads) - MathUtil.sin(latRads) * MathUtil.sin(decRads)) /
        (MathUtil.cos(latRads) * MathUtil.cos(decRads));

    return MathUtil.RADIANS_TO_DEGREES * MathUtil.acos(cosHa);
  }
}
