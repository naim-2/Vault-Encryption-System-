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

public class SignUp2 extends AppCompatActivity {
    private EditText merchantName, userName, email, password, confirm_password;
    private TextView LogInPage;
    private Button Sign_Up;

    DatabaseHelper2 DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        merchantName=findViewById(R.id.merchantName);
        userName=findViewById(R.id.userName);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confirm_password=findViewById(R.id.confirm_password);
        Sign_Up=findViewById(R.id.Sign_Up);
        LogInPage=findViewById(R.id.LogInPage);

        DB=new DatabaseHelper2(this);

        sha256 obj3 = new sha256();

        Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String merchantName1 = merchantName.getText().toString();
                String userName1 = userName.getText().toString();
                String email1 = email.getText().toString();
                String password1 = password.getText().toString();
                String confirm_password1 = confirm_password.getText().toString();

                if (merchantName.length() == 0 || userName1.length() == 0 || email1.length() == 0 || password1.length() == 0) {
                    Toast.makeText(SignUp2.this, "Invalid Input Type!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Cursor res = DB.getdata(merchantName1);
                    if (res.getCount() != 0) {
                        Toast.makeText(SignUp2.this, "Merchant Name is taken!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (!password1.equals(confirm_password1)) {
                        Toast.makeText(getApplicationContext(), "The password match is wrong!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else
                    {
                        String password_sha = obj3.shathis(password1);

                        Boolean checkinsertdata = DB.insertuserdata(userName1, merchantName1, email1, password_sha);
                        if (checkinsertdata == true) {
                            Toast.makeText(SignUp2.this, "Successfully Registered New Merchant!", Toast.LENGTH_SHORT).show();
                            Intent intent2 = new Intent(getApplicationContext(), Merchant.class);
                            intent2.putExtra("key1", merchantName1);
                            startActivity(intent2);
                        }
                        else {
                            Toast.makeText(SignUp2.this, "Not Registered!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
            }
        });
        LogInPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LogIn.class);
                startActivity(intent);
            }
        });
    }
}