package com.amithelpline.ahl.api;

import org.json.JSONObject;


public interface OnApiResponseListener {
    void onSuccess(JSONObject jsonObject);
    void onFailed(String message);
}
