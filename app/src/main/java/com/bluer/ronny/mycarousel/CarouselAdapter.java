package com.bluer.ronny.mycarousel;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ronny on 16-10-23.
 */
public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {
    private  Context mContext;

    private List<Integer> mList = new ArrayList<>();
    public CarouselAdapter(Context context) {
        mContext = context;
        for (int i = 0; i < 5; i++) {
            mList.add(R.drawable.pic1);
            mList.add(R.drawable.pic2);
            mList.add(R.drawable.pic3);
        }
    }

    @Override
    public CarouselViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_item, parent, false);

        return new CarouselViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CarouselViewHolder holder, final int position) {
        holder.mImageView.setImageResource(mList.get(position));
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "you click position is =" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CarouselViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public CarouselViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
        }
    }
}
