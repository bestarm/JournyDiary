package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import entity.DBManager;
import entity.DiaryAdapter;
import thanhcong.com.nhatkydulich.DiaryActivity;
import thanhcong.com.nhatkydulich.R;

/**
 * Created by ThanhCong on 12/12/2016.
 */
public class DiaryFragment extends android.support.v4.app.Fragment {
    private RecyclerView recyclerViewDiary;
    private DiaryAdapter diaryAdapter;
    private DBManager dbManager;
    private int position;

    public DiaryFragment(DBManager dbManager){
        this.dbManager = dbManager;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_diary,container,false);
        recyclerViewDiary = (RecyclerView)rootView.findViewById(R.id.recycle_view_diary);
        Bundle argument = getArguments();
        position = argument.getInt(DiaryActivity.KEY_BUNDLE_SEND_POSITION);
        diaryAdapter = new DiaryAdapter(getContext(),dbManager.getListDiary(position));
        recyclerViewDiary.setAdapter(diaryAdapter);
        recyclerViewDiary.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }
    public void updateWhenAddDiary(){
        diaryAdapter.updateWhenAddDiary(position);
    }
}
