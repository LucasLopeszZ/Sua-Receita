package com.example.receitas;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class MeusIngredientes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meus_ingredientes);

        EditText editTexto = findViewById(R.id.texto);
        Button btnContinuar = findViewById(R.id.btn_continuar);
        TextView txtResultado = findViewById(R.id.txt_resultado);

        GeminiApi gemini = new GeminiApi();

        btnContinuar.setOnClickListener(v -> {

            String ingredientes = editTexto.getText().toString().trim();

            if (ingredientes.isEmpty()) {
                txtResultado.setText("Digite pelo menos um ingrediente!");
                return;
            }

            txtResultado.setText("Gerando receitas...");

            new Thread(() -> {

                String resposta = gemini.gerarReceitas(ingredientes);


                Log.d("API_GEMINI", "Resposta da API: " + resposta);

                runOnUiThread(() -> {

                    try {
                        JSONObject json = new JSONObject(resposta);
                        JSONArray candidates = json.getJSONArray("candidates");
                        JSONObject first = candidates.getJSONObject(0);

                        String texto = "";


                        if (first.has("output_text")) {
                            texto = first.getString("output_text");
                        }


                        else if (first.has("content")) {
                            JSONObject content = first.getJSONObject("content");

                            if (content.has("parts")) {
                                JSONArray parts = content.getJSONArray("parts");

                                if (parts.length() > 0 && parts.getJSONObject(0).has("text")) {
                                    texto = parts.getJSONObject(0).getString("text");
                                }
                            }
                        }

                        if (texto.isEmpty()) {
                            texto = "A API respondeu, mas não enviou texto reconhecido.";
                        }

                        txtResultado.setText(texto);

                    } catch (Exception e) {
                        txtResultado.setText("Erro ao interpretar resposta da API: " + e.getMessage());
                    }

                });
            }).start();
        });
    }
}
