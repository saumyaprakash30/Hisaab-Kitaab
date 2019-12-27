package com.example.hisaabkitaab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class hisaabListAdapter extends ArrayAdapter<hisaab>  {

    ArrayList<hisaab> values;

    public hisaabListAdapter(Context context, ArrayList<hisaab> list) {
        super(context, R.layout.hisaab_list_layout,list);
        this.values = list;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View cv = inflater.inflate(R.layout.hisaab_list_layout,parent,false);

        TextView tvNote = (TextView) cv.findViewById(R.id.tvHisaabListNote);
        tvNote.setText(values.get(position).getNote());

        TextView tvAmt = (TextView) cv.findViewById(R.id.tvHisaabListAmt);
        tvAmt.setText(String.valueOf(values.get(position).getMoney()));
        if(values.get(position).getMoney()<0){
            tvAmt.setTextColor(Color.parseColor("#ff0000"));
        }
        else if(values.get(position).getMoney()>0)
            tvAmt.setTextColor(Color.parseColor("#61C561"));


        TextView tvDate = (TextView) cv.findViewById(R.id.tvHisaabListDate);
        tvDate.setText(String.valueOf(values.get(position).getDate()));
        return cv;
    }
}
