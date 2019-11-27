package com.softnology.experimentproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private  Animation fadeOut;
    private  TextView textViewAsyncTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewAsyncTask = findViewById(R.id.TextView_AsyncTask);

        getLoaderManager().initLoader(1,null,new MyLoaderCallBack());

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1000);

        fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(1000);

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
        textViewAsyncTask.setAnimation(animation);

        textViewAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet animation = new AnimationSet(false); //change to false
                animation.addAnimation(fadeOut);
                textViewAsyncTask.setAnimation(animation);
                Intent intent = new Intent(getApplicationContext(), com.softnology.experimentproject.AsyncTaskExperiment.class);
                startActivity(intent);
            }
        });
    }


    //Async Task
    private class MyTask extends AsyncTask <String , Void , String>{

        @Override
        protected String doInBackground(String... strings) {

            return null;
        }


        protected void onProgressUpdate(Integer... integers) {
        // receive progress updates from doInBackground
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
        }
    }


    //Async Loader
    private class MyLoaderCallBack implements LoaderManager.LoaderCallbacks {

        @Override
        public Loader onCreateLoader(int id, Bundle args) {
            return new MyLoader(MainActivity.this);
        }

        @Override
        public void onLoadFinished(Loader loader, Object data) {

        }

        @Override
        public void onLoaderReset(Loader loader) {

        }
    }

    private static class MyLoader extends AsyncTaskLoader {

        MyLoader(@NonNull Context context) {
            super(context);
        }

        @Nullable
        @Override
        public Object loadInBackground() {
            return null;
        }
    }

    public void animation(){

    }
}
