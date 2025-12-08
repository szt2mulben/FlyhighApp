package hu.bence.flyhigh.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hu.bence.flyhigh.R;
import hu.bence.flyhigh.model.UserModel;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.ViewHolder> {

    public interface AdminUserListener {
        void onEditPermission(UserModel user);
        void onDeleteUser(UserModel user);
    }

    private List<UserModel> users = new ArrayList<>();
    private final AdminUserListener listener;

    public AdminUserAdapter(AdminUserListener listener) {
        this.listener = listener;
    }

    public void setUsers(List<UserModel> newUsers) {
        this.users = newUsers != null ? newUsers : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_admin_user, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel u = users.get(position);

        holder.textUserName.setText(u.getName());
        holder.textUserEmail.setText(u.getEmail());
        holder.textPasswordHidden.setText("Jelszó rejtve");
        holder.textPermission.setText("Jogosultság: " + u.getPermission());

        holder.btnEdit.setOnClickListener(v -> {
            if (listener != null) listener.onEditPermission(u);
        });

        holder.btnDelete.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteUser(u);
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textUserName, textUserEmail, textPasswordHidden, textPermission;
        TextView btnEdit, btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textUserName = itemView.findViewById(R.id.textUserName);
            textUserEmail = itemView.findViewById(R.id.textUserEmail);
            textPasswordHidden = itemView.findViewById(R.id.textPasswordHidden);
            textPermission = itemView.findViewById(R.id.textPermission);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
