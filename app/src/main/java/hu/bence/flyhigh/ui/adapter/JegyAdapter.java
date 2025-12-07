package hu.bence.flyhigh.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hu.bence.flyhigh.R;
import hu.bence.flyhigh.model.JegyModel;

public class JegyAdapter extends RecyclerView.Adapter<JegyAdapter.JegyViewHolder> {

    private List<JegyModel> jegyek;

    public JegyAdapter(List<JegyModel> jegyek) {
        this.jegyek = jegyek;
    }

    public void updateData(List<JegyModel> ujLista) {
        jegyek = ujLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JegyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_jegy, parent, false);
        return new JegyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull JegyViewHolder holder, int position) {
        JegyModel j = jegyek.get(position);

        holder.textRoute.setText(j.getIndulasHely() + " → " + j.getErkezesHely());
        holder.textTimes.setText("Indulás: " + j.getIndulasIdo() + "   Érkezés: " + j.getErkezesIdo());
        holder.textClassPrice.setText("Osztály: " + j.getOsztaly() + " | Ár: " + j.getAr() + " Ft");
        holder.textSeats.setText("Székek: " + j.getSorSzek());
    }

    @Override
    public int getItemCount() {
        return jegyek != null ? jegyek.size() : 0;
    }

    static class JegyViewHolder extends RecyclerView.ViewHolder {

        TextView textRoute, textTimes, textClassPrice, textSeats;

        public JegyViewHolder(@NonNull View itemView) {
            super(itemView);

            textRoute = itemView.findViewById(R.id.textRoute);
            textTimes = itemView.findViewById(R.id.textTimes);
            textClassPrice = itemView.findViewById(R.id.textClassPrice);
            textSeats = itemView.findViewById(R.id.textSeats);
        }
    }
}
