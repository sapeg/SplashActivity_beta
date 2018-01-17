package com.blade_runner;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;



public class SplashActivity extends Activity implements
        MediaPlayer.OnCompletionListener {

    private View myView2;
    private Handler h;
    MediaPlayer mp=new MediaPlayer();
    private int mLongAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        myView2=findViewById(R.id.view2);
        mLongAnimationDuration = getResources().getInteger(
                android.R.integer.config_longAnimTime);
        bell( );

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(

                        SplashActivity.this, MainActivity.class));
                myView2.setAlpha(0f);
                myView2.setVisibility(View.VISIBLE);
                myView2.animate()
                        .alpha(1f)
                        .setDuration(mLongAnimationDuration)
                        .setListener(null);
                myView2.setVisibility(View.GONE);
                finish();
            }
        }, 0);

    }

    public  void bell() {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.blade_runner___little1);

        mp.setVolume((float) 0.4, (float) 0.4);

        mp.start( );

        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener( ) {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {

                mp.release( );
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mp!=null)  {

            mp.release();
        }

    }
    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {

        mp.release( );
    }


}