package com.example.meetup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class signup extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseFirestore database;
    EditText emailbox, passwordbox,namebox;
    Button loginbutton, createbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        database = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        emailbox = findViewById(R.id.emailbox);
        passwordbox = findViewById(R.id.passwordbox);
        namebox = findViewById(R.id.namebox);

        loginbutton = findViewById(R.id.loginbutton);
        createbutton = findViewById(R.id.createbutton);
        createbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password, name;
                email = emailbox.getText().toString();
                password = passwordbox.getText().toString();
                name = namebox.getText().toString();
                final User user = new User();
                user.setEmail(email);
                user.setPassword(password);
                user.setName(name);
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            database.collection("Users")
                                    .document().set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    startActivity(new Intent(signup.this, Login.class));
                                }
                            }); 
                            Toast.makeText(signup.this, "Account is created", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(signup.this, task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });

    }
}