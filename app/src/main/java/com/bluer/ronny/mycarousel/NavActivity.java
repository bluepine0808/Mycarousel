package com.bluer.ronny.mycarousel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearSnapHelper;

import com.bluer.ronny.mycarousel.carousellayoutmanager.CarouselLayoutManager;
import com.bluer.ronny.mycarousel.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.bluer.ronny.mycarousel.carousellayoutmanager.CenterScrollListener;

public class NavActivity extends AppCompatActivity {

    private CarouselRecyclerView mCarouselRecyclerView;
    private CarouselAdapter mCarouselAdapter;
    private CarouselLayoutManager mCarouselLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);
        mCarouselRecyclerView = (CarouselRecyclerView) findViewById(R.id.recycler_view);

        mCarouselAdapter = new CarouselAdapter(this);
        mCarouselLayoutManager = new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false);
        mCarouselLayoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        mCarouselRecyclerView.setLayoutManager(mCarouselLayoutManager);
        mCarouselRecyclerView.setHasFixedSize(true);
        mCarouselRecyclerView.setAdapter(mCarouselAdapter);
        new CarouselSnapHelper(this).attachToRecyclerView(mCarouselRecyclerView);
//        mCarouselRecyclerView.addOnScrollListener(new CenterScrollListener());
    }
}
