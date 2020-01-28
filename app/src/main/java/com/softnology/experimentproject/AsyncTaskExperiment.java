package com.softnology.experimentproject;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import static com.softnology.experimentproject.MainActivity.EXTRA_MESSAGES;

public class AsyncTaskExperiment extends AppCompatActivity {

    private static final String TEXT_STATE = "currentText";
    Integer count = 1;
    private TextView mTextView = null;
    private TextView textViewProgress = null;
    private TextView toastText;
    private ProgressBar progressBar;
    private Toast toast;
    private Context context;
    private int duration;
    private ArrayList<CharSequence> charSequence;
    private  SimpleAsyncTask simpleAsyncTask;
    private View linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        mTextView = findViewById(R.id.textView1);
        textViewProgress = findViewById(R.id.TextView_Progress);
        progressBar = findViewById(R.id.Progressbar);
        Button showToast = findViewById(R.id.btn_showToast);
        TextView textHeader = findViewById(R.id.text_header);
        progressBar.setProgress(0);
        Intent intent = getIntent();
        String message =  intent.getStringExtra(EXTRA_MESSAGES);
        textHeader.setText(message);
        // Inflating view
        LayoutInflater inflater = getLayoutInflater();
        linearLayout = inflater.inflate(R.layout.custom_toast_layout,(ViewGroup)findViewById(R.id.custom_toast_container));
        toastText = linearLayout.findViewById(R.id.text);
        toastText.setText(R.string.custom_toast_text);

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
                toast = Toast.makeText(context,"", duration);
                toastText.setText(charSequence.get(0));
                toast.setGravity(Gravity.TOP | Gravity.START, 0, 0);
                toast.setView(linearLayout);
                toast.show();

                toast = Toast.makeText(context, charSequence.get(1), duration);
                toast.setGravity(Gravity.BOTTOM | Gravity.END, 0, 0);
                toast.show();

                toast = Toast.makeText(context, "", duration);
                toastText.setText(charSequence.get(2));
                toast.setGravity(Gravity.CENTER | Gravity.START, 0, 600);
                toast.setView(linearLayout);
                toast.show();

                toast = Toast.makeText(context, charSequence.get(3), duration);
                toast.setGravity(Gravity.CENTER | Gravity.START, 100, -100);
                toast.show();

                toast = Toast.makeText(context, "", duration);
                toastText.setText(charSequence.get(4));
                toast.setGravity(Gravity.TOP | Gravity.START, 500, 300);
                toast.setView(linearLayout);
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
        mTextView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sleeping,0);
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
        simpleAsyncTask = new SimpleAsyncTask(mTextView, textViewProgress, progressBar, AsyncTaskExperiment.this);
        simpleAsyncTask.execute(miliSec);

    }

    // Ending Async Task
    public void EndTask(View view) {
       try {
           if(simpleAsyncTask != null)
           simpleAsyncTask.cancel(true);

           mTextView.setText(R.string.i_am_ready_to_start_work);
           mTextView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_ready,0);
           textViewProgress.setText(R.string.startTask);
           progressBar.setMax(0);
       }
       catch (Exception e){
           toast = Toast.makeText(context, "", duration);
           toastText.setText(e.toString());
           toast.setView(linearLayout);
           toast.setGravity(Gravity.CENTER , 0, 0);
           toast.show();

       }

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
                textViewProgress.get().setText(String.format("%s%%", valueCalcu));
                _Bar.get().setProgress(values[0]);
            }
        }

        protected void onPostExecute(String result) {
            mTextView.get().setText(result);
            mTextView.get().setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_done,0);
            textViewProgress.get().setText(String.format("Task %s%% Completed", valueCalcu));
        }


    }

// Finishing activity on Toolbar back button press
    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }

    @Override
    protected void onStart() {

        Toast.makeText(getApplicationContext(),"onStart "+AsyncTaskExperiment.class.getSimpleName(),Toast.LENGTH_LONG).show();
        super.onStart();
    }

    @Override
    protected void onRestart() {

        Toast.makeText(getApplicationContext(),"onRestart "+AsyncTaskExperiment.class.getSimpleName(),Toast.LENGTH_LONG).show();
        super.onRestart();
    }

    @Override
    protected void onResume() {

        Toast.makeText(getApplicationContext(),"onResume "+AsyncTaskExperiment.class.getSimpleName(),Toast.LENGTH_LONG).show();
        super.onResume();
    }

    @Override
    protected void onPause() {

        Toast.makeText(getApplicationContext(),"onPause "+AsyncTaskExperiment.class.getSimpleName(),Toast.LENGTH_LONG).show();
        super.onPause();
    }

    @Override
    protected void onStop() {

        Toast.makeText(getApplicationContext(),"onStop "+AsyncTaskExperiment.class.getSimpleName(),Toast.LENGTH_LONG).show();
        super.onStop();
    }

    @Override
    protected void onDestroy() {

        Toast.makeText(getApplicationContext(),"onDestroy "+AsyncTaskExperiment.class.getSimpleName(),Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}
