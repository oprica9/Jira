package rs.raf.projekat1.ognjen_prica_10620.view.fragments;

import static rs.raf.projekat1.ognjen_prica_10620.view.viewpager.PagerAdapter.FRAGMENT_1;
import static rs.raf.projekat1.ognjen_prica_10620.view.viewpager.PagerAdapter.FRAGMENT_2;
import static rs.raf.projekat1.ognjen_prica_10620.view.viewpager.PagerAdapter.FRAGMENT_3;
import static rs.raf.projekat1.ognjen_prica_10620.view.viewpager.PagerAdapter.FRAGMENT_4;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ognjen_prica_10620.R;
import rs.raf.projekat1.ognjen_prica_10620.view.activities.MainActivity;
import rs.raf.projekat1.ognjen_prica_10620.view.viewpager.PagerAdapter;
import rs.raf.projekat1.ognjen_prica_10620.viewmodel.UserViewModel;

public class MainFragment extends Fragment {

    private UserViewModel viewModel;
    private ViewPager viewPager;

    public MainFragment() {
        super(R.layout.fragment_main);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    // private

    private void init(View view) {
        initView(view);
        initViewPager(view);
        initNavigation(view);
        initListeners();
    }

    private void initViewPager(View view) {
        viewPager = view.findViewById(R.id.viewPagerMain);
        // ovde je parent i dalje activity
        viewPager.setAdapter(new PagerAdapter(getChildFragmentManager()));
    }

    private void initNavigation(View view) {

        ((BottomNavigationView) (view).findViewById(R.id.bottomNavBar)).setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_1:
                    viewPager.setCurrentItem(FRAGMENT_1, false);
                    break;
                case R.id.navigation_2:
                    viewPager.setCurrentItem(FRAGMENT_2, false);
                    break;
                case R.id.navigation_3:
                    viewPager.setCurrentItem(FRAGMENT_3, false);
                    break;
                default:
                    viewPager.setCurrentItem(FRAGMENT_4, false);
                    break;
            }
            return true;
        });
    }

    private void initView(View view) {
        // s ovim vezujemo viewmodel za fragment
        // viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // s ovim vezujemo viewmodel za activity
        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        if (getActivity() != null)
            ((MainActivity) getActivity()).setActionBarTitle("Main");

    }

    private void initListeners() {


    }

}
