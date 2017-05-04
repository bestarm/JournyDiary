package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import thanhcong.com.nhatkydulich.R;

/**
 * Created by ThanhCong on 25/04/2017.
 */

public class RegisterOrLoginFragment extends android.support.v4.app.Fragment {
    private EditText edtUserName, edtPassWord;
    private Button btnRegisterOrLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_register,container,false);
        edtPassWord = (EditText) v.findViewById(R.id.edt_fragment_register_password);
        edtUserName = (EditText)v.findViewById(R.id.edt_fragment_register_username);
        btnRegisterOrLogin = (Button)v.findViewById(R.id.btn_fragment_register_or_login);
        return v;
    }
}
