package hu.bence.flyhigh.ui.main;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import hu.bence.flyhigh.R;
import hu.bence.flyhigh.ui.main.GepListaFragment;
import hu.bence.flyhigh.ui.main.AdminFragment;
import hu.bence.flyhigh.session.SessionManager;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottom_nav);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_gepek) {
                loadFragment(new GepListaFragment());
                return true;

            }else if (id == R.id.nav_jegyadatok) {
                loadFragment(new JegyAdatokFragment());
                return true;
            }

            else if (id == R.id.nav_profile) {
                loadFragment(new ProfileFragment());
                return true;

            } else if (id == R.id.nav_about) {
                loadFragment(new AboutFragment());
                return true;
            }else if (id == R.id.nav_admin) {
                SessionManager sm = SessionManager.getInstance();
                if (sm.isAdmin()) {
                    loadFragment(new AdminFragment());
                } else {
                    Toast.makeText(this, "Nincs jogosultságod az admin felülethez.", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });


        if (savedInstanceState == null) {
            bottomNav.setSelectedItemId(R.id.nav_gepek);
        }
    }

    private void loadFragment(@NonNull Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container_view, fragment)
                .commit();
    }
}
