package com.example.hraftiproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.hraftiproject.Activities.HomeActivity;

import com.google.firebase.FirebaseApp;

import com.example.hraftiproject.Model.JobModel;
import com.example.hraftiproject.Activities.RegisterActivity;
import com.example.hraftiproject.Model.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


public class Helper extends SQLiteOpenHelper {

    // creating a constant variables for our database.
    private static final String DB_NAME = "database";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "professionnel";
    private static final String ID_COL = "id";
    private static final String Nom_COL = "nomComplet";
    private static final String EMAIL_COL = "email";
    private static final String PASSWORD_COL = "mdps";
    private static final String Metier_COL = "metier";
    private static final String numTel_COL = "numTel";
    private static final String ville_COL = "ville";
    private static final String description_COL = "description";
    private static final String image_COL = "image";
    Context context;

    private   byte[] imageInBytes;
    private ByteArrayOutputStream byteArrayOutputStream;



    public Helper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context=context;
    }
    public Helper(RegisterActivity context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public Helper(HomeActivity context) {
        super(context, DB_NAME, null, DB_VERSION);
    }




    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating an sqlite query and we are setting our column names along with their data types.
        String query ="CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Nom_COL +  " TEXT,"
                +EMAIL_COL+  " TEXT UNIQUE ,"
                +PASSWORD_COL+ " TEXT ,"
                + Metier_COL + " TEXT ,"
                + numTel_COL + " INTEGER UNIQUE ,"
                + ville_COL + " TEXT,"
                + description_COL + " TEXT,"
                +image_COL+ " BLOB)";

        // at last we are calling a exec sql method to execute above sql query

        db.execSQL(query);

    }

    // this method is use to add new Professionnel to our sqlite database.
    public void addNewProfessionnel(String nom ,String email ,String password,String mt,int numtel ,String ville,String  description ,Bitmap Image) {

        // on below line we are creating a variable for our sqlite database and calling writable method as we are writing data in our database.

        SQLiteDatabase db = this.getWritableDatabase();
        ///IMAGE STORE
        Bitmap profileImage=Image; //imagetostorebitmap
        byteArrayOutputStream= new ByteArrayOutputStream();
        profileImage.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        imageInBytes=byteArrayOutputStream.toByteArray();

        // on below line we are creating a  variable for content values.

        ContentValues values = new ContentValues();

        // on below line we are passing all values along with its key and value pair.

        values.put(Nom_COL, nom);
        values.put(EMAIL_COL, email);
        values.put(PASSWORD_COL, password);
        values.put(Metier_COL , mt);
        values.put(numTel_COL , numtel);
        values.put(ville_COL, ville);
        values.put(description_COL, description);
        values.put(image_COL, imageInBytes);

        // after adding all values we are passing content values to our table.
        db.insert(TABLE_NAME, null, values);
       // Long checkifQueryRun =db.insert(TABLE_NAME, null, values);
        db.close();


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public Boolean checkEmail(String email){
       SQLiteDatabase mydatabase =  this.getWritableDatabase();
        Cursor cursor=mydatabase.rawQuery("select * from professionnel where email = ?",new String[]{email});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public Boolean checkEmailPassword(String email,String mdps){
        SQLiteDatabase mydatabase =  this.getWritableDatabase();
        Cursor cursor=mydatabase.rawQuery("select * from professionnel where email = ? and mdps=?",new String[]{email,mdps});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean updatePasswd(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PASSWORD_COL, password);
       long result =  db.update(TABLE_NAME, values,"email= ?" ,new String[]{email});
        if(result == -1) return false;
        else return true;
    }
    public ArrayList<User> getAllData() {

        ArrayList<User> arrayList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM professionnel", null);

        while (cursor.moveToNext()) {
            String Name = cursor.getString(1);
            String Email = cursor.getString(2);
            String Metier = cursor.getString(4);
            int phone = cursor.getInt(5);
            String ville = cursor.getString(6);
            String description = cursor.getString(7);



            User user = new User(Name, Email, Metier, ville, description, phone);
            arrayList.add(user);
        }
        return arrayList;
    }

    public User getUser(String Email) {

        SQLiteDatabase db = this.getReadableDatabase();


        String sql = "SELECT * FROM " + TABLE_NAME
                + " WHERE " + EMAIL_COL + " Like '" + Email + "'";
        try {
            Cursor cursor = db.rawQuery(sql, null);

            User user = new User();
            // Read data, I simplify cursor in one line
            if (cursor.moveToFirst()) {
                byte[] imgByte = cursor.getBlob(8);
                Bitmap image=BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
                // Get imageData in byte[]. Easy, right?
                user.setId(cursor.getInt(0));
                user.setName(cursor.getString(1));
                user.setEmail(cursor.getString(2));
                user.setMetier(cursor.getString(4));
                user.setPhone(cursor.getInt(5));
                user.setVille(cursor.getString(6));
                user.setDescription(cursor.getString(7));
                user.setImage(image);
            }
            cursor.close();
            db.close();
            return user;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void UpdateProfessionnel(int id,String nom, String email, String mt, int numtel, String ville, String description,Bitmap image) {

        String where="id=?";
        String[] whereArgs = new String[] {String.valueOf(id)};
        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        Bitmap profileImage=image;
        byteArrayOutputStream= new ByteArrayOutputStream();
        profileImage.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        imageInBytes=byteArrayOutputStream.toByteArray();
        values.put(Nom_COL, nom);
        values.put(EMAIL_COL, email);
        values.put(Metier_COL, mt);
        values.put(numTel_COL, numtel);
        values.put(ville_COL, ville);
        values.put(description_COL, description);
        values.put(image_COL, imageInBytes);


        // after adding all values we are passing
        // content values to our table.
        db.update(TABLE_NAME, values,where,whereArgs);
        db.close();
    }


    public ArrayList<JobModel> readJobs() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorJobs = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        ArrayList<JobModel> jobModelArrayList = new ArrayList<>();

        while (cursorJobs.moveToNext()){
            byte[] imgByte = cursorJobs.getBlob(8);
            Bitmap image=BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            jobModelArrayList.add(new JobModel(
                    cursorJobs.getString(4),
                    cursorJobs.getString(1),
                    cursorJobs.getString(5),
                    cursorJobs.getString(6),
                    cursorJobs.getString(2),
                    image));
        }

        cursorJobs.close();
        return jobModelArrayList;
    }

    public ArrayList<JobModel> returnJob( String value){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorJobs = db.rawQuery("SELECT * FROM " + TABLE_NAME + " where metier like ? or ville like ? or nomComplet like ? " , new String[]{"%"+value+"%","%"+value+"%","%"+value+"%"});
        ArrayList<JobModel> jobs = new ArrayList<JobModel>();

        while (cursorJobs.moveToNext()){
            byte[] imgByte = cursorJobs.getBlob(8);
            Bitmap image=BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
            jobs.add(new JobModel(
                    cursorJobs.getString(4),
                    cursorJobs.getString(1),
                    cursorJobs.getString(5),
                    cursorJobs.getString(6),
                    cursorJobs.getString(2),
                   image

                    ));
        }

        cursorJobs.close();
        return jobs;
    }

    public String getEmailByName(String name){
        String email = " ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorJobs = db.rawQuery("SELECT email FROM " + TABLE_NAME + " where  nomComplet = ?" , new String[]{name});
        if (cursorJobs.moveToFirst()) {
             email  =cursorJobs.getString(0);
        }
        cursorJobs.close();
        return email;
    }


}



