package fragment;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tc.networking.NetworkClient;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;

import de.hdodenhof.circleimageview.CircleImageView;
import entity.DialogNotifyLogin;
import thanhcong.com.nhatkydulich.R;

/**
 * Created by ThanhCong on 29/11/2016.
 */
public class AccountFragment extends Fragment {
    private static final String ACCOUNT_FILE_NAME = "account.txt";
    private InforFragment inforFragment;
    private RegisterOrLoginFragment registerOrLoginFragment;
    private NetworkClient networkClient;



    public AccountFragment(){
        networkClient = NetworkClient.getInstance(getContext());


        inforFragment = new InforFragment();
        registerOrLoginFragment = new RegisterOrLoginFragment();


    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_account,container,false);
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!isAccountExit()){
            showRegisterOrLoginFragment();
        }else{
            showInforFragment();
        }
    }

    public void showRegisterOrLoginFragment(){
        FragmentManager fragmentManager = getChildFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_account,registerOrLoginFragment).commit();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void showInforFragment(){
        FragmentManager fragmentManager = getChildFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_account,inforFragment).commit();
    }

    public boolean isAccountExit(){
        try {
            getContext().openFileInput(ACCOUNT_FILE_NAME);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }
}
