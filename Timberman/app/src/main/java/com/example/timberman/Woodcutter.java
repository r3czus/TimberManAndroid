package com.example.timberman;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;

//drwal
public class Woodcutter extends BaseObject {
    private ArrayList<Bitmap> arrBms = new ArrayList<>();
    private int count, vHit, idCurrentBitmap;
    final Handler handler = new Handler(Looper.getMainLooper());
    public AktualnaStrona aktualnaStrona;

    public enum AktualnaStrona {
        LEWO,
        PRAWO
    }

    public AktualnaStrona getAktualnaStrona() {
        return aktualnaStrona;
    }

    public void setStrona(AktualnaStrona aktualnaStrona) {
        this.aktualnaStrona = aktualnaStrona;
    }

    public Woodcutter(){
    this.count = 0;
    this.vHit = 5;
    this.idCurrentBitmap = 0;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(this.getBm(),this.x, this.y, null);
    }

    public ArrayList<Bitmap> getArrBms() {
        return arrBms;
    }

    public void setArrBms(ArrayList<Bitmap> arrBms) {
        this.arrBms = arrBms;
        for (int i = 0; i<arrBms.size(); i++){
            this.arrBms.set(i,Bitmap.createScaledBitmap(this.arrBms.get(i),this.width,this.height, true));
        }
    }

    public void onClick(int strona){
        if(strona==1){
            this.x = 170*Constants.SCREEN_WIDTH/1080;

            idCurrentBitmap = 0;
            handler.postDelayed(() -> idCurrentBitmap = 1,100);
            idCurrentBitmap = 2;
            this.aktualnaStrona = AktualnaStrona.LEWO;
        }
        else if(strona==2){
            this.x = 700*Constants.SCREEN_WIDTH/1080;

            idCurrentBitmap = 3;
            handler.postDelayed(() -> idCurrentBitmap = 4,100);
            idCurrentBitmap = 5;
            this.aktualnaStrona = AktualnaStrona.PRAWO;
        }
    }

    public void smierc(){
        idCurrentBitmap=6;
    }

    @Override
    public Bitmap getBm() {
        return this.getArrBms().get(idCurrentBitmap);
    }
}
