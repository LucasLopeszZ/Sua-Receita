package com.example.receitas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class cadastro extends AppCompatActivity {

    EditText edtNome, edtTelefone, edtEmail, edtSenha, edtConfirmar;
    Button btnSalvar;

    FirebaseAuth auth;
    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        edtNome = findViewById(R.id.edtNome);
        edtTelefone = findViewById(R.id.edtTelefone);
        edtEmail = findViewById(R.id.edtEmail);
        edtSenha = findViewById(R.id.edtSenha);
        edtConfirmar = findViewById(R.id.edtConfirmarSenha);
        btnSalvar = findViewById(R.id.btnSalvar);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("Users");

        btnSalvar.setOnClickListener(v -> {

            String nome = edtNome.getText().toString().trim();
            String telefone = edtTelefone.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            String senha = edtSenha.getText().toString().trim();
            String confirmar = edtConfirmar.getText().toString().trim();

            if (nome.isEmpty() || telefone.isEmpty() || email.isEmpty() ||
                    senha.isEmpty() || confirmar.isEmpty()) {

                Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!senha.equals(confirmar)) {
                Toast.makeText(this, "As senhas não coincidem!", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.createUserWithEmailAndPassword(email, senha)
                    .addOnSuccessListener(authResult -> {

                        String uid = auth.getCurrentUser().getUid();

                        Usuario usuario = new Usuario(nome, telefone, email);

                        database.child(uid).setValue(usuario)
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(this, "Cadastro realizado!", Toast.LENGTH_LONG).show();
                                    finish();
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(this, "Erro ao salvar no banco: " + e.getMessage(), Toast.LENGTH_LONG).show()
                                );

                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
        });
    }
}
