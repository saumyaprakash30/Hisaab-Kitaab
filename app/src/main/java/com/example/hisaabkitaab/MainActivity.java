package com.example.hisaabkitaab;

import android.app.KeyguardManager;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvName;
    ImageButton btnAdd;
    SearchView sv;
    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = findViewById(R.id.btnaddName);
        lvName = findViewById(R.id.lvName);

        if(flag == 0)
        {
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            Intent i = keyguardManager.createConfirmDeviceCredentialIntent("Unlock", "Hi");
            startActivityForResult(i,101);
        }

        ArrayList<hisaab> list = new ArrayList<hisaab>();

        hisaab h1 = new hisaab("Saumya","9110911562",1000,"22/11/2019","testing");

        list.add(h1);

        NameListAdapter adapter = new NameListAdapter(this,list);
        lvName.setAdapter(adapter);





        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,com.example.hisaabkitaab.addNewHisaab.class));
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
                Toast.makeText(this, "Success !", Toast.LENGTH_SHORT).show();
                flag =1;
            }
            else{
                Toast.makeText(this, "Failed !", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }
    }
}
