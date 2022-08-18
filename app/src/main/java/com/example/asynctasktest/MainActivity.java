package com.example.asynctasktest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    Button start, cancel;
    ProgressBar progress;
    int value;
    BackgroundTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.tv);
        progress = findViewById(R.id.progress);
        start = findViewById(R.id.start);
        cancel = findViewById(R.id.cnacel);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task = new BackgroundTask();
                task.execute(100);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                task.cancel(true);
            }
        });
    }

    class BackgroundTask extends AsyncTask<Integer, Integer, Integer>{
        @Override
        protected void onPreExecute() {
            value = 0;
            progress.setProgress(value);
            Log.e("msg", "비동기 작업을 시작합니다");
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            Log.e("msg", "integers : " + integers[0].toString());
            while (isCancelled() == false){

                value++;
                if(value >= 100){
                    break;
                } else {
                    publishProgress(value);
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return  value;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progress.setProgress(values[0].intValue());
            tv.setText("현재 진행 값 : " + values[0].toString());
        }

        @Override
        protected void onPostExecute(Integer integer) {
            progress.setProgress(0);
            tv.setText("완료되었습니다.");
        }

        @Override
        protected void onCancelled(Integer integer) {
            progress.setProgress(0);
            tv.setText("취소되었습니다.");
        }
    }
}