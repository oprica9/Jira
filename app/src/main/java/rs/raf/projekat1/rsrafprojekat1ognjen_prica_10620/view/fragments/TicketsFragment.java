package rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.fragments;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.R;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.viewpager.TicketPagerAdapter;


public class TicketsFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;


    public TicketsFragment() {
        super(R.layout.fragment_tickets);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        initView(view);
        initTabs();
    }

    private void initView(View view) {
        viewPager = view.findViewById(R.id.viewPagerTickets);
        tabLayout = view.findViewById(R.id.tabLayout);
    }

    private void initTabs() {
        viewPager.setAdapter(new TicketPagerAdapter(getChildFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

}
