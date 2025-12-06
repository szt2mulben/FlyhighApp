package hu.bence.flyhigh.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import hu.bence.flyhigh.R;
import hu.bence.flyhigh.model.LoginRequest;
import hu.bence.flyhigh.model.LoginTokenResponse;
import hu.bence.flyhigh.network.ApiClient;
import hu.bence.flyhigh.network.UseradatokApi;
import hu.bence.flyhigh.session.SessionManager;
import hu.bence.flyhigh.util.JwtUtils;
import hu.bence.flyhigh.util.JwtUtils.JwtUserInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText editUsername;
    private EditText editPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> doLogin());
    }

    private void doLogin() {
        String username = editUsername.getText().toString().trim();
        String password = editPassword.getText().toString().trim();

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Töltsd ki a mezőket!", Toast.LENGTH_SHORT).show();
            return;
        }

        UseradatokApi api = ApiClient.getUserApi();
        LoginRequest request = new LoginRequest(username, password);

        api.login(request).enqueue(new Callback<LoginTokenResponse>() {
            @Override
            public void onResponse(Call<LoginTokenResponse> call, Response<LoginTokenResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    String token = response.body().getToken();
                    JwtUserInfo info = JwtUtils.decode(token);

                    if (info == null) {
                        Toast.makeText(LoginActivity.this,
                                "Token feldolgozási hiba",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                    SessionManager.getInstance().setUser(
                            token,
                            info.userId,
                            info.name,
                            info.permission
                    );

                    Toast.makeText(LoginActivity.this,
                            "Bejelentkezve: " + info.name + " (" + info.permission + ")",
                            Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,
                            "Hibás felhasználónév vagy jelszó",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginTokenResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,
                        "Hálózati hiba: " + t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
