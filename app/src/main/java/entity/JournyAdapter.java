package entity;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.tc.inter.IJournyChangeFragmentListener;
import java.util.List;
import thanhcong.com.nhatkydulich.R;

/**
 * Created by ThanhCong on 03/12/2016.
 */
public class JournyAdapter extends RecyclerView.Adapter<JournyAdapter.JournyHolder> {
    private List<Journy> journyList;
    private Context mContext;
    private DBManager dbManager;
    private IJournyChangeFragmentListener listener;

    public JournyAdapter(Context mContext, IJournyChangeFragmentListener myJournyFragment){
        dbManager = DBManager.getInstance(mContext);
        this.journyList = dbManager.getListJourny();
        this.mContext = mContext;
        this.listener = myJournyFragment;
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
        if(journy.getCoverImage() != null && !journy.getCoverImage().equals("")){
            ivCoverItemJourny.setImageBitmap(BitmapFactory.decodeFile(journy.getCoverImage()));
        }
    }

    @Override
    public int getItemCount() {
        return journyList.size();
    }

    public class JournyHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView txtTimeItemJourny,txtNameItemJourny;
        public ImageView ivCoverItemJourny;
        public Spinner spinnerOption;

        public JournyHolder(Context context,View itemView) {
            super(itemView);
            txtTimeItemJourny = (TextView) itemView.findViewById(R.id.txt_item_journy_time);
            ivCoverItemJourny = (ImageView)itemView.findViewById(R.id.iv_item_journy);
            txtNameItemJourny = (TextView)itemView.findViewById(R.id.txt_item_journy_name);

            spinnerOption = (Spinner)itemView.findViewById(R.id.spinner_item_journy_option);
            ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                    context,R.array.option_item_journy,android.R.layout.simple_spinner_item);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerOption.setAdapter(spinnerAdapter);
            spinnerOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 1:
                            listener.showAddJournyFragment(journyList.get(
                                    journyList.size() - getAdapterPosition() - 1));
                            break;
                        case 2:
                            dbManager.delete("journy",journyList.get(
                                    journyList.size() - getAdapterPosition() -1).getJournyID());
                            listener.updateJournyListFragment();
                            break;
                        default:
                            break;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //this.context = context;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if(position != RecyclerView.NO_POSITION){
                listener.showDiaryActivity(position);
            }
        }
    }
}
