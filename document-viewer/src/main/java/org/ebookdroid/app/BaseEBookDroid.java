package org.ebookdroid.app;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import org.emdev.common.log.LogManager;
import org.emdev.utils.LengthUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Locale;
import java.util.Properties;

public class BaseEBookDroid {
    public static Context context;

    public static int APP_VERSION_CODE;

    public static String APP_VERSION_NAME;

    public static String APP_PACKAGE;

    public static File EXT_STORAGE;

    public static File APP_STORAGE;

    public static File APP_BOOK_STORAGE;

    public static Properties BUILD_PROPS;

    public static boolean IS_EMULATOR;

    public static String APP_NAME;

    public static Locale defLocale;

    private static Locale appLocale;

    public void initEBookDroid(Context context, String appStorageName) {
        this.init(context, appStorageName);

        LogManager.init(context);
    }

    protected void init(Context applicationContext, String appStorageName) {
        context = applicationContext;

        final Configuration config = context.getResources().getConfiguration();
        appLocale = defLocale = config.locale;

        BUILD_PROPS = new Properties();
        try {
            BUILD_PROPS.load(new FileInputStream("/system/build.prop"));
        } catch (final Throwable th) {
        }

        final PackageManager pm = context.getPackageManager();
        try {
            final PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            APP_NAME = context.getString(pi.applicationInfo.labelRes);
            APP_VERSION_CODE = pi.versionCode;
            APP_VERSION_NAME = LengthUtils.safeString(pi.versionName, "DEV");
            APP_PACKAGE = pi.packageName;
            EXT_STORAGE = Environment.getExternalStorageDirectory();
            APP_STORAGE = getAppStorage(appStorageName);
            APP_BOOK_STORAGE = getAppBookStorage(appStorageName, "files/books/");
            IS_EMULATOR = "sdk".equalsIgnoreCase(Build.MODEL);
        } catch (final PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void onConfigurationChanged(final Configuration newConfig) {
        final Configuration oldConfig = context.getResources().getConfiguration();
        final int diff = oldConfig.diff(newConfig);
        final Configuration target = diff == 0 ? oldConfig : newConfig;

        if (appLocale != null) {
            setAppLocaleIntoConfiguration(target);
        }
    }

    protected File getAppStorage(final String appPackage) {
/*        File dir = EXT_STORAGE;
        if (dir != null) {
            final File appDir = new File(dir, "." + appPackage);
            if (appDir.isDirectory() || appDir.mkdir()) {
                dir = appDir;
            }
        } else {
            dir = context.getFilesDir();
        }
        dir.mkdirs();
        return dir.getAbsoluteFile();*/
        File appDir = new File(appPackage);
        if (!appDir.exists() && !appDir.mkdirs()) {
            appDir = context.getFilesDir();
        }
        return appDir;
    }

    protected File getAppBookStorage(String appPackage, String bookFolder) {
        File dir = new File(appPackage, bookFolder);
        if (!dir.exists() && !dir.mkdirs()) {
            dir = context.getFilesDir();
        }
        return dir;
    }

    public static File getBookFromStorage(String fileName) {
        if (!TextUtils.isEmpty(fileName)) {
            File file = new File(APP_BOOK_STORAGE.getAbsolutePath() + "/" + fileName);
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }

    public static void clearBookStorage() {
        clearDirectory(APP_BOOK_STORAGE);
    }

    private static void clearDirectory(File dir) {
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                clearDirectory(file);
            }
            file.delete();
        }
    }

    public static void setAppLocale(final String lang) {
        final Configuration config = context.getResources().getConfiguration();
        appLocale = LengthUtils.isNotEmpty(lang) ? new Locale(lang) : defLocale;
        setAppLocaleIntoConfiguration(config);
    }

    protected static void setAppLocaleIntoConfiguration(final Configuration config) {
        if (!config.locale.equals(appLocale)) {
            Locale.setDefault(appLocale);
            config.locale = appLocale;
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        }
        Log.i(APP_NAME, "UI Locale: " + appLocale);
    }
}
