package com.tc.networking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import entity.DBManager;
import entity.Journy;

/**
 * Created by ThanhCong on 20/04/2017.
 */

public class NetworkClient {
    private DBManager dbManager;
    private Socket socketClient = new Socket();
    private OutputStream out;
    private String url;

    private static NetworkClient instance;

    public static NetworkClient getInstance(Context context){
        if(instance == null){
            instance = new NetworkClient(context);
        }
        return instance;
    }
    protected NetworkClient(Context context){
        this.dbManager = DBManager.getInstance(context);
        this.url = "10.0.3.2";
    }

    public void uploadJourny(){
        String journyJSON = "000"+journyJSONToSync();
        new UploadTask().execute("upload journy",journyJSON);
    }

    public String journyJSONToSync(){
        ArrayList<Journy> arrListJourny = new ArrayList<Journy>();
        ArrayList<Journy> arrayListJournyNotSync = new ArrayList<Journy>();
        arrListJourny = dbManager.getListJourny();
        String strJourny = "[";
        int sizeList = arrListJourny.size();
        for(int i = 0; i < sizeList;i++){
            Journy journy = arrListJourny.get(i);
            if(!journy.isSync()){
                arrayListJournyNotSync.add(journy);
            }
        }
        sizeList = arrayListJournyNotSync.size();
        for(int i = 0; i < sizeList;i++){
            Journy journy = arrayListJournyNotSync.get(i);
            strJourny +="{\"JournyID\":"+journy.getJournyID()+
                    ",\"PlaceJourny\":\""+journy.getPlaceJourny()+
                    "\",\"Name\":\""+journy.getNameJourny()+
                    "\",\"DistanceJourny\":"+journy.getDistance()+
                    ",\"UserID\":"+journy.getUserID()+
                    ",\"CoverImage\":\""+journy.getCoverImage()+
                    "\",\"TimeJourny\":\""+journy.getTimeJourny()+"\"}";
            if(i< sizeList-1){
                strJourny+=",";
            }else{
                strJourny+="]";
            }
        }
        return strJourny;
    }

    private class UploadTask extends AsyncTask<String,Void,Void>{


        @Override
        protected Void doInBackground(String... params) {
            switch (params[0]){
                case "upload journy":
                    try {
                        socketClient.connect(new InetSocketAddress(url,8000));
                        out = socketClient.getOutputStream();
                        out.write(params[1].getBytes());
                        out.write("\r\n".getBytes());
                    } catch (IOException e) {
                        Log.e("ERROR","Error upload journy");
                    }
                    break;
                case "upload image":

                    String buff = "";
                    int size = 6-params[1].length();
                    for(int i = 0; i < size; i++){
                        buff +="0";
                    }
                    int diaryID = Integer.parseInt(params[1]);
                    String[] arrPathImage = dbManager.getImagesOfDiary(diaryID);
                    size = arrPathImage.length;
                    try {
                        socketClient.connect(new InetSocketAddress(url,8000));
                        out = socketClient.getOutputStream();
                        // format header : 010.0000001
                        out.write(("010"+buff+params[1]).getBytes());
                    } catch (IOException e) {
                        Log.e("ERROR","Error write header 010");
                    }
                    for(int i = 0;i < size; i++){
                        Bitmap bm = BitmapFactory.decodeFile(arrPathImage[i]);
                        ByteArrayOutputStream bao = new ByteArrayOutputStream();
                        bm.compress(Bitmap.CompressFormat.JPEG,0,bao);
                        byte[] b = bao.toByteArray();
                        try {
                            InputStream in = socketClient.getInputStream();
                            out.write(b);
                            in.read();

                        } catch (IOException e) {
                            Log.e("ERROR","Error upload image");
                        }
                    }
                    break;
                default:
                    break;
            }

            return null;
        }
    }

    public boolean isCoonectToServerStatus(){
        try {
            URL mUrl = new URL(url);
            URLConnection connection = mUrl.openConnection();
            connection.connect();
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
}
