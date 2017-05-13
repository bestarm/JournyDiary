package fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tc.inter.IJournyChangeFragmentListener;

import java.lang.reflect.Field;

import entity.DBManager;
import entity.Journy;
import entity.JournyAdapter;
import thanhcong.com.nhatkydulich.DiaryActivity;
import thanhcong.com.nhatkydulich.R;

/**
 * Created by ThanhCong on 29/11/2016.
 */
public class MyJournyFragment extends Fragment implements IJournyChangeFragmentListener {
    private static final String TAG = "TAG";
    public static final String KEY_INTENT_SEND_POSITION = "KEY_INTENT_SEND_POSITION";
    private JournyListFragment journyListFragment = null;
    private AddJournyFragment addJournyFragment = null;
    private FloatingActionButton fab;


    public MyJournyFragment(FloatingActionButton fab) {
        this.fab = fab;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_journy, container, false);

        return rootView;
    }

    private void initViews() {
            journyListFragment = new JournyListFragment(getContext(), this);
            addJournyFragment = new AddJournyFragment(getContext(),this);
            showJournyListFragment();
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (journyListFragment.isVisible()) {
                        showAddJournyFragment(null);
                    }
                    fab.hide();
                }
            });
    }
    @Override
    public void showDiaryActivity(int position) {
        Intent intentDiaryAct = new Intent();
        intentDiaryAct.setClass(getContext(), DiaryActivity.class);
        intentDiaryAct.putExtra(KEY_INTENT_SEND_POSITION, position);
        startActivity(intentDiaryAct);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showJournyListFragment() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_fragment_my_journy, journyListFragment).commit();
    }

    @Override
    public void showAddJournyFragment(Journy journyForEdit) {
        if(journyForEdit != null){
            addJournyFragment.setUpJournyForEdit(journyForEdit);
        }
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_fragment_my_journy, addJournyFragment).commit();
        fab.hide();
    }
    @Override
    public void updateJournyListFragment(){
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.detach(journyListFragment);
        fragmentTransaction.attach(journyListFragment);
        fragmentTransaction.commit();
    }

    public boolean isAddjournyFragmentVisible(){
        return addJournyFragment.isVisible();
    }

}
