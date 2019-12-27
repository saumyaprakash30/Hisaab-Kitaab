package com.example.hisaabkitaab;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class addNewHisaab extends AppCompatActivity {
    EditText etName,etPhone,etDate,etAmount,etNote;
    RadioGroup rg;
    RadioButton rb;
    Button btnSave,btnCancel;
    ImageButton btnContact;
    int moneyType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_hisaab);
        etName = findViewById(R.id.etNHName);
        etPhone = findViewById(R.id.etNHNumber);
        etAmount = findViewById(R.id.etNewAmount);
        etDate = findViewById(R.id.etNHDate);
        etNote = findViewById(R.id.etNewNote);
        rg = findViewById(R.id.rgNH);

        btnCancel = findViewById(R.id.btnNHCancel);
        btnSave = findViewById(R.id.btnNHSave);
        btnContact = findViewById(R.id.btnNHContact);
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat cformat = new SimpleDateFormat("YYYY/MM/dd");
        String d = cformat.format(c);
        etDate.setText(d);

        boolean check = getIntent().getBooleanExtra("check",false);
        if(check)
        {
            String phone = getIntent().getStringExtra("mob");
            Toast.makeText(this, phone, Toast.LENGTH_SHORT).show();
            etName.setText(getIntent().getStringExtra("name"));
            etPhone.setText(phone);
        }


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etName.getText().toString().isEmpty() || etNote.getText().toString().isEmpty() || etDate.getText().toString().isEmpty() ||
                        etAmount.getText().toString().isEmpty() || etPhone.getText().toString().isEmpty() || rg.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(addNewHisaab.this, "Please enter all fields !", Toast.LENGTH_SHORT).show();
                }
                else
                {



                    String name = etName.getText().toString().trim(),phone = etPhone.getText().toString().trim();
                    int money = Integer.parseInt(etAmount.getText().toString().trim());
                    money = moneyType*money;
                    hisaab h = new hisaab(name,phone,money,etDate.getText().toString().trim(),etNote.getText().toString().trim());

                    MyDBHandler mydb = new MyDBHandler(addNewHisaab.this,null,null,1);
                    mydb.addHisaab(h);
                    Toast.makeText(addNewHisaab.this, "Hisaab saved!", Toast.LENGTH_SHORT).show();
                    addNewHisaab.this.finish();

                }


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewHisaab.this.finish();

            }
        });





    }

    public void onClickedAddPerosn(View view)
    {
        if(ContextCompat.checkSelfPermission(addNewHisaab.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions( addNewHisaab.this,new String[]{Manifest.permission.READ_CONTACTS},1);
        }
        else {
            Intent intent = new Intent(Intent.ACTION_PICK);

            intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);

            startActivityForResult(intent, 101);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101)
        {
            if(resultCode==RESULT_OK)
            {
                if(resultCode == RESULT_OK)
                {
                    Uri contactUri = data.getData();
                    String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

                    Cursor cursor = getContentResolver().query(contactUri, projection,
                            null, null, null);
                    // If the cursor returned is valid, get the phone number
                    if (cursor != null && cursor.moveToFirst()) {
                        int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        String number = cursor.getString(numberIndex);
                        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        cursor.moveToFirst();
                        etName.setText(name);
                        etPhone.setText(number);

                    }



                }
            }
        }
    }

    public void onClickRB(View view)
    {
//        int id = rg.getCheckedRadioButtonId();
//        rb = findViewById(id);
//        Toast.makeText(this, rb.getText().toString(), Toast.LENGTH_SHORT).show();
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()){
            case R.id.rbTake:
                if(checked)
                {
                    moneyType = 1;
                    Toast.makeText(this, "take", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.rbGive:
                if(checked)
                {
                    moneyType = -1;
                    Toast.makeText(this, "give", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
