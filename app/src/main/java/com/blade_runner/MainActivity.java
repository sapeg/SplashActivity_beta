package com.blade_runner;


import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.renderscript.Sampler;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.AttrRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.method.KeyListener;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Locale;

import static android.content.ContentValues.TAG;
import static android.view.KeyEvent.KEYCODE_BACK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.blade_runner.MyDraw.*;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

//14.01.18 Изменения.
//
//  Изменён дизайн игрового поля
//  Добавлено в гейм-плей завершение игры при пересечении пешек
//  Улучшено распознавание координат
//  Улучшен дизайн интерфейса
//



public class MainActivity extends Activity implements
        KeyListener,
        View.OnTouchListener,
        TextToSpeech.OnInitListener {

    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_COLOR_DESK_WHITE = "colorDeskWhite";
    public static final String APP_PREFERENCES_COLOR_DESK_BLACK = "colorDeskBlack";
    public static final String APP_PREFERENCES_COLOR_FON = "colorFon";
    public static final String APP_PREFERENCES_ZADERJKA = "pausa_mezdu_slovami";
    public static final String APP_PREFERENCES_GROMKOST = "gromkost";

    private SharedPreferences mSettings;
    private static final String TAG = "MyApp";
    public static TextToSpeech mTTS;
    Button setWhiteFigure, button3, zaderzka;
    View ds, myView, frame, ll,frame3, frame6;
    volatile static Integer but_as = 0, but = 0;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView pravila;
    public EditText edText,et4;
    MediaPlayer mp1=new MediaPlayer();

    int sc,neRasposnano=0;
    static volatile char x2_ABC = 0, y2_123 = 0;
    char reco1='0';
    char reco2;
    char recoPR;
    static String reco[]=new String[5];
    String tekZn="",tekZn1="";
    volatile int r=0;
    private int zz=0;
    volatile static  boolean startGame = false, endGame = false;
    private long Xmsec;
    private int XmsecEdit;// = 300;
    private static int x2_0 = 0, y2_0 = 0;
    private boolean Bigfoot = false;
    private boolean pointPoint=false,inSquare=true;

    private int countOfMovesBlack =0;
    static public int countOfMovesWhite =0;

    ArrayList<String> listBlack = new ArrayList<String>();
    static ArrayList<String> listWhite = new ArrayList<String>();


    private boolean neRaspoznano;
    private Button gromkost,b6,colorFon,button2,button13,button4, button5;
    private float x,y;
    private String sDown , sMove, sUp;
    private TextView textView4,textView3;
    private Button nastroyki;
    private View nastrFrame,frame4,frame5,frame7;;
    private Button backFromOptions,b7,b8,b9,b10,b11,b12,button14,button6,b18;
    private Button b22,b27,b32,b33,b34;
    private int ii,iii=0;
    int yy=0;
    private char rec;
    private String spVolume;//="0.5";
    private boolean pause;
    private boolean pause1;
    ArrayList<String> listVybor = new ArrayList<>(5);

    private int vyborRes=0;
    private boolean okonechnoeRaspoznavanie;
    private boolean vybor;
    private int compare_x2=0,compare_y2=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);// подключается MyDraw
        endGame = false;

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        mTTS = new TextToSpeech(this, this);
        myView = findViewById(R.id.view);
        edText = (EditText) findViewById(R.id.editText);
        zaderzka = (Button) findViewById(R.id.zaderzka);
        colorFon = (Button) findViewById(R.id.colorFon);
        button2= (Button) findViewById(R.id.button2);
        button13 = (Button) findViewById(R.id.button13);
        button14 = (Button) findViewById(R.id.button14);
        button5 = (Button) findViewById(R.id.button5);
        button4 = (Button) findViewById(R.id.button4);
        button3 = (Button) findViewById(R.id.button3);
        button6 = (Button) findViewById(R.id.button6);

        nastroyki= (Button) findViewById(R.id.nastroyki);
        final FrameLayout.LayoutParams param = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll = findViewById(R.id.LL);
        textView4=(TextView)findViewById(R.id.textView4);
        textView3=(TextView)findViewById(R.id.textView3);
        frame = findViewById(R.id.frame);
        frame4 = findViewById(R.id.frame4);
        frame5 = findViewById(R.id.frame5);
        frame6 = findViewById(R.id.frame6);
        frame7 = findViewById(R.id.frame7);

        nastrFrame =findViewById(R.id.nastrFrame);
        pravila = (TextView) findViewById(R.id.pravila);
        setWhiteFigure = (Button) findViewById(R.id.setWhiteFigure);
        backFromOptions =(Button) findViewById(R.id.backFromOptions);
        b7 = (Button) findViewById(R.id.b7);
        b8 = (Button) findViewById(R.id.b8);
        b9 = (Button) findViewById(R.id.b9);
        b10 = (Button) findViewById(R.id.b10);
        b11 = (Button) findViewById(R.id.b11);
        b12 = (Button) findViewById(R.id.b12);
        b18 = (Button) findViewById(R.id.b18);
        b22 = (Button) findViewById(R.id.b22);
        b27 = (Button) findViewById(R.id.b27);
        b32 = (Button) findViewById(R.id.b32);
        b33 = (Button) findViewById(R.id.b33);
        b34 = (Button) findViewById(R.id.b34);



        frame3 = findViewById(R.id.frame3);
        gromkost = (Button) findViewById(R.id.gromkost);
        b6 = (Button) findViewById(R.id.b6);
        et4 = (EditText) findViewById(R.id.et4);
        if (isOnline(this)) {}else{
            frame6.setVisibility(VISIBLE);
            button5.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    MainActivity.super.finish();
                }
            });

        }
        final  Handler  h = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        setWhiteFigure.setBackgroundResource(R.drawable.rounded_button_red);
                        break;
                    case 2:
                        setWhiteFigure.setBackgroundResource(R.drawable.rounded_button);
                        break;
                    case 3:
                        setWhiteFigure.setBackgroundResource(R.drawable.rounded_button_red);
                        break;
                    case 4:
                        setWhiteFigure.setBackgroundResource(R.drawable.rounded_button);

                        break;
                }
                return true;
            }
        });

        b22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDraw.colorDeskW=getResources().getColor(R.color.white);
                MyDraw.colorDeskB=getResources().getColor(R.color.black);

                refresh();
            }
        });
        b27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDraw.colorDeskW=getResources().getColor(R.color.grey);
                MyDraw.colorDeskB=getResources().getColor(R.color.brown);

                refresh();
            }
        });
        b32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDraw.colorDeskW=getResources().getColor(R.color.cream);
                MyDraw.colorDeskB=getResources().getColor(R.color.orange);

                refresh();
            }
        });
        b33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDraw.colorDeskW=getResources().getColor(R.color.blueLite);
                MyDraw.colorDeskB=getResources().getColor(R.color.blueDark);

                refresh();
            }
        });
        b34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDraw.colorDeskW=getResources().getColor(R.color.greenLite);
                MyDraw.colorDeskB=getResources().getColor(R.color.greenDark);

                refresh();
            }
        });

        button6.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                nastrFrame.setVisibility(GONE);
                button2.setVisibility(GONE);
                button13.setVisibility(GONE);
                frame7.setVisibility(VISIBLE);
            }
        });
        b18.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                frame7.setVisibility(GONE);
                nastrFrame.setVisibility(VISIBLE);
            }
        });
        textView3.setMovementMethod(new ScrollingMovementMethod());
        button14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                frame.setVisibility(GONE);
                nastrFrame.setVisibility(VISIBLE);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDraw.calcX1Y1Draw( );

                refresh();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                button2.setBackgroundResource(R.drawable.rounded_button_red);
                pause=true;

            }
        });

        button13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button2.setBackgroundResource(R.drawable.rounded_button);
                pause1=true;
                speakCoords(null,1);
            }
        });

        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDraw.colorFon=getResources().getColor(R.color.lightGreen);
                refresh();
            }
        });
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDraw.colorFon=getResources().getColor(R.color.blue);
                refresh();
            }
        });
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDraw.colorFon=getResources().getColor(R.color.grey);
                refresh();
            }
        });
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDraw.colorFon=getResources().getColor(R.color.yellow);
                refresh();
            }
        });
        b11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDraw.colorFon=getResources().getColor(R.color.cyan);
                refresh();
            }
        });
        b12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frame4.setVisibility(GONE);
                nastrFrame.setVisibility(VISIBLE);
                if(but_as==2){
                    button2.setVisibility(VISIBLE);
                    button13.setVisibility(VISIBLE);

                }
            }
        });

        colorFon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nastrFrame.setVisibility(GONE);
                button2.setVisibility(GONE);
                button13.setVisibility(GONE);

                frame4.setVisibility(VISIBLE);
            }
        });


        gromkost.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                nastrFrame.setVisibility(GONE);
                button2.setVisibility(GONE);
                button13.setVisibility(GONE);

                frame3.setVisibility(VISIBLE);
                MainActivity.super.onPause( );
                b6.setOnClickListener(new View.OnClickListener( ) {
                    @Override
                    public void onClick(View v) {

                        try {
                            spVolume = et4.getText( ).toString( );
                        }catch (NumberFormatException e){
                            spVolume=("0.5");
                        }
                        if(spVolume.equals("")||spVolume.equals(".")) spVolume="0.5";
                        frame3.setVisibility(GONE);
                        nastrFrame.setVisibility(VISIBLE);
                        if(but_as==2){
                            button2.setVisibility(VISIBLE);
                            button13.setVisibility(VISIBLE);

                        }

                        MainActivity.super.onResume( );
                    }

                });
            }
        });

        nastroyki.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                nastroyki.setVisibility(GONE);
                setWhiteFigure.setVisibility(GONE);
                nastrFrame.setVisibility(VISIBLE);
            }
        });
        backFromOptions.setOnClickListener(new View.OnClickListener( ) {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View view) {
                if (mp1.isPlaying()) {
                    mp1.pause( );
                }
                if(mp1!=null) mp1.stop();

                nastrFrame.setVisibility(GONE);
                nastroyki.setVisibility(VISIBLE);
                setWhiteFigure.setVisibility(VISIBLE);




            }
        });

        zaderzka.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                nastrFrame.setVisibility(GONE);
                button2.setVisibility(GONE);
                button13.setVisibility(GONE);

                ll.setVisibility(VISIBLE);
                MainActivity.super.onPause( );
                button3.setOnClickListener(new View.OnClickListener( ) {
                    @Override
                    public void onClick(View v) {

                        try {
                            XmsecEdit = parseInt(edText.getText( ).toString( ));
                        }catch (NumberFormatException e){
                            XmsecEdit=1500;
                        }
                        Xmsec = XmsecEdit;
                        ll.setVisibility(GONE);
                        nastrFrame.setVisibility(VISIBLE);
                        MainActivity.super.onResume( );
                        if(but_as==2){
                            button2.setVisibility(VISIBLE);
                            button13.setVisibility(VISIBLE);

                        }

                    }

                });
            }
        });

        pravila.setOnClickListener(new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                nastrFrame.setVisibility(GONE);
                frame.setVisibility(VISIBLE);
                frame.setOnTouchListener(new View.OnTouchListener( ) {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        frame.setVisibility(GONE);
                        nastrFrame.setVisibility(VISIBLE);
                        return true;
                    }
                });
            }

        });

        myView.setOnTouchListener(new View.OnTouchListener( ) {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(but_as==1) {
                    x = event.getX( );
                    y = event.getY( );

                    switch (event.getAction( )) {
                        case MotionEvent.ACTION_UP:// отпускание

                        case MotionEvent.ACTION_CANCEL:
                            sMove = "";
                            sUp = "Up: " + x + "," + y;
                            xyxAyA(x, y);

                            ABC123_x2y2( );

                            if (!inSquare) {
                                MyDraw.x2 = 0;
                                MyDraw.y2 = 0;
                                refresh( );
                                speakCoords("Фигура должна быть в пределах красных границ", 0);

                            } else if (pointPoint) {
                                MyDraw.x2 = 0;
                                MyDraw.y2 = 0;
                                refresh( );
                                speakCoords("Фигуру нельзя ставить на одно поле с белой", 0);
                                pointPoint = false;
                            } else {
                                refresh( );
                                setWhiteFigure.setText("Н а ч а т ь ");

                                Toast toast = Toast.makeText(getApplicationContext(),
                                        "Нажмите кнопку Начать", Toast.LENGTH_LONG);
                                toast.show();

                                Runnable runnable = new Runnable( ) {
                                    public void run() {
                                        for (int qq = 1; qq < 7; qq++) {
                                            try {
                                                Thread.sleep(500);
                                            } catch (InterruptedException e) {

                                            }

                                            h.sendEmptyMessage(qq);
                                        }
                                    }
                                };
                                Thread thread = new Thread(runnable);
                                thread.start( );
                            }

                            break;
                        case MotionEvent.ACTION_DOWN: // нажатие

                            sDown = "Down: " + x + "," + y;
                            sMove = "";
                            sUp = "";
                            break;
                        case MotionEvent.ACTION_MOVE: // движение

                            sMove = "Move: " + x + "," + y;
                            break;


                    }
                    textView4.setText(sDown + "\n" + sMove + "\n" + sUp);
                }
                return true;
            }

        });



        setWhiteFigure.setOnClickListener(new View.OnClickListener( ) {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                nastroyki.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.nastroyki, 0);
                param.setMargins(-70,540,0,0);//left, top, right, bottom
                nastroyki.setLayoutParams(param);
                nastroyki.setText(" ");

                but_as++;

                if (but_as == 1) {
                    setWhiteFigure.setText("Установка чёрной фигуры на доску");

                    MyDraw.calcX1Y1Draw( );
                    reco[0]= String.valueOf(x1_ABC)+String.valueOf(MyDraw.y1_123);
                    listWhite.add(countOfMovesWhite,reco[0]);

                    countOfMovesWhite++;

                    refresh( );
                }

                if (but_as == 2) {
                    button2.setVisibility(VISIBLE);
                    button13.setVisibility(VISIBLE);
                    reco[0]= String.valueOf(x2_ABC)+String.valueOf(y2_123);
                    listBlack.add(countOfMovesBlack,reco[0]);

                    countOfMovesBlack++;

                    setWhiteFigure.setText("З а в е р ш и т ь  и г р у ");
                    startGame=true;

                    speakCoords(null, 1);


                }
                if(but_as>2){
                    button2.setVisibility(GONE);
                    button13.setVisibility(GONE);

                }

                if (but_as == 3) {
                    MainActivity.super.onPause();
                    MainActivity.super.onDestroy();
                    refresh( );
                    nastroyki.setVisibility(GONE);
                    setWhiteFigure.setText(" В ы х о д ");
                }
                if (but_as == 4) {

                }
                if (setWhiteFigure.getText( ) == " В ы х о д " & but_as >= 4) {

                    openQuitDialog( );

                }
            }
        });


    }




    public void  musicOnNastroyki(){
        final MediaPlayer mp1 = MediaPlayer.create(this, R.raw.fatal_snipe____fight_4_the_lite);

        mp1.setVolume((float) 0.4, (float) 0.4);

        mp1.start( );

        mp1.setOnCompletionListener(new MediaPlayer.OnCompletionListener( ) {
            @Override
            public void onCompletion(MediaPlayer mp1) {


                mp1.release( );

            }
        });
    }
    private void xyxAyA(float xA, float yA) {
        int x_int=(int)xA;
        int y_int=(int)yA;
        if(x_int>= 70 & x_int<120) x2_ABC ='A';
        if(x_int>=120 & x_int<170) x2_ABC ='B';
        if(x_int>=170 & x_int<220) x2_ABC ='C';
        if(x_int>=220 & x_int<270) x2_ABC ='D';
        if(x_int>=270 & x_int<320) x2_ABC ='E';
        if(x_int>=320 & x_int<370) x2_ABC ='F';
        if(x_int>=370 & x_int<420) x2_ABC ='G';
        if(x_int>=420 & x_int<470) x2_ABC ='H';
        reco1=x2_ABC;

        if(y_int>=  20 & y_int< 70) y2_123 ='8';
        if(y_int>=  70 & y_int<120) y2_123 ='7';
        if(y_int>= 120 & y_int<170) y2_123 ='6';
        if(y_int>= 170 & y_int<220) y2_123 ='5';
        if(y_int>= 220 & y_int<270) y2_123 ='4';
        if(y_int>= 270 & y_int<320) y2_123 ='3';
        if(y_int>= 320 & y_int<370) y2_123 ='2';
        if(y_int>= 370 & y_int<420) y2_123 ='1';


    }


    @Override
    public int getInputType() {

        return 0;
    }

    @Override
    public boolean onKeyDown(View view, Editable text, int keyCode, KeyEvent event) {

        if (keyCode == KEYCODE_BACK && event.getRepeatCount() == 0) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(View view, Editable text, int keyCode, KeyEvent event) {

        return true;
    }

    @Override
    public boolean onKeyOther(View view, Editable text, KeyEvent event) {

        return true;
    }

    @Override
    public void clearMetaKeyState(View view, Editable content, int states) {

    }


    public void refresh() {
        myView.invalidate( );
    }

    @Override
    protected void onStop() {
        super.onStop( );
    }

    @Override
    protected void onPause() {
        super.onPause( );
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putInt(APP_PREFERENCES_COLOR_DESK_BLACK, MyDraw.colorDeskB);
        editor.putInt(APP_PREFERENCES_COLOR_DESK_WHITE, MyDraw.colorDeskW);
        editor.putInt(APP_PREFERENCES_COLOR_FON, MyDraw.colorFon);
        editor.putInt(APP_PREFERENCES_ZADERJKA, XmsecEdit);
        editor.putString(APP_PREFERENCES_GROMKOST, spVolume);
        editor.apply();


    }


    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if(keyCode==KeyEvent.KEYCODE_BACK) finish();
    }

    @Override
    protected void onResume() {
        super.onResume( );
        Log.d(TAG, "XmsecEdit="+XmsecEdit);
        Log.d(TAG, "spVolume="+spVolume);

        if (mSettings.contains(APP_PREFERENCES_COLOR_FON)) {
            MyDraw.colorFon = mSettings.getInt(APP_PREFERENCES_COLOR_FON,MyDraw.colorFon);

        }
        if (mSettings.contains(APP_PREFERENCES_COLOR_DESK_WHITE)) {
            MyDraw.colorDeskW = mSettings.getInt(APP_PREFERENCES_COLOR_DESK_WHITE,MyDraw.colorDeskW);

        }
        if (mSettings.contains(APP_PREFERENCES_COLOR_DESK_BLACK)) {
            MyDraw.colorDeskB = mSettings.getInt(APP_PREFERENCES_COLOR_DESK_BLACK,MyDraw.colorDeskB);

        }
        if (mSettings.contains(APP_PREFERENCES_ZADERJKA)) {
            XmsecEdit = mSettings.getInt(APP_PREFERENCES_ZADERJKA,XmsecEdit);

        }
        if (mSettings.contains(APP_PREFERENCES_GROMKOST)) {
            spVolume = mSettings.getString(APP_PREFERENCES_GROMKOST,spVolume);

        }

        final Dialog dialog=new Dialog(this);
        dialog.setOnKeyListener(new Dialog.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface arg0, int keyCode,
                                 KeyEvent event) {
                // TODO Auto-generated method stub
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    finish();
                    dialog.dismiss();
                }
                return true;
            }
        });

        Xmsec = XmsecEdit;
    }

    @Override
    protected void onRestart() {
        super.onRestart( );
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart( );
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {


        return true;
    }

    public void bell() {
        final MediaPlayer mp = MediaPlayer.create(MainActivity.this, R.raw.fatal_snipe____fight_4_the_lite);
        mp.setVolume((float) 0.8, (float) 0.8);
        mp.start( );
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener( ) {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mp.release( );
            }
        });

    }

    public static boolean isOnline(Context context)
    {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting())
        {
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {

        if(ll.getVisibility()==VISIBLE||frame.getVisibility()==VISIBLE||frame3.getVisibility()==VISIBLE||frame4.getVisibility()==VISIBLE) {} else finish( );
    }

    public void openQuitDialog() {
        AlertDialog.Builder quitDialog = new AlertDialog.Builder(
                this);
        quitDialog.setTitle("Выход: Вы уверены?");

        quitDialog.setPositiveButton("Да!", new DialogInterface.OnClickListener( ) {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                refresh();

                MainActivity.super.onDestroy();
                setWhiteFigure.setVisibility(GONE);

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //TODO
                        finish();
                    }
                }, 400);
            }
        });

        quitDialog.setNegativeButton("Нет. Заново!", new DialogInterface.OnClickListener( ) {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                MainActivity.super.recreate( );
            }
        });

        quitDialog.show( );
    }


/*------------------\
|----speakCoords-----|//********************************************************************
\------------------*/
//
//                  ------   -------\  ------       /\       |    /
//                  \        |      |  |           /  \      |  /
//                   \___    |_____/   |_____     /    \     |/
//                       \   |         |         /------\    |\
//                        \  |         |        /        \   | \
//                  -------  |         |-----  /          \  |  \___
//

    public synchronized void speakCoords(CharSequence text, int r) {
        //       Log.d(TAG, "speakCoords()");
        this.r = r;//если r==1 запускается startActivityForRecognize()
        // если text==null произносятся координаты

        if (endGame) startGame = false;

        if (startGame & (!neRaspoznano) & (!Bigfoot) & (!pointPoint)&(iii!=1)& (but_as!=3)&(!pause)&(!vybor)) {
            Log.d(TAG, "startGame="+startGame);
            Log.d(TAG, "neRaspoznano="+neRaspoznano);
            Log.d(TAG, "Bigfoot="+Bigfoot);
            Log.d(TAG, "pointPoint="+pointPoint);
            Log.d(TAG, "iii="+iii);
            Log.d(TAG, "but_as="+but_as);
            Log.d(TAG, "pause="+pause);
            Log.d(TAG, "vybor="+vybor);

            MyDraw.calcX1Y1Draw();


            reco[0]= String.valueOf(x1_ABC)+String.valueOf(y1_123);
            listWhite.add(countOfMovesWhite,reco[0]);
            Log.d(TAG, "listWhite="+listWhite);
            Log.d(TAG, "countOfMovesWhite="+countOfMovesWhite);

            countOfMovesWhite++;
            refresh( );

            Rect erectActiv = new Rect(MyDraw.aa, MyDraw.bb, MyDraw.cc, MyDraw.dd);
            inSquare = erectActiv.contains(MyDraw.x2, MyDraw.y2);

            if ((MyDraw.x1 == MyDraw.x2) & (MyDraw.y1 == MyDraw.y2)) {
                endGame=true;
                startGame=false;
            nastroyki.setVisibility(GONE);
            button2.setVisibility(GONE);
            button13.setVisibility(GONE);
                setWhiteFigure.setText(" В ы х о д ");
                but_as=3;
                refresh( );

            }
        }
        if(pause1){
            pause=false;
            pause1=false;
        }

        //  Log.d(TAG, "r="+r);
        Bundle bundle = new Bundle( );
        bundle.putFloat(TextToSpeech.Engine.KEY_PARAM_VOLUME, parseFloat(spVolume));
        bundle.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "deros");
        if(r==2) mTTS.setSpeechRate(parseFloat("0.9"));//0.0--1.0
        //  Log.d(TAG, "x1_ABC="+x1_ABC);
        //  Log.d(TAG, "y1_123="+y1_123);

        if (text == null) text = x1_ABC + " " + y1_123;//trigger
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mTTS.speak(text, TextToSpeech.QUEUE_ADD, bundle, "deros");
        }
//       Log.d(TAG, "text="+text);
        Log.d(TAG, "--------------------------------");

    }

    @Override
    public void onInit(int status) {
        // TODO Auto-generated method stub
        Log.d(TAG, "onInit");
        if (status == TextToSpeech.SUCCESS) {

            Locale locale = new Locale("ru");

            int result = mTTS.setLanguage(locale);
            //int result = mTTS.setLanguage(Locale.getDefault());

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "Извините, этот язык не поддерживается");
            } else {
                //      mButton.setEnabled(true);

            }

        } else {
            Log.e("TTS", "Ошибка!");
            Log.d(TAG, "TTS Ошибка!");

        }
        //onInit speakeCoords


        //***************************************************************
//************************>>>             onDone               <<<******************************
        //***************************************************************

        mTTS.setOnUtteranceProgressListener(new UtteranceProgressListener( ) {

            @Override
            public void onDone(String utteranceId) {
                //метод вызывается когда speakecoords завершилась

                Log.d(TAG, "TTS completed!");
                //         Log.d(TAG,"but_as="+but_as);

                //          Log.d(TAG,"r="+r);
                setWhiteFigure.setEnabled(true);

/*
                if (reco[0]!=null & but_as==1) {
                    setWhiteFigure.setBackgroundColor(getResources( ).getColor(R.color.red));
                }
*/


                if (but_as ==1 && r == 1) {}

                if (but_as==3) {
                    MainActivity.super.onDestroy();
                    MainActivity.super.onPause();

                }

    /*                if (endGame) {
                 //       MainActivity.super.recreate();
                        openQuitDialog();
                    }*/
                if (startGame & r==1 & but_as!=3) {
                    //                    CompareActiv( );
                    //     frame5.setVisibility(VISIBLE);
                    pauseX( );
                    //                  new Thread(myThread).start();//для ProgressBar

                    recoPR=reco1;
                    x2_0 = MyDraw.x2;
                    y2_0 = MyDraw.y2;
                    neRasposnano=0;
                    //                  Log.d(TAG, "recoPR="+recoPR );
                    //                  Log.d(TAG, "x2_0="+x2_0 );
                    //                  Log.d(TAG, "y2_0="+y2_0 );

                    //                  Log.d(TAG, "pause="+pause );
                    //                 Log.d(TAG, "pause1="+pause1 );
                    //            frame5.setVisibility(GONE);

                    if (pause){}else
                        startActivityForRecognize( );

                }

            }//end onDone

            @Override
            public void onError(String utteranceId) {
                Log.d(TAG, "onError OnUtteranceProgressListener");
            }

            @Override
            public void onStart(String utteranceId) {
                //  Log.d(TAG,"onStart OnUtteranceProgressListener");
            }
        });

    }//end onInit

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy( );
        // Don't forget to shutdown mTTS!

        Log.d(TAG, "mTTS.isSpeaking()=" + mTTS.isSpeaking());

        mTTS.stop( );
        Log.d(TAG, "mTTS.stop");
        mTTS.shutdown( );
        Log.d(TAG, "mTTS.shutdown");



        if(mp1!=null)  {

            mp1.release();
        }

        neRaspoznano=false;
        startGame=false;
        Bigfoot=false;inSquare=true;pointPoint=false;
        pause=false;
        vybor=false;
        but = 0;
        but_as = 0;
        x1 = 0;
        y1 = 0;
        x=0;
        y=0;
        MyDraw.x2 = 0;
        MyDraw.y2 = 0;
        x2_ABC = 0;
        y2_123 = 0;
        x1_ABC = 0;
        y1_123 = 0;
        r = 0;
        iii=0;
        reco[0]="";
        listWhite.clear();
        listBlack.clear();
        countOfMovesBlack=0;
        countOfMovesWhite=0;

    }

/*-------------------------------\
|----startActivityForRecognize----|//*********************************************************
\-------------------------------*/
//
//                  |----\       |------       /----\        /--\
//                  |     \      |            /             /    \
//                  |      \     |           /             /      \
//                  |-----/      |----      (             (        )
//                  |   \        |           \             \      /
//                  |    \       |            \             \    /
//                  |     \      |------       \----/        \--/
//

    public synchronized void startActivityForRecognize() {
        //    if(but_as==3) MainActivity.super.onPause();
        //      Log.d(TAG, " startActivityForRecognize");

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault( ));//"ru-RU");
/*    intent.putExtra(RecognizerIntent.ACTION_GET_LANGUAGE_DETAILS, "en-US");

    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "en-US");
    intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, "en-US");
*/
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);



        if ( neRaspoznano) {
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    "Фраза не распознана, пожалуйста повторите...");
            neRaspoznano=false;

        }else

        if (Bigfoot) {
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    "Можно ходить не дальше чем на одну клетку. Пожалуйста повторите...");
            Bigfoot = false;
        }else
        if(pointPoint) {
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    "Нельзя ставить на тоже поле что и у белой фигуры. Пожалуйста повторите...");
            pointPoint=false;
        }else
        if(!inSquare) {
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    "Фигура должна быть в границах красного квадрата. Пожалуйста повторите...");
            inSquare=true;
        }else
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                    "Назовите координаты");
        try {

            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    // Здесь получаем результат распознавания
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult" );


        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {

                if (resultCode == RESULT_OK && null != data) {


                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//sc - переменнная обозначающая номер фразы в общем списке распознанного выражения

                    reco[0]="";
                    iii=0;
                    ii=0;
                    int dd[]=new int[8];
                    String prov = Character.toString(recoPR);
                    Boolean detectedTwoSimbolsLength=false;
                    for(int i=0;i<8;i++){
                        dd[i]=0;

                    }

                    Log.d(TAG, "result1=" + result.toString( ));


                    ArrayList<String> list = new ArrayList<>(5);


                    ArrayList<String> list1 = new ArrayList<>(5);
                    ArrayList<String> list2 = new ArrayList<>(5);
                    ArrayList<String> list3 = new ArrayList<>(5);
                    ArrayList<String> list4 = new ArrayList<>(5);
                    ArrayList<String> list5 = new ArrayList<>(5);
                    ArrayList<String> list6 = new ArrayList<>(5);
                    ArrayList<String> list7 = new ArrayList<>(5);
                    ArrayList<String> list8 = new ArrayList<>(5);

                    ArrayList<String> rezz = new ArrayList<>(5);
                    ArrayList<String> rezz1 = new ArrayList<>(5);
                    ArrayList<String> rezzAntiH = new ArrayList<>(5);
                    ArrayList<String> rezzAntiE = new ArrayList<>(5);

                    ArrayList<String> rezzTwoSimbol = new ArrayList<>(5);



                    //в этом цикле редактируется список result: все его элементы обрезаются
                    // по краям от пробелов и приводятся к верхнему регистру (напр: " s 2" станет "S 2"
                    for (int scB=0;scB<result.size();scB++) {
                        tekZn1 = result.get(scB).trim().toUpperCase();
                        result.set(scB,tekZn1);
                    }

                    for (int scB=0;scB<result.size();scB++) {
                        if(tekZn1.equals("EXIT")) {
                            openQuitDialog();
                            return;
                        }
                    }

                    if(vybor){
                        if(result.size()==0) speakCoords(" Выбор не распознан! Повторите",1);
                        for (int scB=0;scB<result.size();scB++) {
                            if (result.get(scB).trim().toUpperCase().length()<3) continue;
                            if( result.get(scB).trim().toUpperCase().contains("ПЕРВ")) vyborRes=1;
                            if( result.get(scB).trim().toUpperCase().contains("ВТОР")) vyborRes=2;
                            if( result.get(scB).trim().toUpperCase().contains("ТРЕТ")) vyborRes=3;
                            if( result.get(scB).trim().toUpperCase().contains("ЧЕТВ")) vyborRes=4;
                            if( result.get(scB).trim().toUpperCase().contains("ПЯТ"))  vyborRes=5;
                        }
                        if(vyborRes==0) speakCoords(" Выбор не распознан! Повторите",1);

                    }
                    Log.d(TAG, "vyborRes=" + vyborRes);

//удаляем элемент из списка если он начинается с цифры
//также из результатов удаляется значение Е1, т.к. оно встречается во многих случаях.
                    for (int scB=0;scB<result.size();scB++) {
                        tekZn1 = result.get(scB);
                        rec = tekZn1.charAt(0);
                        if (Character.isDigit(rec)) result.remove(scB);
                        if(tekZn1.equals("E1")||tekZn1.equals("Е1")) result.remove(scB);
                    }
                    Log.d(TAG, "result(без цифр и Е1)=" + result.toString( ));


//начинается перебор списка result
                    for (sc = 0; sc < result.size(); sc++){
                        tekZn=result.get(sc);
                        //                   Log.d(TAG,"//Начало цикла// reco["+sc+"]="+ tekZn);
                        Bigfoot=false;
                        pointPoint=false;
                        inSquare=true;



//любой результат с кол-ом символов>2 и не вошедший в исключения будет обнуляться---???
                        if(result.get(sc).trim().length( ) != 2 & (!vybor) ){
                            okonechnoeRaspoznavanie =true;
                            int iSC=0;

                            //region  свёрнуто

                            //обработка исключений c кол-ом символов !=2
                            if(tekZn.equals("ОДИН")||tekZn.equals("ODIN")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);
                                reco[0] = "A1";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }
                                break;
                            }
                            if(tekZn.equals("А ПЯТЬ")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "A5";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("АСЕМ")||tekZn.equals("АСИМ")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "A7";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("A-8")||tekZn.equals("А-8")||tekZn.equals("ОЧЕНЬ")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "A8";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }

                            if(tekZn.equals("БАДЕН")||tekZn.equals("БАДИН")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "B1";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("БИ-2")||tekZn.equals("БИ 2")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "B2";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("БАССЕЙН")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "B7";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }

                            if(tekZn.equals("САЙДИНГ")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "C1";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("СУДЬБА")||tekZn.equals("СИЛЬПО")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "C2";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }

                            if(tekZn.equals("SO4")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "C4";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }


                            if(tekZn.equals("ДЯДИН")||tekZn.equals("ДАДИН")||tekZn.equals("ДО 1")||tekZn.equals("Д 1")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "D1";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("ДО 2")||tekZn.equals("Д 2")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "D2";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }

                            if(tekZn.equals("ДО 3")||tekZn.equals("Д 3")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "D3";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }

                            if(tekZn.equals("ДО 4")||tekZn.equals("DO4A")||tekZn.equals("ДА 4")||tekZn.equals("Д 4")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "D4";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("ДАЙ ПЯТЬ")||tekZn.equals("ДО 5")||tekZn.equals("Д 5")||tekZn.equals("У ТЕБЯ ПЯТЬ")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "D5";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("ДО 6:00")||tekZn.equals("ДО 6")||tekZn.equals("Д 6")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "D6";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("ДО 7:00")||tekZn.equals("ДО 7")||tekZn.equals("Д 7")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "D7";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }

                            if(tekZn.equals("ДО 8")||tekZn.equals("Д 8")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "D8";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("Я ОДИН")||tekZn.equals("E-1")||tekZn.equals("Е-1")||tekZn.equals("Е 1")||tekZn.equals("E 1")||tekZn.equals("И ОДИН")||tekZn.equals("YA1")||tekZn.equals("Я 1")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "E1";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }

                            if(tekZn.equals("ЕДВА")||tekZn.equals("Е 2")||tekZn.equals("Я 2")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "E2";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }

                            if(tekZn.equals("Я 3")||tekZn.equals("Е 3")||tekZn.equals("И 3")||tekZn.equals("И ТЫ")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "E3";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("Я 4")||tekZn.equals("Я ЧЕТЫРЕ")||tekZn.equals("Е 4")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "E4";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }

                            if(tekZn.equals("И 5")||tekZn.equals("Е 5")||tekZn.equals("Я 5")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "E5";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("И 6")||tekZn.equals("Е 6")||tekZn.equals("Я 6")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "E6";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("ЯСЕНЬ")||tekZn.equals("Я 7")||tekZn.equals("Е 7")||tekZn.equals("ЕСЛИ")||tekZn.equals("ЭССЕ")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "E7";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("И ВОСЕМЬ")||tekZn.equals("Я 8")||tekZn.equals("Е 8")||tekZn.equals("IE8")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "E8";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
//F2,3.. удалено "А В 2,3..."
                            if(tekZn.equals("В 2:00")||tekZn.equals("А В 2:00")||tekZn.equals("АВ2")||tekZn.equals("AV2")||tekZn.equals("В 2")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "F2";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }

                            if(tekZn.equals("В 3:00")||tekZn.equals("А В 3:00")||tekZn.equals("АВ3")||tekZn.equals("AV3")||tekZn.equals("В 3")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "F3";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("В 4:00")||tekZn.equals("А В 4:00")||tekZn.equals("АВ4")||tekZn.equals("AV4")||tekZn.equals("В 4")||tekZn.equals("RAV4")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "F4";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("В 5:00")||tekZn.equals("А В 5:00")||tekZn.equals("АВ5")||tekZn.equals("AV5")||tekZn.equals("В 5")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "F5";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("В 6:00")||tekZn.equals("А В 6:00")||tekZn.equals("АВ6")||tekZn.equals("AV6")||tekZn.equals("В 6")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "F6";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("В 7:00")||tekZn.equals("А В 7:00")||tekZn.equals("АВ7")||tekZn.equals("AV7")||tekZn.equals("В 7")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "F7";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("В 8:00")||tekZn.equals("А В 8:00")||tekZn.equals("АВ8")||tekZn.equals("AV8")||tekZn.equals("В 8")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "F8";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("УЖЕ 1")||tekZn.equals("ЖАДЕН")||tekZn.equals("GODIN")||tekZn.equals("ГОДИН")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "G1";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("G-2")||tekZn.equals("УЖЕ 2")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "G2";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }

                            if(tekZn.equals("ДЖИДЖИ")||tekZn.equals("УЖЕ 3")||tekZn.equals("ДЖИ ДЖИ")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "G3";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }

                            if(tekZn.equals("G-4")||tekZn.equals("УЖЕ 4")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "G4";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("G-5")||tekZn.equals("УЖЕ 5")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "G5";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("G-6")||tekZn.equals("УЖЕ 6")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "G6";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("G-7")||tekZn.equals("УЖЕ 7")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "G7";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }

                            if(tekZn.equals("УЖЕ 8")||tekZn.equals("G-8")||tekZn.equals("ЖИВОЙ")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "G8";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }

                            if(tekZn.equals("АС-4")||tekZn.equals("AS-4")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "H4";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }
                            if(tekZn.equals("ТАСКАТЬ")||tekZn.equals("ИСКАТЬ")||tekZn.equals("ТАШКЕНТ")) {
                                Log.d(TAG, "//reco["+sc+"]="+tekZn);

                                reco[0] = "H5";
                                proverkaBWords();
                                if(iii==1) {
                                    reco[0] = "";

                                    continue;
                                }

                                break;
                            }

//endregion
                            if(iSC==result.size()) okonechnoeRaspoznavanie =false;
                        }//end result.get(sc).trim().length( ) != 2

//если vybor=true то в этот цикл не попадаем, т.к. result.get(sc).trim().length( ) != 2
//соответственно listVybor остаётся таким же, как при первом проходе.

                        if (result.get(sc).trim().length( ) == 2){
//из 5 значений массива result берётся самый первый результат распознавания с числом символов=2 (после метода trim()), т.к. он самый точный (по определению)

                            reco1 = tekZn.charAt(0);
                            reco2 = tekZn.charAt(1);
                            x2_ABC = reco1;
                            y2_123 = reco2;


                            //
                            //| reco1 == '1' | reco1 == '2' | reco1 == '3' | reco1 == '4' | reco1 == '5' | reco1 == '6' | reco1 == '7' | reco1 == '8'
                            if ((reco1 == 'A' | reco1 == 'B' | reco1 == 'C' | reco1 == 'T'| reco1 == 'D' | reco1 == 'E' | reco1 == 'F' | reco1 == 'G' | reco1 == 'J' | reco1 == 'Z'| reco1 == 'H' | reco1 == 'А' | reco1 == 'Б' | reco1 == 'Ц' | reco1 == 'Д' | reco1 == 'Е' | reco1 == 'Я'| reco1 == 'Ф' |  reco1 == 'Ж' | reco1 == 'Г'| reco1 == 'Ш' | reco1 == 'Х'| reco1 == 'S' | reco1 == 'I' | reco1 == 'M' | reco1 == 'С' | reco1 == 'И' | reco1 == 'М') & (y2_123 == '1' | y2_123 == '2' | y2_123 == '3' | y2_123 == '4' | y2_123 == '5' | y2_123 == '6' | y2_123 == '7' | y2_123 == '8')) {
                                //                   Log.d(TAG, "Reco=" + x2_ABC + y2_123);
//выражение содержит допустимый символ в reco1 и цифру в reco2

                            } else continue;




                            //                   ABC123_x2y2();

                            MyDraw.compareF = true;
                            if (x2_ABC == 'A' | x2_ABC == 'А' ) MyDraw.x2 = 70;
                            if (x2_ABC == 'B' | x2_ABC == 'Б' ) MyDraw.x2 = 120;
                            if (x2_ABC == 'C' | x2_ABC == 'Ц' |x2_ABC == 'T' |x2_ABC == 'Т' ) MyDraw.x2 = 170;
                            if (x2_ABC == 'D' | x2_ABC == 'Д' ) MyDraw.x2 = 220;
                            if (x2_ABC == 'E' | x2_ABC == 'Е' ) MyDraw.x2 = 270;
                            if (x2_ABC == 'F' | x2_ABC == 'Ф' ) MyDraw.x2 = 320;
                            if (x2_ABC == 'G' | x2_ABC == 'Ж' | x2_ABC == 'J'| x2_ABC == 'Z'| x2_ABC == 'Г' ) MyDraw.x2 = 370;
                            if (x2_ABC == 'H' | x2_ABC == 'Ш' | x2_ABC == 'Х' ) MyDraw.x2 = 420;

                            if (x2_ABC == 'S' | x2_ABC == 'С' ) MyDraw.x2 = 170;//='C'
                            if (x2_ABC == 'I' | x2_ABC == 'И' | x2_ABC == 'Я') MyDraw.x2 = 270;//='E'
                            if (x2_ABC == 'M' | x2_ABC == 'М' ) MyDraw.x2 = 320;//='F'


                            if (y2_123 == '8') MyDraw.y2 = 20;
                            if (y2_123 == '7') MyDraw.y2 = 70;
                            if (y2_123 == '6') MyDraw.y2 = 120;
                            if (y2_123 == '5') MyDraw.y2 = 170;
                            if (y2_123 == '4') MyDraw.y2 = 220;
                            if (y2_123 == '3') MyDraw.y2 = 270;
                            if (y2_123 == '2') MyDraw.y2 = 320;
                            if (y2_123 == '1') MyDraw.y2 = 370;



                            //Bigfoot
                            if (startGame & ((Math.abs(MyDraw.x2 - x2_0) > 50 )||(Math.abs(MyDraw.y2 - y2_0)>50))) {
                                Log.d(TAG,"Bigfoot ");
                                MyDraw.x2=x2_0;
                                MyDraw.y2=y2_0;
                                reco1=recoPR;
                                Bigfoot=true;
                                iii=1;
                                if(sc==(result.size()-1)) break;
                                continue;
                            }

                            //inSquare
                            Rect erectActiv = new Rect(MyDraw.aa, MyDraw.bb, MyDraw.cc, MyDraw.dd);
                            inSquare = erectActiv.contains(MyDraw.x2, MyDraw.y2);
                            //       Log.d(TAG,"inSquare="+ inSquare);
                            if(!inSquare) {
                                Log.d(TAG,"Не в квадрате!");
                                MyDraw.x2=x2_0;
                                MyDraw.y2=y2_0;
                                reco1=recoPR;
                                iii=1;
                                if(sc==(result.size()-1)) break;
                                continue;
                            }

 /*                           //pointPoint
                            if ((MyDraw.x1 == MyDraw.x2) & (MyDraw.y1 == MyDraw.y2)) pointPoint=true;
                            //       Log.d(TAG,"pointPoint="+ pointPoint);
                            if(pointPoint) {
                                Log.d(TAG,"Постановка на поле белой фигуры!");
                                MyDraw.x2=x2_0;
                                MyDraw.y2=y2_0;
                                reco1=recoPR;
                                iii=1;
                                if(sc==(result.size()-1)) break;
                                continue;
                            }
*/

                            if((MyDraw.x2-compare_x2)!=0 & (MyDraw.y2-compare_y2)!=0) listVybor.add(tekZn);

                            compare_x2=MyDraw.x2;
                            compare_y2=MyDraw.y2;


                            if(!Bigfoot&!pointPoint&inSquare)iii=0;
                            //                           if(iii==1) reco[0]="";else reco[0]=tekZn;

                        }//end if (result.get(sc).trim().length( ) == 2)

                    }//завершение цикла for. При первом проходе сдесь сформируется
                    Log.d(TAG,"listVybor="+ listVybor);

//reco[0], если нет, то listVybor, если нет, то нужно распознавать заново


//для второго прохода
                    if (vybor) {
                        okonechnoeRaspoznavanie=true;
                        switch (vyborRes) {
                            case 1:
                                reco[0] = listVybor.get(vyborRes - 1);
                                break;
                            case 2:
                                reco[0] = listVybor.get(vyborRes - 1);
                                break;
                            case 3:
                                reco[0] = listVybor.get(vyborRes - 1);
                                break;
                            case 4:
                                reco[0] = listVybor.get(vyborRes - 1);
                                break;
                            case 5:
                                reco[0] = listVybor.get(vyborRes - 1);
                                break;
                        }
                    }


                    if(!vybor) {
                        if ((reco[0].equals("")) & (listVybor.size() == 0)) {
                            if (iii == 1) {
                                speakCoords("Не правильно, повторите...", 1);
                                return;
                            }
                            else {
                                neRaspoznano = true;
                                Log.d(TAG, "Фраза не распознана, повторите...");
                                speakCoords("Фраза не рас познана, повторите...", 1);
                                return;
                            }
                        } else


                        {

                            //              Reco();//есть корректный результат распознавания
                            Bigfoot = false;

                            if (!(reco[0].equals(""))) {
                                //есть распознание по длинным фразам
                                //приоритет отдаётся им. Ничего больше работаем с этим значением.
                            }
                            //                       if(!vybor) listVybor.addAll(listVybor);

                            if (listVybor.size() != 0 & (!(reco[0].equals("")))) {
//если распознана двухсимвольная фраза и длинная фраза, то ничего не делать,
//приоритет отдан длинным фразам
                            }
                            if (listVybor.size() != 0 & ((reco[0].equals("")))) {
//если распознана двухсимвольная фраза,а длинная фраза нет
                                if (listVybor.size() == 1) {
                                    reco[0] = listVybor.get(0);
                                    okonechnoeRaspoznavanie=true;
                                }

                                if (listVybor.size() > 1) {
                                    vybor = true;
                                    speakCoords("Внимание! Распознано " + listVybor.size() + " значения. Какое вы выберете?" + "Первое: " + listVybor.get(0) + "или второе: " + listVybor.get(1), 1);
                                }
                            }

                        }//end if(!vybor)-первый проход
                    }
                    if(okonechnoeRaspoznavanie){
                        Log.d(TAG,"okonechnoeRaspoznavanie!");

                        reco1 = reco[0].charAt(0);
                        reco2 = reco[0].charAt(1);
                        x2_ABC = reco1;
                        y2_123 = reco2;


                        //                   ABC123_x2y2();

                        MyDraw.compareF = true;
                        if (x2_ABC == 'A' | x2_ABC == 'А' ) MyDraw.x2 = 70;
                        if (x2_ABC == 'B' | x2_ABC == 'Б' ) MyDraw.x2 = 120;
                        if (x2_ABC == 'C' | x2_ABC == 'Ц' |x2_ABC == 'T' |x2_ABC == 'Т' ) MyDraw.x2 = 170;
                        if (x2_ABC == 'D' | x2_ABC == 'Д' ) MyDraw.x2 = 220;
                        if (x2_ABC == 'E' | x2_ABC == 'Е' ) MyDraw.x2 = 270;
                        if (x2_ABC == 'F' | x2_ABC == 'Ф' ) MyDraw.x2 = 320;
                        if (x2_ABC == 'G' | x2_ABC == 'Ж' | x2_ABC == 'J'| x2_ABC == 'Z'| x2_ABC == 'Г' ) MyDraw.x2 = 370;
                        if (x2_ABC == 'H' | x2_ABC == 'Ш' | x2_ABC == 'Х' ) MyDraw.x2 = 420;

                        if (x2_ABC == 'S' | x2_ABC == 'С' ) MyDraw.x2 = 170;//='C'
                        if (x2_ABC == 'I' | x2_ABC == 'И' | x2_ABC == 'Я') MyDraw.x2 = 270;//='E'
                        if (x2_ABC == 'M' | x2_ABC == 'М' ) MyDraw.x2 = 320;//='F'


                        if (y2_123 == '8') MyDraw.y2 = 20;
                        if (y2_123 == '7') MyDraw.y2 = 70;
                        if (y2_123 == '6') MyDraw.y2 = 120;
                        if (y2_123 == '5') MyDraw.y2 = 170;
                        if (y2_123 == '4') MyDraw.y2 = 220;
                        if (y2_123 == '3') MyDraw.y2 = 270;
                        if (y2_123 == '2') MyDraw.y2 = 320;
                        if (y2_123 == '1') MyDraw.y2 = 370;



                        //Bigfoot
                        if (startGame & ((Math.abs(MyDraw.x2 - x2_0) > 50 )||(Math.abs(MyDraw.y2 - y2_0)>50))) {
                            Log.d(TAG,"Bigfoot ");
                            MyDraw.x2=x2_0;
                            MyDraw.y2=y2_0;
                            reco1=recoPR;
                            Bigfoot=true;
                            reco[0]="";
                        }

                        //inSquare
                        Rect erectActiv = new Rect(MyDraw.aa, MyDraw.bb, MyDraw.cc, MyDraw.dd);
                        inSquare = erectActiv.contains(MyDraw.x2, MyDraw.y2);
                        //       Log.d(TAG,"inSquare="+ inSquare);
                        if(!inSquare) {
                            Log.d(TAG,"Не в квадрате!");
                            MyDraw.x2=x2_0;
                            MyDraw.y2=y2_0;
                            reco1=recoPR;
                            reco[0]="";
                        }

 /*                       //pointPoint
                        if ((MyDraw.x1 == MyDraw.x2) & (MyDraw.y1 == MyDraw.y2)) pointPoint=true;
                        //       Log.d(TAG,"pointPoint="+ pointPoint);
                        if(pointPoint) {
                            Log.d(TAG,"Постановка на поле белой фигуры!");
                            MyDraw.x2=x2_0;
                            MyDraw.y2=y2_0;
                            reco1=recoPR;
                            reco[0]="";
                        }
*/
                    }
//если нет ничего на выбор, то
                    if(okonechnoeRaspoznavanie) {
                        vybor = false;
                        okonechnoeRaspoznavanie =false;
                        vyborRes = 0;
                        listVybor.clear();
                        compare_x2=0;
                        compare_y2=0;

                        Log.d(TAG, "Reco[0]=" + reco[0]);
                        listBlack.add(countOfMovesBlack , reco[0]);
                        //                      Log.d(TAG, "countOfMovesBlack="+countOfMovesBlack);
                        //                    Log.d(TAG, "listBlack="+listBlack);

                        countOfMovesBlack++;
                        reco1 = reco[0].charAt(0);
                        reco2 = reco[0].charAt(1);
                        x2_ABC = reco1;
                        y2_123 = reco2;
                        //            Log.d(TAG,"reco1="+ reco1);


                        ABC123_x2y2();

                        refresh();//завершение одного игрового цикла(сделан один ход фигурами)

                        CompareActiv();

                    }

                }else {
                    if(but_as==3) break;

                    neRaspoznano=true;
                    Log.d(TAG, "Произошёл сбой, повторите...");
                    //       if(button2.isPressed()){}else speakCoords(null,1);

                }

                break;
            }

        }


    }//onActivityResult


    public void pauseX() {

        try {
            Thread.sleep(Xmsec);
        } catch (InterruptedException e) {
            e.printStackTrace( );
        }


    }


    public void ABC123_x2y2()

    {

        MyDraw.compareF = true;
        if (x2_ABC == 'A' | x2_ABC == 'А' | x2_ABC == '1') MyDraw.x2 = 70;
        if (x2_ABC == 'B' | x2_ABC == 'Б' | x2_ABC == '2') MyDraw.x2 = 120;
        if (x2_ABC == 'C' | x2_ABC == 'Ц' |x2_ABC == 'T' | x2_ABC == '3') MyDraw.x2 = 170;
        if (x2_ABC == 'D' | x2_ABC == 'Д' | x2_ABC == '4') MyDraw.x2 = 220;
        if (x2_ABC == 'E' | x2_ABC == 'Е' | x2_ABC == '5') MyDraw.x2 = 270;
        if (x2_ABC == 'F' | x2_ABC == 'Ф' | x2_ABC == '6') MyDraw.x2 = 320;
        if (x2_ABC == 'G' | x2_ABC == 'Ж' | x2_ABC == 'J'| x2_ABC == 'Z'| x2_ABC == 'Г'| x2_ABC == '7') MyDraw.x2 = 370;
        if (x2_ABC == 'H' | x2_ABC == 'Ш' | x2_ABC == 'Х'| x2_ABC == '8') MyDraw.x2 = 420;

        if (x2_ABC == 'S' | x2_ABC == 'С' ) MyDraw.x2 = 170;//='C'
        if (x2_ABC == 'I' | x2_ABC == 'И' | x2_ABC == 'Я') MyDraw.x2 = 270;//='E'
        if (x2_ABC == 'M' | x2_ABC == 'М' ) MyDraw.x2 = 320;//='F'

        if (y2_123 == '8') MyDraw.y2 = 20;
        if (y2_123 == '7') MyDraw.y2 = 70;
        if (y2_123 == '6') MyDraw.y2 = 120;
        if (y2_123 == '5') MyDraw.y2 = 170;
        if (y2_123 == '4') MyDraw.y2 = 220;
        if (y2_123 == '3') MyDraw.y2 = 270;
        if (y2_123 == '2') MyDraw.y2 = 320;
        if (y2_123 == '1') MyDraw.y2 = 370;

        if (startGame & (Math.abs(MyDraw.x2 - x2_0) > 50 || Math.abs(MyDraw.y2 - y2_0) > 50)) Bigfoot = true;


        IfinSquareAndpointPoint();
    }

    public void IfinSquareAndpointPoint (){
        Rect erectActiv = new Rect(MyDraw.aa, MyDraw.bb, MyDraw.cc, MyDraw.dd);
        inSquare = erectActiv.contains(MyDraw.x2, MyDraw.y2);

        if ((MyDraw.x1 == MyDraw.x2) & (MyDraw.y1 == MyDraw.y2)) pointPoint=true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void CompareActiv() {

        if (MyDraw.compareF) {
            if(startGame) {
                if (pointPoint||!inSquare) {
                    endGame = true;
                    startGame = false;
                    if(but_as>2||endGame){
                        button2.setVisibility(GONE);
                        button13.setVisibility(GONE);

                    }

                    for(; countOfMovesBlack ==0; --countOfMovesBlack){

                    }
                    but_as=3;
                    nastroyki.setVisibility(GONE);


                    setWhiteFigure.setText(" В ы х о д ");
                    refresh( );
                }
                if(Bigfoot){
                    MyDraw.x2=x2_0;
                    MyDraw.y2=y2_0;
                    speakCoords("Нельзя ходить больше чем на одну клетку",1);
                }

            }
            if(endGame) speakCoords("Игра закончена!",2);
        }
        if (startGame & (!Bigfoot) & (!pointPoint) & (inSquare)) {
            speakCoords(null, 1);
        }


    }

    public void proverkaBWords(){


        x2_ABC = reco[0].charAt(0);
        y2_123 = reco[0].charAt(1);


        MyDraw.compareF = true;
        if (x2_ABC == 'A' | x2_ABC == 'А' ) MyDraw.x2 = 70;
        if (x2_ABC == 'B' | x2_ABC == 'Б' ) MyDraw.x2 = 120;
        if (x2_ABC == 'C' | x2_ABC == 'Ц' |x2_ABC == 'T' ) MyDraw.x2 = 170;
        if (x2_ABC == 'D' | x2_ABC == 'Д' ) MyDraw.x2 = 220;
        if (x2_ABC == 'E' | x2_ABC == 'Е' ) MyDraw.x2 = 270;
        if (x2_ABC == 'F' | x2_ABC == 'Ф' ) MyDraw.x2 = 320;
        if (x2_ABC == 'G' | x2_ABC == 'Ж' | x2_ABC == 'J'| x2_ABC == 'Z'| x2_ABC == 'Г' ) MyDraw.x2 = 370;
        if (x2_ABC == 'H' | x2_ABC == 'Ш' | x2_ABC == 'Х' ) MyDraw.x2 = 420;

        if (x2_ABC == 'S' | x2_ABC == 'С' ) MyDraw.x2 = 170;//='C'
        if (x2_ABC == 'I' | x2_ABC == 'И' | x2_ABC == 'Я') MyDraw.x2 = 270;//='E'
        if (x2_ABC == 'M' | x2_ABC == 'М' ) MyDraw.x2 = 320;//='F'


        if (y2_123 == '8') MyDraw.y2 = 20;
        if (y2_123 == '7') MyDraw.y2 = 70;
        if (y2_123 == '6') MyDraw.y2 = 120;
        if (y2_123 == '5') MyDraw.y2 = 170;
        if (y2_123 == '4') MyDraw.y2 = 220;
        if (y2_123 == '3') MyDraw.y2 = 270;
        if (y2_123 == '2') MyDraw.y2 = 320;
        if (y2_123 == '1') MyDraw.y2 = 370;

        IfinSquareAndpointPoint();

        if(!inSquare) {
            MyDraw.x2=x2_0;
            MyDraw.y2=y2_0;
            reco1=recoPR;
            iii=1;
        }
        if(pointPoint) {
            MyDraw.x2=x2_0;
            MyDraw.y2=y2_0;
            reco1=recoPR;
            iii=1;
        }


        if (startGame & ((Math.abs(MyDraw.x2 - x2_0) > 50 )||(Math.abs(MyDraw.y2 - y2_0)>50))) {
            MyDraw.x2=x2_0;
            MyDraw.y2=y2_0;
            reco1=recoPR;
            Bigfoot=true;
            iii=1;

        }
    }


}