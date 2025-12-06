package hu.bence.flyhigh.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hu.bence.flyhigh.R;
import hu.bence.flyhigh.model.GepAdatokModel;

public class GepAdapter extends RecyclerView.Adapter<GepAdapter.GepViewHolder> {

    private List<GepAdatokModel> gepek;   

    public GepAdapter(List<GepAdatokModel> gepek) {
        this.gepek = gepek;
    }

    public void updateData(List<GepAdatokModel> ujLista) {
        this.gepek = ujLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_gep, parent, false);
        return new GepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GepViewHolder holder, int position) {
        GepAdatokModel gep = gepek.get(position);
        holder.textGepNev.setText(gep.getGepneve());
    }

    @Override
    public int getItemCount() {
        return gepek != null ? gepek.size() : 0;
    }

    static class GepViewHolder extends RecyclerView.ViewHolder {
        TextView textGepNev;

        public GepViewHolder(@NonNull View itemView) {
            super(itemView);
            textGepNev = itemView.findViewById(R.id.textGepNev);
        }
    }
}
