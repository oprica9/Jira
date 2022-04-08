package rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.viewpager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.fragments.NewTicketFragment;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.fragments.ProfileFragment;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.fragments.StatisticsFragment;
import rs.raf.projekat1.rsrafprojekat1ognjen_prica_10620.view.fragments.TicketsFragment;

public class PagerAdapter extends FragmentPagerAdapter {

    private final int ITEM_COUNT = 4;
    public static final int FRAGMENT_1 = 0;
    public static final int FRAGMENT_2 = 1;
    public static final int FRAGMENT_3 = 2;
    public static final int FRAGMENT_4 = 3;

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch(position){
            case FRAGMENT_1: fragment = new StatisticsFragment(); break;
            case FRAGMENT_2: fragment = new NewTicketFragment(); break;
            case FRAGMENT_3: fragment = new TicketsFragment(); break;
            default: fragment = new ProfileFragment(); break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

}
