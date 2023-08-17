package com.example.aes;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogIn extends AppCompatActivity{
    private EditText name, userName, password;
    private Button submit;
    private TextView sign_up, sign_up2;

    DatabaseHelper1 DB;
    DatabaseHelper2 DB2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        name = findViewById(R.id.name);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        sign_up = findViewById(R.id.sign_up);
        sign_up2 = findViewById(R.id.sign_up2);
        submit = findViewById(R.id.submit);

        DB=new DatabaseHelper1(this);
        DB2=new DatabaseHelper2(this);

        sha256 obj1 = new sha256();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1 = name.getText().toString();
                String userName1 = userName.getText().toString();
                String password1 = password.getText().toString();
                String password_sha = obj1.shathis(password1);

                if (name1.length() == 0 || userName1.length() == 0 || password1.length() == 0) {
                    Toast.makeText(LogIn.this, "Invalid Input Type!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Cursor res1 = DB.getdata(userName1);
                    Cursor res2 = DB2.getdata2(userName1);
                    if (res1.getCount() == 0 && res2.getCount() == 0) {
                        Toast.makeText(LogIn.this, "No such user exists!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    String db1_password = "", db2_password = "";
                    if (res1.getCount() != 0) {
                        while (res1.moveToNext()) {
                            db1_password = res1.getString(4);
                        }
                        if (db1_password.equals(password_sha)) {
                            Intent intent10 = new Intent(getApplicationContext(), CreditCard.class);
                            intent10.putExtra("key1", userName1);
                            startActivity(intent10);
                        }
                    } else if (res2.getCount() != 0) {
                        while (res2.moveToNext()) {
                            db2_password = res2.getString(3);
                        }
                        if (db2_password.equals(password_sha)) {
                            Intent intent2 = new Intent(getApplicationContext(), Merchant.class);
                            intent2.putExtra("key1", name1);
                            startActivity(intent2);
                        }
                    } else if (!db1_password.equals(password_sha) || !db2_password.equals(password_sha)) {
                        Toast.makeText(getApplicationContext(), "Wrong password entered!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });

        sign_up2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUp2.class);
                startActivity(intent);
            }
        });
    }
}