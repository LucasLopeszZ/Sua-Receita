package com.example.receitas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class activity_login extends AppCompatActivity {

    EditText edtEmail, edtSenha;
    Button btnLogin, btnCadastro;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        btnLogin = findViewById(R.id.btnLogin);
        btnCadastro = findViewById(R.id.btncadastro);


        auth = FirebaseAuth.getInstance();


        btnLogin.setOnClickListener(v -> {

            String email = edtEmail.getText().toString().trim();
            String senha = edtSenha.getText().toString().trim();

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(activity_login.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }


            auth.signInWithEmailAndPassword(email, senha)
                    .addOnCompleteListener(task -> {

                        if (task.isSuccessful()) {

                            Toast.makeText(activity_login.this, "Login realizado!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(activity_login.this, tela2.class);
                            startActivity(intent);
                            finish();

                        } else {

                            Toast.makeText(activity_login.this,
                                    "E-mail ou senha incorretos!",
                                    Toast.LENGTH_SHORT).show();
                        }

                    });

        });


        btnCadastro.setOnClickListener(v -> {
            Intent intent = new Intent(activity_login.this, cadastro.class);
            startActivity(intent);
        });
    }
}

