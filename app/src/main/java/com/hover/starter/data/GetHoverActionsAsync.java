package com.hover.starter.data;

import android.os.AsyncTask;
import android.util.Log;

import com.hover.starter.network.NetworkOps;

import org.json.JSONArray;

public class GetHoverActionsAsync extends AsyncTask<Void, Void, Void> {

    private final HoverActionDao mDao;
    private final NetworkOps mNetworkOps;

    GetHoverActionsAsync(AppDatabase db, NetworkOps networkOps) {
        mDao = db.actionDao();
        mNetworkOps = networkOps;
    }

    @Override
    protected Void doInBackground(final Void... params) {
        try {
            String actions = mNetworkOps.download("custom_actions");
            JSONArray jsonArray = new JSONArray(actions);
            for (int i = 0; i < jsonArray.length(); i++) {
                HoverAction action = new HoverAction(jsonArray.getJSONObject(i));
                String uid = mDao.getById(action.uid);
                if (uid == null)
                    mDao.insert(action);
                else
                    mDao.update(action);

            }
        } catch (Exception e) {
            Log.d("HoverActionRepository", "download failed" +e.getMessage(),e);
        }
        return null;
    }
}
