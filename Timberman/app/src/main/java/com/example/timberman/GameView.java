package com.example.timberman;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MonthDisplayHelper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import androidx.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;

public class GameView extends View {
    private final android.os.Handler handler;
    private final Runnable runnable;
    private Woodcutter woodcutter;
    private int fajnaliczba;
    private final ArrayList<Stick>arrSicks=new ArrayList<>();
    public boolean pbstart = false;
    private int los;
    private final float leftPersentage;
    private boolean changeSide=false;
    public ProgressBar pb;
    public int progressCounter = 100,licznikPbScal=0,secondLicznikPbScal=0;
    public MotionEvent skrt;
    MediaPlayer deadsound,gamemusick;


    public ImageButton btn_shop,btn_retry,btn_musicon;
    public void initBtn(ImageButton shop, ImageButton retry, ImageButton musicicon){
        this.btn_shop = shop;
        this.btn_retry = retry;
        this.btn_musicon = musicicon;

    }
    public void setPb(ProgressBar pb) {
        this.pb = pb;
    }
    private boolean start;

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        start = false;
        Constants.IsDead = false;


        initWoodCutter();
        initSticks();

        leftPersentage = (Constants.SCREEN_WIDTH)*50/100;

        handler = new Handler();
        runnable = () -> {
            invalidate();
            if(pb!=null){
                if(pbstart==true){
                    if(licznikPbScal==3) {
                        if(secondLicznikPbScal==8){
                            int dodatek=0;
                            dodatek = (int)Constants.score/40;
                            progressCounter-=dodatek;
                            secondLicznikPbScal=0;
                        }
                        secondLicznikPbScal++;
                        progressCounter -= 3;
                        licznikPbScal = 0;
                    }
                    else {
                        licznikPbScal++;
                    }
                    pb.setProgress(progressCounter);//TODO Tutuaj co sie pierdoli z wskaznikeim finda od progressbara jutro to naprawie/dzis
                    Log.d("pb-status",""+progressCounter);
                    if(progressCounter==0||progressCounter==-1||progressCounter==-2){
                        if(Constants.IsDead==false) {
                            Constants.StopMusick=true;
                            stopMusick();
                            playDeadSound();


                        }
                        woodcutter.smierc();
                        start=false;
                        btn_shop.setVisibility(View.VISIBLE);
                        btn_retry.setVisibility(View.VISIBLE);
                        btn_musicon.setVisibility(View.VISIBLE);
                    }
                }
            }
        };
    }

    public void initSticks() {

        //pierwszy element
        this.arrSicks.add(new Stick((357*Constants.SCREEN_WIDTH/1080),(1300*Constants.SCREEN_HEIGHT/1920),(395*Constants.SCREEN_WIDTH/1080),(240*Constants.SCREEN_HEIGHT/1920)));
        arrSicks.get(0).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.srodek));
        arrSicks.get(0).setStrona(Stick.Strona.SRODEK);


        int sumbranch = 8;
        for (int i = 1; i < 8; i++){

                los = new Random().nextInt(2);

            if (los == 0){
                if (!changeSide) {
                    this.arrSicks.add(new Stick((-36*Constants.SCREEN_WIDTH/1080), arrSicks.get(arrSicks.size() - 1).getY() - (240*Constants.SCREEN_HEIGHT/1920), ((395+395)*Constants.SCREEN_WIDTH/1080), (240*Constants.SCREEN_HEIGHT/1920)));
                    arrSicks.get(i).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.lewo));
                    arrSicks.get(i).setStrona(Stick.Strona.LEWO);
                }
                else{
                    this.arrSicks.add(new Stick((357*Constants.SCREEN_WIDTH/1080), arrSicks.get(arrSicks.size() - 1).getY() - (240*Constants.SCREEN_HEIGHT/1920), (395*Constants.SCREEN_WIDTH/1080), (240*Constants.SCREEN_HEIGHT/1920)));
                    arrSicks.get(i).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.srodek));
                    arrSicks.get(i).setStrona(Stick.Strona.SRODEK);
                    changeSide=false;
                }
            }
            else{
                if (changeSide) {
                    this.arrSicks.add(new Stick((357*Constants.SCREEN_WIDTH/1080),arrSicks.get(arrSicks.size() - 1).getY() - (240*Constants.SCREEN_HEIGHT/1920),((395+395)*Constants.SCREEN_WIDTH/1080),(240*Constants.SCREEN_HEIGHT/1920)));
                    arrSicks.get(i).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.prawo));
                    arrSicks.get(i).setStrona(Stick.Strona.PRAWO);
                }
                else{
                    this.arrSicks.add(new Stick((357*Constants.SCREEN_WIDTH/1080),arrSicks.get(arrSicks.size() - 1).getY() - (240*Constants.SCREEN_HEIGHT/1920),(395*Constants.SCREEN_WIDTH/1080),(240*Constants.SCREEN_HEIGHT/1920)));
                    arrSicks.get(i).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.srodek));
                    arrSicks.get(i).setStrona(Stick.Strona.SRODEK);
                    changeSide=true;
                }
            }
        }
    }

    public void initWoodCutter(){
        woodcutter = new Woodcutter();
        //ustawianie wielkości drwala
        woodcutter.setWidth(300*Constants.SCREEN_WIDTH/1080);
        woodcutter.setHeight(300*Constants.SCREEN_HEIGHT/1920);
        //połeżenie drwala
        woodcutter.setX(170*Constants.SCREEN_WIDTH/1080);
        woodcutter.setY(1300*Constants.SCREEN_HEIGHT/1920);
        ArrayList<Bitmap> arrBms = new ArrayList<>();
        Log.d("MainActivity","WoodcuterY"+woodcutter.getY());

        if(Constants.Avatar==0) {
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.left1));
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.left2));
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.left3));
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.right1));
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.right2));
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.right3));
        }
        else if(Constants.Avatar==1){
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.popcat1));
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.popcat1));
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.popcat2));
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.popcatright1));
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.popcatright1));
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.popcatright2));
        }
        else if(Constants.Avatar==2){
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.omniman));
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.omniman));
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.omniman2));
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.omnimanright1));
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.omnimanright1));
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.omnimanright2));
        }
        else if(Constants.Avatar==3){
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.amongus1));
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.amongus1));
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.amongus2));
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.amongusright1));
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.amongusright1));
            arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.amongusright2));
        }

        arrBms.add(BitmapFactory.decodeResource(this.getResources(), R.drawable.nagrobek));
        woodcutter.setArrBms(arrBms);
        woodcutter.setStrona(Woodcutter.AktualnaStrona.LEWO);//Defualtowo aktualna strona lewo
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
            for(int i = 0;i<arrSicks.size(); i++){
                arrSicks.get(i).draw(canvas);
            }
        canvas.drawBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.podstawadrzewauseless),(478*Constants.SCREEN_WIDTH/1080),(100*Constants.SCREEN_HEIGHT/1920), true),(322*Constants.SCREEN_WIDTH/1080),(1540*Constants.SCREEN_HEIGHT/1920), null);
            woodcutter.draw(canvas);
        if(Constants.IsDead){ //todo tu bedzie else do zrestartowania gry
            start=false;
            woodcutter.smierc();
            for(int i = 0;i<arrSicks.size(); i++){
                arrSicks.get(i).draw(canvas);
            }
            woodcutter.draw(canvas);

        }
        handler.postDelayed(runnable, 10);
    }

    private void EdoTensei(){

        //przesuwanie drzewa w dol
        for(int i = 0;i<arrSicks.size(); i++) { arrSicks.get(i).setY(arrSicks.get(i).getY() + (240*Constants.SCREEN_HEIGHT/1920)); }

        //usuwanie dolngo elementu
        arrSicks.remove(0);

        //dodawanie elementu na gore

        los = new Random().nextInt(2);

        if (los == 0) {
            if (!changeSide) {
                this.arrSicks.add(new Stick((-36*Constants.SCREEN_WIDTH/1080), arrSicks.get(arrSicks.size()-1).getY() - (240*Constants.SCREEN_HEIGHT/1920), ((395+395)*Constants.SCREEN_WIDTH/1080), (240*Constants.SCREEN_HEIGHT/1920)));
                arrSicks.get(arrSicks.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.lewo));
                arrSicks.get(arrSicks.size()-1).setStrona(Stick.Strona.LEWO);
            }
            else{
                this.arrSicks.add(new Stick((357*Constants.SCREEN_WIDTH/1080),arrSicks.get(arrSicks.size()-1).getY() - (240*Constants.SCREEN_HEIGHT/1920),(395*Constants.SCREEN_WIDTH/1080),(240*Constants.SCREEN_HEIGHT/1920)));
                arrSicks.get(arrSicks.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.srodek));
                arrSicks.get(arrSicks.size()-1).setStrona(Stick.Strona.SRODEK);
                changeSide=false;
            }
        }
        else {
            if (changeSide) {
                this.arrSicks.add(new Stick((357*Constants.SCREEN_WIDTH/1080),arrSicks.get(arrSicks.size() - 1).getY() - (240*Constants.SCREEN_HEIGHT/1920),((395+395)*Constants.SCREEN_WIDTH/1080),(240*Constants.SCREEN_HEIGHT/1920)));
                arrSicks.get(arrSicks.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.prawo));
                arrSicks.get(arrSicks.size()-1).setStrona(Stick.Strona.PRAWO);
            }
            else{
                this.arrSicks.add(new Stick((357*Constants.SCREEN_WIDTH/1080),arrSicks.get(arrSicks.size()-1).getY() - (240*Constants.SCREEN_HEIGHT/1920),(395*Constants.SCREEN_WIDTH/1080),(240*Constants.SCREEN_HEIGHT/1920)));
                arrSicks.get(arrSicks.size()-1).setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.srodek));
                arrSicks.get(arrSicks.size()-1).setStrona(Stick.Strona.SRODEK);
                changeSide=true;
            }
        }

    }

    @Override

    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if (start) {
                if(pbstart=true){
                    if(progressCounter<90){
                        progressCounter=progressCounter+10;
                    }
                    else{
                        progressCounter=100;
                    }
                }
                float xValue = event.getX();


                if (xValue <= leftPersentage) {
                    Constants.click=1;
                    Constants.clickL++;
                    woodcutter.onClick(1);
                    if (arrSicks.get(0).getStrona() == Stick.Strona.LEWO) {
                        Log.d("OnTouchEventDead-Left", "Gameover - Leftside");
                        if(woodcutter.getAktualnaStrona() == Woodcutter.AktualnaStrona.LEWO) {
                            //TODO stawianie nagrobka - probowalem ale nie orietuje sie w tym jak jest jakas tablica do przekazania no kurwa nie dziala(podmienienie woodcuter-drawble na nagrobek)
                            Constants.IsDead = true;
                            start = false;
                        }
                    }
                    else if (arrSicks.get(1).getStrona() == Stick.Strona.LEWO) {
                        if(woodcutter.getAktualnaStrona() == Woodcutter.AktualnaStrona.LEWO) {
                            EdoTensei();
                            Log.d("OnTouchEventDead-Left", "Gameover - Leftside");
                            //TODO stawianie nagrobka - probowalem ale nie orietuje sie w tym jak jest jakas tablica do przekazania no kurwa nie dziala(podmienienie woodcuter-drawble na nagrobek)
                            Constants.IsDead = true;
                            start = false;
                        }
                    }
                    else {
                        Constants.score++;
                        EdoTensei();
                    }
                }
                else {
                    Constants.click=2;
                    Constants.clickR++;
                    woodcutter.onClick(2);
                    if (arrSicks.get(0).getStrona() == Stick.Strona.PRAWO) {
                        if(woodcutter.getAktualnaStrona()== Woodcutter.AktualnaStrona.PRAWO) {
                            Log.d("OnTouchEventDead-Right", "Gameover - Rightside");
                            //TODO stawianie nagrobka - probowalem ale nie orietuje sie w tym jak jest jakas tablica do przekazania no kurwa nie dziala(podmienienie woodcuter-drawble na nagrobek)
                            Constants.IsDead = true;
                            start = false;
                        }
                    }
                    else if (arrSicks.get(1).getStrona() == Stick.Strona.PRAWO) {
                        if(woodcutter.getAktualnaStrona()== Woodcutter.AktualnaStrona.PRAWO) {
                            EdoTensei();
                            Log.d("OnTouchEventDead-Right", "Gameover - Rightside");
                            //TODO stawianie nagrobka - probowalem ale nie orietuje sie w tym jak jest jakas tablica do przekazania no kurwa nie dziala(podmienienie woodcuter-drawble na nagrobek) tu na dole jest to co probwale ja :D
                            woodcutter.setBm(BitmapFactory.decodeResource(this.getResources(), R.drawable.nagrobek));
                            Constants.IsDead = true;
                            start = false;
                        }
                    }
                    else {
                        Constants.score++;
                        EdoTensei();
                    }
                }
            }
        }
        return true;
    }
    public void setMediapleyer(MediaPlayer mainDeadsound){
        deadsound=mainDeadsound;
    }
    public void playDeadSound(){
        deadsound.start();
    }

    public void setPleyer(MediaPlayer mainDeadsound){ gamemusick=mainDeadsound; }
    public void playMusick(){
        gamemusick.start();
    }
    public void stopMusick(){ gamemusick.stop(); }

    public void setStart(boolean start) {
        this.start = start;
    }
    public boolean getStart() {
       return this.start;
    }
}
