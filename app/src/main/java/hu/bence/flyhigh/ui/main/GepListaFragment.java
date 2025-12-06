package hu.bence.flyhigh.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hu.bence.flyhigh.R;
import hu.bence.flyhigh.model.GepAdatokModel;
import hu.bence.flyhigh.network.ApiClient;
import hu.bence.flyhigh.network.GepAdatokApi;
import hu.bence.flyhigh.ui.adapter.GepAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GepListaFragment extends Fragment {

    private RecyclerView recyclerGepek;
    private ProgressBar progressBar;
    private GepAdapter adapter;
    private List<GepAdatokModel> gepek = new ArrayList<>();

    public GepListaFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_gep_lista, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerGepek = view.findViewById(R.id.recyclerGepek);
        progressBar = view.findViewById(R.id.progressBarGepek);

        recyclerGepek.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new GepAdapter(gepek);
        recyclerGepek.setAdapter(adapter);

        loadGepek();
    }

    private void loadGepek() {
        progressBar.setVisibility(View.VISIBLE);

        GepAdatokApi api = ApiClient.getGepAdatokApi();
        api.getOsszesGep().enqueue(new Callback<List<GepAdatokModel>>() {
            @Override
            public void onResponse(Call<List<GepAdatokModel>> call,
                                   Response<List<GepAdatokModel>> response) {
                if (!isAdded()) return;
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    adapter.updateData(response.body());
                } else {
                    Toast.makeText(getContext(),
                            "Nem sikerült betölteni a gépadatokat.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GepAdatokModel>> call, Throwable t) {
                if (!isAdded()) return;
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),
                        "Hiba: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
