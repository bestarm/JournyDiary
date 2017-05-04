package fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tc.inter.IJournyChangeFragmentListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import entity.DBManager;
import entity.Journy;
import thanhcong.com.nhatkydulich.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ThanhCong on 17/12/2016.
 */
public class AddJournyFragment extends Fragment implements View.OnClickListener {
    private static final int REQUEST_CODE_LOAD_IMAGE_COVER_JOURNY = 111;
    private EditText edtName, edtPlace, edtDistance;
    private ImageView ivCover;
    private Button btnCreate;

    private String place, name, distance, coverImage = null, time;
    private int userID = 0;
    private int journyID;

    private DBManager dbManager;
    private Context mContext;
    private IJournyChangeFragmentListener listener;
    private CoordinatorLayout coordinatorLayout;
    boolean isEdit = false;

    public AddJournyFragment(Context context, IJournyChangeFragmentListener listener) {
        dbManager = DBManager.getInstance(context);
        this.mContext = context;
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_journy, container, false);
        edtName = (EditText) rootView.findViewById(R.id.edt_fragment_add_journy_name);
        edtPlace = (EditText) rootView.findViewById(R.id.edt_fragment_add_journy_place);
        edtDistance = (EditText) rootView.findViewById(R.id.edt_fragment_add_journy_distance);
        ivCover = (ImageView) rootView.findViewById(R.id.iv_fragment_add_journy_cover);

        btnCreate = (Button) rootView.findViewById(R.id.btn_fragment_add_journy_create);
        coordinatorLayout = (CoordinatorLayout) ((Activity) mContext).findViewById(R.id.main_activity);

        ivCover.setOnClickListener(this);
        btnCreate.setOnClickListener(this);

        initViews();
        return rootView;
    }

    private void initViews() {
        if(isEdit){
            edtName.setText(name);
            edtPlace.setText(place);
            edtDistance.setText(distance);

            ivCover.setImageBitmap(BitmapFactory.decodeFile(coverImage));

            btnCreate.setText("Sửa");
            isEdit = false;
        }else{
            // TO DO
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_fragment_add_journy_cover:
                Intent intentPickImage = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentPickImage, REQUEST_CODE_LOAD_IMAGE_COVER_JOURNY);
                break;
            case R.id.btn_fragment_add_journy_create:
                if (btnCreate.getText().equals("Tạo")) {
                    name = edtName.getText().toString();
                    place = edtPlace.getText().toString();
                    distance = edtDistance.getText().toString();

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
                    time = dateFormat.format(new Date(System.currentTimeMillis()));

                    String columnData[] = {place, name, distance, userID + "", coverImage, time};
                    dbManager.insert("journy", columnData);
                    listener.showJournyListFragment();
                } else {
                    name = edtName.getText().toString();
                    place = edtPlace.getText().toString();
                    distance = edtDistance.getText().toString();

                    String columnData[] = {place, name, distance, userID+"", coverImage};
                    dbManager.update("journy",journyID,columnData);
                    listener.showJournyListFragment();
                }

                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {

        // null pointer exception
        super.onDestroyView();
        Snackbar.make(coordinatorLayout, "Add Diary Fragment was destroyed !", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOAD_IMAGE_COVER_JOURNY && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = mContext.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            coverImage = cursor.getString(columnIndex);
            ivCover.setImageBitmap(BitmapFactory.decodeFile(coverImage));
            cursor.close();
        }
    }

    public void setUpJournyForEdit(Journy journyForEdit) {
        this.isEdit = true;
        journyID = journyForEdit.getJournyID();


        name = journyForEdit.getNameJourny();
        place = journyForEdit.getPlaceJourny();
        distance = journyForEdit.getDistance()+"";

        coverImage = journyForEdit.getCoverImage();

    }

    public void setIsEdit(boolean isEdit){
        this.isEdit = isEdit;
    }
}
