package com.hover.starter;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.hover.sdk.api.HoverParameters;
import com.hover.starter.ui.actions.ActionDetailFragment;
import com.hover.starter.ui.actions.HoverTransactionListAdapter;

public class ActionDetail extends AppCompatActivity implements HoverTransactionListAdapter.OnTransactionListItemClickListener {

    private static final String TAG = "ActionDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_detail_activity);
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            Log.d(TAG, "has uuid: " + intent.hasExtra("uuid"));
            if (!intent.hasExtra("action_id"))
                return;
            Bundle bundle = new Bundle();
            bundle.putAll(intent.getExtras());
            ActionDetailFragment actionDetailFragment = new ActionDetailFragment();
            actionDetailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.action_detail_container, actionDetailFragment)
                    .commitNow();
        }
        final Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        final View titleLayout = findViewById(R.id.layout_title);
        titleLayout.post(new Runnable() {
            @Override
            public void run() {
                CollapsingToolbarLayout.LayoutParams layoutParams = (CollapsingToolbarLayout.LayoutParams) toolbar.getLayoutParams();
                layoutParams.height = titleLayout.getHeight() + getResources().getDimensionPixelSize(R.dimen.abc_action_bar_default_height_material);
                toolbar.setLayoutParams(layoutParams);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, MainActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            ActionDetailFragment actionDetailFragment = getFragment();
            assert actionDetailFragment != null;
            actionDetailFragment.onResultReceived(data);
        } else if (requestCode == 0 && resultCode == Activity.RESULT_CANCELED) {
            Toast.makeText(this, "Error: " + data.getStringExtra("error"),
                    Toast.LENGTH_LONG).show();
        }
    }

    public void setTitle(String title, String subtitle) {
        if (findViewById(R.id.layout_title) != null) {
            ((TextView) findViewById(R.id.title)).setText(title);
            ((TextView) findViewById(R.id.subtitle)).setText(subtitle);
        }
    }

    public void runAction(View view) {
        ActionDetailFragment actionDetailFragment = getFragment();
        Intent i = actionDetailFragment.initializeHoverParametersBuilder();

        LocalBroadcastManager.getInstance(this).registerReceiver(mSMSReceiver,
                new IntentFilter(getPackageName() + ".SMS_MISS"));
        startActivityForResult(i, 0);
    }

    private BroadcastReceiver mSMSReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            unregisterSMSReceiver();
            Log.e(TAG, "Got an sms");
        }
    };

    public void unregisterSMSReceiver() {
        if (mSMSReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mSMSReceiver);
            mSMSReceiver = null;
        }
    }

    private void showResultDialog(String resultId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Result id: "+ resultId)
                .setMessage("Coming soon");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private ActionDetailFragment getFragment() {
        return (ActionDetailFragment) getSupportFragmentManager().findFragmentById(R.id.action_detail_container);
    }

    @Override
    public void onTransactionListItemClick(String transactionId) {
        showResultDialog(transactionId);
    }
}
