package se.netdev.allakartor;

import se.netdev.allakartor.activities.BaseActivity;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentTransaction;

import android.app.ActionBar;
import android.app.ActionBar.Tab;

public class TabListener<T extends Fragment> implements ActionBar.TabListener {
    private final BaseActivity activity;
    private final String tag;
    private final Class<T> clz;
    private final Bundle args;
    private Fragment fragment;

    public TabListener(BaseActivity activity, String tag, Class<T> clz) {
        this(activity, tag, clz, null);
    }

    public TabListener(BaseActivity activity, String tag, Class<T> clz, Bundle args) {
        this.activity = activity;
        this.tag = tag;
        this.clz = clz;
        this.args = args;

        // Check to see if we already have a fragment for this tab, probably
        // from a previously saved state.  If so, deactivate it, because our
        // initial state is that a tab isn't shown.
        fragment = activity.getFragmentManager().findFragmentByTag(tag);
        if (fragment != null && !fragment.isDetached()) {
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            ft.detach(fragment);
            ft.commit();
        }
    }

    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        if (fragment == null) {
            fragment = Fragment.instantiate(activity, clz.getName(), args);
            ft.add(android.R.id.content, fragment, tag);
        } else {
            ft.attach(fragment);
        }
    }

    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        if (fragment != null) {
            ft.detach(fragment);
        }
    }

    public void onTabReselected(Tab tab, FragmentTransaction ft) {
       
    }
}
