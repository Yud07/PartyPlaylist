package wristonfroze.partyplaylist;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public SFTPManager sftpManager;
    public File mLoggerFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                showAlert();
            }
        });

        sftpManager = new SFTPManager();
        mLoggerFile = Logger.createLoggerFile(getApplicationContext());
        //Logger.saveLogData(mLoggerFile, "");
    }

    // Vertical ellipses "..." settings button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // Settings menu option
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent modifySettings = new Intent(this, SettingsActivity.class);
        startActivity(modifySettings);
        return true;
    }

    private void showAlert()
    {
        //final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Enter URL");

        final EditText input = new EditText(MainActivity.this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        //input.setText(sharedPreferences.getString("filter", ""));
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = input.getText().toString();
                Log.d(this.getClass().getSimpleName(), "Write string:"+ value);

                //save data
                /*SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("filter", value);
                editor.commit();
                DeviceScanningFragment.filter = value;*/

                loadSFTPSettings();
                new UploadFileTask().execute(mLoggerFile);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }

    // Uploads log to server in background
    private class UploadFileTask extends AsyncTask<File, Void, Boolean> {
        protected Boolean doInBackground(File... params) {
            return sftpManager.upload(params[0]);
        }

        protected void onPostExecute(Boolean result){
            if (result){
                Toast.makeText(getApplicationContext(),"Upload Failed, Verify SFTP Settings.",Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(),"Upload Successful",Toast.LENGTH_LONG).show();
            }
        }
    }

    // Loads SFTP settings
    public void loadSFTPSettings(){
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sftpManager.ip = sharedPreferences.getString("ip", "");
        sftpManager.username = sharedPreferences.getString("username", "");
        sftpManager.pass = sharedPreferences.getString("password", "");
        sftpManager.path = sharedPreferences.getString("path", "");
    }
}

