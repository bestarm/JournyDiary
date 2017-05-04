package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import thanhcong.com.nhatkydulich.R;

/**
 * Created by ThanhCong on 25/04/2017.
 */

public class InforFragment extends android.support.v4.app.Fragment{
    private CircleImageView civ_avatar;
    private TextView txtName, txtInfor, txtSetting,txtRate, txtReport;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_infor,container,false);
        civ_avatar = (CircleImageView)rootView.findViewById(R.id.civ_fragment_account_avatar);
        txtName = (TextView)rootView.findViewById(R.id.txt_fragment_account_name);
        txtInfor = (TextView)rootView.findViewById(R.id.txt_fragment_account_infor);
        txtSetting = (TextView)rootView.findViewById(R.id.txt_fragment_account_setting);
        txtRate = (TextView)rootView.findViewById(R.id.txt_fragment_account_rate);
        txtReport = (TextView)rootView.findViewById(R.id.txt_fragment_account_report);
        return rootView;
    }
}
