package com.hover.starter;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.hover.starter.ui.actions.ActionDetailFragment;

public class ActionDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.action_detail_activity);
        if (savedInstanceState == null) {
            Intent intent = getIntent();

            if (intent.getStringExtra("actionId") == null)
                return;

            Bundle bundle = new Bundle();
            bundle.putString("actionId", intent.getStringExtra("actionId"));
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

    public void setTitle(String title, String subtitle) {
        if (findViewById(R.id.layout_title) != null) {
            ((TextView) findViewById(R.id.title)).setText(title);
            ((TextView) findViewById(R.id.subtitle)).setText(subtitle);
        }
    }
}
