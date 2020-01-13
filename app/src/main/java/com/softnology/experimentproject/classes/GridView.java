package com.softnology.experimentproject.classes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.softnology.experimentproject.R;
import com.softnology.experimentproject.adapter.MultiViewTypeAdapter;
import com.softnology.experimentproject.model.MultiViewModel;

import java.util.ArrayList;

public class GridView extends AppCompatActivity {
    ArrayList<MultiViewModel> gridViewModelArrayList;
    String[] activity_names;
    int[] icons;
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view);
        activity_names = new String[]{"The Sun", "The Moon","The Earth","River","Mountains","Snow","Forest","Desert","Fields","Cities","AnyThing"};
        icons = new int[]{R.drawable.sun,R.drawable.ic_andorid,R.drawable.sun,R.drawable.ic_ready,R.drawable.ic_done,R.drawable.sun,R.drawable.ic_andorid,R.drawable.sun,R.drawable.ic_ready,R.drawable.ic_done,R.drawable.ic_andorid};

        //activity_names.add(new MultiViewModel(1,"The Moon"))
        gridViewModelArrayList = new ArrayList<>();

        prepareData();

        MultiViewTypeAdapter adapter = new MultiViewTypeAdapter(gridViewModelArrayList,this);
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        mRecyclerView = findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager lm =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(adapter);
    }

    void prepareData()
    {
        MultiViewModel gridViewModel=null;
        for (int i=0;i<activity_names.length;i++)
        {
            if (i==6)
            {
                gridViewModel = new MultiViewModel(MultiViewModel.TYPE_BANNER,"",R.drawable.banner);
                gridViewModelArrayList.add(gridViewModel);
                gridViewModel = new MultiViewModel(MultiViewModel.TYPE_IMAGE_WITH_TEXT, activity_names[i], icons[i]);
                gridViewModelArrayList.add(gridViewModel);
            }
            else {
                gridViewModel = new MultiViewModel(MultiViewModel.TYPE_IMAGE_WITH_TEXT, activity_names[i], icons[i]);
                gridViewModelArrayList.add(gridViewModel);
            }
        }
    }
}
