package entity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fragment.MyJournyFragment;
import thanhcong.com.nhatkydulich.R;

/**
 * Created by ThanhCong on 03/12/2016.
 */
public class JournyAdapter extends RecyclerView.Adapter<JournyAdapter.JournyHolder> {
    public static final String KEY_BUNDLE_SEND_DBMANAGER = "KEY_BUNDLE_SEND_DBMANAGER";
    public static final String KEY_INTENT_SEND_POSITION = "KEY_INTENT_SEND_POSITION";
    private List<Journy> journyList;
    private Context mContext;
    private DBManager dbManager;
    private MyJournyFragment myJournyFragment;

    public JournyAdapter(Context mContext, MyJournyFragment myJournyFragment){
        dbManager = DBManager.getInstance(mContext);
        this.journyList = dbManager.getListJourny();
        this.mContext = mContext;
        this.myJournyFragment = myJournyFragment;
    }

    @Override
    public JournyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View itemViewJourny = inflater.inflate(R.layout.item_journy,parent,false);
        JournyHolder journyHolder = new JournyHolder(mContext,itemViewJourny);
        return journyHolder;
    }

    @Override
    public void onBindViewHolder(JournyHolder holder, int position) {
        Journy journy = journyList.get(getItemCount() - position -1);

        TextView txtTimeItemJourny = holder.txtTimeItemJourny;
        txtTimeItemJourny.setText(journy.getTimeJourny());
        TextView txtNameItemJourny = holder.txtNameItemJourny;
        txtNameItemJourny.setText(journy.getNameJourny());
        ImageView ivCoverItemJourny = holder.ivCoverItemJourny;
        ivCoverItemJourny.setImageBitmap(BitmapFactory.decodeFile(journy.getCoverImage()));
    }

    @Override
    public int getItemCount() {
        return journyList.size();
    }

    public class JournyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView txtTimeItemJourny,txtNameItemJourny;
        public ImageView ivCoverItemJourny;
        private Context context;

        public JournyHolder(Context context,View itemView) {
            super(itemView);
            txtTimeItemJourny = (TextView) itemView.findViewById(R.id.txt_item_journy_time);
            ivCoverItemJourny = (ImageView)itemView.findViewById(R.id.iv_item_journy);
            txtNameItemJourny = (TextView)itemView.findViewById(R.id.txt_item_journy_name);
            this.context = context;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                myJournyFragment.showDiaryActivity(position);
            }
        }
    }
    public void updateListJourny(){
        journyList = dbManager.getListJourny();
    }
    public void notifyItemInsert(){
        notifyItemInserted(journyList.size() - 1);
        notifyDataSetChanged();
    }
}
