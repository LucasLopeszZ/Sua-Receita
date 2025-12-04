package com.example.receitas;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Escolha extends AppCompatActivity {

    private static final String TAG = "Escolha";
    private DatabaseReference mDatabase;
    private TextView textViewReceitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha);

        textViewReceitas = findViewById(R.id.textViewReceitas);


        mDatabase = FirebaseDatabase.getInstance("https://appreceitas-71bc5-default-rtdb.firebaseio.com/").getReference("Receitas");
        Log.d(TAG, "Iniciando listener do Firebase para o nó 'Receitas'");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange chamado");
                if (dataSnapshot.exists()) {
                    Log.d(TAG, "Snapshot existe. Valor: " + dataSnapshot.getValue());
                    StringBuilder sb = new StringBuilder();

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String key = snapshot.getKey();
                        Object value = snapshot.getValue();

                        if (key != null && !key.equals("Nome") && !key.equals("tempo") && !key.equals("Ingredientes")) {
                            sb.append(key).append(": ").append(value.toString()).append("\n");
                        }
                    }

                    if (sb.length() == 0) {
                        textViewReceitas.setText("Não foram encontrados ingredientes na receita.");
                    } else {
                        textViewReceitas.setText(sb.toString());
                        Log.d(TAG, "TextView atualizado com sucesso.");
                    }
                } else {
                    Log.d(TAG, "Snapshot não existe no caminho 'Receitas'.");
                    textViewReceitas.setText("Nenhum dado encontrado no caminho 'Receitas'.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                textViewReceitas.setText("Falha ao carregar ingredientes. Verifique o Logcat para erros.");
            }
        });
    }
}
