package com.example.jesunn.twinez;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class adapter_developers extends BaseAdapter{

    private Context context;
    private ArrayList<model_developers> models;

    public adapter_developers(Context context, ArrayList<model_developers> models) {
        this.context = context;
        this.models = models;
    }

    @Override
    public int getCount(){
        return models.size();
    }
    @Override
    public Object getItem(int position){
        return models.get(position);
    }
    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView == null){
            convertView = View.inflate(context, R.layout.list_dev, null);
        }

        ImageView images = convertView.findViewById(R.id.Image);
        TextView name = convertView.findViewById(R.id.Name);
        TextView program = convertView.findViewById(R.id.Desc);
        TextView email = convertView.findViewById(R.id.Mail);

        model_developers model = models.get(position);
        images.setImageResource(model.getDevImage());
        name.setText(model.getDevName());
        program.setText(model.getDevProgram());
        email.setText(model.getDevEmail());


        return convertView;


    }
}
