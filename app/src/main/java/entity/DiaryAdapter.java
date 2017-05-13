package entity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.tc.inter.IDiaryActChangeFragmentListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import thanhcong.com.nhatkydulich.CustomGalleryActivity;
import thanhcong.com.nhatkydulich.DiaryActivity;
import thanhcong.com.nhatkydulich.MapDiaryActivity;
import thanhcong.com.nhatkydulich.R;

import static thanhcong.com.nhatkydulich.MapDiaryActivity.KEY_INTENT_SEND_LATTITUDE;
import static thanhcong.com.nhatkydulich.MapDiaryActivity.KEY_INTENT_SEND_LONGTITUDE;

public class DiaryAdapter extends BaseAdapter {
    public static final String KEY_SEND_DIARYID = "KEY_SEND_DIARYID";
    public static final String KEY_SEND_TITLE_DIARY = "KEY_SEND_TITLE_DIARY";
    List<Diary> diaryList = new ArrayList<Diary>();
    private DBManager dbManager;
    private LayoutInflater inflater;
    private IDiaryActChangeFragmentListener listener;
    private Context context;

    public DiaryAdapter(Context context, int position) {
        dbManager = DBManager.getInstance(context);
        diaryList = dbManager.getListDiary(position);
        inflater = LayoutInflater.from(context);
        if (context instanceof IDiaryActChangeFragmentListener) {
            listener = (IDiaryActChangeFragmentListener) context;
        }
        this.context = context;
    }

    @Override
    public int getCount() {
        return diaryList.size();
    }


    //reverse list view
    @Override
    public Diary getItem(int position) {
        return diaryList.get(getCount() - position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View itemView, ViewGroup parent) {
        DiaryHolder diaryHolder;
        if (itemView == null) {
            itemView = inflater.inflate(R.layout.item_diary, parent, false);
            diaryHolder = new DiaryHolder();
            diaryHolder.civCoverDiary = (CircleImageView) itemView.findViewById(R.id.circle_image_item_diary);
            diaryHolder.txtDescription = (TextView) itemView.findViewById(R.id.txt_item_diary_description);
            diaryHolder.txtTime = (TextView) itemView.findViewById(R.id.txt_item_diary_time);
            diaryHolder.spinnerOption = (Spinner) itemView.findViewById(R.id.spinner_option_item_diary);
            diaryHolder.ivShowMap = (ImageView)itemView.findViewById(R.id.iv_item_diary_show_map);
        } else {
            diaryHolder = (DiaryHolder) itemView.getTag();
        }

        diaryHolder.civCoverDiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(KEY_SEND_DIARYID, getItem(position).getDiaryID());
                intent.putExtra(KEY_SEND_TITLE_DIARY, getItem(position).getPlaceDiary());
                intent.setClass(context, CustomGalleryActivity.class);
                context.startActivity(intent);
            }
        });

        // setting for spinner option of diary item
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(context,
                R.array.option_item_diary, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        diaryHolder.spinnerOption.setAdapter(spinnerAdapter);
        diaryHolder.spinnerOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int posItemSpinner, long id) {
                switch (posItemSpinner) {
                    case 1:
                        listener.showAddDiaryFragment(true, position);// send position, true for edit
                        break;
                    case 2:
                        dbManager.delete("diary", getItem(position).getDiaryID(),null);
                        if (context instanceof DiaryActivity) {
                            ((DiaryActivity) context).updateDetailJournyFragment();
                        }
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // open activity google map
        diaryHolder.ivShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Location location = dbManager.getLocationOfDiary(getItem(position).getDiaryID());
//                String uri = "geo:"+location.getLattitude()+","+location.getLongtitude()+"?z=18";
//                Uri gmmIntentUri = Uri.parse(uri);
//                Intent mapIntent = new Intent(Intent.ACTION_VIEW,gmmIntentUri);
//                mapIntent.setPackage("com.google.android.apps.map");
////                intent.setClass(context, MapDiaryActivity.class);
//                context.startActivity(mapIntent);
                Intent intent = new Intent();
                intent.setClass(context, MapDiaryActivity.class);
                intent.putExtra(KEY_INTENT_SEND_LATTITUDE,location.getLattitude());
                intent.putExtra(KEY_INTENT_SEND_LONGTITUDE,location.getLongtitude());
                context.startActivity(intent);
            }
        });

        Diary diary = diaryList.get(getCount() - position - 1);
        if (diary.getImage() == null) {
            diaryHolder.civCoverDiary.setImageBitmap(BitmapFactory.
                    decodeFile("/storage/emulated/0/DCIM/100ANDRO/none_image.PNG"));
        } else {
            diaryHolder.civCoverDiary.setImageBitmap(BitmapFactory.decodeFile(diary.getImage()));
        }
        diaryHolder.txtDescription.setText(diary.getDescription());
        diaryHolder.txtTime.setText(diary.getTimeDiary());

        itemView.setTag(diaryHolder);
        return itemView;
    }

    private class DiaryHolder {
        public CircleImageView civCoverDiary;
        public TextView txtDescription;
        public TextView txtTime;
        public Spinner spinnerOption;
        public ImageView ivShowMap;
    }


}