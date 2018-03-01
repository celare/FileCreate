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
		super.onStart();
		
		if (selfPermissionGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
			if (isExternalStorageWritable()){
				File file = new File(Environment.getExternalStoragePublicDirectory(
								 Environment.DIRECTORY_MUSIC), "eMusic2/Radiohead");
				if (!file.exists()) {
					if (!file.mkdirs()) {
						Log.e(LogName, "Directory not created");
					}
				}
			} else {
				Log.e(LogName, "External storage not writable");
			}
		} else {
			Log.e(LogName, "Write external storage permission not granted");
		}
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
		
	/* Checks if external storage is available for read and write */
	public boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

}
