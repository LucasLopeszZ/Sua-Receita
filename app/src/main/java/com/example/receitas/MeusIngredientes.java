package com.example.receitas;

import android.os.Bundle;
import android.text.Html;
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


                        if (!resposta.trim().startsWith("{")) {
                            txtResultado.setText("Erro da API:\n" + resposta);
                            return;
                        }


                        JSONObject json = new JSONObject(resposta);
                        JSONArray candidates = json.getJSONArray("candidates");
                        JSONObject firstCandidate = candidates.getJSONObject(0);

                        StringBuilder fullText = new StringBuilder();

                        if (firstCandidate.has("content")) {
                            JSONObject content = firstCandidate.getJSONObject("content");
                            if (content.has("parts")) {
                                JSONArray parts = content.getJSONArray("parts");
                                for (int i = 0; i < parts.length(); i++) {
                                    JSONObject part = parts.getJSONObject(i);
                                    if (part.has("text")) {
                                        fullText.append(part.getString("text"));
                                    }
                                }
                            }
                        }

                        String textoFinal;
                        if (fullText.length() > 0) {
                            textoFinal = formatRecipeText(fullText.toString());
                        } else {
                            textoFinal = "A API respondeu, mas não enviou texto reconhecido.";
                        }

                        txtResultado.setText(Html.fromHtml(textoFinal, Html.FROM_HTML_MODE_LEGACY));

                    } catch (Exception e) {
                        Log.e("API_GEMINI_ERROR", "Erro ao interpretar resposta da API", e);
                        txtResultado.setText("Erro ao interpretar resposta da API: " + e.getMessage());
                    }
                });
            }).start();
        });
    }

    private String formatRecipeText(String rawText) {
        String formattedText = rawText.replaceAll("\\*\\*(.*?)\\*\\*", "<b>$1</b>");

        formattedText = formattedText.replaceAll("\n", "<br>");

        formattedText = formattedText.replaceAll("\\*", "• ");
        formattedText = formattedText.replaceAll("- ", "<br>• ");

        return formattedText;
    }
}
