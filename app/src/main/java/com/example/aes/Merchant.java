package com.example.aes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;

public class Merchant extends AppCompatActivity {
    private EditText session_id, cardNumber, userName, amount;
    private TextView all;
    private Button charge, award;

    private DatePickerDialog datePickerDialog;
    DatabaseHelper4 DB;
    DatabaseHelper3 DBBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);

        session_id=findViewById(R.id.session_id);
        cardNumber=findViewById(R.id.cardNumber);
        userName=findViewById(R.id.userName);
        amount=findViewById(R.id.amount);
        charge=findViewById(R.id.charge);
        award=findViewById(R.id.award);
        all=findViewById(R.id.all);

        DB=new DatabaseHelper4(this);
        DBBalance=new DatabaseHelper3(this);

        aes_ed obj2 = new aes_ed();

        charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String session_id1 = session_id.getText().toString();
                String cardNumber1 = cardNumber.getText().toString();
                String userName1 = userName.getText().toString();
                String amount1 = amount.getText().toString();

                if (session_id1.length() == 0 || cardNumber1.length() == 0 || userName1.length() == 0 || amount1.length() == 0) {
                    Toast.makeText(Merchant.this, "Invalid Input Type!", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor res = DB.getdata(session_id1);
                    if (res.getCount() != 0) {
                        Toast.makeText(Merchant.this, "Session ID is already taken!", Toast.LENGTH_SHORT).show();
                    } else {

                            String cardNumber_e = obj2.aes_e(cardNumber1, userName1);
                            String session_id_e = obj2.aes_e(session_id1, userName1);

                            Intent intent2 = getIntent();
                            String merchantName1 = intent2.getStringExtra("key1");

                            Boolean checkinsertdata = DB.insertuserdata(session_id_e, userName1, cardNumber_e, merchantName1, amount1);
                            if (checkinsertdata == true) {
                                Cursor res2 = DBBalance.getdata3(cardNumber_e);
                                int getTotal=0;
                                while(res2.moveToNext()) {
                                    String total = res2.getString(7);
                                    getTotal = Integer.valueOf(total) - Integer.valueOf(amount1);
                                }
                                Boolean checkinsertdata2 = DBBalance.updateuserdata2(cardNumber_e, String.valueOf(getTotal));
                                if (checkinsertdata2 == true) {
                                    Toast.makeText(Merchant.this, "Successfully Charged!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Merchant.this, "Not Successful!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                return;
            }
        });

        award.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String session_id1 = session_id.getText().toString();
                String cardNumber1 = cardNumber.getText().toString();
                String userName1 = userName.getText().toString();
                String amount01 = amount.getText().toString();
                String amount1 = "-"+amount01;

                if (session_id1.length() == 0 || cardNumber1.length() == 0 || userName1.length() == 0 || amount1.length() == 0) {
                    Toast.makeText(Merchant.this, "Invalid Input Type!", Toast.LENGTH_SHORT).show();
                } else {
                    Cursor res = DB.getdata(session_id1);
                    if (res.getCount() != 0) {
                        Toast.makeText(Merchant.this, "Session ID is already taken!", Toast.LENGTH_SHORT).show();
                    } else {

                        String cardNumber_e = obj2.aes_e(cardNumber1, userName1);
                        String session_id_e = obj2.aes_e(session_id1, userName1);

                        Intent intent2 = getIntent();
                        String merchantName1 = intent2.getStringExtra("key1");

                        Boolean checkinsertdata = DB.insertuserdata(session_id_e, userName1, cardNumber_e, merchantName1, amount1);
                        if (checkinsertdata == true) {
                            Cursor res2 = DBBalance.getdata3(cardNumber_e);
                            int getTotal=0;
                            while(res2.moveToNext()) {
                                String total = res2.getString(7);
                                getTotal = Integer.valueOf(total) - Integer.valueOf(amount1);
                            }
                            Boolean checkinsertdata2 = DBBalance.updateuserdata2(cardNumber_e, String.valueOf(getTotal));
                            if (checkinsertdata2 == true) {
                                Toast.makeText(Merchant.this, "Successfully Awarded!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Merchant.this, "Not Successful!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return;
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=getIntent();
                String merchantName1=intent2.getStringExtra("key1");

                Cursor res=DB.getdata2(merchantName1);
                if (res.getCount()==0){
                    Toast.makeText(Merchant.this,"There are no Payments/Charges to Display",Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){
                        buffer.append("Session ID : " + res.getString(0) + "\n");
                        buffer.append("Username : " + res.getString(1) + "\n");

                        String cardNumber_e = res.getString(2);
                        String cardNumber_d = obj2.aes_d(cardNumber_e, res.getString(1));

                        buffer.append("Card Number : " + cardNumber_d + "\n");
                        buffer.append("Amount Charged : " + res.getString(4) + "\n\n");
                }

                AlertDialog.Builder builder=new AlertDialog.Builder(Merchant.this);
                builder.setCancelable(true);

                builder.setTitle(merchantName1+"'s charges: ");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
    }
}