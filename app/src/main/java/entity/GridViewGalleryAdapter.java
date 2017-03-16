package entity;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import java.util.ArrayList;

import thanhcong.com.nhatkydulich.CustomGalleryActivity;
import thanhcong.com.nhatkydulich.R;

/**
 * Created by ThanhCong on 21/02/2017.
 */
public class GridViewGalleryAdapter extends BaseAdapter{
    private ArrayList<String> galleryImageUrls;
    private LayoutInflater layoutInflater;
    private SparseBooleanArray sparseBooleanArray;
    private Button btnImgSelected;
    private Context context;
    private int selected = 0;

    public GridViewGalleryAdapter(Context context, ArrayList<String> galleryImageUrls){
        this.context = context;
        this.galleryImageUrls = galleryImageUrls;
        layoutInflater = LayoutInflater.from(context);
        sparseBooleanArray = new SparseBooleanArray();
        // button accepting number of picture selected
        btnImgSelected = (Button)((CustomGalleryActivity)context).findViewById(R.id.btn_activity_customgallery_img_selected);
    }
    @Override
    public int getCount() {
        return galleryImageUrls.size();
    }

    @Override
    public String getItem(int position) {
        return galleryImageUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(itemView == null){
            viewHolder = new ViewHolder();
            itemView = layoutInflater.inflate(R.layout.item_gridview,parent,false);
            viewHolder.imgGallery = (ImageView)itemView.findViewById(R.id.img_item_gridview_gallery);
            viewHolder.cbxImageSelect = (CheckBox)itemView.findViewById(R.id.cbx_item_gridview);
            viewHolder.cbxImageSelect.setOnCheckedChangeListener(mCheckChangeListener);
        }else{
            viewHolder = (ViewHolder)itemView.getTag();
        }
        String pathImage = galleryImageUrls.get(position);
        viewHolder.imgGallery.setImageBitmap(BitmapFactory.decodeFile(pathImage));
        viewHolder.cbxImageSelect.setTag(position);// set Tag for CheckBox
        viewHolder.cbxImageSelect.setChecked(sparseBooleanArray.get(position));
        itemView.setTag(viewHolder);
        return itemView;
    }

    CompoundButton.OnCheckedChangeListener mCheckChangeListener = new CompoundButton.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            sparseBooleanArray.put((Integer)buttonView.getTag(),isChecked);
            if(isChecked){
                selected++;
            }else{
                selected--;
            }
            btnImgSelected.setText("Select "+selected+" image");
            if (selected >0){
                btnImgSelected.setVisibility(View.VISIBLE);
            }else {
                btnImgSelected.setVisibility(View.GONE);
            }
//            ((CustomGalleryActivity)context).showSelectedBtn(selected);
        }
    };

    // method to return selected Images
    public ArrayList<String> getImageUrlsSelected(){
        ArrayList<String> imageUrlsSelected = new ArrayList<String>();
        int size = getCount();
        for(int i = 0 ; i < size;i++){
            if(sparseBooleanArray.get(i)){
                imageUrlsSelected.add(galleryImageUrls.get(i));
            }
        }
        return imageUrlsSelected;
    }

    private class ViewHolder{
        CheckBox cbxImageSelect;
        ImageView imgGallery;
    }
}
