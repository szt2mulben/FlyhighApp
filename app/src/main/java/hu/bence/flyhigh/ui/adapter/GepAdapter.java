package hu.bence.flyhigh.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hu.bence.flyhigh.R;
import hu.bence.flyhigh.model.GepAdatokModel;

public class GepAdapter extends RecyclerView.Adapter<GepAdapter.GepViewHolder> {

    private List<GepAdatokModel> gepek = new ArrayList<>();

    public GepAdapter(List<GepAdatokModel> gepek) {
        if (gepek != null) {
            this.gepek = gepek;
        }
    }

    public void updateData(List<GepAdatokModel> ujLista) {
        gepek = ujLista != null ? ujLista : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gep, parent, false);
        return new GepViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GepViewHolder holder, int position) {
        GepAdatokModel gep = gepek.get(position);

        holder.textGepNev.setText(gep.getGepneve());

        String turista = "Turista ülőhelyek: "
                + gep.getTuristaUlohelyek()
                + " (foglalva: " + gep.getFoglaltTurista() + ")";
        holder.textTuristaAdatok.setText(turista);

        String elso = "Első osztály ülőhelyek: "
                + gep.getElsoOsztUlohelyek()
                + " (foglalva: " + gep.getFoglaltElsoOsztaly() + ")";
        holder.textElsoAdatok.setText(elso);

        holder.textModositas.setOnClickListener(v ->
                Toast.makeText(v.getContext(),
                        "Módosítás (demó, nincs még implementálva)",
                        Toast.LENGTH_SHORT).show());

        holder.textTorles.setOnClickListener(v ->
                Toast.makeText(v.getContext(),
                        "Törlés (demó, nincs még implementálva)",
                        Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount() {
        return gepek != null ? gepek.size() : 0;
    }

    static class GepViewHolder extends RecyclerView.ViewHolder {

        TextView textGepNev;
        TextView textTuristaAdatok;
        TextView textElsoAdatok;
        TextView textModositas;
        TextView textTorles;

        public GepViewHolder(@NonNull View itemView) {
            super(itemView);
            textGepNev = itemView.findViewById(R.id.textGepNev);
            textTuristaAdatok = itemView.findViewById(R.id.textTuristaAdatok);
            textElsoAdatok = itemView.findViewById(R.id.textElsoAdatok);
            textModositas = itemView.findViewById(R.id.textModositas);
            textTorles = itemView.findViewById(R.id.textTorles);
        }
    }
}
