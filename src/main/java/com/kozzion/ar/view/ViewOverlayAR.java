package com.kozzion.ar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;

import com.kozzion.ar.coordinate.CoordinateRAD;
import com.kozzion.ar.coordinate.CoordinateScreen;
import com.kozzion.ar.coordinate.CoordinateWGS84;
import com.kozzion.ar.coordinate.provider.ProviderSolar;
import com.kozzion.ar.coordinate.provider.ProviderStellar;
import com.kozzion.ar.coordinate.model.ModelPerspective;
import com.kozzion.ar.coordinate.provider.ProviderTerrestrial;
import com.kozzion.ar.coordinate.tools.ToolsPositionCalculatorSolar;

import java.util.Date;


public class ViewOverlayAR extends View {

    Context context;
    private float[] rotatedProjectionMatrix = new float[16];
    private CoordinateWGS84 mObserverLocation;
    //private List<ModelCoordinate> mARNodeList;
    private long mTimeOffset;

    public ViewOverlayAR(Context context) {
        super(context);

        this.context = context;

        //Demo points
        mObserverLocation = new CoordinateWGS84(0,0,0);
        //mARNodeList = new ArrayList<>();
        mTimeOffset = 0;
    }

    public void updateRotatedProjectionMatrix(float[] rotatedProjectionMatrix) {
        this.rotatedProjectionMatrix = rotatedProjectionMatrix;
        this.invalidate();
    }

    public void updateCurrentLocation(CoordinateWGS84 observerLocation){
        mObserverLocation = observerLocation;
        this.invalidate();
    }

    //public void updateARNodeList(ArrayList<ModelCoordinate> arNodeList) {
        //mARNodeList = new ArrayList<>(arNodeList);
   //     this.invalidate();
    //}

    public void updateTimeOffset(long timeOffset) {
        mTimeOffset = timeOffset;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Date date = new Date(new Date().getTime() + mTimeOffset);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        paint.setTextSize(60);

        ModelPerspective perspectiveAR = new ModelPerspective(date, mObserverLocation, rotatedProjectionMatrix, canvas.getWidth(), canvas.getHeight());




        CoordinateRAD suna = ToolsPositionCalculatorSolar.getSolarPosition(date );
        CoordinateScreen sunas = suna.convertToScreen(perspectiveAR);
        drawPoint(canvas, sunas, paint, "SUN");

        //onDraw: east: -0.9523489655818351 north: 0.3050105699073537 up: 0.10662305186859597
        CoordinateRAD sunb = new CoordinateRAD(Math.toRadians(49.46517),Math.toRadians(18.235355));
       // Log.e("sunenu", "onDraw: " + sunb.convertToAE(mObserverLocation, date).convertToENU().toString());

        CoordinateScreen sunbs = new CoordinateRAD(Math.toRadians(49.46517),Math.toRadians(18.235355)).convertToScreen(perspectiveAR);
        drawPoint(canvas, sunbs, paint, "SUNB");

        CoordinateScreen mdl = ProviderTerrestrial.getCoordinate("Medellin").convertToScreen(perspectiveAR);
        drawPoint(canvas, mdl, paint, "MDL");

        CoordinateScreen csme = ProviderSolar.getCoordinate(ProviderSolar.SolarObject.Mercury, date).convertToScreen(perspectiveAR);
        drawPoint(canvas, csme, paint, "ME");

        CoordinateScreen csur = ProviderSolar.getCoordinate(ProviderSolar.SolarObject.Uranus, date).convertToScreen(perspectiveAR);
        drawPoint(canvas, csur, paint, "UR");

        CoordinateScreen csm74 = ProviderStellar.getCoordinate(ProviderStellar.StellarObject.M74).convertToScreen(perspectiveAR);
        drawPoint(canvas, csm74, paint, "M74");

        CoordinateScreen csmal = ProviderStellar.getCoordinate(ProviderStellar.StellarObject.ALDEBARAN).convertToScreen(perspectiveAR);
        drawPoint(canvas, csmal, paint, "ALDEBARAN");
    }

    public void drawPoint(Canvas canvas, CoordinateScreen coordinateScreen, Paint paint, String text)
    {
        canvas.drawCircle((float)coordinateScreen.mX, (float)coordinateScreen.mY, 15, paint);
        canvas.drawText(text, (float)coordinateScreen.mX - (30 * text.length() / 2), (float)coordinateScreen.mY - 80, paint);

    }
}

