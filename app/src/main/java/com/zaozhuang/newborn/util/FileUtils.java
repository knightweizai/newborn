package com.zaozhuang.newborn.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.zaozhuang.newborn.BuildConfig;
import com.zaozhuang.newborn.config.Const;
//import com.ctb.opencar.BuildConfig;
//import com.ctb.opencar.config.Const;
//import com.ctb.opencar.db.ChatImageDBOperation;
//import com.ctb.opencar.db.chatroom.ChatRoomImageEntity;
//import com.ctb.opencar.manage.UserManager;
//import com.yanzhenjie.permission.AndPermission;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

//import io.reactivex.Observable;
//import io.reactivex.ObservableOnSubscribe;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.functions.Consumer;
//import io.reactivex.schedulers.Schedulers;
import mangogo.appbase.BaseApplication;
import mangogo.appbase.util.MD5Util;


public class FileUtils {

    public static final int GLOBAL_BUFFER_SIZE = 512 * 1024;

    public static void makesureParentExist(File file_) {
        File parent = file_.getParentFile();
        if ((parent != null) && (!parent.exists()))
            mkdirs(parent);
    }

    public static void makesureParentExist(String filepath_) {
        makesureParentExist(new File(filepath_));
    }

    public static void makesureFileExist(File file_) {
        if (!file_.exists()) {
            makesureParentExist(file_);
            createNewFile(file_);
        }
    }

    public static void mkdirs(File dir_) {

        if ((!dir_.exists()) && (!dir_.mkdirs()) && (BuildConfig.DEBUG))
            throw new RuntimeException("fail to make " + dir_.getAbsolutePath());
    }

    public static void createNewFile(File file_) {
        if ((!__createNewFile(file_)) && (BuildConfig.DEBUG))
            throw new RuntimeException(file_.getAbsolutePath()
                    + " doesn't be created!");
    }

    private static boolean __createNewFile(File file_) {
        makesureParentExist(file_);
        if (file_.exists())
            delete(file_);
        try {
            return file_.createNewFile();
        } catch (IOException e) {
        }
        return false;
    }

    public static void delete(File f) {
        if (f != null) {
            try {
                if (f.exists()) {
                    f.delete();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void delete(String path) {
        if (!TextUtils.isEmpty(path)) {
            delete(new File(path));
        }
    }

    public static void makesureFileExist(String filePath_) {
        makesureFileExist(new File(filePath_));
    }

    public static FileInputStream getFileInputStream(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {

        }

        return fis;
    }

    public static FileInputStream getFileInputStream(String path) {
        return getFileInputStream(new File(path));
    }


    public static boolean closeStream(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
                return true;
            }
        } catch (IOException e) {
        }

        return false;
    }

    public static InputStream makeInputBuffered(InputStream input_) {
        if ((input_ instanceof BufferedInputStream)) {
            return input_;
        }
        return new BufferedInputStream(input_, GLOBAL_BUFFER_SIZE);
    }

    public static OutputStream makeOutputBuffered(OutputStream output_) {
        if ((output_ instanceof BufferedOutputStream)) {
            return output_;
        }

        return new BufferedOutputStream(output_, GLOBAL_BUFFER_SIZE);
    }


    public static long getFileSize(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return 0;
        }

        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            return file.length();
        } else {
            return 0;
        }
    }


    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }

        File file = new File(filePath);
        return (file.exists() && file.isFile());
    }

    public static boolean isFileExist(File file) {
        return (file.exists() && file.isFile());
    }

    public static void copy(String pathIn_, String pathOut_) throws IOException {
        copy(new File(pathIn_), new File(pathOut_));
    }

    public static void copy(InputStream input_, String pathOut_) throws IOException {
        File out_ = new File(pathOut_);
        makesureParentExist(out_);
        copy(input_, new FileOutputStream(out_));
    }

    public static void copy(File in_, File out_) throws IOException {
        makesureParentExist(out_);
        copy(new FileInputStream(in_), new FileOutputStream(out_));
    }

    public static void copy(InputStream input_, OutputStream output_)
            throws IOException {
        try {
            byte[] buffer = new byte[GLOBAL_BUFFER_SIZE];
            int temp = -1;
            input_ = makeInputBuffered(input_);
            output_ = makeOutputBuffered(output_);
            while ((temp = input_.read(buffer)) != -1) {
                output_.write(buffer, 0, temp);
                output_.flush();
            }
        } catch (IOException e) {
            throw e;
        } finally {
            FileUtils.closeStream(input_);
            FileUtils.closeStream(output_);
        }
    }

    public static String getBaseSdDir() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String baseDir = Environment.getExternalStorageDirectory().getPath();
            if (!baseDir.endsWith(File.separator)) {
                baseDir = baseDir + File.separator;
            }
            return baseDir;
        } else {
            return null;
        }
    }

    public static String getBaseDataDir() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String baseDir = Environment.getExternalStorageDirectory().getPath();
            if (!baseDir.endsWith(File.separator)) {
                baseDir = baseDir + File.separator;
            }
            baseDir += Const.ANDROID_DATA + BaseApplication.getGlobalContext().getPackageName();
            return baseDir;
        } else {
            return BaseApplication.getGlobalContext().getFilesDir().getPath();
        }
    }

    public static String getConfigDir() {
        String dir;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dir = BaseApplication.getGlobalContext().getExternalFilesDir(Const.CONFIG_PATH).getPath();
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
        } else {
            dir = getBaseDataDir();
            if (!dir.endsWith(File.separator)) {
                dir = dir + File.separator;
            }
            dir += Const.CONFIG_PATH;
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return dir;
    }

    public static String getUploadUserImgDir() {
        String dir;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dir = BaseApplication.getGlobalContext().getExternalFilesDir(Const.UPLOAD_IMAGE).getPath();
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
        } else {
            dir = getBaseDataDir();
            if (!dir.endsWith(File.separator)) {
                dir = dir + File.separator;
            }
            dir += Const.UPLOAD_IMAGE;
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return dir;
    }

    public static String getRecordDir() {
        String dir;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dir = BaseApplication.getGlobalContext().getExternalFilesDir(Const.RECORD_PATH).getPath();
            if (!dir.endsWith(File.separator)) {
                dir = dir + File.separator;
            }
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
        } else {
            dir = getBaseDataDir();
            if (!dir.endsWith(File.separator)) {
                dir = dir + File.separator;
            }
            dir += Const.RECORD_PATH;
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return dir;
    }

    public static String getVoiceDir() {
        String dir;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dir = BaseApplication.getGlobalContext().getExternalFilesDir(Const.VOICE_PATH).getPath();
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
        } else {
            dir = getBaseDataDir();
            if (!dir.endsWith(File.separator)) {
                dir = dir + File.separator;
            }
            dir += Const.VOICE_PATH;
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return dir;
    }

    public static String getImgDir() {
        String dir;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dir = BaseApplication.getGlobalContext().getExternalFilesDir(Const.IMG).getPath();
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
        } else {
            dir = getBaseDataDir();
            if (!dir.endsWith(File.separator)) {
                dir = dir + File.separator;
            }
            dir += Const.IMG;
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return dir;
    }

    /**
     * 获取聊天室存储图片目录
     *
     * @return
     */
    public static String getRtmImageDir() {
        String dir;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dir = BaseApplication.getGlobalContext().getExternalFilesDir(Const.RTM_IMAGE_PATH).getPath();
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
        } else {
            dir = getBaseDataDir();
            if (!dir.endsWith(File.separator)) {
                dir = dir + File.separator;
            }
            dir += Const.VOICE_PATH;
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return dir;
    }

    /**
     * 保存聊天室图片
     *
     * @param inImgPath
     */
//    public static String saveImage(String inImgPath, String fileName, String mediaId) {
//        try {
//            File file = new File(FileUtils.getRtmImageDir(), fileName);
//            FileUtils.copy(new File(inImgPath), file);
//            MyLogs.e("aaaaa", "图片保存本地成功!");
//
//            ChatRoomImageEntity chatRoomImage = new ChatRoomImageEntity();
//            chatRoomImage.setUserId(UserManager.getUserId());
//            chatRoomImage.setFileName(fileName);
//            chatRoomImage.setFilePath(file.getAbsolutePath());
//            chatRoomImage.setMediaId(mediaId);
//            ChatImageDBOperation.saveImage(chatRoomImage);
//            return file.getAbsolutePath();
//        } catch (IOException e) {
//            e.printStackTrace();
//            MyLogs.e("aaaaa", "图片保存本地失败:" + e.toString());
//        }
//        return "";
//    }

    /**
     * 保存字节文件图片
     *
     * @param buffer
     * @throws IOException
     */
//    public static void saveByteImg(byte[] buffer, String fileName, String mediaId) {
//        File out_ = new File(FileUtils.getRtmImageDir(), fileName);
//        OutputStream output_ = null;
//        try {
//            output_ = new FileOutputStream(out_);
//            int temp = buffer.length;
//            output_ = makeOutputBuffered(output_);
//            output_.write(buffer, 0, temp);
//            output_.flush();
//
//            ChatRoomImageEntity chatRoomImage = new ChatRoomImageEntity();
//            chatRoomImage.setUserId(UserManager.getUserId());
//            chatRoomImage.setFileName(fileName);
//            chatRoomImage.setFilePath(out_.getAbsolutePath());
//            chatRoomImage.setMediaId(mediaId);
//            ChatImageDBOperation.saveImage(chatRoomImage);
//        } catch (IOException e) {
//            MyLogs.e("aaaaaaa", "保存图片失败");
//        } finally {
//            FileUtils.closeStream(output_);
//        }
//    }

    /**
     * 获取目录
     *
     * @return
     */
    public static Vector<String> getImageFilePaths() {
        Vector<String> vector = new Vector<>();
        File fileDir = new File(FileUtils.getRtmImageDir());
        File[] files = fileDir.listFiles();
        for (File file : files) {
            if (!file.isDirectory()) {
                vector.add(file.getAbsolutePath());
            }
        }
        return vector;
    }

    public static String getAppDownloadFileDir() {
        String dir;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dir = BaseApplication.getGlobalContext().getExternalFilesDir(Const.DOWNLOAD).getPath();
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
        } else {
            dir = getBaseDataDir();
            if (!dir.endsWith(File.separator)) {
                dir = dir + File.separator;
            }
            dir += Const.DOWNLOAD;
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
        }
        return dir;
    }


    public static String getCrashFileDir() {
        String dir = getBaseDataDir();
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        dir += Const.CRASH;
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dir + File.separator;
    }


    public static String getWebViewCacheDir() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            File fileTmp = BaseApplication.getGlobalContext().getExternalFilesDir(Const.WEBVIEW);
            if (fileTmp == null) {
                return "";
            }

            String dir = fileTmp.getPath();
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
            return dir;
        } else {
            String dir = getBaseDataDir();
            if (!dir.endsWith(File.separator)) {
                dir = dir + File.separator;
            }
            dir += Const.WEBVIEW;
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
            return dir;
        }

    }

    public static String getLogDir() {
        String dir = getBaseDataDir();
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        dir = dir + Const.LOG;
        File file = new File(dir);
        if (!file.exists())
            file.mkdirs();
        return dir + File.separator;
    }

    public static String getFileContent(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            File urlFile = new File(filePath);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(urlFile), "UTF-8");
            BufferedReader br = new BufferedReader(isr);

            String next = "";
            while ((next = br.readLine()) != null) {
                stringBuilder.append(next);
            }

            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
            stringBuilder.delete(0, stringBuilder.length());
        }


        return stringBuilder.toString();
    }

    public static String getFileContent(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader br = new BufferedReader(isr);

            String next = "";
            while ((next = br.readLine()) != null) {
                stringBuilder.append(next);
            }

            isr.close();
        } catch (Exception e) {
            e.printStackTrace();
            stringBuilder.delete(0, stringBuilder.length());
        }


        return stringBuilder.toString();
    }

    public static void saveLog(String str) {
        String path = getLogDir() + "log-" + System.currentTimeMillis() + ".txt";
        saveFile(path, str);
    }


    public static String saveCrashLog(String str) {
        String path = getCrashFileDir() + "crash-" + ".txt";
        saveFile(path, str);

        return path;
    }

    public static void saveFile(String filePath, String content) {
        File file = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            br = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content.getBytes())));
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));

            String bStr = null;
            while ((bStr = br.readLine()) != null) {
                bw.write(bStr);
                bw.write("\n");
            }
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将一个inputStream里面的数据写到SD卡中
     *
     * @param path
     * @param fileName
     * @param inputStream
     * @return
     */
    public static File writeToSDfromInput(String path, String fileName, InputStream inputStream) {
        File file = new File(path + fileName);

        OutputStream outStream = null;
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            outStream = new FileOutputStream(file);
            byte[] buffer = new byte[4 * 1024];
            while (inputStream.read(buffer) != -1) {
                outStream.write(buffer);
            }
            outStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outStream != null)
                    outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static File assetsWriteToSDfromInput(String path, String fileName, InputStream inputStream) {
        File file = new File(path + fileName);

        OutputStream outStream = null;
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            outStream = new FileOutputStream(file);
            byte[] buffer = new byte[inputStream.available()];
            while (inputStream.read(buffer) != -1) {
                outStream.write(buffer);
            }
            outStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outStream != null)
                    outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static void zipFile(File resFile, String rootpath) throws IOException {
        final int BUFF_SIZE = 1024 * 1024; // 1M Byte
        ZipOutputStream zipout = null;
        BufferedInputStream in = null;
        try {
            zipout = new ZipOutputStream(new BufferedOutputStream(
                    new FileOutputStream(rootpath), BUFF_SIZE));

            rootpath = resFile.getName();
            rootpath = new String(rootpath.getBytes("8859_1"), "utf-8");

            byte buffer[] = new byte[BUFF_SIZE];
            in = new BufferedInputStream(new FileInputStream(resFile), BUFF_SIZE);
            zipout.putNextEntry(new ZipEntry(rootpath));
            int realLength;
            while ((realLength = in.read(buffer)) != -1) {
                zipout.write(buffer, 0, realLength);
            }
            in.close();
            zipout.flush();
            zipout.closeEntry();
        } finally {
            if (in != null)
                in.close();
            if (zipout != null)
                zipout.close();
        }
    }

    public static void unZipFolder(String zipFileString, String outPathString) throws Exception {
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString));
        ZipEntry zipEntry;
        String szName = "";
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            if (zipEntry.isDirectory()) {
                // get the folder name of the widget
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(outPathString + File.separator + szName);
                folder.mkdirs();
            } else {

                File file = new File(outPathString + File.separator + szName);
                file.getParentFile().mkdirs();
                file.createNewFile();
                // get the output stream of the file
                FileOutputStream out = new FileOutputStream(file);
                int len;
                byte[] buffer = new byte[1024];
                // read (len) bytes into buffer
                while ((len = inZip.read(buffer)) != -1) {
                    // write (len) byte from buffer at the position 0
                    out.write(buffer, 0, len);
                    out.flush();
                }
                out.close();
            }
        }
        inZip.close();
    }


    public static String getUploadImgPath(String pic_name) {
        return FileUtils.getUploadUserImgDir() + File.separator + pic_name + System.currentTimeMillis() + ".jpg";
    }

//    public static void saveImg(Activity activity, String url) {
//        saveImg(activity,url,null);
//    }

//    public static void saveImg(Activity activity, String url,String fileName) {
//        Glide.with(activity).downloadOnly().load(url).into(new CustomTarget<File>() {
//            @Override
//            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
//                saveGlideImage(activity, resource, TextUtils.isEmpty(fileName) ? MD5Util.encrypt(url) : fileName + "." + getFileType(url));
//            }
//
//            @Override
//            public void onLoadCleared(@Nullable Drawable placeholder) {
//
//            }
//        });
//    }


//    @SuppressLint("CheckResult")
//    public static void saveGlideImage(Activity activity, File file, String saveImgName) {
//
//        //检测存储权限
//        if (!AndPermission.hasPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
//            return;
//        Observable.create((ObservableOnSubscribe<File>) emitter -> {
//            String saveDir = FileUtils.getImgDir();
//            if (!new File(saveDir).exists()) {
//                new File(saveDir).mkdirs();
//            }
//
//            File target = new File(saveDir, saveImgName);
//            copy(file, target);
//            emitter.onNext(target);
//        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<File>() {
//            @Override
//            public void accept(File o) throws Exception {
////                Uri uri = Uri.fromFile(o);
////                activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
////                    ToastUtil.shortShow(activity, "保存至相册" + o.getAbsolutePath());
//            }
//        });
//
//
//    }

//    @SuppressLint("CheckResult")
//    public static void saveGlideFile(Activity activity,String url,String targetFileName, Consumer<File> consumer) {
//        //检测存储权限
//        if (!AndPermission.hasPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
//            return;
//        Glide.with(activity).downloadOnly().load(url).into(new CustomTarget<File>() {
//            @Override
//            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
//                String saveImgName = TextUtils.isEmpty(targetFileName) ? MD5Util.encrypt(url) : targetFileName + "." + getFileType(url);
//                saveFile(resource,saveImgName,consumer);
//            }
//
//            @Override
//            public void onLoadCleared(@Nullable Drawable placeholder) {
//
//            }
//        });
//
//
//
//    }
//
//    @SuppressLint("CheckResult")
//    public static void saveFile(File file, String saveImgName, Consumer<File> consumer){
//        Observable.create((ObservableOnSubscribe<File>) emitter -> {
//                    File files = new File(FileUtils.getImgDir());
//                    if (!files.exists()) {
//                        files.mkdirs();
//                    }
//                    String saveDir = files.getPath();
//                    if (!new File(saveDir).exists()) {
//                        new File(saveDir).mkdirs();
//                    }
//
//                    File target = new File(saveDir, saveImgName);
//                    copy(file, target);
//                    emitter.onNext(target);
//                }).observeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .onErrorReturn(throwable -> null)
//                .subscribe(consumer);
//    }


    public static String getFileType(String paramString) {
        String str = "";
        if (TextUtils.isEmpty(paramString)) {
            return str;
        }
        int i = paramString.lastIndexOf('.');
        if (i <= -1) {
            return str;
        }
        str = paramString.substring(i + 1);
        return str;
    }

}
