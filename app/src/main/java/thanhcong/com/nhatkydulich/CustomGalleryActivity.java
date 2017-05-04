package thanhcong.com.nhatkydulich;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import entity.DBManager;
import entity.DiaryAdapter;
import entity.GridViewGalleryAdapter;
import fragment.AddDiaryFragment;

/**
 * Created by ThanhCong on 21/02/2017.
 */
public class CustomGalleryActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String KEY_RETURN_SELECTED_IMAGES = "KEY_RETURN_SELECTED_IMAGES";
    private GridView grvGallery;
    private GridViewGalleryAdapter gridViewGalleryAdapter;
    private TextView txtTitle;
    private ArrayList<String> galleryImageUrls;
    private Button btnImageSelected;
    private DBManager dbManager;

    public CustomGalleryActivity(){
        galleryImageUrls = new ArrayList<String>();
        dbManager = DBManager.getInstance(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customgallery);
        initViews();
    }

    private void initViews() {
        grvGallery = (GridView)findViewById(R.id.gridview_gallery);
        txtTitle = (TextView)findViewById(R.id.txt_customgallery_activity_title);

        Intent intent = getIntent();
        int diaryID = intent.getIntExtra(DiaryAdapter.KEY_SEND_DIARYID,0);
        String titleDiary = intent.getStringExtra(DiaryAdapter.KEY_SEND_TITLE_DIARY);
        if(diaryID == 0){
            fetchGalleryImages();
            gridViewGalleryAdapter = new GridViewGalleryAdapter(this,galleryImageUrls,true);
        }else{
            String[] arrImageOfDiary =  dbManager.getImagesOfDiary(diaryID);
            int lengh = arrImageOfDiary.length;
            for(int i = 0; i < lengh; i++){
                galleryImageUrls.add(arrImageOfDiary[i]);
            }
            gridViewGalleryAdapter = new GridViewGalleryAdapter(this,galleryImageUrls,false);
            txtTitle.setText(titleDiary);
        }


        btnImageSelected = (Button)findViewById(R.id.btn_activity_customgallery_img_selected);
        btnImageSelected.setOnClickListener(this);

        setupGridView();
    }

    private void setupGridView() {
        grvGallery.setAdapter(gridViewGalleryAdapter);
    }

    public void fetchGalleryImages(){
        String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        String orderBy = MediaStore.Images.Media.DATE_TAKEN; // order data by date
        // get all images in Cursor by sorting in DESC order
        Cursor imageCursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,columns,null,null, orderBy +" DESC");
        for(int i =0; i < imageCursor.getCount();i++){
            imageCursor.moveToPosition(i);
            int dataColumnIndex = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);// get column index
            galleryImageUrls.add(imageCursor.getString(dataColumnIndex));// get Image from column index

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_activity_customgallery_img_selected:
                //fill array with selected images
                ArrayList<String> imageUrlsSelected = gridViewGalleryAdapter.getImageUrlsSelected();

                Intent intent = new Intent();
                intent.putExtra(KEY_RETURN_SELECTED_IMAGES,imageUrlsSelected.toString());
                setResult(RESULT_OK,intent);
                finish();
                break;
            default:
                break;
        }
    }

    public void showSelectedBtn(int selected) {

    }
}
