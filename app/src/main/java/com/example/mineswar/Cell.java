package com.example.mineswar;

import android.content.Context;

public class Cell extends androidx.appcompat.widget.AppCompatImageView {
    int state,i,j;
    boolean isShow,flag;


    public Cell(Context context) {
        super(context);
        isShow=false;
        flag=false;
    }

    public boolean changeFlag(){
        flag=!flag;

        return !flag;

    }


}
