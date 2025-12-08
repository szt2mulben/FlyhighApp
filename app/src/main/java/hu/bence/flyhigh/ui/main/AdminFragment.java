package hu.bence.flyhigh.ui.main;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
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

    private RecyclerView recyclerView;
    private AdminUserAdapter adapter;
    private EditText editSearch;
    private Spinner spinnerPermission;

    private final List<UserModel> allUsers = new ArrayList<>();

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

        recyclerView = view.findViewById(R.id.recyclerAdminUsers);
        editSearch = view.findViewById(R.id.editSearch);
        spinnerPermission = view.findViewById(R.id.spinnerPermission);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AdminUserAdapter(this);
        recyclerView.setAdapter(adapter);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                new String[]{"Mind", "Ugyfel", "Admin"}
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPermission.setAdapter(spinnerAdapter);

        setUpFilters();
        loadUsers();
    }

    private void setUpFilters() {
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilter();
            }
            @Override public void afterTextChanged(Editable s) {}
        });

        spinnerPermission.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                applyFilter();
            }
            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) { }
        });
    }

    private void loadUsers() {
        UseradatokApi api = ApiClient.getUserApi();
        api.getUsers().enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {
                if (!isAdded()) return;

                if (response.isSuccessful() && response.body() != null) {
                    allUsers.clear();
                    allUsers.addAll(response.body());
                    applyFilter();
                } else {
                    Toast.makeText(getContext(), "Nem sikerült betölteni a felhasználókat.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<UserModel>> call, Throwable t) {
                if (!isAdded()) return;
                Toast.makeText(getContext(), "Hálózati hiba: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void applyFilter() {
        String search = editSearch.getText().toString().trim().toLowerCase();
        String permFilter = spinnerPermission.getSelectedItem().toString(); 

        List<UserModel> filtered = new ArrayList<>();
        for (UserModel u : allUsers) {
            boolean matchesSearch =
                    search.isEmpty()
                            || (u.getName() != null && u.getName().toLowerCase().contains(search))
                            || (u.getEmail() != null && u.getEmail().toLowerCase().contains(search));

            boolean matchesPerm =
                    permFilter.equals("Mind")
                            || (u.getPermission() != null
                            && u.getPermission().equalsIgnoreCase(permFilter));

            if (matchesSearch && matchesPerm) {
                filtered.add(u);
            }
        }

        adapter.setUsers(filtered);
    }


    @Override
    public void onEditPermission(UserModel user) {
        if (!isAdded()) return;

        String newPerm = user.getPermission() != null
                && user.getPermission().equalsIgnoreCase("Admin") ? "Ugyfel" : "Admin";

        new AlertDialog.Builder(requireContext())
                .setTitle("Jogosultság módosítása")
                .setMessage("Biztosan módosítod " + user.getName() + " jogosultságát \"" +
                        user.getPermission() + "\" → \"" + newPerm + "\" ?")
                .setPositiveButton("Igen", (dialog, which) -> {
                    user.setPermission(newPerm);
                    saveUser(user);
                })
                .setNegativeButton("Mégse", null)
                .show();
    }

    private void saveUser(UserModel user) {
        UseradatokApi api = ApiClient.getUserApi();
        api.updateUser(user.getId(), user).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!isAdded()) return;
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Jogosultság módosítva: " + user.getPermission(), Toast.LENGTH_SHORT).show();
                    loadUsers();
                } else {
                    Toast.makeText(getContext(), "Nem sikerült módosítani.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                if (!isAdded()) return;
                Toast.makeText(getContext(), "Hálózati hiba: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDeleteUser(UserModel user) {
        if (!isAdded()) return;

        new AlertDialog.Builder(requireContext())
                .setTitle("Felhasználó törlése")
                .setMessage("Biztosan törlöd a következő felhasználót: " + user.getName() + "?")
                .setPositiveButton("Törlés", (dialog, which) -> {
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
                            Toast.makeText(getContext(), "Hálózati hiba: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Mégse", null)
                .show();
    }
}
