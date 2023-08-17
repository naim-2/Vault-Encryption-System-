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

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignUp extends AppCompatActivity {
    private EditText firstName, lastName, userName, email, password, confirm_password;
    private TextView LogInPage;
    private Button Sign_Up;

    DatabaseHelper1 DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstName=findViewById(R.id.firstName);
        lastName=findViewById(R.id.lastName);
        userName=findViewById(R.id.userName);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        confirm_password=findViewById(R.id.confirm_password);
        Sign_Up=findViewById(R.id.Sign_Up);
        LogInPage=findViewById(R.id.LogInPage);

        DB=new DatabaseHelper1(this);

        sha256 obj2 = new sha256();

        Sign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName1 = firstName.getText().toString();
                String lastName1 = lastName.getText().toString();
                String userName1 = userName.getText().toString();
                String email1=email.getText().toString();
                String password1 = password.getText().toString();
                String confirm_password1=confirm_password.getText().toString();

                if (firstName1.length() == 0 || lastName1.length() == 0 || userName1.length() == 0 || email1.length() == 0 || password1.length() == 0) {
                    Toast.makeText(SignUp.this, "Invalid Input Type!", Toast.LENGTH_SHORT).show();
                    return;
                } else {

                    Cursor res = DB.getdata(userName1);
                    if (res.getCount() != 0) {
                        Toast.makeText(SignUp.this, "Username is taken!", Toast.LENGTH_SHORT).show();
                        return;
                    } else if (!password1.equals(confirm_password1)) {
                        Toast.makeText(getApplicationContext(), "The password match is wrong!", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        String password_sha = obj2.shathis(password1);

                        Boolean checkinsertdata = DB.insertuserdata(userName1, firstName1, lastName1, email1, password_sha);
                        if (checkinsertdata == true) {
                            Toast.makeText(SignUp.this, "Successfully Registered!", Toast.LENGTH_SHORT).show();
                            Intent intent2 = new Intent(getApplicationContext(), CreditCard.class);
                            intent2.putExtra("key1", userName1);
                            startActivity(intent2);
                        } else {
                            Toast.makeText(SignUp.this, "Not Registered!", Toast.LENGTH_SHORT).show();
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