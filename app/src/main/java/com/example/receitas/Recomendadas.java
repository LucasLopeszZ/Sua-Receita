package com.example.receitas;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Recomendadas extends AppCompatActivity {

    RecyclerView rv;
    Adaptacao_receitas adapter;
    ArrayList<Receitas> listaReceitas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recomendadas);

        rv = findViewById(R.id.rvRecomendadas);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adaptacao_receitas(this, listaReceitas);
        rv.setAdapter(adapter);

        carregarReceitas();
    }

    private void carregarReceitas() {
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("receitas");

        ref.limitToFirst(5).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaReceitas.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Receitas r = ds.getValue(Receitas.class);
                    listaReceitas.add(r);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FIREBASE", error.getMessage());
            }
        });
    }
}
