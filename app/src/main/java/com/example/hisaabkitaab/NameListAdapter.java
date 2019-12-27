package com.example.hisaabkitaab;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class NameListAdapter extends ArrayAdapter<hisaab> {

    ArrayList<hisaab> values;

    public NameListAdapter(Context context, ArrayList<hisaab> list) {
        super(context, R.layout.name_list_view,list);
        this.values = list;
    }


    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View cv = inflater.inflate(R.layout.name_list_view,parent,false);

        TextView tvName = (TextView) cv.findViewById(R.id.tvNLName);


        TextView tvAmt = (TextView) cv.findViewById(R.id.tvNLAmount);
        tvAmt.setText(String.valueOf(values.get(position).getMoney()));
        if(values.get(position).getMoney()<0){
            tvAmt.setTextColor(Color.parseColor("#ff0000"));
        }
        else if(values.get(position).getMoney()>0)
            tvAmt.setTextColor(Color.parseColor("#61C561"));
        tvName.setText(values.get(position).getName());




        return cv;
    }
}
