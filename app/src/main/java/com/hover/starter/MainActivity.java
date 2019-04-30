package com.hover.starter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Intent;
import android.Manifest;
import android.content.pm.PackageManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Build;

import com.hover.sdk.permissions.PermissionActivity;
import com.hover.starter.ui.main.MainFragment;

import com.hover.sdk.api.Hover;

public class MainActivity extends AppCompatActivity implements PermissionsListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
    }

    public static boolean hasAllPermissions(Context c) {
        return hasPhonePermissions(c) && hasAdvancedPermissions(c);
    }

    private static boolean hasPhonePermissions(Context c) {
        return Build.VERSION.SDK_INT < 23 || (ContextCompat.checkSelfPermission(c, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(c, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED);
    }

    private static boolean hasAdvancedPermissions(Context c) {
        return Hover.isAccessibilityEnabled(c) && Hover.isOverlayEnabled(c);
    }

    public void requestAdvancedPermissions(){
        startActivityForResult(new Intent(this, PermissionActivity.class), 1);
    }
}
