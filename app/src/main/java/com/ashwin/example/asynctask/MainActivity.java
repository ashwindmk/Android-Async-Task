package com.ashwin.example.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static class MyAsyncTask extends AsyncTask<String, Integer, String> {
        private WeakReference<MainActivity> activityWeakReference;

        MyAsyncTask(MainActivity activity) {
            activityWeakReference = new WeakReference<>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.progressBar.setVisibility(View.VISIBLE);
            activity.progressBar.setProgress(0);
            activity.contentTextView.setVisibility(View.GONE);
        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try {
                Response response = client.newCall(request).execute();

                for (int i = 1; i <= 10; i++) {
                    publishProgress(i * 10);
                    Thread.sleep(1000);
                }

                if (response.isSuccessful()) {
                    return response.body().string();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            super.onPostExecute(content);
            MainActivity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.progressBar.setVisibility(View.INVISIBLE);
            activity.contentTextView.setVisibility(View.VISIBLE);
            if (content != null) {
                activity.contentTextView.setText(content);
            } else {
                activity.contentTextView.setText("Error");
            }
        }
    }

    private ProgressBar progressBar;
    private TextView contentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        progressBar = findViewById(R.id.task_progressbar);
        contentTextView = (TextView) findViewById(R.id.content_textview);
    }

    public void startTask(View view) {
        MyAsyncTask task = new MyAsyncTask(this);
        task.execute(Constants.GIST_URL);
    }
}
