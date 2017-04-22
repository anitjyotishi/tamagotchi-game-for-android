package com.example.chintan.tamagotchigo;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;


/**
 * Created by chintan24 on 7/26/2016.
 */
public class tama {
    Context context;
    int counter = 0,cntr=0;
    Canvas canvas;
    private Handler handler;
    boolean flag = true,toggale=true;

    public tama(Context context, Canvas canvas) {
        this.context = context;
        this.canvas = canvas;
        if(Assets.tstate==Assets.TamaState.Load){
            Assets.y=canvas.getHeight() - Assets.walk1.getHeight();
            Assets.tstate=Assets.TamaState.Move;

            //For managing the speed of tama part1
            float curTime = System.nanoTime() / 1000000000f;
            Assets.speed =(canvas.getWidth()/ 2);
            Assets.animateTimer = curTime;

        }else if(Assets.tstate==Assets.TamaState.Move){
            //For managing the speed of tama part2
            float curTime = System.nanoTime() / 1000000000f;
            float elapsedTime = curTime - Assets.animateTimer;
            Assets.animateTimer = curTime;

            //For moving tama in the horizontal way
            if(Assets.counter==0) {
                //Assets.x += (Assets.speed * elapsedTime);
                Assets.x+=10;
            }
            Assets.counter++;
            System.out.println("Counter decode value of x"+Assets.x+" Value of y "+Assets.y +"..Elasped time"+(Assets.speed * elapsedTime));
            // method calling for movement of tamagotchi
            tamaMove();
        }
    }

    public void tamaMove() {

        if (Assets.x >= canvas.getWidth()) {
            flag = false;
        }

        if (flag) {
            if (Assets.loop==0) {
                //Assets.loop=1;
                System.out.println("Flag decode value of x "+ Assets.x +" and flag "+flag+" Toggle  "+Assets.counter);
                canvas.drawBitmap(Assets.walk1, Assets.x, Assets.y, null);
                if(!Assets.tamaMove) {
                    if (Assets.counter == 4) {
                        Assets.counter = 0;
                        Assets.loop = 1;
                    }
                }else{
                    if (Assets.counter == 8) {
                    Assets.counter = 0;
                    Assets.loop = 1;
                }

                }
            } else {
                //Assets.loop=0;
                canvas.drawBitmap(Assets.wa1k2, Assets.x, Assets.y, null);
                if(!Assets.tamaMove) {
                    if (Assets.counter == 2) {
                        Assets.loop = 0;
                        Assets.counter = 0;
                    }
                }else{
                    if (Assets.counter == 8) {
                    Assets.loop = 0;
                    Assets.counter = 0;
                }
                }
            }
        }else {
            Assets.x=0;
            flag = false;
        }
    }



}
