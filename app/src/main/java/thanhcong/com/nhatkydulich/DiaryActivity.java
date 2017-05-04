package thanhcong.com.nhatkydulich;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tc.inter.IDiaryActChangeFragmentListener;

import fragment.AddDiaryFragment;
import fragment.DetailJournyFragment;
import fragment.MyJournyFragment;

/**
 * Created by ThanhCong on 15/02/2017.
 */
public class DiaryActivity extends AppCompatActivity implements IDiaryActChangeFragmentListener {

    private DetailJournyFragment detailJournyFragment;
    private AddDiaryFragment addDiaryFragment;
    private int positionItemJourny;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        initViews();
    }

    public void initViews() {
        // Get position from JournyAdapter's getAdapterPosition()
        Intent intentGetPos = getIntent();
        positionItemJourny = intentGetPos.getIntExtra(MyJournyFragment.KEY_INTENT_SEND_POSITION,0);
        detailJournyFragment = new DetailJournyFragment(this,positionItemJourny);
        addDiaryFragment = new AddDiaryFragment(this,positionItemJourny);
        showDetailJournyFragment();
    }

    @Override
    public void showDetailJournyFragment() {
//        detailJournyFragment.getView().
        getFragmentManager().beginTransaction().replace(R.id.layout_main_diary,detailJournyFragment).commit();
    }

    public void updateDetailJournyFragment(){
        //update UI when remove diaryID
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(detailJournyFragment);
        transaction.attach(detailJournyFragment);
        transaction.commit();
    }

    @Override
    public void showAddDiaryFragment(boolean isEdit,int positionItemDiary){
        addDiaryFragment.setIsEdit(isEdit);
        if(isEdit){
            addDiaryFragment.setUpForEdit(detailJournyFragment.
                    getDiaryClickedToEdit(positionItemDiary));
        }
        getFragmentManager().beginTransaction().replace(R.id.layout_main_diary,addDiaryFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if(addDiaryFragment.isVisible()){
            showDetailJournyFragment();
            return;
        }
        super.onBackPressed();
    }
}
