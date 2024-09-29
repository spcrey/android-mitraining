package com.example.homework_day_09.adapter;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.homework_day_09.R;
import com.example.homework_day_09.item.QuickGameItem;

import java.util.List;

public class QuickGameAdapter  extends BaseQuickAdapter<QuickGameItem, BaseViewHolder>
        implements LoadMoreModule {
    public QuickGameAdapter(List<QuickGameItem> data) {
        super(R.layout.item_quick_game, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, QuickGameItem quickGameItem) {
        TextView gameNameView = baseViewHolder.getView(R.id.gameName);
        TextView packageNameView = baseViewHolder.getView(R.id.packageName);
        TextView versionNameView = baseViewHolder.getView(R.id.versionName);
        gameNameView.setText(quickGameItem.getGameName());
        packageNameView.setText(quickGameItem.getPackageName());
        versionNameView.setText(quickGameItem.getVersionName());
    }
}
