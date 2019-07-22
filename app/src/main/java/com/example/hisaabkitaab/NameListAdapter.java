package com.example.hisaabkitaab;

import android.content.Context;
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
        tvName.setText(values.get(position).getName());

        TextView tvAmt = (TextView) cv.findViewById(R.id.tvNLAmount);





        return super.getView(position, convertView, parent);
    }
}
