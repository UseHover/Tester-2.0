package com.hover.starter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

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
                    .replace(R.id.container, actionDetailFragment)
                    .commitNow();
        }
    }
}
