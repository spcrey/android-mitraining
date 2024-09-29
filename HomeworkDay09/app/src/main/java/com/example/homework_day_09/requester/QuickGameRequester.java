package com.example.homework_day_09.requester;

import com.example.homework_day_09.listener.RequestOnResponseListener;

public interface QuickGameRequester {
    void request(String searchString, int current, RequestOnResponseListener listener);
}
