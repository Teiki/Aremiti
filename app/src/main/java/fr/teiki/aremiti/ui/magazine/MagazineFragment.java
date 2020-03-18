package fr.teiki.aremiti.ui.magazine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import fr.teiki.aremiti.R;

public class MagazineFragment extends Fragment {

	private MagazineViewModel magazineViewModel;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		magazineViewModel =
				ViewModelProviders.of(this).get(MagazineViewModel.class);
		View root = inflater.inflate(R.layout.fragment_magazine, container, false);
		final TextView textView = root.findViewById(R.id.text_dashboard);
		magazineViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
			@Override
			public void onChanged(@Nullable String s) {
				textView.setText(s);
			}
		});
		return root;
	}
}
