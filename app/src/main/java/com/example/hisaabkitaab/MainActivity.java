package com.example.hisaabkitaab;

import android.app.KeyguardManager;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvName;
    TextView tvMsg;
    ImageButton btnAdd;
    SearchView sv;
    int flag=0;
    ArrayList<hisaab> list = new ArrayList<hisaab>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnaddName);
        lvName = findViewById(R.id.lvName);
        tvMsg = findViewById(R.id.tvMainMsg);

        if(flag == 0)
        {
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            Intent i = keyguardManager.createConfirmDeviceCredentialIntent("Unlock", "Hi");
            startActivityForResult(i,101);
        }





        MyDBHandler mydb = new MyDBHandler(MainActivity.this,null,null,1);
        list = mydb.getHisaabNames();

        if(list.isEmpty())
        {
            tvMsg.setVisibility(View.VISIBLE);
        }
        else{
            tvMsg.setVisibility(View.GONE);
        }


        NameListAdapter adapter = new NameListAdapter(MainActivity.this ,list);
        lvName.setAdapter(adapter);





        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,com.example.hisaabkitaab.addNewHisaab.class));
            }
        });

        lvName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this,com.example.hisaabkitaab.HisaabDetail.class);

                intent.putExtra("name",list.get(i).getName());
                intent.putExtra("mob",list.get(i).getPhone());
                startActivity(intent);
            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101)
        {
            if (resultCode == RESULT_OK)
            {

                flag =1;
            }
            else{

                this.finish();
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        MyDBHandler mydb = new MyDBHandler(MainActivity.this,null,null,1);
        list = mydb.getHisaabNames();

        if(list.isEmpty())
        {
            tvMsg.setVisibility(View.VISIBLE);
        }
        else{
            tvMsg.setVisibility(View.GONE);
        }


        NameListAdapter adapter = new NameListAdapter(MainActivity.this ,list);
        lvName.setAdapter(adapter);
    }
}
