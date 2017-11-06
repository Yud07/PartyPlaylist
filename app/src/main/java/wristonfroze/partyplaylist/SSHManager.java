package wristonfroze.partyplaylist;

import android.util.Log;

import com.jcraft.jsch.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by mlgmyudy on 8/11/2017.
 */

public class SSHManager {
    private final static String TAG = SSHManager.class.getSimpleName();
    public String ip;
    public String username;
    public String pass;
    public String path;

    private JSch ssh;
    private Session session;
    private Channel channel;
    private ChannelSftp channelSftp;

    public SSHManager(){
        ip = username = pass = path = "";
    }

    public boolean login(File uploadFile){
        Log.d(TAG, "login -- ip: " + ip + " username: " + username + " pass: " + pass);
        session = null;
        channel = null;
        boolean failed = false;
        if (ip == "" || username == "" || pass == ""){
            failed = true;
            return failed;
        }
        else {
            try {
                ssh = new JSch();
                session = ssh.getSession(username, ip, 22);
                session.setConfig("StrictHostKeyChecking", "no");
                session.setPassword(pass);
                session.connect();
                channel = session.openChannel("channelSftp");
                channel.connect();
                channelSftp = (ChannelSftp) channel;
            } catch (Exception e) {
                failed = true;
                e.printStackTrace();
            } finally {
                return failed;
            }
        }
    }

    public boolean upload(File uploadFile){
        Log.d(TAG, "upload - uploadFile: " + uploadFile + " path: " + path);

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
                channel = session.openChannel("channelSftp");
                channel.connect();
                channelSftp = (ChannelSftp) channel;
                //Log.d(TAG, "absoluteFilePath: " + uploadFile.getAbsolutePath());
                String tempPath;
                if (path.length() == 0) {
                    tempPath = "/home/" + username + "/Logs";
                } else {
                    tempPath = path;
                }
                try {
                    channelSftp.mkdir(tempPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                channelSftp.put(uploadFile.getAbsolutePath(), tempPath);
            } catch (Exception e) {
                failed = true;
                e.printStackTrace();
            } finally {
                return failed;
            }
        }
    }

    /*public boolean upload(File uploadFile){
        Log.d(TAG, "upload - ip: " + ip + " username: " + username + " pass: " + pass + " path: " + path);
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
                channel = session.openChannel("channelSftp");
                channel.connect();
                ChannelSftp channelSftp = (ChannelSftp) channel;
                //Log.d(TAG, "absoluteFilePath: " + uploadFile.getAbsolutePath());
                String tempPath;
                if (path.length() == 0) {
                    tempPath = "/home/" + username + "/Logs";
                } else {
                    tempPath = path;
                }
                try {
                    channelSftp.mkdir(tempPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                channelSftp.put(uploadFile.getAbsolutePath(), tempPath);
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
    }*/

    public ArrayList<String> listFiles(String path){
        try {
            channelSftp.cd(path);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            List<ChannelSftp.LsEntry> list = channelSftp.ls("*.csv");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        ArrayList<String> fileNames = new ArrayList<>();
        /*for(ChannelSftp.LsEntry entry : list) {
            Log.d(TAG, entry.getFilename());
            fileNames.add(entry.getFilename());
        }*/
        return  fileNames;
    }
}
