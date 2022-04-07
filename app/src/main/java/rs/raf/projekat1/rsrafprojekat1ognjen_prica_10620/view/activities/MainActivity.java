package rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.activities;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.R;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.fragments.LoginFragment;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.fragments.MainFragment;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    public static final String USER = "userLoggedIn";
    private UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // todo splashScreen
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(this::init);
        setContentView(R.layout.activity_main);
        init();
    }

    public void setActionBarTitle(String title) {
        setTitle(title);    // as you used custom view
    }

    // private
    private boolean init() {
        loadData();
        initView();
        return false;
    }

    private void initView() {
        if (viewModel.isLoggedIn()) {
            initFragment(new MainFragment());
        } else {
            initFragment(new LoginFragment());
        }
    }

    public void initFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fcvMain, fragment);
        transaction.commit();
    }

    private void loadData() {
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.loadData(this);
    }
}