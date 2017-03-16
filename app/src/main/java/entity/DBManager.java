package entity;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ThanhCong on 04/12/2016.
 */
public class DBManager{
    private static String TAG = DBManager.class.getName();
    private static final String PATH_DB = Environment.getDataDirectory().getPath()+"/data/thanhcong.com.nhatkydulich/databases";
    private static final String DB_NAME = "journydiarydb.db";
    private SQLiteDatabase mSQLiteDatabase;
    private static final String GET_IMAGES_DIARY = "select Path from images where DiaryID = ";
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

    public boolean insert(String tableName, String columnData[]){
        openDB();
        long result = -1;
        ContentValues values = new ContentValues();

        switch (tableName){
            case "images":
                int size = columnData.length;
                for(int i = 0; i < size; i++){
                    values.put("Path",columnData[i]);
                    result = mSQLiteDatabase.insert(tableName,null,values);
                }
                break;
            case "diary":
                values.put("TimeDiary",columnData[0]);
                values.put("PlaceDiary",columnData[1]);
                values.put("Description",columnData[2]);
                if(!columnData[3].equals("")){
                    //values.put("Image","/storage/emulated/0/DCIM/100ANDRO/none_image.PNG");
                    values.put("Image",columnData[3]);
                }
                values.put("Video",columnData[4]);
                values.put("Mp3",columnData[5]);
                values.put("JournyId",columnData[6]);
                result = mSQLiteDatabase.insert(tableName,null,values);
                break;
            default:
                break;
        }

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

    public int getJournyID(int position) {
        int index = 0;
        openDB();
        Cursor cursor = mSQLiteDatabase.rawQuery(GET_LIST_JOURNY,null);
        if(cursor != null){
            int totalRow = cursor.getCount();
            cursor.moveToFirst();
            int journyIdIndex = cursor.getColumnIndex("JournyId");
            while (!cursor.isAfterLast()){
                if(totalRow - index -1 == position){
                    return cursor.getInt(journyIdIndex);
                }
                index++;
                cursor.moveToNext();
            }
        }
        return 0;
    }

    public String[] getImagesOfDiary(int diaryID){
        openDB();
        Cursor cursor = mSQLiteDatabase.rawQuery(GET_IMAGES_DIARY+diaryID,null);
        int count = cursor.getCount();
        String arrImages[] = new String[count];
        if(cursor != null){
            cursor.moveToFirst();
            int i = cursor.getPosition();
            while (i < count){
                arrImages[i] = cursor.getString(0);
                i = cursor.getPosition();
                cursor.moveToNext();
            }
        }
        return arrImages;
    }

    public boolean update(String tableName, int diaryID,String[] values){
        int result = -1;
        openDB();
        ContentValues contentValues = new ContentValues();
        contentValues.put("PlaceDiary",values[0]);
        contentValues.put("Description",values[1]);
        result = mSQLiteDatabase.update(tableName,contentValues,"DiaryID ="+diaryID,null);
                            return result > -1;
    }

}
