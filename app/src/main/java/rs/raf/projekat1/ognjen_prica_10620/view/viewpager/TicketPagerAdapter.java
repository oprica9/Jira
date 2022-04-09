package rs.raf.projekat1.ognjen_prica_10620.view.viewpager;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import ognjen_prica_10620.R;
import rs.raf.projekat1.ognjen_prica_10620.view.fragments.DoneFragment;
import rs.raf.projekat1.ognjen_prica_10620.view.fragments.InProgressFragment;
import rs.raf.projekat1.ognjen_prica_10620.view.fragments.ToDoFragment;

public class TicketPagerAdapter extends FragmentPagerAdapter {

    private final int ITEM_COUNT = 3;
    public static final int FRAGMENT_1 = 0;
    public static final int FRAGMENT_2 = 1;
    public static final int FRAGMENT_3 = 2;
    private Context context;

    public TicketPagerAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case FRAGMENT_1:
                fragment = new ToDoFragment();
                break;
            case FRAGMENT_2:
                fragment = new InProgressFragment();
                break;
            default:
                fragment = new DoneFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case FRAGMENT_1:
                return context.getString(R.string.todo);
            case FRAGMENT_2:
                return context.getString(R.string.in_progress);
            default:
                return context.getString(R.string.done);
        }
    }
}