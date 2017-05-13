package fragment;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.design.widget.CoordinatorLayout;
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
import thanhcong.com.nhatkydulich.MapDiaryActivity;
import thanhcong.com.nhatkydulich.R;

/**
 * Created by ThanhCong on 13/12/2016.
 */
public class AddDiaryFragment extends Fragment implements OnClickListener{
    public static final int REQUEST_CODE_LOAD_IMAGE = 11;
    private static final String TAG = "TAG";
    public static final String KEY_COMMAND_TO_MAP = "KEY_COMMAND_TO_MAP";
    public static final int REQUEST_CODE_GET_LOCATION = 22;
    private EditText edtPlace, edtDescription;
    private ImageButton ibtnImage;
    private Button btnAddOrEdit, btnGetLocation;
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
    private double lattitude = 0, longtitude = 0;
    private boolean isEdit;
    private CoordinatorLayout coordinatorLayout;

    private IDiaryActChangeFragmentListener listener;

    public AddDiaryFragment(IDiaryActChangeFragmentListener listener, int position) {
        dbManager = DBManager.getInstance(getActivity());
        journyId = dbManager.getJournyID(position);
        this.listener = listener;
//        this.coordinatorLayout = (CoordinatorLayout) ((Activity)getContext()).findViewById(R.id.coordinator_layout_activity_diary);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_diary, container, false);
        edtPlace = (EditText) rootView.findViewById(R.id.edt_activity_add_diary_place);
        edtDescription = (EditText) rootView.findViewById(R.id.edt_activity_add_diary_description);
        ibtnImage = (ImageButton) rootView.findViewById(R.id.ibtn_activity_add_image);
        btnAddOrEdit = (Button) rootView.findViewById(R.id.btn_activity_add_diary);
        btnGetLocation = (Button)rootView.findViewById(R.id.btn_fragment_add_diary_get_location);
        initViews();
        return rootView;
    }

    public void initViewUpForEditOrAdd(){
        if (isEdit) {
            edtPlace.setText(placeDiary);
            edtDescription.setText(description);
            setIsEdit(false);
        }else{
            btnGetLocation.setVisibility(View.VISIBLE);
            edtPlace.setText("");
            edtDescription.setText("");
            btnAddOrEdit.setText("Tạo");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initViewUpForEditOrAdd();
    }

    private void initViews() {
        ibtnImage.setOnClickListener(this);
        btnAddOrEdit.setOnClickListener(this);
        btnGetLocation.setOnClickListener(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE_LOAD_IMAGE:
                if(resultCode == Activity.RESULT_OK){
                    // get intent data
                    if (data != null) {
                        String imageArray = data.getStringExtra(CustomGalleryActivity.KEY_RETURN_SELECTED_IMAGES);
                        // convert string array into List by split by ',' and  substring after '[' and before ']'
                        arrayImages = (imageArray.substring(1, imageArray.length() - 1)).split(", ");

                    }
                }
                break;
            case REQUEST_CODE_GET_LOCATION:
                if(resultCode == Activity.RESULT_OK){
                    if(data != null){
                        lattitude = data.getDoubleExtra(MapDiaryActivity.KEY_INTENT_SEND_LATTITUDE,0);
                        longtitude = data.getDoubleExtra(MapDiaryActivity.KEY_INTENT_SEND_LONGTITUDE,0);
                        placeDiary = data.getStringExtra(MapDiaryActivity.KEY_INTENT_SEND_PLACE_NAME);
                        description = "";
                        isEdit = true;
                    }
                }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // xu ly chon anh?
            case R.id.ibtn_activity_add_image:
                placeDiary = edtPlace.getText().toString();
                description = edtDescription.getText().toString();
                setIsEdit(true);

                Intent intentPickImage = new Intent();
                intentPickImage.setClass(getActivity(), CustomGalleryActivity.class);
                startActivityForResult(intentPickImage, REQUEST_CODE_LOAD_IMAGE);
                break;

            // xu ly su kien click vao button tao nhat ky
            case R.id.btn_activity_add_diary:
                if(!isEdit){
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy-hh:mm:ss");
                    timeDiary = dateFormat.format(new Date(System.currentTimeMillis()));
                    placeDiary = edtPlace.getText().toString();
                    description = edtDescription.getText().toString();
                    if(arrayImages != null){
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
                    // save location
                    dbManager.insert("location",new String[]{lattitude+"",longtitude+""});
                }else {
                    placeDiary = edtPlace.getText().toString();
                    description = edtDescription.getText().toString();
                    String values[] = {placeDiary,description};
                    boolean result = dbManager.update("diary",diaryIDForEdit,values);
                    Log.i(TAG, "Update diary is" + (result == true ? "done" : "error"));
                }

                // tra ve ket qua de DetailJournyFragment update ListView lvDetailJourny
                listener.showDetailJournyFragment();
                break;
            case R.id.btn_fragment_add_diary_get_location:
                LocationManager lm = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
                boolean gpsEnabled = false;
                boolean networkEnabled = false;

                try {

                    gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                } catch(Exception ex) {}

                try {
                    networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                } catch(Exception ex) {}
                if(!gpsEnabled || !networkEnabled){
                    alertTurnOnGPS();
                }else{
                    Intent intentMap = new Intent();
                    intentMap.setClass(getActivity(), MapDiaryActivity.class);
                    intentMap.putExtra(KEY_COMMAND_TO_MAP,true);
                    startActivityForResult(intentMap,REQUEST_CODE_GET_LOCATION);
                }
                break;
            default:
                break;
        }
    }

    // hien tai khong dung den
    protected void alertTurnOnGPS(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your Device's GPS is Disable").setCancelable(false).
                setTitle("GPS Status").
                setPositiveButton("GPS On", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                getActivity().startActivity(intent);
                dialog.cancel();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
