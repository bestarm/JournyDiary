package thanhcong.com.nhatkydulich;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import entity.DBManager;
import entity.Journy;
import fragment.AccountFragment;
import fragment.MyJournyFragment;
import fragment.NotificationFragment;
import fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {
    public static final int TAB_JOURNY = 0;
    public static final int TAB_NOTIFICATION = 1;
    public static final int TAB_SEARCH = 2;
    public static final int TAB_ACCOUNT = 3;
    private static final int REQUEST_CODE_ADD_JOURNY = 1;

    private MyJournyFragment myJournyFragment;
    private NotificationFragment notificationFragment;
    private SearchFragment searchFragment;
    private AccountFragment accountFragment;

    private ViewPager viewPagerTab;
    private TabLayout tabLayout;
    private PagerAdapter pagerAdapter;

    private Toolbar toolbar;
    private TextView txtTitleToolbar;
    private DBManager dbManager;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    private void initViews() {
        dbManager = DBManager.getInstance(this);
        dbManager.openDB();
        toolbar = (Toolbar)findViewById(R.id.toolbar_activity_main);
        txtTitleToolbar = (TextView)toolbar.findViewById(R.id.txt_title_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        fab = (FloatingActionButton)findViewById(R.id.fab);

        viewPagerTab = (ViewPager)findViewById(R.id.view_pager);
        viewPagerTab.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case TAB_JOURNY:
                        txtTitleToolbar.setText("Hành trình");
                        fab.show();
                        break;
                    case TAB_NOTIFICATION:
                        txtTitleToolbar.setText("Thông báo");
                        fab.hide();
                        break;
                    case TAB_SEARCH:
                        txtTitleToolbar.setText("Tìm kiếm");
                        fab.hide();
                        break;
                    case TAB_ACCOUNT:
                        txtTitleToolbar.setText("Cá nhân");
                        fab.hide();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(),tabLayout);

        viewPagerTab.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPagerTab);
        pagerAdapter.setTabIcons();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_menu_setting:
                // xu ly click setting
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    private class PagerAdapter extends FragmentPagerAdapter {
        private TabLayout tabLayout;

        public PagerAdapter(FragmentManager fm,TabLayout tabLayout) {
            super(fm);
            this.tabLayout = tabLayout;
            initFragments();
        }

        private void initFragments() {
            myJournyFragment = new MyJournyFragment(fab);
            notificationFragment = new NotificationFragment();
            searchFragment = new SearchFragment();
            accountFragment = new AccountFragment();
        }

        private void setTabIcons() {
            tabLayout.getTabAt(TAB_JOURNY).setIcon(R.drawable.selector_ic_tab_journy);
            tabLayout.getTabAt(TAB_NOTIFICATION).setIcon(R.drawable.selector_ic_tab_notification);
            tabLayout.getTabAt(TAB_SEARCH).setIcon(R.drawable.selector_ic_tab_search);
            tabLayout.getTabAt(TAB_ACCOUNT).setIcon(R.drawable.selector_ic_tab_account);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch (position){
                case TAB_JOURNY : fragment = myJournyFragment;
                    break;
                case TAB_NOTIFICATION : fragment = notificationFragment;
                    break;
                case TAB_SEARCH : fragment = searchFragment;
                    break;
                case TAB_ACCOUNT : fragment = accountFragment;
                    break;
                default:
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
    // Khi them moi hanh trinh xong, method nay se duoc goi


    @Override
    public void onBackPressed() {
        if(viewPagerTab.getCurrentItem()==TAB_JOURNY){
            int backStack = myJournyFragment.getChildFragmentManager().getBackStackEntryCount();
            if(backStack > 0){
                myJournyFragment.getChildFragmentManager().popBackStack();
                fab.show();
            }else{
                super.onBackPressed();
            }
        }else {
            super.onBackPressed();
        }
    }
}
