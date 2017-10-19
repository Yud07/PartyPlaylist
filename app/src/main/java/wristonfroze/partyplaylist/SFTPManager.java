package wristonfroze.partyplaylist;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.jcraft.jsch.*;

import java.io.File;

/**
 * Created by mlgmyudy on 8/11/2017.
 */

public class SFTPManager {
    private final static String TAG = SFTPManager.class.getSimpleName();
    public String ip;
    public String username;
    public String pass;
    public String path;

    public SFTPManager(){
        ip = username = pass = path = "";
    }

    public boolean upload(File uploadFile){
        Log.d(TAG, "upload-- ip: " + ip + " username: " + username + " pass: " + pass + " path: " + path);
        Session session = null;
        Channel channel = null;
        boolean failed = false;
        if (ip == "" || username == "" || pass == ""){
            failed = true;
            return failed;
        }
        else {
            try {
                JSch ssh = new JSch();
                session = ssh.getSession(username, ip, 22);
                session.setConfig("StrictHostKeyChecking", "no");
                session.setPassword(pass);
                session.connect();
                channel = session.openChannel("sftp");
                channel.connect();
                ChannelSftp sftp = (ChannelSftp) channel;
                //Log.d(TAG, "absoluteFilePath: " + uploadFile.getAbsolutePath());
                String tempPath;
                if (path.length() == 0) {
                    tempPath = "/home/" + username + "/Logs";
                } else {
                    tempPath = path;
                }
                try {
                    sftp.mkdir(tempPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sftp.put(uploadFile.getAbsolutePath(), tempPath);
            } catch (Exception e) {
                failed = true;
                e.printStackTrace();
            } finally {
                if (!failed) {
                    uploadFile.delete();
                }
                if (channel != null) {
                    channel.disconnect();
                }
                if (session != null) {
                    session.disconnect();
                }
                return failed;
            }
        }
    }
}
