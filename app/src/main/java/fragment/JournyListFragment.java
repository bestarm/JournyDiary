package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import entity.DBManager;
import entity.JournyAdapter;
import thanhcong.com.nhatkydulich.R;

/**
 * Created by ThanhCong on 17/12/2016.
 */
public class JournyListFragment extends Fragment {
    private RecyclerView recyclerViewJourny;
    private JournyAdapter journyAdapter;

    public JournyListFragment(Context context,MyJournyFragment myJournyFragment){
        super();
        journyAdapter = new JournyAdapter(context,myJournyFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_journy_list,container,false);
        recyclerViewJourny = (RecyclerView)rootView.findViewById(R.id.recycler_view_journy);


        recyclerViewJourny.setAdapter(journyAdapter);
        recyclerViewJourny.setLayoutManager(new LinearLayoutManager(getContext()));

//        initViews();
        return rootView;
    }

    public void updateJournyList(){
        journyAdapter.updateListJourny();
    }

    public void notifyItemInserted(){
        journyAdapter.notifyItemInsert();
    }

    private void initViews() {

    }
}
