package hu.bence.flyhigh.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hu.bence.flyhigh.R;
import hu.bence.flyhigh.model.JegyAdatokModel;

public class JegyAdatokAdapter extends RecyclerView.Adapter<JegyAdatokAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(JegyAdatokModel jegy);
    }

    private List<JegyAdatokModel> jegyek;
    private OnItemClickListener listener;

    public JegyAdatokAdapter(List<JegyAdatokModel> jegyek, OnItemClickListener listener) {
        this.jegyek = jegyek;
        this.listener = listener;
    }

    public void updateData(List<JegyAdatokModel> ujLista) {
        this.jegyek = ujLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JegyAdatokAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_jegyadat, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull JegyAdatokAdapter.ViewHolder holder, int position) {
        JegyAdatokModel j = jegyek.get(position);

        String route = j.getHonnan() + " → " + j.getHova();
        holder.textRoute.setText(route);

        String dateStr = j.getIdo();
        if (dateStr != null && dateStr.length() >= 10) {
            dateStr = dateStr.substring(0, 10);
        }

        String timeStr = String.format("%02d:%02d", j.getOra(), j.getPerc());

        String dt = "Dátum: " + dateStr + "   Idő: " + timeStr;
        holder.textDateTime.setText(dt);


        int ar = 20000 + (int)(Math.random() * 60000);
        holder.textAr.setText("Ár: " + ar + " Ft");

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(j);
        });
    }

    @Override
    public int getItemCount() {
        return jegyek != null ? jegyek.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textRoute, textDateTime, textAr;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textRoute = itemView.findViewById(R.id.textJegyRoute);
            textDateTime = itemView.findViewById(R.id.textJegyDateTime);
            textAr = itemView.findViewById(R.id.textJegyAr); // 🔥 FONTOS
        }
    }
}
