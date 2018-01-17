package com.blade_runner;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.CYAN;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class SplashDraw extends View {
    private final ArgbEvaluator mArgbEvaluator;
    private final Path pathCir=new Path();
    private final Paint p21=new Paint(),p22=new Paint(),p23=new Paint(),p24=new Paint();
    private Paint  p=new Paint();;
    double xd,yd;
    float x,y;
    int dx=240,dy=300;
    private int  qq=-180;;
    private final long  RECT_ANIMATION   = 1_500;
    private long  mStartTime;
    int color;
    private int color1;
    private Paint pC=new Paint();
    private Paint pCpath1=new Paint(),pCpath2=new Paint();
    Path.Op op = Path.Op.INTERSECT;
    private Path path21=new Path();
    private Path path22=new Path();
    private Path path23=new Path();
    private Path path24=new Path();
    private Path path21LT=new Path();
    private Path path22RT=new Path();
    private Path path23LB=new Path();
    private Path path24RB=new Path();
    Path path=new Path();

    public SplashDraw(Context context, AttributeSet attrs) {
        super(context, attrs);
        mStartTime = getTime();
        mArgbEvaluator = new ArgbEvaluator();
        p21.setStyle(Paint.Style.FILL);
        p21.setColor(Color.BLACK);
        p21.setStrokeWidth(3);
        p22.setStyle(Paint.Style.FILL);
        p22.setColor(Color.WHITE);
        p22.setStrokeWidth(3);
        pC.setStyle(Paint.Style.STROKE);
        pC.setColor(RED);
        pC.setStrokeWidth(3);
        pCpath1.setStrokeWidth(3);
        pCpath1.setColor(BLACK);
        pCpath1.setStyle(Paint.Style.STROKE);
        pCpath1.setTextSize(70);
        pCpath2.setStrokeWidth(3);
        pCpath2.setColor(WHITE);
        pCpath2.setStyle(Paint.Style.STROKE);
        pCpath2.setTextSize(70);
        path21.setFillType(Path.FillType.WINDING);
        path21.addRect(dx-108,dy-108,dx,dy, Path.Direction.CW);
        path22.addRect(dx+108,dy-108,dx,dy, Path.Direction.CW);
        path23.addRect(dx-108,dy+108,dx,dy, Path.Direction.CW);
        path24.addRect(dx+108,dy+108,dx,dy, Path.Direction.CW);
        pathCir.addCircle(240,300,108, Path.Direction.CW);
        path21LT.op(path21,pathCir,op);
        path22RT.op(path22,pathCir,op);
        path23LB.op(path23,pathCir,op);
        path24RB.op(path24,pathCir,op);

    }

    @Override
    public void onDraw(final Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.drawColor(CYAN);
        long curTime = getTime() - mStartTime;
        float fraction = (float) (curTime % RECT_ANIMATION) / RECT_ANIMATION;
        if ((curTime / RECT_ANIMATION) % 2 == 1)
            fraction = 1 - fraction;
        color = (int) mArgbEvaluator.evaluate(fraction, Color.BLACK, Color.WHITE);
        color1 = (int) mArgbEvaluator.evaluate(fraction, Color.WHITE, Color.GREEN);
        canvas.drawPath(path21LT,p21);
        canvas.drawPath(path22RT,p22);
        canvas.drawPath(path23LB,p22);
        canvas.drawPath(path24RB,p21);
        qq++;
        if(qq==-360) qq=360;
        xd = 150 * Math.cos((qq*2*3.14)/180);
        yd= 150 * Math.sin((qq*2*3.14)/180);
        x= new Float(xd);
        y= new Float(yd);
        p.setColor(color);
        canvas.drawCircle(x+240,y+300,40,p);
        canvas.drawCircle(240,300,108, pC);
        path.addCircle(240,300,191, Path.Direction.CW);
        canvas.drawTextOnPath("BLADE",path,795,-5,pCpath1);
        canvas.drawTextOnPath("RUNNER",path,160,-5,pCpath2);
        canvas.restore();
        invalidate();
    }
    private long getTime() { //возвращает текущее время в миллисекундах
        return System.nanoTime() / 1_000_000;
    }
}