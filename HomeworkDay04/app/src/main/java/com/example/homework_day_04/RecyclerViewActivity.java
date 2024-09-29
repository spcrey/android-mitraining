package com.example.homework_day_04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    List<GameBean> data = new ArrayList<>();

    void update_data_name() {
        for(int i=0; i<data.size(); i++) {
            String index_text;
            if (i < 10) {
                index_text = "0" + i;
            }
            else {
                index_text = String.valueOf(i);
            }
            data.get(i).setGameName("game_" + index_text);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        setData();

        GameRecyclerAdapter gameRecycleAdapter = new GameRecyclerAdapter(data);

        gameRecycleAdapter.setOnItemClickListener(
                new GameRecyclerAdapter.OnItemListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (view.getId() == R.id.game_icon) {
                            Toast.makeText(RecyclerViewActivity.this, "icon", Toast.LENGTH_SHORT).show();
                        }
                        else if (view.getId() == R.id.game_button) {
                            Toast.makeText(RecyclerViewActivity.this, "button", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        recyclerView.setAdapter(gameRecycleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button addItem = findViewById(R.id.to_add_item);
        Button deleteItem = findViewById(R.id.to_delete_item);
        EditText itemIndex = findViewById(R.id.item_index);

        addItem.setOnClickListener(v -> {
            GameBean gameBean = new GameBean("add_item", R.drawable.ic_android_black_32dp, "added");
            if (itemIndex.getText().length() == 0) {
                Toast.makeText(this, "no input", Toast.LENGTH_SHORT).show();
                return;
            }
            int position = Integer.parseInt(itemIndex.getText().toString());
            if (position > data.size() || position < 0) {
                Toast.makeText(this, "overstep the bounds", Toast.LENGTH_SHORT).show();
                return;
            }
            data.add(position, gameBean);
            update_data_name();
            gameRecycleAdapter.notifyItemInserted(position);
            gameRecycleAdapter.notifyItemRangeChanged(position, data.size());
        });

        deleteItem.setOnClickListener(v -> {
            if (itemIndex.getText().length() == 0) {
                Toast.makeText(this, "no input", Toast.LENGTH_SHORT).show();
                return;
            }
            int position = Integer.parseInt(itemIndex.getText().toString());
            if (position > data.size() - 1 || position < 0) {
                Toast.makeText(this, "overstep the bounds", Toast.LENGTH_SHORT).show();
                return;
            }
            data.remove(position);
            update_data_name();
            gameRecycleAdapter.notifyItemRemoved(position);
            gameRecycleAdapter.notifyItemRangeChanged(position, data.size());
        });
    }

    private void setData() {
        String[] gameStatus = new String[]{
            "start", "wait", "update", "appointment"
        };
        int[] icons = new int[]{
                R.drawable.contact, R.drawable.information, R.drawable.note};
        for (int i=0; i<12; i++) {
            GameBean gameBean = new GameBean();
            String index_text;
            if (i < 10) {
                index_text = "0" + i;
            }
            else {
                index_text = String.valueOf(i);
            }
            gameBean.setGameName("game_" + index_text);
            gameBean.setGameStatus(gameStatus[i % 4]);
            gameBean.setGameIcon(icons[i % 3]);
            data.add(gameBean);
        }
    }

    public static class GameRecyclerAdapter extends RecyclerView.Adapter<GameRecyclerAdapter.ViewHolder> {
        List<GameBean> data;
        OnItemListener onItemListener;

        public GameRecyclerAdapter(List<GameBean> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
            return new ViewHolder(view);
        }

        public void setOnItemClickListener(OnItemListener onItemListener) {
            this.onItemListener = onItemListener;
        }

        public interface OnItemListener {
            void onItemClick(View view, int position);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            GameBean gameBean = data.get(position);
            Log.d("msg",gameBean.getGameName());
            holder.mGameIcon.setImageResource(gameBean.getGameIcon());
            holder.mGameName.setText(gameBean.getGameName());
            holder.mGameButton.setText(gameBean.getGameStatus());

            if (onItemListener != null) {
                holder.mGameIcon.setOnClickListener(v -> onItemListener.onItemClick(v, position));
                holder.mGameButton.setOnClickListener(v -> onItemListener.onItemClick(v, position));
            }
        }
        @Override
        public int getItemCount() {
            return data.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView mGameIcon;
            public TextView mGameName;
            public Button mGameButton;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                mGameIcon = itemView.findViewById(R.id.game_icon);
                mGameName = itemView.findViewById(R.id.game_name);
                mGameButton = itemView.findViewById(R.id.game_button);
            }
        }
    }
}