package com.example.jesunn.twinez;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Intent;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.jesunn.twinez.BaseActivity.currentIp;

import android.widget.ListView;
import com.baoyz.swipemenulistview.SwipeMenuListView;

public class history extends AppCompatActivity {
    private Button click;
    private TextView result;
    private String HISTORY_SERVER = "http://" + currentIp + "/fruitwine/getData/";
    private String ph;
    private String alcohol_content;
    private String temperature;
    private String soluble_solid;
    private static ProgressDialog mProgressDialog;
    private String Result;
    ArrayList<HashMap<String, String>> arraylist;
    JSONObject jsonObject;
    static SwipeMenuListView listView;
    ListView listview;
    ListViewAdapter adapter;
    static String TRANSACTIONID = "id";
    static String PH = "pH";
    static String ALCOHOL = "AlcoholContent";
    static String TEMPERATURE = "Temperature";
    static String SOLUBLE = "SolubleSolid";
    static String UPLOADED = "Uploaded";
    static String RATING = "Rating";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);
        new HistoryData().execute();
        result =  findViewById(R.id.results);

       // Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

       // getSupportActionBar().setTitle("History");
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public class HistoryData extends AsyncTask<Void, Void, Void> {

        boolean isEmpty = true;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(history.this);
            mProgressDialog.setMessage("Loading. Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String inputLine;
            String response;

            arraylist = new ArrayList<HashMap<String, String>>();
            try {
                URL myUrl = new URL(HISTORY_SERVER);
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
                System.out.println(response);
                if (response.equals("[]") || response == null) {
                    isEmpty = true;
                } else {
                    isEmpty = false;

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            jsonObject = jsonArray.getJSONObject(i);
                            map.put("id", jsonObject.getString("id"));
                            map.put("pH", jsonObject.getString("ph"));
                            map.put("AlcoholContent", jsonObject.getString("alcohol_content"));
                            map.put("Temperature", jsonObject.getString("temperature"));
                            map.put("SolubleSolid", jsonObject.getString("soluble_solid"));
                            map.put("Uploaded", jsonObject.getString("uploaded"));
                            map.put("Rating", jsonObject.getString("rating"));
                            arraylist.add(map);

                        }

                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    }
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void args) {

            if (isEmpty ) {
                mProgressDialog.dismiss();
                AlertDialog.Builder PopupWindow =  new AlertDialog.Builder(history.this);
                View ResultView = getLayoutInflater().inflate(R.layout.layout_history, null);
                TextView ResultText = (TextView)ResultView.findViewById(R.id.historyTextResult);
                Button OkayButton = (Button) ResultView.findViewById(R.id.historyOkayBtn);

                PopupWindow.setView(ResultView);
                AlertDialog dialog = PopupWindow.create();
                dialog.show();
                ResultText.setText("No History!!");
                OkayButton.setOnClickListener(  new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        history.this.finish();
                        Intent intent = new Intent(history.this, MainMenu.class);
                        startActivity(intent);
                    }

                });
            }
            else {
                listView = findViewById(R.id.listview);
                adapter = new ListViewAdapter(history.this, arraylist);
                listView.setAdapter(adapter);
                mProgressDialog.dismiss();

            }
        }
    }
}



