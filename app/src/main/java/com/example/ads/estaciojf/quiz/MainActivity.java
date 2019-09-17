package com.example.ads.estaciojf.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Atributos da classe - Elementos do Layout
    RadioGroup radioGroup;
    RadioButton radioButton1;
    RadioButton radioButton2;
    RadioButton radioButton3;

    // Listas de RadioButtons e Perguntas
    List<RadioButton> listRadioButtons;
    ArrayList<Question> listQuestions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Endereço da base de perguntas
        String url = "http://www.json-generator.com/api/json/get/coUPnGlShu?indent=2";

        // Inicia a lista de Perguntas
        listQuestions = new ArrayList<>();

        // Método que faz o link entre os atributos da classe e os ids de elementos do layout
        bindViews();

        // Inicia a lista de RadioButtons e adiciona na lista
        listRadioButtons = new ArrayList<RadioButton>();
        listRadioButtons.add(radioButton1);
        listRadioButtons.add(radioButton2);
        listRadioButtons.add(radioButton3);

        // Faz a consulta na base de perguntas
        new JsonTask().execute(url);

        // Evento que captura o click em qualquer RadioButton
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.radioButton1){
                    selectRadioButton(radioButton1);
                    Log.i("Radio", "Radio 1");
                }

                if (i == R.id.radioButton2){
                    selectRadioButton(radioButton2);
                    Log.i("Radio", "Radio 2");

                }
                if (i == R.id.radioButton3){
                    selectRadioButton(radioButton3);
                    Log.i("Radio", "Radio 3");

                }

            }
        });

    }

    // Método que faz o link entre os atributos da classe e os ids de elementos do layout
    private void bindViews () {
        radioGroup = findViewById(R.id.radioGroup);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
    }


    // Método que altera as cores do RadioButton de acordo com o click
    private void selectRadioButton (RadioButton radioButton) {
        // Forma atualizada - ContextCompat.getColor(context, R.color.color_name)
        radioButton.setTextColor(getResources().getColor(R.color.accent));
        // Loop na lista de RadioButtons
        // Verifica se o elemento da iteração está marcado
        // Se não muda a cor
        for (RadioButton radio : listRadioButtons) {
            Log.i("Radio", radio.toString() + " " + radio.isChecked());
            if (!radio.isChecked())
                radio.setTextColor(getResources().getColor(R.color.primaryText));
        }
    }

    // Classe interna que faz a requisição na base de dados de perguntas
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
                    listQuestions.add(questionModel);
                }

                Log.i("Question Main", listQuestions.get(0).getOption2());


            }catch (JSONException e){e.printStackTrace();}

        }
    }

}

