package com.androidtutorialpoint.demoemail;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseIntArray;
import android.view.View;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public abstract class RuntimePermissionsActivity extends AppCompatActivity {
    SparseIntArray mErrorString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mErrorString=new SparseIntArray();
    }
    public void  requestAppPermission(final String[] requestedPermission,final int StringId,final int requestCode)
    {
        mErrorString.put(requestCode,StringId);
        int permissionCheck= PackageManager.PERMISSION_GRANTED;
        boolean shouldShowRequestPermissionRationale=false;
        for (String permission:requestedPermission)
        {
            permissionCheck=permissionCheck+ ContextCompat.checkSelfPermission(this,permission);
            shouldShowRequestPermissionRationale=shouldShowRequestPermissionRationale||ActivityCompat.shouldShowRequestPermissionRationale(this,permission);
        }
        if (permissionCheck!=PackageManager.PERMISSION_GRANTED)
        {
            if (shouldShowRequestPermissionRationale)
            {
                Snackbar.make(findViewById(android.R.id.content),StringId,Snackbar.LENGTH_INDEFINITE).setAction("GRANT", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    ActivityCompat.requestPermissions(RuntimePermissionsActivity.this,requestedPermission,requestCode);
                    }
                }).show();
            }
            else
            {
                ActivityCompat.requestPermissions(RuntimePermissionsActivity.this,requestedPermission,requestCode);
            }
        }
        else
        {
            onPermissionGranted(requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        int PermissionCheck=PackageManager.PERMISSION_GRANTED;
        for(int Permission:grantResults)
        {
            PermissionCheck=PermissionCheck+Permission;
        }
        if((grantResults.length>0) && PermissionCheck==PackageManager.PERMISSION_GRANTED)
        {
            onPermissionGranted(requestCode);
        }
        else
        {
            Snackbar.make(findViewById(android.R.id.content),mErrorString.get(requestCode),Snackbar.LENGTH_INDEFINITE).setAction("Enable", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setData(Uri.parse("package:"+getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    startActivity(intent);
                }
            }).show();

        }
    }

    public abstract void onPermissionGranted(int requestCode);
}
