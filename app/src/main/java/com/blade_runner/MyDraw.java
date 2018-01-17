package com.blade_runner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.DebugUtils;
import android.view.View;
import android.widget.Toast;


import java.util.Random;


import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.YELLOW;
import static android.graphics.Color.rgb;

/**
 * Created by sapeg on 27.02.2017.
 */

public class MyDraw extends View {

    static volatile char x1_ABC=0;
    static volatile public int y1_123=0;
    static volatile int x1 = 0;
    static volatile int y1 = 0;
    private static int x1_pred,y1_pred;
    int x0=70, y0=20;

    static volatile int x2 = 0;
    static volatile int y2 = 0;

    static int aa=0,bb=0,cc,dd;
    static public int flag = 0, flag1 = 0;
    private Paint paint = new Paint();
    Paint p, pD1,eRect, eLine; static Paint pD;
    Bitmap bitmap1, bitmap2;
    Rect rect;
    static boolean compare=false;
    static boolean compareF=false;
    public static int colorFon;
    public static int colorDeskW,colorDeskB;
    static int sPred=0;
    private Context contextd;


    public MyDraw(Context context, AttributeSet attrs) {
        super(context, attrs);
        contextd=getContext();
        p = new Paint();
        pD = new Paint();
        pD1 = new Paint();
        rect = new Rect();
        eRect = new Paint();
        eLine = new Paint();
        bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.pawn_w);
        bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.pawn_b);
        colorFon= getResources().getColor(R.color.cyan);
        colorDeskW= getResources().getColor(R.color.white);
        colorDeskB= getResources().getColor(R.color.black);
    }



    public static void calcX1Y1Draw ()
    {


        if (MainActivity.but_as==1)
        {
            randomPoint();

        }
        if (MainActivity.but_as == 2)
        {
            randomMove();
        }
        x1y1_ABC123();

        compareF=false;



    }




    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(colorFon);
        paintDesk(canvas);

        if (x1!=0){
            canvas.drawBitmap(bitmap1, x1, y1, paint);
            eRect(canvas);
        }
        if (x2!=0) canvas.drawBitmap(bitmap2, x2, y2, paint);



        if(MainActivity.endGame)
        {
            gameover(canvas);
        }

        if (MainActivity.but_as==4)
        {

                sU(canvas);

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void Compare(Canvas canvas)
    {

        Rect erect = new Rect(aa, bb, cc, dd);
        compare=erect.contains(x2,y2);
        if (compareF){
            if ((x1== x2 & y1== y2)) gameover(canvas);
            if (!compare) gameover(canvas);
        }
    }

    public void eRect(Canvas canvas){
        eLine.setColor(Color.RED);
        eLine.setStrokeWidth(4);

        aa=x1-100;bb=y1-100;cc=x1+150;dd=y1+150;

        if (aa<70) {aa=70;}
        if (cc>470) {cc=470;}
        if (bb<20) {bb=20;}
        if (dd>420) {dd=420;}

        canvas.drawLine(aa,bb,cc,bb,eLine);
        canvas.drawLine(aa,dd,cc,dd,eLine);
        canvas.drawLine(cc,bb,cc,dd,eLine);
        canvas.drawLine(aa,bb,aa,dd,eLine);


    }

    public void sU(Canvas canvas)  {
        canvas.drawColor(Color.CYAN);
        pD1.setColor(RED);
        pD1.setTextSize(44);
        pD1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText(" B y e !",180,300,pD1);

    }
    public static void pauseP() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace( );
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void gameover(Canvas canvas) {

        canvas.drawColor(Color.CYAN);

        pD.setColor(GREEN);
        canvas.drawRect(100,50,300,240,pD);
        pD.setColor(YELLOW);
        canvas.drawCircle(180,400,40,pD);
        pD.setColor(GRAY);
        canvas.drawRoundRect(400,400,450,450,11,11,pD);
        pD.setColor(BLUE);
        pD.setStrokeWidth(4);
        canvas.drawLine(0,100,320,40,pD);
        canvas.drawLine(0,280,420,400,pD);
        canvas.drawLine(120,40,220,360,pD);

        pD.setColor(RED);
        pD.setTextSize(44);
        pD.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("Game Over",140,320,pD);
    }



    public void paintDesk(Canvas canvas) {
        int a = 0;
        int c = 0;
        int b1;
        int b2;
//        Toast toast=Toast.makeText(contextd,"Hi", Toast.LENGTH_SHORT);
//        toast.show();
        int rg = Color.parseColor("#ff8800");
        p.setColor(rg);
        canvas.drawRect(60,10,480,430,p);

        p.setColor(BLACK);
        canvas.drawLine(60,10,70,20,p);
        canvas.drawLine(470,420,480,430,p);
        canvas.drawLine(60,220,70,220,p);
        canvas.drawLine(470,220,480,220,p);
        canvas.drawLine(470,20,480,10,p);
        canvas.drawLine(60,430,70,420,p);

        p.setColor(GRAY);
        canvas.drawRect(63,216,67,225,p);
        canvas.drawRect(473,216,477,225,p);

        p.setColor(rgb(117,81,57));
        canvas.drawLine(60,10,480,10,p);
        canvas.drawLine(60,10,60,430,p);
        canvas.drawLine(60,430,480,430,p);
        canvas.drawLine(480,430,480,10,p);
        for (int i = 2; i <= 9; i++) {
            for (int j = 2; j <= 9; j++) {

                a = a + 50;
                b1 = i % 2;
                b2 = j % 2;
                if ((b1 == 0 & b2 == 0) | (b1 != 0 & b2 != 0)) {
                    p.setColor(colorDeskW);
                } else {
                    p.setColor(colorDeskB);
                }

                canvas.drawRect(20 + a, 20 + c, 70 + a, 70 + c, p);

            }
            a = 0;
            c = c + 50;

        }

        pD.setColor(BLUE);
        pD.setTextSize(18);
        pD.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));

        canvas.drawText("A", 90, 448, pD);
        canvas.drawText("B", 140, 448, pD);
        canvas.drawText("C", 190, 448, pD);
        canvas.drawText("D", 240, 448, pD);
        canvas.drawText("E", 290, 448, pD);
        canvas.drawText("F", 340, 448, pD);
        canvas.drawText("G", 390, 448, pD);
        canvas.drawText("H", 440, 448, pD);

        canvas.drawText("1", 45, 400, pD);
        canvas.drawText("2", 45, 350, pD);
        canvas.drawText("3", 45, 300, pD);
        canvas.drawText("4", 45, 250, pD);
        canvas.drawText("5", 45, 200, pD);
        canvas.drawText("6", 45, 150, pD);
        canvas.drawText("7", 45, 100, pD);
        canvas.drawText("8", 45, 50, pD);

    }


    public static void randomPoint() {

        Random random = new Random();
        int spx;
        spx = (random.nextInt(8) + 1);
        if (spx == 1) {
            x1 = 70;
        }
        if (spx == 2) {
            x1 = 120;
        }
        if (spx == 3) {
            x1 = 170;
        }
        if (spx == 4) {
            x1 = 220;
        }

        if (spx == 5) {
            x1 = 270;
        }
        if (spx == 6) {
            x1 = 320;
        }
        if (spx == 7) {
            x1 = 370;

        }
        if (spx == 8) {
            x1 = 420;
        }


        int spy;
        spy = (random.nextInt(8) + 1);
        if (spy == 1) {
            y1 = 20;
        }
        if (spy == 2) {
            y1 = 70;
        }
        if (spy == 3) {
            y1 = 120;
        }
        if (spy == 4) {
            y1 = 170;
        }

        if (spy == 5) {
            y1 = 220;
        }
        if (spy == 6) {
            y1 = 270;
        }
        if (spy == 7) {
            y1 = 320;

        }
        if (spy == 8) {
            y1 = 370;
        }

    }


    public static void randomMove() {
        Random random = new Random();
        int jk=0;


        int s;
        for(;;) {
            jk++;
            s = (random.nextInt(8) + 1);
            if (s == 1) if (sPred != 5 & sPred != 7 & sPred != 8) {
                break;
            }
            if (s == 2) if (sPred != 6 & sPred != 7 & sPred != 8) {

                break;
            }
            if (s == 3) if (sPred != 4 & sPred != 7 & sPred != 6){


                break;
            }
            if (s == 4) if (sPred != 5 & sPred != 3 & sPred != 8) {

                break;
            }
            if (s == 5) if (sPred != 1 & sPred != 4 & sPred != 6) {

                break;
            }
            if (s == 6) if (sPred != 5 & sPred != 2 & sPred != 3) {


                break;
            }
            if (s == 7) if (sPred != 1 & sPred != 2 & sPred != 3) {


                break;
            }
            if (s == 8) if (sPred != 1 & sPred != 2 & sPred != 4) {


                break;
            }
        }

        if (s == 1) {
            x1 = x1 - 50;
            y1 = y1 - 50;
        }
        if (s == 2) {

            y1 = y1 - 50;
        }
        if (s == 3) {


            x1 = x1 + 50;
            y1 = y1 - 50;
        }
        if (s == 4) {


            x1 = x1 - 50;
        }

        if (s == 5) {


            x1 = x1 + 50;
        }
        if (s == 6) {


            x1 = x1 - 50;
            y1 = y1 + 50;
        }
        if (s == 7) {


            y1 = y1 + 50;
        }
        if (s == 8) {


            x1 = x1 + 50;
            y1 = y1 + 50;
        }

        if (x1 < 70){
            x1 = x1 + 100;
            s=5;
        }
        if (y1 < 20) {
            y1 = y1 + 100;
            s=7;
        }
        if (x1 >= 470) {
            x1 = x1 - 100;
            s=4;
        }
        if (y1 >= 420) {
            y1 = y1 - 100;
            s=2;
        }
        sPred=s;

        flag = 1;
    }

    public static void x1y1_ABC123()

    {
        if(x1 ==70) x1_ABC ='A';
        if(x1 ==70+50) x1_ABC ='B';
        if(x1 ==70+50*2) x1_ABC ='C';
        if(x1 ==70+50*3) x1_ABC ='D';
        if(x1 ==70+50*4) x1_ABC ='E';
        if(x1 ==70+50*5) x1_ABC ='F';
        if(x1 ==70+50*6) x1_ABC ='G';
        if(x1 ==70+50*7) x1_ABC ='H';

        if(y1 ==20) y1_123 =8;
        if(y1 ==20+50) y1_123 =7;
        if(y1 ==20+50*2) y1_123 =6;
        if(y1 ==20+50*3) y1_123 =5;
        if(y1 ==20+50*4) y1_123 =4;
        if(y1 ==20+50*5) y1_123 =3;
        if(y1 ==20+50*6) y1_123 =2;
        if(y1 ==20+50*7) y1_123 =1;




    }




}