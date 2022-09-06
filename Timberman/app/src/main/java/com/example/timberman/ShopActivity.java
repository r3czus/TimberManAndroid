package com.example.timberman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ShopActivity extends AppCompatActivity {
    public ImageButton Avatar1, btn_shop_back,btn_next,btn_previous;
    public ImageView Avatar2,Avatar3,Avatar4,woodercutter1,popcat2,omniman3,amongus4;
    MediaPlayer beepsound;

    MediaPlayer shopsound;
    public int avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        shopsound = MediaPlayer.create(this, R.raw.shopsong);
        shopsound.setLooping(true);
        shopsound.seekTo(0);
        shopsound.setVolume(0.4f, 0.4f);
        shopsound.start();


       // Avatar1 = findViewById(R.id.Avatar1);

        if(Constants.bestScore>10){
          //  Avatar1.setImageResource(R.drawable.shop);
        }
        btn_shop_back=findViewById(R.id.btn_shop_back);
        btn_next=findViewById(R.id.btn_next);
        btn_previous=findViewById(R.id.btn_previous);
        woodercutter1=findViewById(R.id.woodercutter1);
        popcat2=findViewById(R.id.popcat2);
        omniman3=findViewById(R.id.omniman3);
        amongus4=findViewById(R.id.amognus4);
        avatar=Constants.Avatar;
        WhoChose(avatar);
         beepsound.start();


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avatar++;
                if(avatar>3)
                    avatar=0;
                WhoChose(avatar);
                beepsound.start();
            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avatar--;
                if(avatar==-1)
                    avatar=3;
                WhoChose(avatar);
                beepsound.start();
            }
        });



        btn_shop_back.setOnClickListener(view -> {
            Constants.Restart=false;
            Intent intent= new Intent(ShopActivity.this,MainActivity.class);
            Constants.Avatar=avatar;
            shopsound.stop();
            startActivity(intent);
        });
//        Avatar1.setOnClickListener(v -> {
//            if(Constants.bestScore>10){
//               Constants.Avatar=1;
//            }
//        });

    }
    void WhoChose(int avatar)
    {
        if(beepsound!=null){ beepsound.stop();}
        if(avatar==0) {
            omniman3.setVisibility(View.INVISIBLE);
            woodercutter1.setVisibility(View.VISIBLE);
            popcat2.setVisibility(View.INVISIBLE);
            amongus4.setVisibility(View.INVISIBLE);
            beepsound=MediaPlayer.create(this,R.raw.bumpsound);
            beepsound.setVolume(0.8f, 0.8f);
        }

        else if (avatar==1) {
            woodercutter1.setVisibility(View.INVISIBLE);
            popcat2.setVisibility(View.VISIBLE);
            omniman3.setVisibility(View.INVISIBLE);
            amongus4.setVisibility(View.INVISIBLE);
            beepsound=MediaPlayer.create(this,R.raw.popcatsound);

            beepsound.setVolume(0.5f, 0.5f);

            beepsound.start();
        }
        else if (avatar==2) {
              popcat2.setVisibility(View.INVISIBLE);
              omniman3.setVisibility(View.VISIBLE);
            amongus4.setVisibility(View.INVISIBLE);
            woodercutter1.setVisibility(View.INVISIBLE);
            beepsound=MediaPlayer.create(this,R.raw.omnimansound);
            beepsound.setVolume(0.5f, 0.5f);

        }
        else if (avatar==3) {
            amongus4.setVisibility(View.VISIBLE);
            popcat2.setVisibility(View.INVISIBLE);
            woodercutter1.setVisibility(View.INVISIBLE);
            omniman3.setVisibility(View.INVISIBLE);
            beepsound=MediaPlayer.create(this,R.raw.umongussound);

            beepsound.setVolume(0.5f, 0.5f);
        }
    }
}
