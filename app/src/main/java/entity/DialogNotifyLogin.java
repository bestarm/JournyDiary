package entity;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import thanhcong.com.nhatkydulich.R;

/**
 * Created by ThanhCong on 25/04/2017.
 */

public class DialogNotifyLogin extends Dialog implements View.OnClickListener{
    private TextView txtLogin, txtRegister;

    public DialogNotifyLogin(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_notifi_login);
        initviews();

    }

    private void initviews() {
        txtLogin = (TextView)findViewById(R.id.txt_dialog_login);
        txtRegister = (TextView)findViewById(R.id.txt_dialog_register);

        txtRegister.setOnClickListener(this);
        txtLogin.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

    }
}
