package entity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import thanhcong.com.nhatkydulich.R;

/**
 * Created by ThanhCong on 13/12/2016.
 */
public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.DiaryHolder> {
    private Context context;
    private List<Diary> diaryList;

    public DiaryAdapter(Context context,List<Diary> diaryList){
        this.context = context;
        this.diaryList = diaryList;
    }
    @Override
    public DiaryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemViewDiary = inflater.inflate(R.layout.item_diary,parent,false);
        DiaryHolder diaryHolder = new DiaryHolder(context,itemViewDiary);
        return diaryHolder;
    }

    @Override
    public void onBindViewHolder(DiaryHolder holder, int position) {
        Diary diary = diaryList.get(getItemCount() - position -1);

        CircleImageView civCover = holder.civCover;
        civCover.setImageBitmap(BitmapFactory.decodeFile(diary.getImage()));
        TextView txtDescription = holder.txtItemDiaryDescription;
        txtDescription.setText(diary.getDescription());
        TextView txtTime = holder.txtItemDiaryTime;
        txtTime.setText(diary.getTimeDiary());
    }

    @Override
    public int getItemCount() {
        return diaryList.size();
    }

    public class DiaryHolder extends RecyclerView.ViewHolder{
        public CircleImageView civCover;
        public TextView txtItemDiaryDescription,txtItemDiaryTime;
        public Context context;

        public DiaryHolder(Context context,View itemView) {
            super(itemView);
            this.context = context;
            civCover = (CircleImageView)itemView.findViewById(R.id.circle_image_item_diary);
            txtItemDiaryDescription = (TextView)itemView.findViewById(R.id.txt_item_diary_description);
            txtItemDiaryTime = (TextView)itemView.findViewById(R.id.txt_item_diary_time);
        }
    }
    public void updateWhenAddDiary(int position){
        DBManager dbManager = DBManager.getInstance(context);
        diaryList = dbManager.getListDiary(position);
        notifyItemInserted(diaryList.size() - 1);
        notifyDataSetChanged();
    }
}
