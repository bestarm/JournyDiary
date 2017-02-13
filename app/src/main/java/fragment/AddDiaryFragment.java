package fragment;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.Date;

import entity.DBManager;
import thanhcong.com.nhatkydulich.R;

/**
 * Created by ThanhCong on 13/12/2016.
 */
public class AddDiaryFragment extends Fragment {
    private static final int REQUEST_CODE_LOAD_IMAGE = 11;
    private static final String TAG = "TAG";
    private EditText edtPlace,edtDescription;
    private ImageButton ibtnImage;
    private Button btnAdd;
    private DBManager dbManager;

    private String timeDiary = "";
    private String placeDiary = "";
    private String description = "";
    private String image = "";
    private String video = "";
    private String mp3 = "";
    private int journyId = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_diary,container,false);
        edtPlace = (EditText)rootView.findViewById(R.id.edt_activity_add_diary_place);
        edtDescription = (EditText)rootView.findViewById(R.id.edt_activity_add_diary_description);
        ibtnImage = (ImageButton)rootView.findViewById(R.id.ibtn_activity_add_image);
        btnAdd = (Button)rootView.findViewById(R.id.btn_activity_add_diary);
        return rootView;
    }

    private void initViews() {

        ibtnImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentPickImage,REQUEST_CODE_LOAD_IMAGE);
            }
        });

        // xu ly su kien click vao button tao nhat ky
        btnAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy-hh:mm:ss");
                timeDiary = dateFormat.format(new Date(System.currentTimeMillis()));
                placeDiary = edtPlace.getText().toString();
                description = edtDescription.getText().toString();
                String values[][] = {{"TimeDiary",timeDiary},
                        {"PlaceDiary",placeDiary},
                        {"Description",description},
                        {"Image",image},
                        {"Video",video},
                        {"Mp3",mp3},
                        {"JournyId",journyId+""}
                };
                boolean result = dbManager.insert("diary",values);
                Log.i(TAG,"Insert is"+(result == true?"done":"error"));
                // tra ve ket qua de DiaryActivity update recycler view

            }
        });

//        dbManager = DBManager.getInstance(this);
        // lay vi tri hien tai cua Journy.Neu co Error : vị trí của item được chọn mặc định sẽ là 0
//        Intent intent = getIntent();
//        int  positionPage = intent.getIntExtra(DiaryActivity.KEY_INTENT_SEND_POSITION_VIEW_PAGER,0);
        // lấy journyId của diary để insert vào Database
//        journyId = dbManager.getListJourny().get(dbManager.getSizeJourny() - positionPage - 1).getJournyID();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode == REQUEST_CODE_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
//            Uri selectedImage = data.getData();
//            String[] filePathColummn = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(selectedImage,filePathColummn,null,null,null);
//            cursor.moveToFirst();
//            int columnIndex = cursor.getColumnIndex(filePathColummn[0]);
//            image = cursor.getString(columnIndex);
//        }
//    }
}
