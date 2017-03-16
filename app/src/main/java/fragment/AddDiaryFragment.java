package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.tc.inter.IDiaryActChangeFragmentListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import entity.DBManager;
import entity.Diary;
import thanhcong.com.nhatkydulich.CustomGalleryActivity;
import thanhcong.com.nhatkydulich.R;

/**
 * Created by ThanhCong on 13/12/2016.
 */
public class AddDiaryFragment extends Fragment {
    public static final int REQUEST_CODE_LOAD_IMAGE = 11;
    private static final String TAG = "TAG";
    private EditText edtPlace, edtDescription;
    private ImageButton ibtnImage;
    private Button btnAddOrEdit;
    private DBManager dbManager;

    private int diaryIDForEdit;
    private String timeDiary = "";
    private String placeDiary = "";
    private String description = "";
    private String image = "";
    private String video = "";
    private String mp3 = "";
    private int journyId = 0;
    private String arrayImages[] = null;
    private boolean isEdit;

    private IDiaryActChangeFragmentListener listener;

    public AddDiaryFragment(IDiaryActChangeFragmentListener listener, int position) {
        dbManager = DBManager.getInstance(getActivity());
        journyId = dbManager.getJournyID(position);
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_diary, container, false);
        edtPlace = (EditText) rootView.findViewById(R.id.edt_activity_add_diary_place);
        edtDescription = (EditText) rootView.findViewById(R.id.edt_activity_add_diary_description);
        ibtnImage = (ImageButton) rootView.findViewById(R.id.ibtn_activity_add_image);
        btnAddOrEdit = (Button) rootView.findViewById(R.id.btn_activity_add_diary);
        initViews();
        return rootView;
    }

    private void initViews() {
        if (isEdit) {
            edtPlace.setText(placeDiary);
            edtDescription.setText(description);
            btnAddOrEdit.setText("Sửa");
        }else{
            edtPlace.setText("");
            edtDescription.setText("");
        }
        ibtnImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPickImage = new Intent();
                intentPickImage.setClass(getActivity(), CustomGalleryActivity.class);
                startActivityForResult(intentPickImage, REQUEST_CODE_LOAD_IMAGE);
            }
        });

        // xu ly su kien click vao button tao nhat ky
        btnAddOrEdit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEdit){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy-hh:mm:ss");
                    timeDiary = dateFormat.format(new Date(System.currentTimeMillis()));
                    placeDiary = edtPlace.getText().toString();
                    description = edtDescription.getText().toString();
                    if(image.length() > 0){
                        image = arrayImages[0];
                    }else{
                        image = "";
                    }
                    String values[] = {timeDiary, placeDiary, description, image, video, mp3, journyId + ""};
                    boolean result = dbManager.insert("diary", values);
                    Log.i(TAG, "Insert diary is" + (result == true ? "done" : "error"));

                    // save images
                    if (arrayImages != null) {
                        result = dbManager.insert("images", arrayImages);
                        Log.i(TAG, "Insert images is" + (result == true ? "done" : "error"));
                    }
                }else {
                    placeDiary = edtPlace.getText().toString();
                    description = edtDescription.getText().toString();
                    String values[] = {placeDiary,description};
                    boolean result = dbManager.update("diary",diaryIDForEdit,values);
                    Log.i(TAG, "Update diary is" + (result == true ? "done" : "error"));
                }

                // tra ve ket qua de DetailJournyFragment update ListView lvDetailJourny
                listener.showDetailJournyFragment();
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            String imageArray = data.getStringExtra(CustomGalleryActivity.KEY_RETURN_SELECTED_IMAGES);// get intent data
            // convert string array into List by split by ',' and  substring after '[' and before ']'
            arrayImages = (imageArray.substring(1, imageArray.length() - 1)).split(", ");

        }
    }

    // called before show this Fragment
    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }
    // call before show this Fragment for Edit
    public void setUpForEdit(Diary diaryForEdit){
        this.placeDiary = diaryForEdit.getPlaceDiary();
        this.description = diaryForEdit.getDescription();
        this.arrayImages = dbManager.getImagesOfDiary(diaryForEdit.getDiaryID());
        this.diaryIDForEdit = diaryForEdit.getDiaryID();
    }
}
