package com.xiaojianma.stockanalysis.okhttp.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.xiaojianma.stockanalysis.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public final class FileUtil {

    private static final String TAG = "FileUtil";

    private FileUtil() {

    }

    private static final String[][] MIME_MapTable = {
            //{后缀名，	MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"},
            {"", "*/*"}
    };

    /**
     * 打开文件
     *
     * @param file
     */
    public static void openFile(File file, Activity activity) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置intent的Action属性
        intent.setAction(Intent.ACTION_VIEW);
        //获取文件file的MIME类型
        String type = getMIMEType(file);
        Uri uri = Uri.fromFile(file);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(activity, "com.xiaojianma.stockanalysis.fileprovider", file);
        } else {
            uri = Uri.fromFile(file);
        }
        //设置intent的data和Type属性。
        /*uri*/
        intent.setDataAndType(uri, type);
        //跳转
        activity.startActivity(intent);
    }

    /**
     * 根据文件后缀名获得对应的MIME类型。
     *
     * @param file
     */
    private static String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名 */
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "") return type;
        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) {
            // MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    public static boolean hasAnalysis(String stockNum) {
        String basePath = getBasePath(stockNum);
        File debtFile = getDebtFile(basePath);
        File benefitFile = getBenefitFile(basePath);
        File cashFile = getCashFile(basePath);
        File analysis = getAnalysisFile(basePath);
        return debtFile.exists() && benefitFile.exists() && cashFile.exists() && analysis.exists();
    }

    public static String getBasePath(String stockNum) {
        // 储存下载文件的sdcard目录
        File storageDir = Environment.getExternalStorageDirectory();
        if (TextUtils.isEmpty(stockNum)) {
            return storageDir + File.separator + "weimiao_learn";
        }
        return storageDir + File.separator + "weimiao_learn" + File.separator + stockNum + File.separator + stockNum;
    }

    public static File getAnalysisFile(String basePath) {
        return new File(basePath + "_18步分析.xlsx");
    }

    public static File getDebtFile(String basePath) {
        return new File(basePath + "_debt_year.xls");
    }

    public static File getBenefitFile(String basePath) {
        return new File(basePath + "_benefit_year.xls");
    }

    public static File getCashFile(String basePath) {
        return new File(basePath + "_cash_year.xls");
    }

    public static void copy(String srcfilePath, File analysisFile, Context context) {
        try (InputStream input = context.getAssets().open(srcfilePath);
             FileOutputStream output = new FileOutputStream(analysisFile);) {
            byte[] bytes = new byte[4096];
            int len;
            while ((len = input.read(bytes)) != -1) {
                output.write(bytes, 0, len);
            }
        } catch (Exception e) {
            Log.e(TAG, "yejian generate analysis file exception: " + e.toString());
        }
    }

    public static void openAssignFolder(File file, Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "file/*");
        try {
            activity.startActivity(intent);
//            startActivity(Intent.createChooser(intent,"选择浏览工具"));
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, "yejian openAssignFolder exception: " + e.toString());
        }
    }
}
