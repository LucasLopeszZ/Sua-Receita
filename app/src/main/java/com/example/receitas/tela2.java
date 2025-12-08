package com.example.receitas;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class tela2 extends AppCompatActivity {

    private Button meus_ingredientes;
    private Button escolha;

    private Button Recomendadas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela2);


        meus_ingredientes = findViewById(R.id.meus_ingredientes);
        escolha = findViewById(R.id.escolha);
        Recomendadas = findViewById(R.id.recomendadas);


        meus_ingredientes.setOnClickListener(v -> {
            Intent intent = new Intent(tela2.this, MeusIngredientes.class);
            startActivity(intent);
        });
        escolha.setOnClickListener(V ->{
            Intent esc = new Intent(tela2.this, Escolha.class);
            startActivity(esc);
                });
        Recomendadas.setOnClickListener(V ->{
            Intent rec = new Intent(tela2.this, Recomendadas.class);
            startActivity(rec);
                });
    }
}
