package uk.me.davidreed.FileCreate;

import android.*;
import android.app.*;
import android.content.pm.*;
import android.os.*;
import android.support.v4.content.*;
import android.util.*;
import android.widget.*;
import java.io.*;

public class MainActivity extends Activity 
{
	
	public String LogName = "FileCreateApp";
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

	@Override
	protected void onStart()
	{
		// TODO: Implement this method
		super.onStart();
		TextView mainTextView1 = (TextView)findViewById(R.id.mainTextView1);
		File em2 = getPublicMusicStorageDir("eMusic2/Radiohead");

		mainTextView1.setText("isExternalStorageWritable = " + String.valueOf(isExternalStorageWritable())
							  + ". isExternalStorageReadable = " + String.valueOf(isExternalStorageReadable())
							  + ". eMusic2 = " + em2.getAbsolutePath()
							  + ". RequestPermission = " + String.valueOf(selfPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)));
		
	}
	
	public boolean selfPermissionGranted(String permission) {
        // For Android < Android M, self permissions are always granted.
        boolean result = true;
		int targetSdkVersion = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			try {
				final PackageInfo info = getApplicationContext().getPackageManager().getPackageInfo(
            	    getApplicationContext().getPackageName(), 0);
				targetSdkVersion = info.applicationInfo.targetSdkVersion;
			} catch (PackageManager.NameNotFoundException e) {
				e.printStackTrace();
			}
			
            if (targetSdkVersion >= Build.VERSION_CODES.M) {
                // targetSdkVersion >= Android M, we can use Context#checkSelfPermission
                result = getApplicationContext().checkSelfPermission(permission)
					== PackageManager.PERMISSION_GRANTED;
            } else {
                // targetSdkVersion < Android M, we have to use PermissionChecker
                result = PermissionChecker.checkSelfPermission(getApplicationContext(), permission)
					== PermissionChecker.PERMISSION_GRANTED;
            }
        }

        return result;
    }
	
	public File getPublicMusicStorageDir(String folderName) {
		// Get the directory for the user's public pictures directory.
		File file = new File(Environment.getExternalStoragePublicDirectory(
								 Environment.DIRECTORY_MUSIC), folderName);
		if (!file.exists()) {
			 if (!file.mkdirs()) {
				Log.e(LogName, "Directory not created");
			}
		}
		return file;
	}
	
	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	/* Checks if external storage is available to at least read */
	public boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state) ||
			Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}
}
