package com.example.timberman;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Stick extends BaseObject{
    private Strona strona;

    public enum Strona {
        LEWO,
        PRAWO,
        SRODEK
    }


    public Stick(float x, float y, int width, int height) {
        super(x, y, width, height);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(this.getBm(),this.x,this.y,null);
    }

    @Override
    public void setBm(Bitmap Bm) {
      this.bm =  Bitmap.createScaledBitmap(Bm,this.width,this.height, true);
    }

    public Strona getStrona() {
        return strona;
    }

    public void setStrona(Strona strona) {
        this.strona = strona;
    }
}


