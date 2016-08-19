package com.edampe.pruebas.pruebaandroidgrability.ui.item;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by edamp on 8/14/2016.
 */
public class OffsetDecorationItem extends RecyclerView.ItemDecoration{


    private int mItemOffset;

    public OffsetDecorationItem(int itemOffset){
        mItemOffset = itemOffset;
    }

    public OffsetDecorationItem(@NonNull Context context, @IntegerRes int itemOffsetId){
        int itemOffsetDp = context.getResources().getInteger(itemOffsetId);
        mItemOffset = setupDimensions(context.getResources().getDisplayMetrics(), itemOffsetDp);
    }

    private int setupDimensions(DisplayMetrics metrics, int offsetDp) {
        return  offsetDp * (metrics.densityDpi / 160);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(mItemOffset,mItemOffset,mItemOffset,mItemOffset);
    }
}