package de.dominikwieners.working.ui.activities.main;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hannesdorfmann.mosby3.mvp.MvpActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.dominikwieners.working.Config;
import de.dominikwieners.working.R;
import de.dominikwieners.working.Navigator;
import de.dominikwieners.working.data.Work;
import de.dominikwieners.working.di.wkApplication;
import de.dominikwieners.working.presenter.ActivityMainPresenter;
import de.dominikwieners.working.ui.activities.main.adapter.MainPagerAdapter;
import de.dominikwieners.working.ui.activities.main.fragments.MonthFragment;
import de.dominikwieners.working.ui.view.ActivityMainView;
import es.dmoral.toasty.Toasty;

public class MainActivity extends MvpActivity<ActivityMainView, ActivityMainPresenter> {

    @BindView(R.id.main_drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.main_drawer_navigation_view)
    NavigationView navigatorView;

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;

    @BindView(R.id.main_tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.main_view_pager)
    ViewPager viewPager;

    @BindView(R.id.main_fab)
    FloatingActionButton fab;

    @BindView(R.id.main_hours)
    TextView tvhours;

    @Inject
    SharedPreferences sharedPreferences;

    @Inject
    Navigator navigator;

    MainPagerAdapter mainPagerAdapter;

    private String[] months;

    private List<Integer> yearList;

    private List<Work> works;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_main_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        if (getIntent().hasExtra(Config.SELECTED_YEAR)) {
            getPresenter().setSelectedYear(getIntent().getExtras().getInt(Config.SELECTED_YEAR));
        } else {
            getPresenter().setSelectedYear(getPresenter().getCurrentYear());
        }

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        yearList = presenter.loadYearData(getApplicationContext());
        getPresenter().addYearMenuItemsToDrawer(yearList, navigatorView.getMenu(), getApplicationContext());
        navigatorView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                for (Integer integer : yearList) {
                    if (item.getItemId() == integer.intValue()) {
                        getPresenter().setSelectedYear(integer.intValue());
                        navigator.showMainActivityWithYear(MainActivity.this, integer.intValue());
                    }
                }
                return true;
            }

        });

        ((wkApplication) getApplication()).getComponent().inject(this);

        mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        months = getResources().getStringArray(R.array.months);
        for (int i = 0; i < months.length; i++) {
            works = getPresenter().loadWorkDataByMonth(getApplicationContext(), getPresenter().getSelectedYear(), i);
            mainPagerAdapter.addFragment(months[i], MonthFragment.newInstance(works));
        }
        viewPager.setAdapter(mainPagerAdapter);
        viewPager.setCurrentItem(getPresenter().getSelectedMonthByExtra(getIntent()), true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffset > 0) {
                    fab.hide();
                }
                tvhours.setText(getPresenter().getSumOfHoursOfMonth(getApplicationContext(), presenter.getSelectedYear(), position));
                final int pos = position;
                ((MonthFragment) mainPagerAdapter.getItem(position)).getRecycler().addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
                    @Override
                    public void onChildViewAttachedToWindow(View view) {

                    }

                    @Override
                    public void onChildViewDetachedFromWindow(View view) {
                        tvhours.setText(getPresenter().getSumOfHoursOfMonth(getApplicationContext(), presenter.getSelectedYear(), pos));
                    }
                });
                getPresenter().setCurrentPagerPage(position);

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                fab.show();
            }
        });

        tabLayout.setupWithViewPager(viewPager);
    }

    @NonNull
    @Override
    public ActivityMainPresenter createPresenter() {
        return new ActivityMainPresenter();
    }

    @OnClick(R.id.main_fab)
    public void onClickFab() {
        navigator.showAddWorkingActivityWithExtras(this);
    }

    @Override
    protected void onStart() {
        if (getPresenter().checkIfNextDone(sharedPreferences) == 0) {
            navigator.showWelcomeActivity(this);
        }
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        menu.findItem(R.id.main_menu_year_item).setTitle(Integer.toString(getPresenter().getSelectedYear()));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_delete_year_item:
                if (!yearList.isEmpty()) {
                    getPresenter().deleteWorkByYear(getPresenter().getSelectedYear(), this);
                    navigator.showMainActivityWithYear(this, getPresenter().getCurrentYear());
                    Toasty.success(this, String.format(getString(R.string.main_year_delete_message), Integer.toString(getPresenter().getSelectedYear())), Toast.LENGTH_LONG, false).show();
                } else {
                    Toasty.error(this, getString(R.string.main_year_deleted_message_error), Toast.LENGTH_LONG, false).show();
                }
                break;
            case R.id.main_menu_delete_month_item:
                List<Work> list = presenter.loadWorkDataByMonth(this, getPresenter().getSelectedYear(), getPresenter().getCurrentPagerPosition());
                if (!list.isEmpty()) {
                    getPresenter().deleteWorkByMonth(getPresenter().getSelectedYear(), getPresenter().getCurrentPagerPosition(), this);
                    navigator.showMainActivityWithPositionAndYear(this, getPresenter().getCurrentPagerPosition(), getPresenter().getSelectedYear());

                    Toasty.success(this, String.format(getString(R.string.main_month_delete_message), months[getPresenter().getCurrentPagerPosition()]), Toast.LENGTH_LONG, false).show();
                } else {
                    Toasty.error(this, String.format(getString(R.string.main_month_deleted_message_error), months[getPresenter().getCurrentPagerPosition()]), Toast.LENGTH_LONG, false).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
