package com.example.jesunn.twinez;

import java.util.ArrayList;

public class list_developers {

    public static ArrayList<model_developers> getList(){
        ArrayList<model_developers> about = new ArrayList<>();
        about.add(new model_developers(R.drawable.son," Jason Christian F. Domingo"," BS Computer Engineer"," jesunndomingo@gmail.com"));
        about.add(new model_developers(R.drawable.aaron," Aaron Jed M. Mendoza"," BS Computer Engineer"," aardonjedm@gmail.com"));
        about.add(new model_developers(R.drawable.budz," Salvador S. Misola Jr"," BS Computer Engineer", " veteranbudz@gmail.com"));
        about.add(new model_developers(R.drawable.pads," Christian Adrian A. Padron"," BS Computer Engineer"," padronchristian.20@gmail.com"));

        return about;
    }
}
