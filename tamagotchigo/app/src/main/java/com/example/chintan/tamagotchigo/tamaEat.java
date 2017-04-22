package com.example.chintan.tamagotchigo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by chintan24 on 7/29/2016.
 */
public class tamaEat {
    Context context;
    Canvas canvas;
    boolean flag= true;
    int loop;
    int counter;
    Bitmap[] foodCounter={Assets.food1,Assets.food2,Assets.food3,Assets.food4};//{"Assets.food1","Assets.food2","Assets.food3","Assets.food4"};
    public tamaEat(Context context, Canvas canvas) {
        this.context =context;
        this.canvas =canvas;
        loop=0;
        counter =0;

    }

    public void tamaEat() {
        if (flag) {
            if(counter==10){

                if (loop == 0) {loop=1;counter=0;}else{loop=0;counter=0;}
            }
            if(Assets.x-200<canvas.getWidth()) {
                if (loop == 0) {
                    System.out.println("Flag decode value of x " + Assets.x + " and flag " + flag + " Toggle");
                    canvas.drawBitmap(Assets.walk1, Assets.x, Assets.y, null);
                    canvas.drawBitmap(Assets.food1, Assets.x + 160 + Assets.food1.getWidth(), Assets.y, null);
                    if(Assets.foodCounter==5){
                        Assets.foodCounter=0;
                    }
                    counter++;

                } else {
                    canvas.drawBitmap(Assets.eat, Assets.x, Assets.y, null);
                    canvas.drawBitmap(Assets.food2, Assets.x + 160 + Assets.food1.getWidth(), Assets.y, null);
                    counter++;
                }
            }
        }else {
            flag = false;
        }
    }
}
