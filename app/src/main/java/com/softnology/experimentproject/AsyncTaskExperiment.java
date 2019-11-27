package com.softnology.experimentproject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Random;

public class AsyncTaskExperiment extends AppCompatActivity {

    private static final String TEXT_STATE = "currentText";
    Integer count = 1;
    private TextView mTextView = null;
    private TextView textViewProgress = null;
    private ProgressBar progressBar;
    private Toast toast;
    private Context context;
    private int duration;
    private ArrayList<CharSequence> charSequence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        mTextView = findViewById(R.id.textView1);
        textViewProgress = findViewById(R.id.TextView_Progress);
        progressBar = findViewById(R.id.Progressbar);
        Button showToast = findViewById(R.id.btn_showToast);
        progressBar.setProgress(0);

        //Application context
        context = getApplicationContext();

        // Char Sequence Array For Toast Text
        charSequence = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            charSequence.add("This is my Toast Test #" + (i + 1));
        }

        // Duration of toast
        duration = Toast.LENGTH_SHORT;

        // Show Toast Click Listener
        showToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toast = Toast.makeText(context, charSequence.get(0), duration);
                toast.setGravity(Gravity.TOP | Gravity.START, 0, 0);
                toast.show();

                toast = Toast.makeText(context, charSequence.get(1), duration);
                toast.setGravity(Gravity.BOTTOM | Gravity.END, 0, 0);
                toast.show();

                toast = Toast.makeText(context, charSequence.get(2), duration);
                toast.setGravity(Gravity.CENTER | Gravity.START, 0, 600);
                toast.show();

                toast = Toast.makeText(context, charSequence.get(3), duration);
                toast.setGravity(Gravity.CENTER | Gravity.START, 100, -100);
                toast.show();

                toast = Toast.makeText(context, charSequence.get(4), duration);
                toast.setGravity(Gravity.TOP | Gravity.START, 500, 300);
                toast.show();


            }
        });


        // getting text from saveInstanceState
        if (savedInstanceState != null) {
            mTextView.setText(savedInstanceState.getString(TEXT_STATE));
        }
    }

    // Saving putting text in bundle so that i can get my text when screen is rotated
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TEXT_STATE, mTextView.getText().toString());
    }

    // Method is attached with start task button to start async task
    public void startTask(View view) {
        mTextView.setText(R.string.napping);
        count = 1;
        Random r = new Random();
        int multiple = 1000;
        int number = r.nextInt(11);
        int miliSec = number * multiple;
        // converting milliseconds to seconds
        int milToSec = miliSec / 1000;
        // set progress max to total seconds
        progressBar.setMax(milToSec);
        // executing async task
        new SimpleAsyncTask(mTextView, textViewProgress, progressBar, AsyncTaskExperiment.this).execute(miliSec);


    }

    // Simple Async Task with progress bar
    // AsyncTask is an Abstract Helper class
    public static class SimpleAsyncTask extends AsyncTask<Integer, Integer, String> {
        float valueCalcu = 0;
        private WeakReference<TextView> mTextView;
        private WeakReference<TextView> textViewProgress;
        private WeakReference<ProgressBar> _Bar;
        private int milToSec = 0;

        SimpleAsyncTask(TextView tv, TextView tv2, ProgressBar _progressBar, Context _context) {
            mTextView = new WeakReference<>(tv);
            textViewProgress = new WeakReference<>(tv2);
            _Bar = new WeakReference<>(_progressBar);
        }


        @Override
        protected void onPreExecute() {
            textViewProgress.get().setText(R.string.task_starting);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            // milliseconds to seconds
            milToSec = integers[0] / 1000;

            for (int count = 1; count <= milToSec; count++) {
                try {
                    Thread.sleep(integers[0]);


                } catch (InterruptedException e) {
                    e.printStackTrace();

                }
                //calling progress update
                publishProgress(count);
            }


            // Return a String result
            return "Awake at last after sleeping for " + integers[0] + " milliseconds! and total Sec are " + milToSec + "";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            if (this._Bar != null) {
                // Converting
                valueCalcu = ((float) values[0] / (float) milToSec) * 100;
                textViewProgress.get().setText(valueCalcu + "%");
                _Bar.get().setProgress(values[0]);
            }
        }

        protected void onPostExecute(String result) {
            mTextView.get().setText(result);
            textViewProgress.get().setText("Task " + valueCalcu + "%" + " Completed");
        }


    }


}