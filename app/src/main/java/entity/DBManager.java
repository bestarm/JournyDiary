package entity;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThanhCong on 04/12/2016.
 */
public class DBManager{
    private static String TAG = DBManager.class.getName();
    private static final String PATH_DB = Environment.getDataDirectory().getPath()+"/data/thanhcong.com.nhatkydulich/databases";
    private static final String DB_NAME = "journydiarydb.db";
    private SQLiteDatabase mSQLiteDatabase;
    private static final String GET_LIST_JOURNY = "select * from journy";
    private static final String GET_LIST_DIARY = "select * from diary where JournyId = ";
    private int sizeJourny = 0;
    //implement Singleton
    private static DBManager instance = null;
    protected DBManager(){

    }
    public static DBManager getInstance(Context context){
        if(instance == null){
            instance = new DBManager(context);
        }
        return instance;
    }
    protected DBManager(Context context){
        copyDB(context);
    }
    public void copyDB(Context mContext){
        // tạo thư mục nếu chưa có
        new File(PATH_DB).mkdir();
        File fileDB = new File(PATH_DB+"/"+DB_NAME);
        if(fileDB.exists()){
            return;
        }

        AssetManager assetManager = mContext.getAssets();
        try {
            InputStream input = assetManager.open(DB_NAME);
            FileOutputStream outputStream = new FileOutputStream(fileDB);
            byte buff[] = new byte[1024];
            int length = input.read(buff);
            while (length > 0){
                outputStream.write(buff,0,length);
                length = input.read(buff);
            }
            outputStream.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void openDB(){
        if(mSQLiteDatabase == null || !mSQLiteDatabase.isOpen()){
            mSQLiteDatabase = SQLiteDatabase.openDatabase(PATH_DB+"/"+DB_NAME,null,SQLiteDatabase.OPEN_READWRITE);
            Log.i(TAG,"open completted");
        }
    }

    public boolean insert(String tableName, String columnData[][]){
        openDB();
        ContentValues values = new ContentValues();
        for(int i = 0; i < columnData.length; i ++){
            values.put(columnData[i][0],columnData[i][1]);
        }
        long result = mSQLiteDatabase.insert(tableName,null,values);
        return result > -1;
    }

    public void closeDB(){
        if(mSQLiteDatabase != null && mSQLiteDatabase.isOpen()){
            mSQLiteDatabase.close();
        }
    }

    public List<Journy> getListJourny(){
        List<Journy> journyList = new ArrayList<Journy>();
        openDB();
        Cursor cursor = mSQLiteDatabase.rawQuery(GET_LIST_JOURNY,null);
        if(cursor != null){
            cursor.moveToFirst();
            int nameJournyIndex = cursor.getColumnIndex("Name");
            int placeJournyIndex = cursor.getColumnIndex("PlaceJourny");
            int coverImageIndex = cursor.getColumnIndex("CoverImage");
            int timeJournyIndex = cursor.getColumnIndex("TimeJourny");
            int journyIDIndex = cursor.getColumnIndex("JournyId");
            int distanceIndex = cursor.getColumnIndex("DistanceJourny");
            int userIDIndex = cursor.getColumnIndex("UserID");

            while (cursor.isAfterLast() == false){
                String namejourny = cursor.getString(nameJournyIndex);
                String placeJourny = cursor.getString(placeJournyIndex);
                String coverImage = cursor.getString(coverImageIndex);
                String timeJourny = cursor.getString(timeJournyIndex);
                int journyID = cursor.getInt(journyIDIndex);
                int distance = cursor.getInt(distanceIndex);
                int userID = cursor.getInt(userIDIndex);

                journyList.add(new Journy(namejourny,placeJourny,timeJourny,coverImage,journyID,distance,userID));
                cursor.moveToNext();
            }
        }
        sizeJourny = journyList.size();
        return journyList;
    }

    public int getSizeJourny(){
        return sizeJourny;
    }

    public List<Diary> getListDiary(int position){
        List<Diary> diaryList = new ArrayList<Diary>();
        int journyIDQuery = getListJourny().get(getListJourny().size() - position - 1).getJournyID();
        openDB();
        Cursor cursor = mSQLiteDatabase.rawQuery(GET_LIST_DIARY+journyIDQuery,null);
        if(cursor != null){
            cursor.moveToFirst();
            int diaryIDIndex = cursor.getColumnIndex("DiaryId");
            int timeDiaryIndex = cursor.getColumnIndex("TimeDiary");
            int placeDiaryIndex = cursor.getColumnIndex("PlaceDiary");
            int descriptionIndex = cursor.getColumnIndex("Description");
            int imageIndex = cursor.getColumnIndex("Image");
            int videoIndex = cursor.getColumnIndex("Video");
            int mp3Index = cursor.getColumnIndex("Mp3");
            int journyIDIndex = cursor.getColumnIndex("JournyId");
            while (cursor.isAfterLast() == false){
                int diaryID = cursor.getInt(diaryIDIndex);
                String timeDiary = cursor.getString(timeDiaryIndex);
                String placeDiary = cursor.getString(placeDiaryIndex);
                String imageCover = cursor.getString(imageIndex);
                String description = cursor.getString(descriptionIndex);
                String video = cursor.getString(videoIndex);
                String mp3 = cursor.getString(mp3Index);
                int journyID = cursor.getInt(journyIDIndex);

                diaryList.add(new Diary(diaryID,timeDiary,placeDiary,description,imageCover,video,mp3,journyID));
                cursor.moveToNext();
            }
        }
        return diaryList;
    }
}
