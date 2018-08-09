package com.amithelpline.ahl.api;

public interface OnApiResponseAlternativeListener {
    void onSuccess(String jsonStr);
    void onFailed(String message);
}
