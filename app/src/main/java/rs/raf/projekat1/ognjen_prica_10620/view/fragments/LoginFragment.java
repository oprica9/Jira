package rs.raf.projekat1.ognjen_prica_10620.view.fragments;


import static rs.raf.projekat1.ognjen_prica_10620.view.activities.MainActivity.MAIN_FRAGMENT_TAG;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import ognjen_prica_10620.R;
import rs.raf.projekat1.ognjen_prica_10620.view.activities.MainActivity;
import rs.raf.projekat1.ognjen_prica_10620.viewmodel.UserViewModel;

public class LoginFragment extends Fragment {

    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;

    private UserViewModel viewModel;

    public LoginFragment() {
        super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    // private
    private void init(View view) {
        initView(view);
        initListeners();
    }

    private void initView(View view) {
        // s ovim vezujemo viewmodel za fragment
        // viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // s ovim vezujemo viewmodel za activity
        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        if (getActivity() != null)
            ((MainActivity) getActivity()).setActionBarTitle("Login");

        btnLogin = view.findViewById(R.id.btnLogin);
        etUsername = view.findViewById(R.id.etUsername);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
    }

    private void initListeners() {

        // attempt login
        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            try {
                if (viewModel.login(username, email, password)) {
                    Toast.makeText(getActivity(), "Logged in!", Toast.LENGTH_SHORT).show();

                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.fcvMain, new MainFragment(), MAIN_FRAGMENT_TAG);
                    // ovo ispod daje mogucnost korisniku da se vrati nazad
                    // transaction.addToBackStack(null);
                    transaction.commit();
                }
            } catch (RuntimeException e) {
                Toast.makeText(requireContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
