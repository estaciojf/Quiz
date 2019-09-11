package com.example.ads.estaciojf.quiz;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class JsonTask extends AsyncTask<String, String, String> {

    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();

            String line = "";

            while ((line = reader.readLine()) != null) {
                buffer.append(line+"\n");
                Log.d("Response: ", "> " + line);
            }
            return buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);


        Log.i("Questions"," " + result);

        try {
            JSONObject listaJson = new JSONObject(result);
            JSONArray questions = listaJson.getJSONArray("questions");

            for (int i = 0 ; i < questions.length() ; i ++) {
                JSONObject question = questions.getJSONObject(i);

                String pergunta = question.getString("question");
                String opt1 = question.getString("option1");
                String opt2 = question.getString("option2");
                String opt3 = question.getString("option3");
                int answer = question.getInt("answer");

                Question questionModel = new Question(pergunta, opt1, opt2, opt3, answer);
            }

        }catch (JSONException e){e.printStackTrace();}

    }
}
