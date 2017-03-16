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

import java.lang.reflect.Field;

import entity.DBManager;
import entity.JournyAdapter;
import thanhcong.com.nhatkydulich.DiaryActivity;
import thanhcong.com.nhatkydulich.R;

/**
 * Created by ThanhCong on 29/11/2016.
 */
public class MyJournyFragment extends Fragment {
    private static final String TAG = "TAG";
    public static final String KEY_INTENT_SEND_POSITION = "KEY_INTENT_SEND_POSITION";

    private JournyListFragment journyListFragment;
//    private DetailJournyFragment detailJournyFragment;
    private AddJournyFragment addJournyFragment;
//    private AddDiaryFragment addDiaryFragment;

    private FloatingActionButton fab;
//    private FragmentTransaction fragmentTransaction;

    public MyJournyFragment(FloatingActionButton fab){
        this.fab = fab;
//        fragmentTransaction = getChildFragmentManager().beginTransaction();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_journy,container,false);
        return rootView;

    }


    private void initViews() {
        journyListFragment = new JournyListFragment(getContext(),this);
        addJournyFragment = new AddJournyFragment();
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.layout_fragment_my_journy,journyListFragment).commit();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(journyListFragment.isVisible()){
                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.layout_fragment_my_journy,addJournyFragment).addToBackStack("AddJournyFragment").commit();
                }
//                if(detailJournyFragment.isVisible()){
//
//                    FragmentManager fragmentManager = getChildFragmentManager();
//                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                }
                fab.hide();
            }
        });
    }

    public void showDiaryActivity(int position){
        Intent intentDiaryAct = new Intent();
        intentDiaryAct.setClass(getContext(), DiaryActivity.class);
        intentDiaryAct.putExtra(KEY_INTENT_SEND_POSITION,position);
        startActivity(intentDiaryAct);
//        detailJournyFragment = new DetailJournyFragment(getContext(),position);
//        FragmentManager fragmentManager = getChildFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.layout_fragment_my_journy,detailJournyFragment).addToBackStack("DetailJournyFragment").commit();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this,null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void updateJournyList() {
        journyListFragment.updateJournyList();
    }

    public void notifyItemInserted() {
        journyListFragment.notifyItemInserted();
    }
}
