package com.softnology.experimentproject.classes;

import androidx.recyclerview.widget.GridLayoutManager;

public class GridSpanSizeLookUp extends GridLayoutManager.SpanSizeLookup {
    int[] spanSize = new int[]{1, 4, 1, 4, 1, 1, 1, 1, 1, 1, 2, 2, 2, 3, 1, 4, 4, 1, 2, 2, 1, 3, 4, 3, 2, 3, 1};

    @Override
    public int getSpanSize(int position) {
        return spanSize[position % spanSize.length];
    }

}
