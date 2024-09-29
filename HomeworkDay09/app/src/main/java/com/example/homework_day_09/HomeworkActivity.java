package com.example.homework_day_09;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;

import com.example.homework_day_09.adapter.QuickGameAdapter;
import com.example.homework_day_09.builder.HandlerBuilder;
import com.example.homework_day_09.builder.OkHttpClientBuilder;
import com.example.homework_day_09.builder.QuickGameAdapterBuilder;
import com.example.homework_day_09.builder.RecyclerViewBuilder;
import com.example.homework_day_09.builder.RetrofitBuilder;
import com.example.homework_day_09.builder.SearchButtonBuilder;
import com.example.homework_day_09.builder.SwipeRefreshLayoutBuilder;
import com.example.homework_day_09.item.QuickGameItem;
import com.example.homework_day_09.listener.RequestOnResponseListener;
import com.example.homework_day_09.requester.RetrofitGameRequester;
import com.example.homework_day_09.tools.ApiService;
import com.example.homework_day_09.tools.CommonData;
import com.example.homework_day_09.tools.QuickGameList;
import com.example.homework_day_09.requester.OkHttpQuickGameRequester;
import com.example.homework_day_09.requester.QuickGameRequester;
import com.example.homework_day_09.tools.RequestMode;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeworkActivity extends AppCompatActivity {

    public static List<HomeworkActivity> leakageActivities = new ArrayList<>();

    private static final String TAG = "HomeworkActivity";
    private static final Gson gson = new Gson();
    private static final OkHttpClient okHttpClient = OkHttpClientBuilder.build();
    private static final Retrofit retrofit = RetrofitBuilder.build(okHttpClient, gson);
    private final Handler handler = HandlerBuilder.build();

    private final ApiService apiService = retrofit.create(ApiService.class);

    private EditText searchEditText;
    private int current = 1;
    private Button searchButton;
    private final List<QuickGameItem> data = new ArrayList<>();
    private QuickGameAdapter quickGameAdapter;

    private RecyclerView recyclerView;

    private final RequestMode requestMode = RequestMode.RETROFIT;

    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean loadMoreFlag = true;

    private QuickGameRequester quickGameRequester;

    public HomeworkActivity() {
        super();;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public void resetLoadMoreFlag() {
        loadMoreFlag = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);
        searchEditText = findViewById(R.id.searchEditText);
        quickGameAdapter = QuickGameAdapterBuilder.build(this, data);
        recyclerView = RecyclerViewBuilder.build(this, quickGameAdapter);
        swipeRefreshLayout = SwipeRefreshLayoutBuilder.build(this, quickGameAdapter, searchEditText);
        searchButton = SearchButtonBuilder.build(this, data);
        if (requestMode == RequestMode.OKHTTP) {
            quickGameRequester = new OkHttpQuickGameRequester(okHttpClient, gson);
        }
        else {
            quickGameRequester = new RetrofitGameRequester(apiService);
        }
        leakageActivities.add(this);
    }

    public void requestGetQuickGame() {
        String searchString = searchEditText.getText().toString();
        quickGameRequester.request(searchString, current, new RequestOnResponseListener() {
            @Override
            public void OnResponse(CommonData<QuickGameList<QuickGameItem>> commonData) {
                updateDataByCommonData(commonData);
            }
        });
    }

    private void updateDataByCommonData(CommonData<QuickGameList<QuickGameItem>> commonData) {data.addAll(commonData.data.records);
        handler.post(new Runnable() {
            @Override
            public void run() {
                quickGameAdapter.getLoadMoreModule().loadMoreComplete();
                quickGameAdapter.notifyDataSetChanged();
            }
        });
        if(commonData.data.records.size() == 0) {
            loadMoreFlag = false;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    quickGameAdapter.getLoadMoreModule().loadMoreEnd();
                }
            });
        }
    }

    public void refreshData() {
        swipeRefreshLayout.setRefreshing(false);
        searchEditText.setText("");
        current = 1;
        resetLoadMoreFlag();
        data.clear();
        requestGetQuickGame();
        quickGameAdapter.notifyDataSetChanged();
    }

    public void loadMoreItems() {
        if(!loadMoreFlag){
            quickGameAdapter.getLoadMoreModule().loadMoreEnd();
        }
        else {
            current += 1;
            requestGetQuickGame();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);

        leakageActivities.remove(0); okHttpClient.dispatcher().cancelAll();
        okHttpClient.dispatcher().executorService().shutdown();
        try {
            okHttpClient.dispatcher().executorService().shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}