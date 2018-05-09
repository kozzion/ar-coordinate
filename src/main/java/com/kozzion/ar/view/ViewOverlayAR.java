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
import com.kozzion.ar.coordinate.CoordinateHEEQ;
import com.kozzion.ar.coordinate.provider.ProviderPlanet;
import com.kozzion.ar.coordinate.tools.MathUtil;
import com.kozzion.ar.coordinate.tools.ToolsPositionCalculatorSolar;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ViewOverlayAR extends View {

    Context context;
    private float[] rotatedProjectionMatrix = new float[16];
    private CoordinateWGS84 mObserverLocation;
    //private List<ModelARNode> mARNodeList;
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

    //public void updateARNodeList(ArrayList<ModelARNode> arNodeList) {
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

        final int radius = 30;

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
        paint.setTextSize(60);


        CoordinateWGS84 medellin = new CoordinateWGS84( 6.210646, -75.573583, 1400);
        CoordinateScreen coordinateScreenm = medellin.convertToScreen(mObserverLocation, rotatedProjectionMatrix, canvas);
        canvas.drawCircle((float)coordinateScreenm.mX, (float)coordinateScreenm.mY, 15, paint);
        canvas.drawText("Medellin", (float)coordinateScreenm.mX - (30 * "Medellin".length() / 2), (float)coordinateScreenm.mY - 80, paint);


        CoordinateRAD suna = ToolsPositionCalculatorSolar.getSolarPosition(date );
        CoordinateScreen coordinateScreens = suna.convertToAE(mObserverLocation, date).convertToENU().convertToCamera(rotatedProjectionMatrix).convertToScreen(canvas);
        canvas.drawCircle((float)coordinateScreens.mX, (float)coordinateScreens.mY, 15, paint);
        canvas.drawText("Sun", (float)coordinateScreens.mX - (30 * "Sun".length() / 2), (float)coordinateScreens.mY - 80, paint);


        CoordinateRAD ur = ToolsPositionCalculatorSolar.getUranusPosition(date );
        CoordinateScreen coordinateScreenu = ur.convertToAE(mObserverLocation, date).convertToENU().convertToCamera(rotatedProjectionMatrix).convertToScreen(canvas);
        canvas.drawCircle((float)coordinateScreenu.mX, (float)coordinateScreenu.mY, 15, paint);
        canvas.drawText("UR", (float)coordinateScreenu.mX - (30 * "UR".length() / 2), (float)coordinateScreenu.mY - 80, paint);

        CoordinateRAD m74 = new CoordinateRAD(MathUtil.hoursToRadian(1, 36, 41.8), MathUtil.degreesToRadian( 15, 47, 1));
        CoordinateScreen coordinateScreenm74 = m74.convertToAE(mObserverLocation, date).convertToENU().convertToCamera(rotatedProjectionMatrix).convertToScreen(canvas);
        canvas.drawCircle((float)coordinateScreenm74.mX, (float)coordinateScreenm74.mY, 15, paint);
        canvas.drawText("M74", (float)coordinateScreenm74.mX - (30 * "M74".length() / 2), (float)coordinateScreenm74.mY - 80, paint);

    }
}

