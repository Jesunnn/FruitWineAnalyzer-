package com.example.jesunn.twinez;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.jesunn.twinez.BaseActivity.currentIp;


public class ListViewAdapter extends BaseAdapter {

    /*
    Declare variables
     */
    public ProgressDialog mProgressDialog;
    private String HISTORY_INSTANCE_URL;
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public ListViewAdapter(history context,
                           ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        /*
        Declared variables*/

        TextView transactionid;
        TextView ph;
        TextView ac;
        TextView temp;
        TextView va;
        TextView uploaded;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.listview_item, parent, false);
        resultp = data.get(position);

        /*
        Locate the textview in listview_item
         */
        transactionid = itemView.findViewById(R.id.transactionid);
        ph = itemView.findViewById(R.id.ph);
        ac = itemView.findViewById(R.id.alcohol);
        temp = itemView.findViewById(R.id.temps);
        va = itemView.findViewById(R.id.va);
        uploaded = itemView.findViewById(R.id.uploaded);

        /*
        Capture the position and set the text to text view
         */

        transactionid.setText(resultp.get(history.TRANSACTIONID));
        ph.setText(resultp.get(history.PH));
        ac.setText(resultp.get(history.ALCOHOL));
        temp.setText(resultp.get(history.TEMPERATURE));
        va.setText(resultp.get(history.VOLATILE));
        uploaded.setText(resultp.get(history.UPLOADED));

        System.out.println(resultp.get(history.TRANSACTIONID));
        System.out.println(resultp.get(history.PH));
        System.out.println(resultp.get(history.ALCOHOL));
        System.out.println(resultp.get(history.TEMPERATURE));
        System.out.println(resultp.get(history.VOLATILE));
        System.out.println(resultp.get(history.UPLOADED));

                /*
        Create swipe menu instance
         */
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem deleteItem = new SwipeMenuItem(context.getApplicationContext());
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9, 0x3F, 0x25)));
                deleteItem.setWidth(360);
                deleteItem.setIcon(R.drawable.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };

        /*Apply the created swipe menu instance to swipelistview
         */
        history.listView.setMenuCreator(creator);
      /*
        If the swipe menu is clicked (Delete & Open)
         */
        history.listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                resultp = data.get(position);
                switch (index) {
                    /*
                    Open Case
                     */
                    case 0:
                        /*
                        Pass all related data
                         */
                        Intent single_intent = new Intent(context, SingleItemView.class);
                        single_intent.putExtra("ph", resultp.get(history.PH));
                        single_intent.putExtra("volatile", resultp.get(history.VOLATILE));
                        single_intent.putExtra("transactionid", resultp.get(history.TRANSACTIONID));
                        single_intent.putExtra("alcohol", resultp.get(history.ALCOHOL));
                        single_intent.putExtra("temperature", resultp.get(history.TEMPERATURE));
                        context.startActivity(single_intent);

                        break;

                     case 1:
                         String current_id = resultp.get(history.TRANSACTIONID);
                         HISTORY_INSTANCE_URL = "http://" + currentIp + "/fruitwine/deleteData/" + current_id + "/";
                         new DeleteHistoryData().execute();

                         break;
                }
                return false;
            }
        });

        return itemView;
    }


    /*DELETE TRANSACTIONS*/
    public class DeleteHistoryData extends AsyncTask<Void, Void, Void> {
        int response_code;
        String response_message;
        boolean isDeleted;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.setMessage("Removing. Please wait...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL myUrl = new URL("http://" + currentIp + "/fruitwine/getData/");
                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
                connection.setRequestMethod("DELETE");
                connection.setReadTimeout(15000);
                connection.setConnectTimeout(15000);
                connection.connect();
                response_code = connection.getResponseCode();
                response_message = connection.getResponseMessage();
                if (response_code == 204) {
                    isDeleted = true;
                    Log.e("history", "Server response message : " + response_message);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (isDeleted) {
                Toast.makeText(context, "History removed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, SingleItemView.class);
                context.startActivity(intent);
            }
        }
    }
}