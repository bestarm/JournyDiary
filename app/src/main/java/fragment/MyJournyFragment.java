package fragment;

import android.content.Context;
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
import thanhcong.com.nhatkydulich.R;

/**
 * Created by ThanhCong on 29/11/2016.
 */
public class MyJournyFragment extends Fragment {
    private static final String TAG = "TAG";
    private JournyListFragment journyListFragment;
    private DetailJournyFragment detailJournyFragment;
    private AddJournyFragment addJournyFragment;
    private AddDiaryFragment addDiaryFragment;
    private FloatingActionButton fab;
//    private FragmentTransaction fragmentTransaction;

    public MyJournyFragment(FloatingActionButton fab){
        this.fab = fab;
//        fragmentTransaction = getChildFragmentManager().beginTransaction();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG,"onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        Log.i(TAG,"onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_journy,container,false);
        Log.i(TAG,"onCreateView");
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
                if(detailJournyFragment.isVisible()){

                    FragmentManager fragmentManager = getChildFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                }
                fab.hide();
            }
        });
    }

    public void showDetailFragment(int position){
        detailJournyFragment = new DetailJournyFragment(getContext(),position);
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layout_fragment_my_journy,detailJournyFragment).addToBackStack("DetailJournyFragment").commit();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG,"onActivityCreateed");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG,"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG,"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG,"onStop");
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
