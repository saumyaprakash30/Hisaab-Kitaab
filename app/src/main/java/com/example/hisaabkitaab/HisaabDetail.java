package com.example.hisaabkitaab;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HisaabDetail extends AppCompatActivity {

    TextView tvName,tvPhone;
    Button btnAdd,btnSend,btnPay;
    ListView lvHisaab;
    ArrayList<hisaab> h1 = new ArrayList<hisaab>();
    int totalAmount =0;
    String upiId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hisaab_detail);

        tvName = findViewById(R.id.tvOHName);
        tvPhone = findViewById(R.id.tvOHPhone);
        btnAdd = findViewById(R.id.btnOHAdd);
        btnSend = findViewById(R.id.btnOHSend);
        btnPay = findViewById(R.id.btnPay);
        lvHisaab =  findViewById(R.id.lvOH);


        final String name = getIntent().getStringExtra("name");
        final String phone = getIntent().getStringExtra("mob");

        tvName.setText(name);
        tvPhone.setText(phone);
        final MyDBHandler  mydb = new MyDBHandler(HisaabDetail.this,null,null,1);

        h1 = mydb.getHisaabDetails(name);

        hisaabListAdapter hadapter = new hisaabListAdapter(HisaabDetail.this,h1);

        lvHisaab.setAdapter(hadapter);
        Toast.makeText(this, "Tap to delete Hisaab", Toast.LENGTH_SHORT).show();


        lvHisaab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {


                return false;
            }
        });

        lvHisaab.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(HisaabDetail.this);
                builder.setMessage("Do you want to delete hisaab of "+h1.get(pos).getNote()+
                        " of Rs. "+h1.get(pos).getMoney()+" ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mydb.deleteIndividualItemHisaab(name,(h1.get(pos).getMoney()),h1.get(pos).getDate());
                        finish();
                        startActivity(getIntent());

                        //Toast.makeText(HisaabDetail.this, "hi"+pos, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.show();

            }
        });


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HisaabDetail.this,com.example.hisaabkitaab.addNewHisaab.class);

                intent.putExtra("name",name);
                intent.putExtra("mob",phone);
                intent.putExtra("check",true);
                startActivity(intent);

            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(HisaabDetail.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(HisaabDetail.this,new String[]{Manifest.permission.SEND_SMS},1);
                }
                else
                {
                    int totalAmt =0;
                    String msg = "Hisaab Detail\n";
                    for(int i =0;i<h1.size();i++)
                    {
                        msg = msg + h1.get(i).getNote()+":"+h1.get(i).getMoney()+"\n";
                        totalAmt+=h1.get(i).getMoney();
                    }
                    totalAmount = totalAmt;
                    if(totalAmt<0){
                        msg+="-----\nRecv: "+totalAmt;
                    }
                    else
                        msg+="-----\nPay: "+totalAmt;
                    Uri uri = Uri.parse("smsto:"+phone);
                    Intent sendSms = new Intent(Intent.ACTION_VIEW,uri);
                    sendSms.putExtra("sms_body",msg);
                    startActivity(sendSms);

                }

            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(com.example.hisaabkitaab.HisaabDetail.this);
                builder.setTitle("Payment");
                final EditText et = new EditText(com.example.hisaabkitaab.HisaabDetail.this);
                et.setHint("Enter upi Id");
                builder.setView(et);
                builder.setPositiveButton("Pay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        upiId = et.getText().toString();
                        Uri uri = Uri.parse("upi://pay").buildUpon()
                                .appendQueryParameter("pa",upiId)
                                .appendQueryParameter("pn",tvName.getText().toString())
                                .appendQueryParameter("am",Integer.toString(totalAmount))
                                .build();
                        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();
//                Uri uri = Uri.parse("upi://pay");

            }
        });


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        MyDBHandler mydb = new MyDBHandler(HisaabDetail.this,null,null,1);
        h1 = mydb.getHisaabDetails(getIntent().getStringExtra("name"));
        hisaabListAdapter hadapter = new hisaabListAdapter(HisaabDetail.this,h1);
        lvHisaab.setAdapter(hadapter);


    }
}
