package com.thirdparty.proxy.cache;

import android.content.Context;
import android.os.Environment;

import com.thirdparty.proxy.utils.DataCleanUtils;
import com.thirdparty.proxy.utils.DiskLruCacheUtil;
import com.thirdparty.proxy.utils.FileUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author:dongpo 创建时间: 6/27/2016
 * 描述:
 * 修改:
 */
public class FileSaveHelper {
    public static final int INTERNAL_FILES_DIR = 1;
    public static final int INTERNAL_CACHE_DIR = 2;
    public static final int EXTERNAL_FILES_DIR = 3;

    /**
     * @param context
     * @param ser
     * @param
     * @return 保存到files/file文件夹
     */
    public static boolean saveObjectToFileDir(Context context, Serializable ser, String fileName) {
        try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            return saveObject(fos, ser);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param context
     * @param ser
     * @param
     * @return 保存到files/cache文件夹
     */
    public static boolean saveObjectToCacheDir(Context context, Serializable ser, String fileName) {
        try {
            File file = new File(context.getCacheDir(), fileName);
            if (file.exists()) {
                file.delete();
            }
            FileOutputStream fos = new FileOutputStream(file);
            return saveObject(fos, ser);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @param context
     * @param ser
     * @param fileName  文件名称
     * @param directory 文件夹名称
     * @return 保存到SD卡的文件夹下
     */
    public static boolean saveObjectToExternal(Context context, Serializable ser, String fileName, String directory) {
        boolean isSDCardEnable = FileUtil.checkExternalSDExists();
        if (!isSDCardEnable) {
            return false;
        }

        File dir = new File(Environment.getExternalStorageDirectory(), directory);
        if (dir.exists()) {
            if (dir.isDirectory()) {
                return false;
            }
        } else {
            dir.mkdir();
        }

        File file = new File(dir, fileName);

        if (file.exists()) {
            file.delete();
        }

        try {
            FileOutputStream fos = new FileOutputStream(file);
            return saveObject(fos, ser);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 保存对象
     *
     * @param ser
     * @throws IOException
     */
    private static boolean saveObject(FileOutputStream fos, Serializable ser) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
            } catch (Exception e) {
            }
            try {
                fos.close();
            } catch (Exception e) {
            }
        }
    }

    public static Serializable readObjectFromFileDir(Context context, String file) {
        return readObject(new File(context.getFilesDir(), file));
    }

    public static Serializable readObjectFromCacheDir(Context context, String file) {
        return readObject(new File(context.getCacheDir(), file));
    }

    public static Serializable readObjectFromExternal(String directory, String fileName) {
        boolean isSDCardEnable = FileUtil.checkExternalSDExists();
        if (!isSDCardEnable) {
            return null;
        }

        File dir = new File(Environment.getExternalStorageDirectory(), directory);
        if (dir.exists()) {
            if (dir.isDirectory()) {
                return false;
            }
        } else {
            dir.mkdir();
        }

        File file = new File(dir, fileName);

        if (!file.exists()) {
            return null;
        }

        return readObject(file);
    }

    /**
     * 读取对象
     *
     * @param file
     * @return
     * @throws IOException
     */
    private static Serializable readObject(File file) {
        if (!file.exists() || file.isDirectory()) {
            return null;
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();
        } catch (FileNotFoundException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            // 反序列化失败 - 删除缓存文件
            if (e instanceof InvalidClassException) {
                file.delete();
            }
        } finally {
            FileUtil.closeIO(ois);
            FileUtil.closeIO(fis);
        }
        return null;
    }

    /**
     * 判断缓存是否存在
     *
     * @param cachefile
     * @return
     */
    public static boolean isExistDataCache(Context context, int location, String cachefile, String directoryName) {
        switch (location) {
            case INTERNAL_CACHE_DIR:
                return new File(context.getCacheDir(), cachefile).exists();
            case INTERNAL_FILES_DIR:
                return new File(context.getFilesDir(), cachefile).exists();
            case EXTERNAL_FILES_DIR:
                boolean isSDCardEnable = FileUtil.checkExternalSDExists();
                if (!isSDCardEnable) {
                    return false;
                }
                return new File(Environment.getExternalStorageDirectory() + File.separator + directoryName, cachefile).exists();
        }

        return false;
    }

    /**
     * @param context 删除data/data/<package name>/files 所有文件
     */
    public static void cleanFilesDir(Context context) {
        DataCleanUtils.cleanFiles(context);
    }

    public static void delteFile(File file) {
        if (file != null && file.exists()) {
            file.delete();
        }
    }

    public static void deleteFile(Context context, String fileName) {
        File file = new File(context.getFilesDir(), fileName);
        delteFile(file);
    }

    public static boolean saveFileToFileDir(Context context, String fileName, InputStream in) {
        try {
            return saveFile(context.openFileOutput(fileName, Context.MODE_PRIVATE), in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean saveFileToCacheDir(Context context, String fileName, InputStream in) {
        try {
            return saveFile(new FileOutputStream(new File(context.getCacheDir(), fileName)), in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean saveFileToExternal(String directory, String fileName, InputStream in) {
        boolean isSDCardEnable = FileUtil.checkExternalSDExists();
        if (!isSDCardEnable) {
            return false;
        }

        File dir = new File(Environment.getExternalStorageDirectory(), directory);
        if (dir.exists()) {
            if (dir.isDirectory()) {
                return false;
            }
        } else {
            dir.mkdir();
        }

        File file = new File(dir, fileName);
        if(file.exists()){
            file.delete();
        }

        try {
            return saveFile(new FileOutputStream(file),in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean saveFile(FileOutputStream out, InputStream in) {
        try {
            byte[] buffer = new byte[1024 * 4];
            int count = 0;
            while ((count = in.read(buffer)) != -1) {
                out.write(buffer, 0, count);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            FileUtil.closeIO(in);
            FileUtil.closeIO(out);
        }
    }

    public static void lruSaveObject(Context context, Serializable ser, String key){
        DiskLruCacheUtil.saveObject(context,ser,key);
    }

    public static void lruReadObject(Context context, String key){
        DiskLruCacheUtil.readObject(context,key);
    }


}
