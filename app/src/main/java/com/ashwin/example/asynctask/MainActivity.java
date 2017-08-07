package com.ashwin.example.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.ashwin.example.asynctask.models.Employee;
import com.ashwin.example.asynctask.models.Employees;
import com.bluelinelabs.logansquare.LoganSquare;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mContentTextView;
    private Employees mEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        startTask();
    }

    private void initViews() {
        mContentTextView = (TextView) findViewById(R.id.content_text_view);
    }

    private void startTask() {

        new AsyncTask<Void, Void, Employees>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                mContentTextView.setText("Loading...");
            }

            @Override
            protected Employees doInBackground(Void... params) {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(Constants.GIST_URL)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    if (response.isSuccessful()) {
                        mEmployees = (Employees) LoganSquare.parse(response.body().string(), Employees.class);
                        return mEmployees;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Employees employees) {
                super.onPostExecute(employees);

                if (employees != null) {
                    StringBuilder sb = new StringBuilder();

                    for (Employee e : employees.getEmployees()) {
                        sb.append(e.getName() + "\n\n");
                    }

                    mContentTextView.setText(sb.toString());
                } else {
                    mContentTextView.setText("Error");
                }
            }
        }.execute();

    }
}
