package com.example.aes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.IntegerRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class CreditCard extends AppCompatActivity {
    private EditText cardNumber, cardName, expirationDate, securityCode, billingAddress, dateCreated;
    private TextView all;
    private Button add_c, update, delete;

    private DatePickerDialog datePickerDialog;
    DatabaseHelper3 DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        cardNumber=findViewById(R.id.cardNumber);
        cardName=findViewById(R.id.cardName);
        expirationDate=findViewById(R.id.expirationDate);
        securityCode=findViewById(R.id.securityCode);
        billingAddress=findViewById(R.id.billingAddress);
        dateCreated=findViewById(R.id.dateCreated);
        add_c=findViewById(R.id.add_c);
        update=findViewById(R.id.update);
        delete=findViewById(R.id.delete);
        all=findViewById(R.id.all);

        DB=new DatabaseHelper3(this);

        aes_ed obj1 = new aes_ed();

        expirationDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(CreditCard.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        expirationDate.setText(dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        dateCreated.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(CreditCard.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        dateCreated.setText(dayOfMonth + "-"
                                + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        add_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cardNumber1 = cardNumber.getText().toString();
                String cardName1 = cardName.getText().toString();
                String expirationDate1 = expirationDate.getText().toString();
                String securityCode1 = securityCode.getText().toString();
                String billingAddress1 = billingAddress.getText().toString();
                String dateCreated1 = dateCreated.getText().toString();
                String amount1 = "10000";

                if (cardNumber1.length() == 0 || cardName1.length() == 0 || expirationDate1.length() == 0 || securityCode1.length() == 0 || billingAddress1.length() == 0 || dateCreated1.length() == 0) {
                    Toast.makeText(CreditCard.this, "Invalid Input Type!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent2 = getIntent();
                    String userName1 = intent2.getStringExtra("key1");

                    Cursor res = DB.getdata(cardNumber1);
                    if (res.getCount() != 0) {
                        Toast.makeText(CreditCard.this, "Credit Number is Already Taken!", Toast.LENGTH_SHORT).show();
                    } else {
                        String[] values1 = dateCreated1.split("-");
                        String[] values2 = expirationDate1.split("-");

                        int c_year = Integer.valueOf(values1[2]);
                        int c_month = Integer.valueOf(values1[1]);
                        int c_day = Integer.valueOf(values1[0]);
                        int e_year = Integer.valueOf(values2[2]);
                        int e_month = Integer.valueOf(values2[1]);
                        int e_day = Integer.valueOf(values2[0]);

                        if ((c_year > e_year) || (c_year == e_year && c_month > e_month) || (c_year == e_year && c_month == e_month && c_day > e_day)) {
                            Toast.makeText(CreditCard.this, "Date Created should be before Expiration Date!", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (c_year == e_year && c_month == e_month && c_day == e_day) {
                            Toast.makeText(CreditCard.this, "Date Created and Expiration Date cannot be on the same day!", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            String cardNumber_e = obj1.aes_e(cardNumber1, userName1);
                            String securityCode_e = obj1.aes_e(securityCode1, userName1);

                            Boolean checkinsertdata = DB.insertuserdata(cardNumber_e, cardName1, userName1, expirationDate1, securityCode_e, billingAddress1, dateCreated1, amount1);
                            if (checkinsertdata == true) {
                                Toast.makeText(CreditCard.this, "Successfully Registered New Card Number!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CreditCard.this, "Not Registered!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                return;
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cardNumber1 = cardNumber.getText().toString();
                String cardName1 = cardName.getText().toString();
                String expirationDate1 = expirationDate.getText().toString();
                String securityCode1 = securityCode.getText().toString();
                String billingAddress1 = billingAddress.getText().toString();
                String dateCreated1 = dateCreated.getText().toString();

                if (cardNumber1.length() == 0 || cardName1.length() == 0 || expirationDate1.length() == 0 || securityCode1.length() == 0 || billingAddress1.length() == 0 || dateCreated1.length() == 0) {
                    Toast.makeText(CreditCard.this, "Invalid Input Type!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent2 = getIntent();
                    String userName1 = intent2.getStringExtra("key1");

                    String[] values1 = dateCreated1.split("-");
                    String[] values2 = expirationDate1.split("-");

                    int c_year = Integer.valueOf(values1[2]);
                    int c_month = Integer.valueOf(values1[1]);
                    int c_day = Integer.valueOf(values1[0]);
                    int e_year = Integer.valueOf(values2[2]);
                    int e_month = Integer.valueOf(values2[1]);
                    int e_day = Integer.valueOf(values2[0]);

                    if ((c_year > e_year) || (c_year == e_year && c_month > e_month) || (c_year == e_year && c_month == e_month && c_day > e_day)) {
                        Toast.makeText(CreditCard.this, "Date Created should be before Expiration Date!", Toast.LENGTH_SHORT).show();
                    } else if (c_year == e_year && c_month == e_month && c_day == e_day) {
                        Toast.makeText(CreditCard.this, "Date Created and Expiration Date cannot be on the same day!", Toast.LENGTH_SHORT).show();
                    } else {
                        String cardNumber_e = obj1.aes_e(cardNumber1, userName1);
                        String securityCode_e = obj1.aes_e(securityCode1, userName1);

                        Boolean checkupdatedata = DB.updateuserdata(cardNumber_e, cardName1, userName1, expirationDate1, securityCode_e, billingAddress1, dateCreated1);
                        if (checkupdatedata == true) {
                            Toast.makeText(CreditCard.this, "Entry Updated!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CreditCard.this, "Entry Not Updated!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return;
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cardNumber1 = cardNumber.getText().toString();
                Intent intent2=getIntent();
                String userName1=intent2.getStringExtra("key1");

                String cardNumber_e = obj1.aes_e(cardNumber1, userName1) ;

                Boolean checkdeletedata = DB.deleteuserdata(cardNumber_e);
                if (checkdeletedata==true){
                    Toast.makeText(CreditCard.this, "Entry Deleted!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(CreditCard.this, "Entry Not Deleted!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=getIntent();
                String userName1=intent2.getStringExtra("key1");

                Cursor res=DB.getdata(userName1);
                if (res.getCount()==0){
                    Toast.makeText(CreditCard.this,"There are no Credit Card to Display",Toast.LENGTH_SHORT).show();
                    return;
                }

                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){
                    String cardNumber_e = res.getString(0);
                    String cardNumber_d = obj1.aes_d(cardNumber_e, userName1);

                    buffer.append("Card Number : "+cardNumber_d+"\n");
                    buffer.append("Card Name : "+res.getString(1)+"\n");
                    buffer.append("Expiration Date : "+res.getString(3)+"\n");

                    String securityCode_e = res.getString(4);
                    String securityCode_d = obj1.aes_d(securityCode_e, userName1);

                    buffer.append("Security Code : "+securityCode_d+"\n");
                    buffer.append("Billing Address : "+res.getString(5)+"\n");
                    buffer.append("Date Created : "+res.getString(6)+"\n");
                    buffer.append("Balance : "+res.getString(7)+"\n\n");
                }

                AlertDialog.Builder builder=new AlertDialog.Builder(CreditCard.this);
                builder.setCancelable(true);

                builder.setTitle(userName1+"'s Card Details: ");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });
    }
}