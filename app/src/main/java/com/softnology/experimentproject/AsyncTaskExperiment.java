package com.softnology.experimentproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AsyncTaskExperiment extends AppCompatActivity {

    private static final String TEXT_STATE = "currentText";
    private TextView mTextView = null;
    private TextView textViewProgress = null;
    private ProgressBar progressBar;
    Integer count =1;
    private Toast toast, toast2,toast3,toast4,toast5;
    private Context context;
    private  int duration;
    private ArrayList<CharSequence> charSequence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        mTextView = findViewById(R.id.textView1);
        textViewProgress = findViewById(R.id.TextView_Progress);
        progressBar = (ProgressBar) findViewById(R.id.Progressbar);
        Button showToast =  findViewById(R.id.btn_showToast);
        progressBar.setProgress(0);
        context = getApplicationContext();
        charSequence = new ArrayList<>();
        for (int i = 0 ; i < 5 ; i ++){
            charSequence.add("This is my Toast Test #"+(i+1));
        }

        duration = Toast.LENGTH_SHORT;

        showToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast = Toast.makeText(context,charSequence.get(0),duration);
                toast.setGravity(Gravity.TOP|Gravity.START,0,0);
                toast.show();

                toast2 = Toast.makeText(context,charSequence.get(1),duration) ;
                toast2.setGravity(Gravity.BOTTOM|Gravity.END,0,0);
                toast2.show();

                toast3 = Toast.makeText(context,charSequence.get(2),duration) ;
                toast3.setGravity(Gravity.CENTER|Gravity.START,0,600);
                toast3.show();

                toast4 = Toast.makeText(context,charSequence.get(3),duration) ;
                toast4.setGravity(Gravity.CENTER|Gravity.START,100,-100);
                toast4.show();

                toast4 = Toast.makeText(context,charSequence.get(4),duration) ;
                toast4.setGravity(Gravity.TOP|Gravity.START,500,300);
                toast4.show();


            }
        });




        if (savedInstanceState != null) {
            mTextView.setText(savedInstanceState.getString(TEXT_STATE));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXT_STATE, mTextView.getText().toString());
    }

    public void startTask(View view) {

        mTextView.setText(R.string.napping);
        count =1;
        Random r = new Random();
        int multiple = 1000;
        int number = r.nextInt(11);
        int miliSec = number * multiple;
        int milToSec = miliSec/1000;
        progressBar.setMax(milToSec);
        new SimpleAsyncTask(mTextView,textViewProgress,progressBar,AsyncTaskExperiment.this).execute(miliSec);


    }

    public static class SimpleAsyncTask extends AsyncTask<Integer, Integer, String> {
        private WeakReference<TextView> mTextView;
        private WeakReference<TextView> textViewProgress;
        private SoftReference<Context> context;
        private WeakReference<ProgressBar> _Bar;
        private int milToSec  = 0;
        float valueCalcu = 0;
        SimpleAsyncTask(TextView tv,TextView tv2,ProgressBar _progressBar ,Context _context) {
            mTextView = new WeakReference<>(tv);
            textViewProgress = new WeakReference<>(tv2);
            context = new SoftReference<>(_context);
            _Bar = new WeakReference<>(_progressBar);
        }


        @Override
        protected void onPreExecute(){
            textViewProgress.get().setText(R.string.task_starting);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            milToSec = integers[0]/1000;
            for (int count = 1 ; count <= milToSec ; count++) {
                try {
                    Thread.sleep(integers[0]);


                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
                publishProgress(count);
            }






            // Return a String result
            return "Awake at last after sleeping for " + integers[0] + " milliseconds! and total Sec are " + milToSec + "";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
           /* super.onProgressUpdate(values);
            textViewProgress.get().setText(Arrays.toString(values));*/
            if (this._Bar != null) {
                valueCalcu = ((float)values[0]/(float)milToSec)*100;
                textViewProgress.get().setText(valueCalcu +"%");
                _Bar.get().setProgress(values[0]);
            }
        }

        protected void onPostExecute(String result) {
            mTextView.get().setText(result);
            textViewProgress.get().setText("Task "+valueCalcu +"%"+" Completed");
        }


    }


}
