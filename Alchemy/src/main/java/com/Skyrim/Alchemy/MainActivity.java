package com.Skyrim.Alchemy;

import android.app.Activity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.Locale;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks,
            ActionBar.TabListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    public static SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public static ViewPager mViewPager;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private FunctionDeclare FD = new FunctionDeclare();
    private NavigationDrawerFragment NDF = new NavigationDrawerFragment();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        /*
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
        */

    }

    private void onceActivityView(){
        final ActionBar actionbar = getSupportActionBar();
        ToggleTab(FD.IoE);
        FD.TabCnt = 4;
        PrepareInstance(getSupportFragmentManager());
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionbar.setSelectedNavigationItem(position);
            }
        });
        DeleteTabAll(actionbar);
        CreateTab(actionbar,FD.TabCnt ,new String[]{
                "EFFECT1",
                "EFFECT2",
                "EFFECT3",
                "EFFECT4",
        });

    }

    public void restoreActionBar() {

        ActionBar actionBar = getSupportActionBar();
        ToggleTab(FD.IoE);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(FD.GetActionbarTitle(FD.DrawerSelected));
        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        ContainTabMethod(actionBar,4,getSupportFragmentManager());
    }

    public void ContainTabMethod(final ActionBar actionbar,int cnt,FragmentManager FM){
        FD.TabCnt = cnt;
        actionbar.setTitle(FD.DrawerListList[FD.DrawerSelected]);
        PrepareInstance(FM);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionbar.setSelectedNavigationItem(position);
            }
        });

        DeleteTabAll(actionbar);
        // For each of the sections in the app, add a tab to the action bar.
        CreateTab(actionbar,cnt ,FD.GetTabsStrings(FD.DrawerSelected));
        //FillEffectTV(FD.GetTabsStrings(FD.DrawerSelected));
    }

    private void FillEffectTV(String st[]){//TextView에 효과들 넣기,
        PlaceholderFragment PF = new PlaceholderFragment();
        for(int i=0;i<4;i++){
            PF.TV[i].setText(st[i]);
        }
    }

    private void PrepareInstance(FragmentManager FM){//객체 인스턴스화,

        mSectionsPagerAdapter = new SectionsPagerAdapter(FM);

        // Set up the ViewPager with the sections adapter.
        if(mViewPager==null)
            mViewPager = (ViewPager) findViewById(R.id.pager);
        if(mViewPager!=null){
            mViewPager.setAdapter(mSectionsPagerAdapter);
        }
    }
    public void DeleteTabAll(ActionBar actionbar){
        actionbar.removeAllTabs();
    }
    public void CreateTab(ActionBar actionbar, int cnt, String str[]){
        for(int i=0;i<cnt;i++){
            actionbar.addTab(
                actionbar.newTab()
                    .setText(str[i])
                    .setTabListener(this));
        }
    }

    private void ToggleTab(int ioe){
        ActionBar actionBar = getSupportActionBar();
        switch (ioe){
            case 0:
                actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
                break;
            case 1:
                actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            TranslateMenuIcon(menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void TranslateMenuIcon(Menu menu){
        menu.findItem(R.id.Menu_Item).setTitle(FD.BTnamelist[FD.LangVer][0]);
        menu.findItem(R.id.Menu_Effect).setTitle(FD.BTnamelist[FD.LangVer][1]);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            return PlaceholderFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return FD.TabCnt;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        FunctionDeclare FD = new FunctionDeclare();

        public TextView TV[] = new TextView[4];
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            //for(int i=0;i<4;i++)
            //    TV[i] = new TextView();
            /*
            TV[0] = (TextView)rootView.findViewById(R.id.TV_EF1);
            TV[1] = (TextView)rootView.findViewById(R.id.TV_EF2);
            TV[2] = (TextView)rootView.findViewById(R.id.TV_EF3);
            TV[3] = (TextView)rootView.findViewById(R.id.TV_EF4);
            TV[0].setText("set");
            */
            setRetainInstance(true);
            ListView listview = (ListView) rootView.findViewById(R.id.listView);
            ImageListAdapter ILA = new ImageListAdapter(getActivity(),R.layout.imagelist_sub,FD.CreateEffectList(getArguments().getInt(ARG_SECTION_NUMBER)));
            listview.setAdapter(ILA);
            return rootView;
        }


        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

        }

        @Override
        public void onDetach() {
            super.onDetach();
            mViewPager = null;
            mSectionsPagerAdapter = null;
            try {
                Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
                childFragmentManager.setAccessible(true);
                childFragmentManager.set(this, null);

                Field supportFragmentManager = Fragment.class.getDeclaredField("mFragmentManager");
                supportFragmentManager.setAccessible(true);
                supportFragmentManager.set(this, null);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
