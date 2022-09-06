package com.example.timberman;

import static android.view.View.INVISIBLE;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public ImageView imageView_logo,imageView_game_over;
    MediaPlayer player,beepsound,deadsound,deadplayer;
    public ImageButton btn_start,btn_shop,btn_info,btn_pause,btn_select,btn_retry,btn_musicon;
    private GameView gv;
    public TextView txt_best_score;
    public TextView txt_score;
    public ProgressBar pb;

    @SuppressLint({"WrongViewCast", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);// ustawia full screen
        DisplayMetrics dm = new DisplayMetrics();// wyświetlacz w telefonie
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);


        //wczytywanie danych z pliku
        loadData();
        //wysokosc i szerokosc ekranu
        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;


        setContentView(R.layout.activity_main);


        btn_musicon=findViewById(R.id.btn_musicon);
        imageView_logo=findViewById(R.id.imageView_logo);
        btn_pause=findViewById(R.id.btn_pause);
        btn_start=findViewById(R.id.btn_start);
        btn_info=findViewById(R.id.btn_info);
        btn_shop=findViewById(R.id.btn_shop);
        txt_best_score = findViewById(R.id.txt_best_score);
        txt_score = findViewById(R.id.txt_score);
        btn_retry=findViewById(R.id.btn_retry);
        btn_select=findViewById(R.id.btn_select);
        gv=findViewById(R.id.gv);
        pb = findViewById(R.id.idpbbar);
        gv.setPb(pb);
        gv.initBtn(btn_shop,btn_retry,btn_musicon);
        //muzyka w tle i drzdewo smierc
        if(Constants.Musick==true) {
            player = MediaPlayer.create(this, R.raw.song2);
            player.setLooping(true);
            player.seekTo(0);
            player.setVolume(0.2f, 0.2f);
            gv.setPleyer(player);
            gv.playMusick();
        }

        else{ player=null;}

//        deadplayer= MediaPlayer.create(this, R.raw.deadsong);
//        deadplayer.seekTo(0);
//        deadplayer.setVolume(0.5f, 0.5f);

        deadsound=MediaPlayer.create(this,R.raw.deadsound);
        deadsound.seekTo(0);
        deadsound.setVolume(0.5f, 0.5f);
        gv.setMediapleyer(deadsound);
        if(Constants.Avatar==1) {
            beepsound=MediaPlayer.create(this,R.raw.popcatsound);
            beepsound.seekTo(300);
            beepsound.setVolume(0.5f, 0.5f);
        }
        if( Constants.Avatar==0 )  {
            beepsound=MediaPlayer.create(this,R.raw.bumpsound);
            beepsound.seekTo(0);
            beepsound.setVolume(0.5f, 0.5f);
        }
        if( Constants.Avatar==2 ){
            beepsound=MediaPlayer.create(this,R.raw.omnimansound);
            beepsound.seekTo(0);
            beepsound.setVolume(0.5f, 0.5f);
        }
        if( Constants.Avatar==3 ){
            beepsound=MediaPlayer.create(this,R.raw.umongussound);
            beepsound.seekTo(0);
            beepsound.setVolume(0.5f, 0.5f);
        }
      //  beepsound.start();

        //----------------------




//restart
        if(!Constants.Restart) {
            btn_start.setOnClickListener(view -> {
                gv.setStart(true);
                gv.pbstart=true;
                gv.progressCounter = 100;
//                pbReset();
                txt_best_score.setText(String.valueOf(Constants.bestScore)); // linia do sprawdzania czy wynik dobrze sie zapisuje

                //w tymi miejscu trzeba dopisać schowanie punkotw

                btn_start.setVisibility(INVISIBLE);
                pb.setVisibility(View.VISIBLE);
                btn_retry.setVisibility(INVISIBLE);
                btn_info.setVisibility(INVISIBLE);
                btn_shop.setVisibility(INVISIBLE);
                txt_best_score.setVisibility(View.VISIBLE);
                txt_score.setVisibility(View.VISIBLE);
                btn_pause.setVisibility(View.VISIBLE);
                imageView_logo.setVisibility(INVISIBLE);
            });
        }
        else{
            gv.setStart(true);
            gv.pbstart=true;
            gv.progressCounter = 100;
//            pbReset();
            txt_best_score.setText(String.valueOf(Constants.bestScore)); // linia do sprawdzania czy wynik dobrze sie zapisuje

            //w tymi miejscu trzeba dopisać schowanie punkotw
            pb.setVisibility(View.VISIBLE);
            btn_start.setVisibility(INVISIBLE);
            btn_info.setVisibility(INVISIBLE);
            btn_shop.setVisibility(INVISIBLE);
            btn_retry.setVisibility(INVISIBLE);
            txt_best_score.setVisibility(View.VISIBLE);
            txt_score.setVisibility(View.VISIBLE);
            btn_pause.setVisibility(View.VISIBLE);
            imageView_logo.setVisibility(INVISIBLE);
        }


        // Animacja
        gv.setOnTouchListener((view, motionEvent) -> {
                beepsound.start();

            TextView txt_score = findViewById(R.id.txt_score);
            txt_score.setText(String.valueOf(Constants.score));
            TextView txt_best_score = findViewById(R.id.txt_best_score); txt_best_score.setText(String.valueOf(Constants.bestScore));
            if(Constants.score>Constants.bestScore){ saveData(); }
            if(Constants.IsDead){
                deadsound.start();
//                deadplayer.start();
               if(Constants.Musick==true) {player.pause();}

                //sprawdzanie umierania
                gv.pbstart=false;

                btn_musicon.setVisibility(View.VISIBLE);
                btn_retry.setVisibility(View.VISIBLE);
                btn_shop.setVisibility(View.VISIBLE);

                pb.setVisibility(INVISIBLE);
            }

            if (Constants.click == 2 && Constants.clickR!=0) {
                switch (Constants.clickR){
                    case 1:
                        ImageView AnimatedTree = findViewById(R.id.animation);
                        AnimatedTree.setImageResource(R.drawable.animation);
                        AnimationDrawable runningTree = (AnimationDrawable) AnimatedTree.getDrawable();
                        runningTree.start();
                        break;
                    case 2:
                        ImageView AnimatedTree2 = findViewById(R.id.animation2);
                        AnimatedTree2.setImageResource(R.drawable.animation2);
                        AnimationDrawable runningTree2 = (AnimationDrawable) AnimatedTree2.getDrawable();
                        runningTree2.start();
                        break;
                    case 3:
                        ImageView AnimatedTree3 = findViewById(R.id.animation3);
                        AnimatedTree3.setImageResource(R.drawable.animation3);
                        AnimationDrawable runningTree3 = (AnimationDrawable) AnimatedTree3.getDrawable();
                        runningTree3.start();
                        Constants.clickR =0;
                        break;
                    default: break;
                }
                Constants.click = 0;
            }
            else if (Constants.click == 1 && Constants.clickL!=0) {
                switch (Constants.clickL){
                    case 1:
                        ImageView AnimatedTree4 = findViewById(R.id.animation4);
                        AnimatedTree4.setImageResource(R.drawable.animation4);
                        AnimationDrawable runningTree4 = (AnimationDrawable) AnimatedTree4.getDrawable();
                        runningTree4.start();
                        break;
                    case 2:
                        ImageView AnimatedTree5 = findViewById(R.id.animation5);
                        AnimatedTree5.setImageResource(R.drawable.animation5);
                        AnimationDrawable runningTree5 = (AnimationDrawable) AnimatedTree5.getDrawable();
                        runningTree5.start();
                        break;
                    case 3:
                        ImageView AnimatedTree6 = findViewById(R.id.animation6);
                        AnimatedTree6.setImageResource(R.drawable.animation6);
                        AnimationDrawable runningTree6 = (AnimationDrawable) AnimatedTree6.getDrawable();
                        runningTree6.start();
                        Constants.clickL =0;
                        break;
                    default: break;
                }
                Constants.click = 0;
            }
            return false;
        });


        btn_shop.setOnClickListener(view -> {
            if(Constants.score>Constants.bestScore){ saveData(); }
            Constants.score=0;
             Intent intent = new Intent(MainActivity.this,ShopActivity.class);
         if(Constants.Musick==true){ gv.stopMusick();}
            startActivity(intent);
        });
        btn_retry.setOnClickListener(view -> {
            gv.pbstart=true;
            Constants.Restart=true;
            if(Constants.score>Constants.bestScore){ saveData(); }
            Constants.score=0;
            recreate();

            //w tymi miejscu trzeba dopisać schowanie punkotw
        });
        btn_info.setOnClickListener(view -> Toast.makeText(MainActivity.this, R.string.info_string, Toast.LENGTH_LONG).show());
        btn_pause.setOnClickListener(view -> {
            if(gv.pbstart==true){
                gv.pbstart=false;
            }
            else{
                gv.pbstart=true;
            }

            if(!Constants.IsDead){
                if(gv.getStart()) {
                    gv.setStart(false);
                    btn_shop.setVisibility(View.VISIBLE);
                    btn_retry.setVisibility(View.VISIBLE);
                    btn_musicon.setVisibility(View.VISIBLE);

                }
                else {
                    gv.setStart(true);
                    btn_shop.setVisibility(INVISIBLE);
                    btn_retry.setVisibility(INVISIBLE);
                    btn_musicon.setVisibility(INVISIBLE);

                }
            }
        });
        btn_select.setOnClickListener(view -> {
            btn_start.setVisibility(View.VISIBLE);
            btn_info.setVisibility(View.VISIBLE);
            btn_shop.setVisibility(View.VISIBLE);
            btn_select.setVisibility(INVISIBLE);
        });
        btn_musicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Constants.Musick==true)
                {
                   gv.stopMusick();
                    Constants.Musick=false;
                }
                else if (Constants.Musick==false) {
                    player = MediaPlayer.create(MainActivity.this, R.raw.song2);
                    player.setVolume(0.2f, 0.2f);
                    player.seekTo(0);
                    gv.setPleyer(player);
                    gv.playMusick();
                    Constants.Musick = true;
                }

            }
        });



    }


    // zapis do pliku najlepszego wyniku gracza
    @Override
    protected void onPause() {
        super.onPause();
        if(Constants.score>Constants.bestScore){ saveData(); }
    }
    private  void saveData() {
        SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("best_score", Constants.score);
        editor.apply();
    }
    private  void loadData() {
        SharedPreferences prefs = getSharedPreferences("pref", MODE_PRIVATE);
        Constants.bestScore = prefs.getInt("best_score", 0);
    }
    //muzyka
    public void onClick(View view) {
        if(Constants.Musick==true){
            if(player.isPlaying()){
                player.pause();
            }
            else{
               gv.playMusick();
            }
        }
    }
}