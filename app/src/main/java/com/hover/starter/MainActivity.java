package com.hover.starter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.hover.sdk.api.Hover;
import com.hover.sdk.permissions.PermissionActivity;
import com.hover.starter.data.HoverAction;
import com.hover.starter.ui.main.HoverActionListAdapter;
import com.hover.starter.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity implements PermissionsListener, HoverActionListAdapter.OnActionListItemClickListener {

    private static final String TAG = "MainActivity";
    static int REQUEST_PERMISSIONS = 1;

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
        startActivityForResult(new Intent(this, PermissionActivity.class), REQUEST_PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PERMISSIONS) {
            if (resultCode == RESULT_OK) {

            }
        }
    }

    @Override
    public void onActionListItemClick(String actionId) {
        Intent intent = new Intent(this, ActionDetail.class);
        Log.d(TAG, "mActionId: " + actionId);
        intent.putExtra("actionId", actionId);
        startActivity(intent);
    }
}
