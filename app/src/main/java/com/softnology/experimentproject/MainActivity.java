package com.softnology.experimentproject;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;

import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private Animation fadeOut;
    private TextView textViewAsyncTask;
    private Snackbar mySnackBar;
    private Toast toast = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewAsyncTask = findViewById(R.id.TextView_AsyncTask);
        Button btnPrint = findViewById(R.id.btn_print);
        Button btnEmail = findViewById(R.id.btn_email);
        Button btnSettings = findViewById(R.id.btn_settings);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        /* getLoaderManager().initLoader(1, null, new MyLoaderCallBack());*/
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(3000);
        fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
        fadeOut.setStartOffset(0);
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
        setMySnackBar("onCreate");

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doBitmapPrint();
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmailReceipt();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);*/
            }
        });

    }
    // Finishing activity on Toolbar back button press
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
    /*  Activity Life Cycle State*/

    @Override
    protected void onStart() {
        setMySnackBar("onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        setMySnackBar("onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        setMySnackBar("onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        setMySnackBar("onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        setMySnackBar("onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        setMySnackBar("onDestroy");
        super.onDestroy();
    }
    /*  End of Activity Life Cycle State Methods*/

    //Snack bar
    public void setMySnackBar(final String activityState) {
        //Snack Bar work
        mySnackBar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), "Hey there! welcome to Garage of experimentation Reporting from " + activityState, Snackbar.LENGTH_SHORT);
        mySnackBar.setAction("click me!", new snackBarClickListener());
        mySnackBar.setDuration(3000);
        mySnackBar.show();
        mySnackBar.addCallback(new Snackbar.Callback() {
            @Override
            public void onShown(Snackbar snackbar) {
                toast = Toast.makeText(getApplicationContext(), "SnackBar Showed " + activityState, Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.TOP | Gravity.END, 0, 200);
                toast.show();
            }

            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                //see Snackbar.Callback docs for event details
                if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                    // Snackbar closed on its own
                    toast = Toast.makeText(getApplicationContext(), "SnackBar Dismissed " + activityState, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP | Gravity.START, 0, 200);
                    toast.show();
                }
            }


        });

    }

    //Snack bar click listener
    public class snackBarClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), AsyncTaskExperiment.class);
            startActivity(intent);
        }
    }

    void doBitmapPrint(){
        PrintHelper bitmapPrinter = new PrintHelper(MainActivity.this);
        bitmapPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        Bitmap bitmapImage = BitmapFactory.decodeResource(getResources(),R.drawable.ic_andorid);
        bitmapPrinter.printBitmap("Android Logo icon",bitmapImage);
    }

    void sendEmailReceipt(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Paysa Donation Receipt");
        i.putExtra(Intent.EXTRA_TEXT   , "You send ABC gift card to Xyz.");
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    /*private static class MyLoader extends AsyncTaskLoader {

        MyLoader(@NonNull Context context) {
            super(context);
        }

        @Nullable
        @Override
        public Object loadInBackground() {
            return null;
        }
    }*/
  /*  //Async Task
    private class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            return null;
        }


        protected void onProgressUpdate(Integer... integers) {
            // receive progress updates from doInBackground
        }

        @Override
        protected void onPostExecute(String result) {
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
    }*/


}
