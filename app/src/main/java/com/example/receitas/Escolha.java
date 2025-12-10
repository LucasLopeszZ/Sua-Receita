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

public class Escolha extends AppCompatActivity {

    private static final String TAG = "Escolha";
    private DatabaseReference mDatabase;

    private RecyclerView rv;
    private Adaptacao_receitas adapter;
    private ArrayList<Receitas> listaReceitas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha);

        rv = findViewById(R.id.rvEscolha);
        rv.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adaptacao_receitas(this, listaReceitas);
        rv.setAdapter(adapter);

        mDatabase = FirebaseDatabase
                .getInstance("https://appreceitas-71bc5-default-rtdb.firebaseio.com/")
                .getReference("receitas");

        carregarReceitas();
    }

    private void carregarReceitas() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaReceitas.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    Receitas r = ds.getValue(Receitas.class);

                    if (r != null) {
                        listaReceitas.add(r);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "Erro Firebase", error.toException());
            }
        });
    }
}
