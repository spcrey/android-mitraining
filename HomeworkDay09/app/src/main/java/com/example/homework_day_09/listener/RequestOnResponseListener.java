package com.example.homework_day_09.listener;

import com.example.homework_day_09.item.QuickGameItem;
import com.example.homework_day_09.tools.CommonData;
import com.example.homework_day_09.tools.QuickGameList;

public interface RequestOnResponseListener {
    void OnResponse(CommonData<QuickGameList<QuickGameItem>> commonData);
}
