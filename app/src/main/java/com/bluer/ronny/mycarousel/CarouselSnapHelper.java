package com.bluer.ronny.mycarousel;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;

import com.bluer.ronny.mycarousel.carousellayoutmanager.CarouselLayoutManager;

/**
 * Created by ronny on 16-10-23.
 */
public class CarouselSnapHelper extends LinearSnapHelper {
    private Context mContext;
    private static final float INVALID_DISTANCE = 1f;

    public CarouselSnapHelper(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull View targetView) {
        int[] out = new int[2];
        CarouselLayoutManager carouselLayoutManager = (CarouselLayoutManager) layoutManager;
        out[0] = -carouselLayoutManager.getOffsetForCurrentView(targetView);
        return out;
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {

        if (!(layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
            return RecyclerView.NO_POSITION;
        }

        final int itemCount = layoutManager.getItemCount();
        if (itemCount == 0) {
            return RecyclerView.NO_POSITION;
        }
        CarouselLayoutManager carouselLayoutManager = (CarouselLayoutManager) layoutManager;

        final View currentView = findSnapView(carouselLayoutManager);
        if (currentView == null) {
            return RecyclerView.NO_POSITION;
        }

        final int currentPosition = carouselLayoutManager.getPosition(currentView);
        if (currentPosition == RecyclerView.NO_POSITION) {
            return RecyclerView.NO_POSITION;
        }

        RecyclerView.SmoothScroller.ScrollVectorProvider vectorProvider =
                (RecyclerView.SmoothScroller.ScrollVectorProvider) layoutManager;
        // deltaJumps sign comes from the velocity which may not match the order of children in
        // the LayoutManager. To overcome this, we ask for a vector from the LayoutManager to
        // get the direction.
        PointF vectorForEnd = vectorProvider.computeScrollVectorForPosition(itemCount - 1);
        if (vectorForEnd == null) {
            // cannot get a vector for the given position.
            return RecyclerView.NO_POSITION;
        }

        int vDeltaJump = 0, hDeltaJump;
        if (carouselLayoutManager.canScrollHorizontally()) {
            hDeltaJump = estimateNextPositionDiffForFling(carouselLayoutManager, velocityX, 0);
            if (vectorForEnd.x < 0) {
                hDeltaJump = -hDeltaJump;
            }
        } else {
            hDeltaJump = 0;
        }

        int deltaJump = carouselLayoutManager.canScrollVertically() ? vDeltaJump : hDeltaJump;
        if (deltaJump == 0) {
            return RecyclerView.NO_POSITION;
        }

        int targetPos = currentPosition + deltaJump;
        if (targetPos < 0) {
            targetPos = 0;
        }
        if (targetPos >= itemCount) {
            targetPos = itemCount - 1;
        }
        return targetPos;
    }

    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        CarouselLayoutManager carouselLayoutManager = (CarouselLayoutManager) layoutManager;
        int center = carouselLayoutManager.getCenterItemPosition();
        return carouselLayoutManager.getViewForPosition(center);
    }

    @Override
    public boolean onFling(int velocityX, int velocityY) {
        return super.onFling(velocityX, velocityY);
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) throws IllegalStateException {
        super.attachToRecyclerView(recyclerView);
    }

    @Override
    public int[] calculateScrollDistance(int velocityX, int velocityY) {
        int[] out = new int[2];
        out = super.calculateScrollDistance(velocityX, velocityY);

        return out;
    }

    private int estimateNextPositionDiffForFling(CarouselLayoutManager layoutManager, int velocityX, int velocityY) {
        int[] distances = calculateScrollDistance(velocityX, velocityY);
        float distancePerChild = layoutManager.getScrollItemSize();
        if (distancePerChild <= 0) {
            return 0;
        }
        int distance =
                Math.abs(distances[0]) > Math.abs(distances[1]) ? distances[0] : distances[1];
        return (int) Math.floor(distance / distancePerChild);
    }

}
