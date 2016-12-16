package thanhcong.com.nhatkydulich;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

import entity.Journy;
import fragment.JournyFragment;

/**
 * Created by ThanhCong on 09/12/2016.
 */
public class AddJournyActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int REQUEST_CODE_LOAD_IMAGE = 2;
    public static final String KEY_SEND_BUNDLE_JOURNY = "KEY_SEND_BUNDLE_JOURNY";
    private EditText edtName,edtPlace,edtDistance;
    private ImageView ivCover;
    private Toolbar toolbar;
    private Button btnCheck;

    private String nameJourny = "";
    private String placeJourny = "";
    private String coverImage = "";
    private String timeJourny = "";
    private int journyID;
    private int distance;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_journy);
        initViews();
    }

    private void initViews() {
        edtName = (EditText)findViewById(R.id.edt_activity_add_journy_name);
        edtPlace = (EditText)findViewById(R.id.edt_activity_add_journy_place);
        edtDistance = (EditText)findViewById(R.id.edt_activity_add_journy_distance);
        ivCover = (ImageView)findViewById(R.id.iv_activity_add_journy_cover);

        toolbar = (Toolbar)findViewById(R.id.toolbar_activity_add_journy);
        btnCheck = (Button) toolbar.findViewById(R.id.btn_toolbar_add_journy_check);
        btnCheck.setOnClickListener(this);
        ivCover.setOnClickListener(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_toolbar_add_journy_check:
                nameJourny = edtName.getText().toString();
                placeJourny = edtPlace.getText().toString();
                try{
                    distance = Integer.parseInt(edtDistance.getText().toString());
                }catch (NumberFormatException e){
                    Snackbar.make(findViewById(R.id.layout_activity_add_journy),"Bạn nhập không đúng khoảng cách !",
                            Snackbar.LENGTH_LONG).setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }).show();
                }
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM/yyyy");
                timeJourny = dateFormat.format(new Date(System.currentTimeMillis()));
                Intent intentDataReturn = new Intent();
                Journy journy = new Journy(nameJourny,placeJourny,timeJourny,coverImage,journyID,distance,userID);
                Bundle bundle = new Bundle();
                bundle.putSerializable(KEY_SEND_BUNDLE_JOURNY,journy);
                intentDataReturn.putExtras(bundle);
                setResult(RESULT_OK,intentDataReturn);
                finish();
                break;
            case R.id.iv_activity_add_journy_cover:
                Intent intentPickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentPickImage,REQUEST_CODE_LOAD_IMAGE);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_LOAD_IMAGE&&resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            String[] filePathColummn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,filePathColummn,null,null,null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColummn[0]);
            coverImage = cursor.getString(columnIndex);
            ivCover.setImageBitmap(BitmapFactory.decodeFile(coverImage));
            cursor.close();
        }
    }
    public void addJournyFragmentUpdateListener(JournyFragment journyFragment){
        journyFragment.updateJournyList();
        journyFragment.notifyItemInserted();
    }
}
