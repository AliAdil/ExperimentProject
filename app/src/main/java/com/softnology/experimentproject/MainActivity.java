package com.softnology.experimentproject;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.print.PrintHelper;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.softnology.experimentproject.classes.GridView;

import java.util.Objects;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    public static final String EXTRA_MESSAGES = "com.example.android.twoactivities.extra.MESSAGE";
    private Animation fadeOut;
    private TextView textViewAsyncTask;
    private Snackbar mySnackBar;
    private Toast toast = null;
    private AppCompatEditText edit_Message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewAsyncTask = findViewById(R.id.TextView_AsyncTask);
        Button btnPrint = findViewById(R.id.btn_print);
        Button btnEmail = findViewById(R.id.btn_email);
        Button btnSettings = findViewById(R.id.btn_settings);
        edit_Message = findViewById(R.id.edit_message);
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
              /*  sendEmailReceipt();*/
                Intent intent = new Intent(MainActivity.this, GridView.class);
                startActivity(intent);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public  boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_settings){
            // Launch settings activity
            startActivity( new Intent(MainActivity.this,SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
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
        Toast.makeText(getApplicationContext(),"onStart "+MainActivity.class.getSimpleName(),Toast.LENGTH_LONG).show();
        super.onStart();
    }

    @Override
    protected void onRestart() {
        setMySnackBar("onRestart");
        Toast.makeText(getApplicationContext(),"onRestart "+MainActivity.class.getSimpleName(),Toast.LENGTH_LONG).show();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        setMySnackBar("onResume");
        Toast.makeText(getApplicationContext(),"onResume "+MainActivity.class.getSimpleName(),Toast.LENGTH_LONG).show();
        super.onResume();
    }

    @Override
    protected void onPause() {
        setMySnackBar("onPause");
        Toast.makeText(getApplicationContext(),"onPause "+MainActivity.class.getSimpleName(),Toast.LENGTH_LONG).show();
        super.onPause();
    }

    @Override
    protected void onStop() {
        setMySnackBar("onStop");
        Toast.makeText(getApplicationContext(),"onStop "+MainActivity.class.getSimpleName(),Toast.LENGTH_LONG).show();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        setMySnackBar("onDestroy");
        Toast.makeText(getApplicationContext(),"onDestroy "+MainActivity.class.getSimpleName(),Toast.LENGTH_LONG).show();
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

    public void gotoNextActivity(View view) {
        Log.d(LOG_TAG,"Button Clicked!");
        Intent intent = new Intent(this,AsyncTaskExperiment.class);
        String message = Objects.requireNonNull(edit_Message.getText()).toString();
        intent.putExtra(EXTRA_MESSAGES,message);
        startActivity(intent);
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
