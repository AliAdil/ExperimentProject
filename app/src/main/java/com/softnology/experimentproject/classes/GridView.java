package com.softnology.experimentproject.classes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.kingfisher.easyviewindicator.AnyViewIndicator;
import com.kingfisher.easyviewindicator.GridLayoutSnapHelper;
import com.softnology.experimentproject.R;
import com.softnology.experimentproject.adapter.MultiViewTypeAdapter;
import com.softnology.experimentproject.model.MultiViewModel;

import java.util.ArrayList;

public class GridView extends AppCompatActivity {
    ArrayList<MultiViewModel> gridViewModelArrayList;
    String[] activity_names;
    int[] icons;
    RecyclerView mRecyclerView;
    AnyViewIndicator anyViewIndicator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        anyViewIndicator = findViewById(R.id.anyViewIndicator2);

        activity_names = new String[]{"The Sun", "The Moon","The Earth","River","Mountains","Snow","Forest","Desert","Fields","Cities","AnyThing"};
        icons = new int[]{R.drawable.ic_ready,R.drawable.ic_andorid,R.drawable.ic_done,R.drawable.ic_ready,R.drawable.ic_done,R.drawable.ic_done,R.drawable.ic_andorid,R.drawable.ic_ready,R.drawable.ic_ready,R.drawable.ic_done,R.drawable.ic_andorid};

        //activity_names.add(new MultiViewModel(1,"The Moon"))
        gridViewModelArrayList = new ArrayList<>();

        prepareData();

        //MultiViewTypeAdapter adapter =
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        mRecyclerView = findViewById(R.id.recyclerView);
        /*PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);*/
        /*lm.setSpanSizeLookup(spanSizeLookup);*/
        // int dp16 = (int) (getResources().getDisplayMetrics().density *1);
        //  mRecyclerView.addItemDecoration(new GridSpanDecoration(100,GridView.this));
        //GridLayoutManager.SpanSizeLookup spanSizeLookup = new GridSpanSizeLookUp();
        anyViewIndicator.setItemCount(2);
        anyViewIndicator.setCurrentPosition(0);
        GridLayoutManager gl = new GridLayoutManager(GridView.this,3,RecyclerView.HORIZONTAL,false);
        // add pager behavior
        mRecyclerView.setLayoutManager(gl);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(new MultiViewTypeAdapter(this,gridViewModelArrayList));
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                        anyViewIndicator.setCurrentPosition((int) (Math.ceil(Double.valueOf(position)/ 6) - 1));
                        break;
                }
            }
        });
    }

    void prepareData()
    {
        MultiViewModel gridViewModel=null;
        for (int i=0;i<activity_names.length;i++)
        {
          /*  if (i==6)
            {
                gridViewModel = new MultiViewModel(MultiViewModel.TYPE_BANNER,"",R.drawable.banner);
                gridViewModelArrayList.add(gridViewModel);
                gridViewModel = new MultiViewModel(MultiViewModel.TYPE_IMAGE_WITH_TEXT, activity_names[i], icons[i]);
                gridViewModelArrayList.add(gridViewModel);
            }
            else {*/
                gridViewModel = new MultiViewModel(MultiViewModel.TYPE_IMAGE_WITH_TEXT, activity_names[i], icons[i]);
                gridViewModelArrayList.add(gridViewModel);
            /*}*/
        }
    }
}
