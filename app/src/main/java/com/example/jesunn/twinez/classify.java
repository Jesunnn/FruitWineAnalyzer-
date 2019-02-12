package com.example.jesunn.twinez;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.jesunn.twinez.BaseActivity.currentIp;

public class classify extends AppCompatActivity {
    private Button click;
    private TextView result;
    JSONObject jsonObject;
    private String GET_SERVER = "http://" + currentIp + "/fruitwine/getData/";
    private String ph;
    private String alcohol_content;
    private String temperature;
    private String volatile_acid;
    private String rate;
    private static ProgressDialog mProgressDialog;
    private String Result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classify);

        click = (Button) findViewById(R.id.get);

        result = (TextView) findViewById(R.id.results);


        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FetchData().execute();
            }
        });
    }

    private class FetchData extends AsyncTask<String, String, String> {

        boolean getData = false;
        int response_code = 0;
        String response_message;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(classify.this);
            //Set Progress dialog title
            mProgressDialog.setTitle("Analyzing Data");
            //Set progress dialog message
            mProgressDialog.setMessage("Loading. Please wait...");
            //Set cancelable false
            mProgressDialog.setCancelable(false);
            //Set canceled on touch outside false
            mProgressDialog.setCanceledOnTouchOutside(false);
            //Set indeterminate false
            mProgressDialog.setIndeterminate(false);
            //Show progress dialog
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String inputLine;
            String response;


            try {
                URL myUrl = new URL(GET_SERVER);
                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.connect();
                InputStreamReader is = new InputStreamReader(connection.getInputStream());
                BufferedReader br = new BufferedReader(is);
                StringBuilder sb = new StringBuilder();
                while ((inputLine = br.readLine()) != null) {
                    sb.append(inputLine);
                }
                br.close();
                is.close();

                response = sb.toString();


                    try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        jsonObject = jsonArray.getJSONObject(i);

                        String json_ph = jsonObject.getString("ph").trim();
                        String json_alcohol_content = jsonObject.getString("alcohol_content").trim();
                        String json_object_temperature = jsonObject.getString("temperature").trim();
                        String json_volatile_acid = jsonObject.getString("volatile_acid").trim();
                        String json_rate = jsonObject.getString("rating").trim();


                        ph = json_ph;
                        alcohol_content = json_alcohol_content;
                        temperature = json_object_temperature;
                        volatile_acid = json_volatile_acid;
                        rate = json_rate;


                        Result = "PH: \t\t" + json_ph + "\nAlcohol Content: \t\t" + json_alcohol_content + "\t%" + "\nTemperature: \t\t" + json_object_temperature + "\t°C" + "\n" +
                                    "Volatile Acid: \t\t" + json_volatile_acid + "\tppm" + "\n\n" + "\n\nRating: \t\t" + json_rate + "\n";
                        System.out.println(Result);
                    }

                } catch (JSONException ex) {
                        ex.printStackTrace();

                }
                //Connect to url
                connection.connect();
                response_code = connection.getResponseCode();
                response_message = connection.getResponseMessage();
                if (response_code == 200) {
                    getData = true;
                    Log.e("classify", "Server response message : " + response_message);
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return Result;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            mProgressDialog.dismiss();

            if (getData) {
                AlertDialog.Builder PopupWindow =  new AlertDialog.Builder(classify.this);
                View ResultView = getLayoutInflater().inflate(R.layout.layout_classify, null);
                TextView ResultText = ResultView.findViewById(R.id.textResult);
                Button OkayButton = ResultView.findViewById(R.id.okayBtn);

                PopupWindow.setView(ResultView);
                AlertDialog dialog = PopupWindow.create();
                dialog.show();
                ResultText.setText(result);
                OkayButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        classify.this.finish();
                        Intent intent = new Intent(classify.this, classify.class);
                        startActivity(intent);

                    }


                });


            }
        }
    }
}




