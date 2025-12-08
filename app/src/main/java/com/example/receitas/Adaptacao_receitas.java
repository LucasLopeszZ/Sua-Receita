package com.example.receitas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adaptacao_receitas extends RecyclerView.Adapter<Adaptacao_receitas.ViewHolder> {

    private Context context;
    private List<Receitas> lista;

    public Adaptacao_receitas(Context context, List<Receitas> lista) {
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_receita, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Receitas r = lista.get(position);

        holder.txtNome.setText(r.nome);
        holder.txtCategoria.setText("Categoria: " + r.categoria);
        holder.txtInfo.setText("Tempo: " + r.tempo_preparo + " min | Porções: " + r.porcoes +
                " | Dificuldade: " + r.dificuldade);

        holder.txtListaIngredientes.setText(formatarLista(r.ingredientes));
        holder.txtListaModo.setText(formatarLista(r.modo_preparo));
        holder.txtObservacoes.setText(r.observacoes);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtNome, txtCategoria, txtInfo;
        TextView txtIngredientes, txtListaIngredientes;
        TextView txtModoPreparo, txtListaModo;
        TextView txtObs, txtObservacoes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNome = itemView.findViewById(R.id.txtNome);
            txtCategoria = itemView.findViewById(R.id.txtCategoria);
            txtInfo = itemView.findViewById(R.id.txtInfo);
            txtIngredientes = itemView.findViewById(R.id.txtIngredientes);
            txtListaIngredientes = itemView.findViewById(R.id.txtListaIngredientes);
            txtModoPreparo = itemView.findViewById(R.id.txtModoPreparo);
            txtListaModo = itemView.findViewById(R.id.txtListaModo);
            txtObs = itemView.findViewById(R.id.txtObs);
            txtObservacoes = itemView.findViewById(R.id.txtObservacoes);
        }
    }

    private String formatarLista(List<String> lista) {
        StringBuilder sb = new StringBuilder();
        for (String item : lista) {
            sb.append("• ").append(item).append("\n");
        }
        return sb.toString();
    }
}
