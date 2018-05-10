# ar-coordinate
A library for converting coordinates in varios systems to screen coordinates for use in android ar applications. 
Orginalliy inspired by https://github.com/dat-ng/ar-location-based-android, 
Also incoperates most of stardroid (https://github.com/sky-map-team/stardroid) empherics.
Demo app exists at:
https://github.com/kozzion/ar-demo

## Including
to your root build.gradle make use to include jitpack
	allprojects {
		repositories {
			google()
			jcenter()
			maven {
				url 'https://jitpack.io' //This one here
			}
		}
	}
then in your app build.gradle
	implementation 'com.github.kozzion:ar-coordinate:1.0.5'

There is also a bintray and a jcenter but they are a terrible mess
https://bintray.com/kozzion/ar/coordinate/
If anyone can help me debug jcentre (its only showing the first) version plz contact me.

## Example

	@Override
	protected void onDraw(Canvas canvas) {
		// To convert from a WSG84 coordinate to screen coordinate you need:
		// Your current location get it from locationService for instance
		CoordinateWGS84 mObserverLocation = new CoordinateWGS84( 4.635669, -75.568597, 1600); //Salento
		// A rotatedProjectionMatrix get it from the ARCamera in this library
		float[] rotatedProjectionMatrix = new float[16];
		// A canvas to draw on
		CoordinateWGS84 medellin = new CoordinateWGS84( 6.210646, -75.573583, 1400);
		CoordinateScreen coordinateScreenm = medellin.convertToScreen(mObserverLocation, rotatedProjectionMatrix, canvas);
		canvas.drawCircle((float)coordinateScreenm.mX, (float)coordinateScreenm.mY, 15, paint);
		canvas.drawText("Medellin", (float)coordinateScreenm.mX - (30 * "Medellin".length() / 2), (float)coordinateScreenm.mY - 80, paint);

		//For the sun and other heavenly bodyes you also need the current datetime
		Date date = new Date()
		CoordinateRAD suna = ToolsPositionCalculatorSolar.getSolarPosition(date );
		CoordinateScreen coordinateScreens = suna.convertToAE(mObserverLocation, date).convertToENU().convertToCamera(rotatedProjectionMatrix).convertToScreen(canvas);
		canvas.drawCircle((float)coordinateScreens.mX, (float)coordinateScreens.mY, 15, paint);
		canvas.drawText("Sun", (float)coordinateScreens.mX - (30 * "Sun".length() / 2), (float)coordinateScreens.mY - 80, paint);


		CoordinateRAD ur = ToolsPositionCalculatorSolar.getUranusPosition(date );
		CoordinateScreen coordinateScreenu = ur.convertToAE(mObserverLocation, date).convertToENU().convertToCamera(rotatedProjectionMatrix).convertToScreen(canvas);
		canvas.drawCircle((float)coordinateScreenu.mX, (float)coordinateScreenu.mY, 15, paint);
		canvas.drawText("UR", (float)coordinateScreenu.mX - (30 * "UR".length() / 2), (float)coordinateScreenu.mY - 80, paint);
	}


