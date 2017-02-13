package fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import entity.DiaryAdapter;
import thanhcong.com.nhatkydulich.R;

/**
 * Created by ThanhCong on 17/12/2016.
 */
public class DetailJournyFragment extends Fragment {
    private ListView lvDetailJourny;
    private DiaryAdapter diaryAdapter;

    public DetailJournyFragment(Context context,int position){
        diaryAdapter = new DiaryAdapter(context,position);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_journy,container,false);
        lvDetailJourny = (ListView)rootView.findViewById(R.id.lv_fragment_detail_journy_list_diary);
        lvDetailJourny.setAdapter(diaryAdapter);
        return rootView;
    }
}
