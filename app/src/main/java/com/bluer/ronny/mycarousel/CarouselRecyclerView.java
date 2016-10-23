package com.bluer.ronny.mycarousel;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by ronny on 16-10-22.
 */
public class CarouselRecyclerView extends RecyclerView {
    public CarouselRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        return super.fling(velocityX, velocityY);
    }
}
