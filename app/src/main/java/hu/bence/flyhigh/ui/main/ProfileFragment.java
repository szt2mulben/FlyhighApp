package hu.bence.flyhigh.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hu.bence.flyhigh.R;
import hu.bence.flyhigh.model.JegyModel;
import hu.bence.flyhigh.network.ApiClient;
import hu.bence.flyhigh.ui.main.LoginActivity;
import hu.bence.flyhigh.network.JegykezelesApi;
import hu.bence.flyhigh.session.SessionManager;
import hu.bence.flyhigh.ui.adapter.JegyAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private TextView textName, textEmail, textPermission;
    private RecyclerView recyclerJegyek;
    private JegyAdapter jegyAdapter;
    private List<JegyModel> sajatJegyek = new ArrayList<>();

    public ProfileFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textName = view.findViewById(R.id.textName);
        textEmail = view.findViewById(R.id.textEmail);
        textPermission = view.findViewById(R.id.textPermission);

        Button btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> kijelentkezes());

        recyclerJegyek = view.findViewById(R.id.recyclerJegyek);
        recyclerJegyek.setLayoutManager(new LinearLayoutManager(getContext()));
        jegyAdapter = new JegyAdapter(sajatJegyek);
        recyclerJegyek.setAdapter(jegyAdapter);

        SessionManager sm = SessionManager.getInstance();
        textName.setText("Név: " + sm.getName());
        textEmail.setText("Email: (később API-ból)");
        textPermission.setText("Jogosultság: " + sm.getPermission());

        loadSajatJegyek(sm.getName());
    }

    private void kijelentkezes() {
        SessionManager sm = SessionManager.getInstance();
        sm.clear();

        Intent i = new Intent(getActivity(), LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    private void loadSajatJegyek(String felhasznaloNev) {
        JegykezelesApi api = ApiClient.getJegykezelesApi();
        api.getJegyek().enqueue(new Callback<List<JegyModel>>() {
            @Override
            public void onResponse(Call<List<JegyModel>> call,
                                   Response<List<JegyModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<JegyModel> osszes = response.body();
                    List<JegyModel> filterezett = new ArrayList<>();

                    for (JegyModel j : osszes) {
                        if (j.getMegrendeloNev() != null &&
                                j.getMegrendeloNev().equalsIgnoreCase(felhasznaloNev)) {
                            filterezett.add(j);
                        }
                    }

                    sajatJegyek = filterezett;
                    jegyAdapter.updateData(sajatJegyek);
                } else {
                    Toast.makeText(getContext(),
                            "Nem sikerült betölteni a jegyeket.",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<JegyModel>> call, Throwable t) {
                Toast.makeText(getContext(),
                        "Hálózati hiba: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
