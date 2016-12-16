package thanhcong.com.nhatkydulich;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import entity.DBManager;
import entity.Journy;
import entity.JournyAdapter;
import fragment.DiaryFragment;

/**
 * Created by ThanhCong on 12/12/2016.
 */
public class DiaryActivity  extends AppCompatActivity{
    public static final String KEY_BUNDLE_SEND_POSITION = "KEY_INTENT_SEND_POSITION";
    public static final int REQUEST_CODE_UPDATE_RECYCLER_VIEW = 22;
    public static final String KEY_INTENT_SEND_POSITION_VIEW_PAGER = "KEY_INTENT_SEND_POSITION_VIEW_PAGER";
    private ViewPager viewPagerDiary;
    private ViewPagerAdapter viewPagerAdapter;
    private List<DiaryFragment> diaryFragmentList = new ArrayList<DiaryFragment>();
    private DBManager dbManager;
    private FloatingActionButton fab;
    private TextView txtNameJourny;
    int selectedPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        initViews();
    }

    private void initViews() {
        viewPagerDiary = (ViewPager)findViewById(R.id.view_pager_activity_diary);
        Intent intentRecv = getIntent();
        // lay position cua Journy khi duoc click
        selectedPosition = intentRecv.getIntExtra(JournyAdapter.KEY_INTENT_SEND_POSITION,0);
        dbManager = DBManager.getInstance(this);

        txtNameJourny = (TextView)findViewById(R.id.txt_activity_diary_name_journy);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerDiary.setAdapter(viewPagerAdapter);
        // hiển thị page phù hợp với tiem journy được chọn
        viewPagerDiary.setCurrentItem(selectedPosition);
        viewPagerDiary.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                txtNameJourny.setText(dbManager.getListJourny().get(dbManager.getSizeJourny()- position -1).getNameJourny());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fab = (FloatingActionButton)findViewById(R.id.fab_activity_diary);

        // xu ly su kien click vao FloatingActionButton
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DiaryActivity.this,AddDiaryActivity.class);
                intent.putExtra(KEY_INTENT_SEND_POSITION_VIEW_PAGER,viewPagerDiary.getCurrentItem());
                startActivityForResult(intent,REQUEST_CODE_UPDATE_RECYCLER_VIEW);
            }
        });
    }
    private class ViewPagerAdapter extends FragmentPagerAdapter{
        int totalFragment;
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            initFragments();
        }

        private void initFragments() {
            totalFragment = dbManager.getListJourny().size();
            for(int i = 0; i < totalFragment; i++){
                DiaryFragment diaryFragment = new DiaryFragment(dbManager);
                Bundle argument =  new Bundle();
                argument.putInt(KEY_BUNDLE_SEND_POSITION,i);
                diaryFragment.setArguments(argument);
                diaryFragmentList.add(diaryFragment);
            }
        }

        @Override
        public Fragment getItem(int position) {
            return diaryFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return totalFragment;
        }
    }
    // CallBack update recycler view cua DiaryFragment tuong ung
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_UPDATE_RECYCLER_VIEW&&resultCode==RESULT_OK){
            diaryFragmentList.get(viewPagerDiary.getCurrentItem()).updateWhenAddDiary();
        }
    }
}
