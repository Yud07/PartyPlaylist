package wristonfroze.partyplaylist;

import android.content.Context;
import android.util.Log;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import static android.content.ContentValues.TAG;

public class Logger {

    private static File mLoggerDirectory;
    private static File mLoggerFile;
    private static File mLoggerOldFile;
    private static Context mContext;


    public static File createLoggerFile(Context context) {
        mContext = context;
        try {
            /**
             * Directory
             */
            mLoggerDirectory = new File(context.getFilesDir() +
                    File.separator
                    + context.getResources().getString(R.string.dl_directory));
            if (!mLoggerDirectory.exists()) {
                boolean result;
                result = mLoggerDirectory.mkdirs();
                Log.d(TAG, "mLoggerDirectory mkdir: " + result);
            }
            else {
                deleteOldFiles();
            }
            Log.d(TAG, "mLoggerDirectory: " + mLoggerDirectory);
            /**
             * File  name
             */
            mLoggerFile = new File(mLoggerDirectory.getAbsoluteFile() + File.separator
                    + Utils.GetTimeandDate() + context.getResources().getString(R.string.dl_file_extension));
            Log.d(TAG, "mLoggerFile1: " + mLoggerFile);
            if (!mLoggerFile.exists()) {
                mLoggerFile.createNewFile();
            }
            //Log.d(TAG, "mLoggerFile2: " + mLoggerFile);
            return mLoggerFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void deleteOldFiles() {
        /**
         * Delete old file
         */
        File[] allFilesList = mLoggerDirectory.listFiles();
        if(allFilesList!=null) {
            long cutoff = System.currentTimeMillis() - (1 * 12 * 60 * 60 * 1000);//12 hours
            for (int pos = 0; pos < allFilesList.length; pos++) {
                File currentFile = allFilesList[pos];
                if (currentFile.lastModified() < cutoff) {
                    currentFile.delete();
                }
            }
        }

    }

    public static void logHeader(File mLoggerFile){
        String message = "DateTime,";
        try {
            OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(mLoggerFile, true),
                    "UTF-8");
            BufferedWriter fbw = new BufferedWriter(writer);
            fbw.write(message);
            fbw.newLine();
            fbw.flush();
            fbw.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void printString(File mLoggerFile, String msg) {
        String message = Utils.GetTimeandDate() + ",";

        try {
            OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(mLoggerFile, true),
                    "UTF-8");
            BufferedWriter fbw = new BufferedWriter(writer);
            fbw.write(message);
            fbw.newLine();
            fbw.flush();
            fbw.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
