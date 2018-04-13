package org.favcode54.adaptor;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import org.favcode54.blog.RecyclerViewFragment;
import org.favcode54.model.Category;

/**
 * Adaptor for ViewPager
 */
public class RecyclerViewFragmentPagerAdaptor extends FragmentPagerAdapter {
    private ArrayList<Category> categories;

    public RecyclerViewFragmentPagerAdaptor(FragmentManager fm, ArrayList<Category> categories) {
        super(fm);
        this.categories = categories;
    }

    @Override
    public Fragment getItem(int position) {
        return RecyclerViewFragment.newInstance(categories.get(position).getId());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categories.get(position).getName();
    }

    @Override
    public int getCount() {
        return categories.size();
    }
}
