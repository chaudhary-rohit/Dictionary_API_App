package com.example.dictionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
{
    EditText editText;
    TextView textView;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText)findViewById(R.id.editText);
        textView = (TextView)findViewById(R.id.textView);
        button   = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                final Dict_API_Task dict_api_task =new Dict_API_Task();
                String word = editText.getText().toString();
                dict_api_task.execute(word);
            }
        });

    }
    class Dict_API_Task extends AsyncTask<String, String, String>
    {
        @Override
        protected String doInBackground(String... strings)
        {

            OkHttpClient client = new OkHttpClient();
            String URL =  "https://mashape-community-urban-dictionary.p.rapidapi.com/define?term=" + strings[0];

            Request request = new Request.Builder()
                    .url(URL)
                    .get()
                    .addHeader("x-rapidapi-host", "mashape-community-urban-dictionary.p.rapidapi.com")
                    .addHeader("x-rapidapi-key", "eeceda4f4dmshd91dd528003ad93p1437f7jsn7c9498e6aee4")
                    .build();

            try
            {
                Response response = client.newCall(request).execute();
                return(response.body().string());
            }
            catch (IOException e)
            {
                //Toast.makeText(MainActivity.this, "Word not found", Toast.LENGTH_SHORT).show();
                return("Meaning not found!!");
            }

        }

        @Override
        protected void onPostExecute(String s)
        {
            textView.setText(s);
        }
    }
}
