package com.example.homework_day_06.adapter;

import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.homework_day_06.R;
import com.example.homework_day_06.item.PhoneItem;

import java.util.List;

public class PhoneAdapter extends BaseQuickAdapter<PhoneItem, BaseViewHolder>
    implements LoadMoreModule


{
    private Context context;
    public PhoneAdapter(List<PhoneItem> data, Context context) {
        super(R.layout.item_phone, data);
        this.context = context;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, PhoneItem phoneItem) {
        ImageView imageView = baseViewHolder.getView(R.id.item_image);
        TextView textView = baseViewHolder.getView(R.id.item_text);
        Glide.with(imageView.getContext())
                .load(phoneItem.getImageId())
                .into(imageView);
        textView.setText(phoneItem.getTitle());
        Button button = baseViewHolder.getView(R.id.item_love_status);
        if(phoneItem.getLoveStatus()) {
            button.setBackgroundColor(ContextCompat.getColor(context, R.color.coral));
        }
        else {
            button.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        }
    }

}
