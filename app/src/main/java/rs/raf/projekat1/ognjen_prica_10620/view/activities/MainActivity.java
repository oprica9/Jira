package rs.raf.projekat1.ognjen_prica_10620.view.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import ognjen_prica_10620.R;
import rs.raf.projekat1.ognjen_prica_10620.view.fragments.LoginFragment;
import rs.raf.projekat1.ognjen_prica_10620.view.fragments.MainFragment;
import rs.raf.projekat1.ognjen_prica_10620.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    public static final String USER = "userLoggedIn";
    public static final String MAIN_FRAGMENT_TAG = "main_fragment_tag";
    public static final String LOGIN_FRAGMENT_TAG = "login_fragment_tag";

    private UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (viewModel.isLoggedIn()) {
            transaction.replace(R.id.fcvMain, new MainFragment(), MAIN_FRAGMENT_TAG);
        } else {
            transaction.replace(R.id.fcvMain, new LoginFragment(), LOGIN_FRAGMENT_TAG);
        }
        transaction.commit();
    }

    private void loadData() {
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);
        viewModel.loadData(this);
    }

}