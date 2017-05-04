package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tc.inter.IDiaryActChangeFragmentListener;

import entity.Diary;
import entity.DiaryAdapter;
import thanhcong.com.nhatkydulich.DiaryActivity;
import thanhcong.com.nhatkydulich.R;

/**
 * Created by ThanhCong on 17/12/2016.
 */
public class DetailJournyFragment extends android.app.Fragment {
    private ListView lvDetailJourny;
    private DiaryAdapter diaryAdapter;
    private FloatingActionButton fab;
    private IDiaryActChangeFragmentListener listener;
    private Context mContext;
    private int position;

    public DetailJournyFragment(DiaryActivity context,int position){
        if(context instanceof IDiaryActChangeFragmentListener){
            this.listener = (IDiaryActChangeFragmentListener)context;
        }
        this.mContext = context;
        this.position = position;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_journy,container,false);
        lvDetailJourny = (ListView)rootView.findViewById(R.id.lv_fragment_detail_journy_list_diary);
//        lvDetailJourny.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
        diaryAdapter = new DiaryAdapter(mContext,position);
        diaryAdapter.notifyDataSetChanged();
        lvDetailJourny.setAdapter(diaryAdapter);
        fab = (FloatingActionButton)rootView.findViewById(R.id.fab_fragment_detail_journy);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.showAddDiaryFragment(false,0);
            }
        });
        return rootView;
    }

    public Diary getDiaryClickedToEdit(int positionItemDiary){
        return diaryAdapter.getItem(positionItemDiary);
    }
}
