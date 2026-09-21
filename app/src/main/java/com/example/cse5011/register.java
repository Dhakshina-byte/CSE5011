package com.example.cse5011;

import android.os.Bundle;

import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

import com.example.cse5011.DB.dbconnect;
import com.example.cse5011.model.users;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        EditText rname,rmail,rpass;
        Button registerbtn;
        rname = findViewById(R.id.rname);
        rmail = findViewById(R.id.rmail);
        rpass = findViewById(R.id.rpass);
        registerbtn = findViewById(R.id.registerbtn);



        Button backbtn = findViewById(R.id.backbtn);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String name = rname.getText().toString();
               String email = rmail.getText().toString();
               String password = rpass.getText().toString();

               if(name.isEmpty() || email.isEmpty() || password.isEmpty()){
                   Toast.makeText(register.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
               }else{
                   users user = new users(name, email, password);
                   dbconnect db = new dbconnect(register.this);
                   db.addUser(user);

                   Toast.makeText(register.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                   Intent intent = new Intent(register.this, MainActivity.class);
                   startActivity(intent);
                   finish();
               }
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(register.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}