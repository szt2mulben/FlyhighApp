package hu.bence.flyhigh.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hu.bence.flyhigh.R;
import hu.bence.flyhigh.model.UserModel;
import hu.bence.flyhigh.network.ApiClient;
import hu.bence.flyhigh.network.UseradatokApi;
import hu.bence.flyhigh.ui.adapter.AdminUserAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminFragment extends Fragment implements AdminUserAdapter.AdminUserListener {

    private RecyclerView recyclerAdminUsers;
    private AdminUserAdapter adapter;
    private List<UserModel> userList = new ArrayList<>();

    public AdminFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerAdminUsers = view.findViewById(R.id.recyclerAdminUsers);
        recyclerAdminUsers.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AdminUserAdapter(userList, this);
        recyclerAdminUsers.setAdapter(adapter);

        loadUsers();
    }

    private void loadUsers() {
        UseradatokApi api = ApiClient.getUserApi();
        api.getOsszesUser().enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (!isAdded()) return;
                if (response.isSuccessful() && response.body() != null) {
                    userList = response.body();
                    adapter.updateData(userList);
                } else {
                    Toast.makeText(getContext(), "Nem sikerült betölteni a felhasználókat.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                if (!isAdded()) return;
                Toast.makeText(getContext(), "Hiba: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onTogglePermission(UserModel user) {
        String ujJog = user.getPermission().equalsIgnoreCase("admin")
                ? "Ugyfel"
                : "Admin";

        user.setPermission(ujJog);

        UseradatokApi api = ApiClient.getUserApi();
        api.updateUser(user.getId(), user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!isAdded()) return;
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Jogosultság módosítva: " + ujJog, Toast.LENGTH_SHORT).show();
                    loadUsers();
                } else {
                    Toast.makeText(getContext(), "Nem sikerült módosítani.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (!isAdded()) return;
                Toast.makeText(getContext(), "Hiba: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDeleteUser(UserModel user) {
        UseradatokApi api = ApiClient.getUserApi();
        api.deleteUser(user.getId()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!isAdded()) return;
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Felhasználó törölve.", Toast.LENGTH_SHORT).show();
                    loadUsers();
                } else {
                    Toast.makeText(getContext(), "Nem sikerült törölni.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (!isAdded()) return;
                Toast.makeText(getContext(), "Hiba: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
