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
        //
        //Log.e("ViewOverlayAR", "onDraw: ");
        Date date = new Date(new Date().getTime() + mTimeOffset);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        paint.setTextSize(60);

        ModelPerspective perspectiveAR = new ModelPerspective(date, mObserverLocation, rotatedProjectionMatrix, canvas.getWidth(), canvas.getHeight());
        //Log.e("OVERLAY", "onDraw: " +perspectiveAR.toString() );


        CoordinateRAD suna = ToolsPositionCalculatorSolar.getSolarPosition(date );
        CoordinateScreen sunas = suna.convertToScreen(perspectiveAR);
        drawPoint(canvas, sunas, paint, "SUN");

        CoordinateScreen mdl = ProviderTerrestrial.getCoordinate("Medellin").convertToScreen(perspectiveAR);
        drawPoint(canvas, mdl, paint, "MDL");

        CoordinateScreen csme = ProviderSolar.getCoordinate(ProviderSolar.SolarObject.Mercury, date).convertToScreen(perspectiveAR);
        drawPoint(canvas, csme, paint, "ME");

        CoordinateScreen csur = ProviderSolar.getCoordinate(ProviderSolar.SolarObject.Uranus, date).convertToScreen(perspectiveAR);
        drawPoint(canvas, csur, paint, "UR");

        CoordinateScreen csm74 = ProviderStellar.getCoordinate("M74").convertToScreen(perspectiveAR);
        drawPoint(canvas, csm74, paint, "M74");

        CoordinateScreen csmal = ProviderStellar.getCoordinate("Aldebaran").convertToScreen(perspectiveAR);
        drawPoint(canvas, csmal, paint, "ALDEBARAN");
    }

    public void drawPoint(Canvas canvas, CoordinateScreen coordinateScreen, Paint paint, String text)
    {
        canvas.drawCircle((float)coordinateScreen.mX, (float)coordinateScreen.mY, 15, paint);
        //Log.e("drawPoint", "drawPoint: " + coordinateScreen.mX + " " + coordinateScreen.mY);
        //canvas.rotate(90);
        canvas.drawText(text, (float)coordinateScreen.mX - (30 * text.length() / 2), (float)coordinateScreen.mY - 80, paint);

    }
}

