package hu.bence.flyhigh.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import hu.bence.flyhigh.R;

public class AboutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView heroImage = view.findViewById(R.id.heroImage);
        String imageUrl = "https://images.pexels.com/photos/3184291/pexels-photo-3184291.jpeg?auto=compress";

        Glide.with(requireContext())
                .load(imageUrl)
                .centerCrop()
                .into(heroImage);

        view.findViewById(R.id.btnJegyvasarlas).setOnClickListener(v ->
                Toast.makeText(requireContext(),
                        "Mobil kliensben a jegyvásárlás a Jegyek menüpontban érhető el.",
                        Toast.LENGTH_SHORT).show()
        );

        view.findViewById(R.id.btnHotelek).setOnClickListener(v ->
                Toast.makeText(requireContext(),
                        "A hotelek jelenleg csak a webes felületen foglalhatók.",
                        Toast.LENGTH_SHORT).show()
        );
    }
}
