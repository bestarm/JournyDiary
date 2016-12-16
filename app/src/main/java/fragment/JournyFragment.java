package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import entity.DBManager;
import entity.Journy;
import entity.JournyAdapter;
import thanhcong.com.nhatkydulich.DiaryActivity;
import thanhcong.com.nhatkydulich.R;

/**
 * Created by ThanhCong on 29/11/2016.
 */
public class JournyFragment extends Fragment {
    private RecyclerView recyclerViewJourny;
    private DBManager dbManager;
    private JournyAdapter journyAdapter;

    public JournyFragment(){
        super();
    }

    public JournyFragment(DBManager dbManager){
        this.dbManager = dbManager;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_journy,container,false);
        recyclerViewJourny = (RecyclerView)rootView.findViewById(R.id.recycler_view_journy);
        journyAdapter = new JournyAdapter(getContext(),dbManager);

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
