package services;

import android.content.Context;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import models.User;
import utils.Logger;

/**
 * Created by remy on 23/05/2016.
 */
public class CacheManager {

    @Inject
    public CacheManager(){

    }

    public String getFilesLocation(Context ctx){
        return ctx.getFilesDir().getAbsolutePath();
    }


    public boolean set(Context ctx, String key, String value){
        Logger.debug("Cache : Set " + key +" => " + value);
        try{
            FileOutputStream outputStream = ctx.openFileOutput(key, Context.MODE_PRIVATE);
            outputStream.write(value.getBytes());
            outputStream.close();
            return true;
        }
        catch(IOException e) {
            Logger.warn("[CacheManager] IO EXCeetion when setting value. " + e.getMessage(), e);
        }
        return false;
    }

    public<T> boolean setObject(Context ctx, String key, T value){
        Logger.debug("Cache : Set object " + key);
        try{
            FileOutputStream outputStream = ctx.openFileOutput(key, Context.MODE_PRIVATE);
            ObjectOutputStream objStream = new ObjectOutputStream(outputStream);
            objStream.writeObject(value);
            objStream.close();
            return true;
        }
        catch(IOException e) {
            Logger.warn("[CacheManager] IO EXCeetion when setting value. " + e.getMessage(), e);
        }
        return false;
    }

    public String getString(Context ctx, String key){
        try{
            FileInputStream inputStream = ctx.openFileInput(key);
            byte[] buffer =   new byte[(int) inputStream.getChannel().size()];

            inputStream.read(buffer);
            inputStream.close();
            String result = new String(buffer);
            Logger.debug("Cache : Get " + key + " => " + result);
            return result;
        }
        catch(IOException e) {
            Logger.warn("[CacheManager] IO EXCeetion when getting value. " + e.getMessage(), e);
        }
        return null;
    }

    public<T> T getObject(Context ctx, String key){
        try{
            FileInputStream inputStream = ctx.openFileInput(key);
            ObjectInputStream objStream = new ObjectInputStream(inputStream);

            T result = (T)objStream.readObject();
            objStream.close();
            return result;
        }
        catch(IOException e) {
            Logger.warn("[CacheManager] IO EXCeetion when getting value. " + e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            Logger.warn("[CacheManager] ClassNotFoundException when getting value. " + e.getMessage(), e);
        }
        return null;
    }

    public boolean exists(Context ctx, String key){
        ArrayList<String> fileList = new ArrayList<String>(Arrays.asList(ctx.fileList()));

        for(String file : fileList){
            if(file.equals(key)){
                return true;
            }
        }
        return false;
    }

    public boolean delete(Context ctx, String key){
        try{
            ctx.deleteFile(key);
            return true;
        }
        catch(Exception e) {
            Logger.warn("[CacheManager] IO EXCeetion when deleting value. " + key, e);
        }
        return false;

    }

}
