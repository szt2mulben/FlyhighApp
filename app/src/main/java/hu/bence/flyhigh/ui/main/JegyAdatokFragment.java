package hu.bence.flyhigh.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hu.bence.flyhigh.R;
import hu.bence.flyhigh.model.JegyAdatokModel;
import hu.bence.flyhigh.network.ApiClient;
import hu.bence.flyhigh.network.JegyAdatokApi;
import hu.bence.flyhigh.ui.adapter.JegyAdatokAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JegyAdatokFragment extends Fragment {

    private RecyclerView recyclerJegyadatok;
    private ProgressBar progressBar;
    private TextView textEmpty;
    private JegyAdatokAdapter adapter;
    private List<JegyAdatokModel> jegyek = new ArrayList<>();

    public JegyAdatokFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_jegyadatok, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerJegyadatok = view.findViewById(R.id.recyclerJegyadatok);
        progressBar = view.findViewById(R.id.progressBarJegyadatok);
        textEmpty = view.findViewById(R.id.textJegyadatokEmpty);

        recyclerJegyadatok.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new JegyAdatokAdapter(jegyek, jegy -> {

            Toast.makeText(getContext(),
                    "Kiválasztott: " + jegy.getHonnan() + " → " + jegy.getHova(),
                    Toast.LENGTH_SHORT).show();
        });
        recyclerJegyadatok.setAdapter(adapter);

        loadJegyek();
    }

    private void loadJegyek() {
        progressBar.setVisibility(View.VISIBLE);
        textEmpty.setVisibility(View.GONE);

        JegyAdatokApi api = ApiClient.getJegyAdatokApi();
        api.getJegyadatok().enqueue(new Callback<List<JegyAdatokModel>>() {
            @Override
            public void onResponse(Call<List<JegyAdatokModel>> call,
                                   Response<List<JegyAdatokModel>> response) {
                if (!isAdded()) return;

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    List<JegyAdatokModel> list = response.body();
                    if (list.isEmpty()) {
                        textEmpty.setVisibility(View.VISIBLE);
                    } else {
                        adapter.updateData(list);
                    }
                } else {
                    Toast.makeText(getContext(),
                            "Hiba a jegyadatok betöltésekor.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<JegyAdatokModel>> call, Throwable t) {
                if (!isAdded()) return;
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),
                        "Hálózati hiba: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
