package com.hover.starter.workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.hover.starter.AppDatabase;
import com.hover.starter.actions.data.HoverActionVariable;
import com.hover.starter.actions.data.HoverActionVariableDao;
import com.hover.starter.actions.data.HoverAction;
import com.hover.starter.actions.data.HoverActionDao;
import com.hover.starter.network.NetworkOps;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;

public class GetHoverActionsWorker extends Worker {

    private static final String TAG = "GetHoverActionsWorker";
    private final HoverActionDao mActionDao;
    private final HoverActionVariableDao mActionVariableDao;
    private final NetworkOps mNetworkOps;

    public GetHoverActionsWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
        mActionDao = AppDatabase.getInstance(context).actionDao();
        // initialize HoverActionVariable DAO
        mActionVariableDao = AppDatabase.getInstance(context).actionTransactionDao();
        mNetworkOps = new NetworkOps(context);
    }

    @Override
    public Result doWork() {
        try {
            String actions = mNetworkOps.makeRequest("custom_actions");
            Log.e(TAG, "actions " + actions);
            JSONArray jsonArray = new JSONArray(actions);
            mActionDao.deleteAll();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject actionJson = jsonArray.getJSONObject(i);
                HoverAction action = new HoverAction(actionJson);

                mActionDao.insert(action);
                JSONArray variables = actionJson.getJSONArray("custom_steps");
                for (int v = 0; v < variables.length(); v++) {
                    if (variables.getJSONObject(v).getBoolean("is_param")) {
                        HoverActionVariable actionVariable = new HoverActionVariable(action.uid,
                                variables.getJSONObject(v).getString("value"));
                        mActionVariableDao.insert(actionVariable);
                    }
                }
            }
        } catch (IOException e) {
            Log.e(TAG, "Request failed: " + e.getMessage(), e);
            Log.e(TAG, "Please check that you have the correct API token (com.hover.ApiKey) in your AndroidManifest.xml");
            return Result.failure();
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse json array:" + e.getMessage(), e);
            return Result.failure();
        }

        return Result.success();
    }
}
