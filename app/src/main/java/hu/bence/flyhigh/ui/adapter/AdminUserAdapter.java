package hu.bence.flyhigh.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hu.bence.flyhigh.R;
import hu.bence.flyhigh.model.UserModel;

public class AdminUserAdapter extends RecyclerView.Adapter<AdminUserAdapter.ViewHolder> {

    public interface AdminUserListener {
        void onTogglePermission(UserModel user);
        void onDeleteUser(UserModel user);
    }

    private List<UserModel> users;
    private AdminUserListener listener;

    public AdminUserAdapter(List<UserModel> users, AdminUserListener listener) {
        this.users = users;
        this.listener = listener;
    }

    public void updateData(List<UserModel> newList) {
        this.users = newList;
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
        holder.textUserPermission.setText("Jog: " + u.getPermission());

        holder.btnTogglePermission.setOnClickListener(v -> {
            if (listener != null) listener.onTogglePermission(u);
        });

        holder.btnDeleteUser.setOnClickListener(v -> {
            if (listener != null) listener.onDeleteUser(u);
        });
    }

    @Override
    public int getItemCount() {
        return users != null ? users.size() : 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textUserName, textUserEmail, textUserPermission;
        Button btnTogglePermission, btnDeleteUser;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textUserName = itemView.findViewById(R.id.textUserName);
            textUserEmail = itemView.findViewById(R.id.textUserEmail);
            textUserPermission = itemView.findViewById(R.id.textUserPermission);
            btnTogglePermission = itemView.findViewById(R.id.btnTogglePermission);
            btnDeleteUser = itemView.findViewById(R.id.btnDeleteUser);
        }
    }
}
