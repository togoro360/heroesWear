package common.controls;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;



public class SmoothActionBarDrawerToggle extends ActionBarDrawerToggle {

    private Runnable runnable;

    public SmoothActionBarDrawerToggle(Activity activity, DrawerLayout drawerLayout,
                                       @StringRes int openDrawerContentDescRes,
                                       @StringRes int closeDrawerContentDescRes) {
        super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        super.onDrawerClosed(drawerView);
        if (runnable != null) {
            runnable.run();
            runnable = null;
        }
    }

    public void runWhenIdle(Runnable runnable) {
        this.runnable = runnable;
    }

    //disable arrow to hamburger rotation animation
    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {
        // super.onDrawerSlide(drawerView, 0); // this disables the animation
    }

    @Override
    public void onDrawerOpened(View drawerView) {
        super.onDrawerSlide(drawerView, 0);
    }

}
