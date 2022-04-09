package rs.raf.projekat1.ognjen_prica_10620.view.fragments;


import static rs.raf.projekat1.ognjen_prica_10620.view.activities.MainActivity.LOGIN_FRAGMENT_TAG;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ognjen_prica_10620.R;
import rs.raf.projekat1.ognjen_prica_10620.model.User;
import rs.raf.projekat1.ognjen_prica_10620.viewmodel.UserViewModel;

public class ProfileFragment extends Fragment {

    private TextView tvUsername;
    private TextView tvEmail;
    private Button btnLogout;

    private UserViewModel viewModel;

    public ProfileFragment() {
        super(R.layout.fragment_profile);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    // private

    private void init(View view) {
        initView(view);
        loadData();
        initListeners();
    }

    private void initView(View view) {
        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        tvUsername = view.findViewById(R.id.tvUsername);
        tvEmail = view.findViewById(R.id.tvEmail);
        btnLogout = view.findViewById(R.id.btnLogout);
    }

    private void loadData() {
        User user = viewModel.getUserData().getValue();
        if (user != null) {
            tvUsername.setText(user.getUsername());
            tvEmail.setText(user.getEmail());
        }
    }

    private void initListeners() {
        btnLogout.setOnClickListener(v -> {
            viewModel.logout();
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fcvMain, new LoginFragment(), LOGIN_FRAGMENT_TAG).commit();
        });
    }
}