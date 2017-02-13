package entity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import thanhcong.com.nhatkydulich.R;

public class DiaryAdapter extends BaseAdapter{
    List<Diary> diaryList = new ArrayList<Diary>();
    private DBManager dbManager;
    private LayoutInflater inflater;

    public DiaryAdapter(Context context,int position){
        dbManager = DBManager.getInstance(context);
        diaryList = dbManager.getListDiary(position);
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return diaryList.size();
    }

    @Override
    public Diary getItem(int position) {
        return diaryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View itemView, ViewGroup parent) {
        DiaryHolder diaryHolder;
        if(itemView==null){
            itemView = inflater.inflate(R.layout.item_diary,parent,false);
            diaryHolder = new DiaryHolder();
            diaryHolder.civCoverDiary = (CircleImageView)itemView.findViewById(R.id.circle_image_item_diary);
            diaryHolder.txtDescription = (TextView)itemView.findViewById(R.id.txt_item_diary_description);
            diaryHolder.txtTime = (TextView)itemView.findViewById(R.id.txt_item_diary_time);
            itemView.setTag(diaryHolder);
        }else{
            diaryHolder = (DiaryHolder)itemView.getTag();
        }
        Diary diary = diaryList.get(position);
        diaryHolder.civCoverDiary.setImageBitmap(BitmapFactory.decodeFile(diary.getImage()));
        diaryHolder.txtDescription.setText(diary.getDescription());
        diaryHolder.txtTime.setText(diary.getTimeDiary());
        return itemView;
    }
    private class DiaryHolder{
        public CircleImageView civCoverDiary;
        public TextView txtDescription;
        public TextView txtTime;
    }
}