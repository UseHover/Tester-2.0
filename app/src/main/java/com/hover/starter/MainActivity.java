package com.hover.starter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.hover.sdk.api.Hover;
import com.hover.sdk.permissions.PermissionActivity;
import com.hover.starter.actions.ui.actiondetail.ActionDetailActivity;
import com.hover.starter.actions.ui.main.HoverActionListAdapter;
import com.hover.starter.actions.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity implements PermissionsListener,
        HoverActionListAdapter.OnActionListItemClickListener {

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
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        Hover.initialize(this);
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
                Log.d(TAG, "permissions granted");
            }
        }
    }

    @Override
    public void onActionListItemClick(String actionId) {
        Intent intent = new Intent(this, ActionDetailActivity.class);
        intent.putExtra("action_id", actionId);
        startActivity(intent);
    }
}
