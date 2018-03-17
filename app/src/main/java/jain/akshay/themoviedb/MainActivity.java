package jain.akshay.themoviedb;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import jain.akshay.themoviedb.Model.Result;
import jain.akshay.themoviedb.Model.RootObject;
import jain.akshay.themoviedb.Service.ApiInterface;
import jain.akshay.themoviedb.Service.ServiceGenerator;
import jain.akshay.themoviedb.Utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;
    private Parcelable mListState = null;

    ApiInterface apiInterface;
    int currentPage = 1;
    SearchView searchView;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    MovieAdapter adapter;
    ArrayList<Result> results=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBundleRecyclerViewState=new Bundle();
         apiInterface= ServiceGenerator.createService(ApiInterface.class);
        results=new ArrayList<Result>();
        searchView = (SearchView)findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                callSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }

            public void callSearch(String query) {
                initRequest(query);
            }

        });
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        gridLayoutManager=new GridLayoutManager(MainActivity.this,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter=new MovieAdapter(results,MainActivity.this);
        adapter.setLoadMoreListener(new MovieAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        loadmore();
                    }
                });
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
        initRequest("all");
    }

    public void loadmore(){
        LinkedHashMap map = new LinkedHashMap<>();
        map.put("api_key", Constants.API_KEY);
        map.put("language", Constants.LANGUAGE);
        map.put("query", "all");
        map.put("page", String.valueOf(currentPage++));
        map.put("include_adult", "0");
        adapter.notifyItemInserted(results.size()-1);
        Call<RootObject> call = apiInterface.getMovies(map);
        call.enqueue(new Callback<RootObject>() {
            @Override
            public void onResponse(Call<RootObject> call, Response<RootObject> response) {
                if(response.isSuccessful()){
                    //remove loading view
                    RootObject result = response.body();
                    if(result.getResults().size()>0){
                        //add loaded data
                        results.addAll(result.getResults());
                        currentPage++;
                    }else{
                        adapter.setMoreDataAvailable(false);
                        //result size 0 means there is no more data available at server
                        //telling adapter to stop calling load more as no more server data available
                        Toast.makeText(MainActivity.this,"No More Data Available",Toast.LENGTH_LONG).show();
                    }
                    adapter.notifyDataChanged();
                    //should call the custom method adapter.notifyDataChanged here to get the correct loading status
                }else{
                    Log.e("MAinactivity"," Load More Response Error "+String.valueOf(response.code()));
                }
            }
            @Override
            public void onFailure(Call<RootObject> call, Throwable t) {
                Log.e("MAinactivity"," Load More Response Error "+t.getMessage());
            }
        });
    }

    void initRequest(String query) {
        LinkedHashMap map = new LinkedHashMap<>();
        map.put("api_key", Constants.API_KEY);
        map.put("language", Constants.LANGUAGE);
        map.put("query", (query.equals("")?"all":query));
        map.put("page", String.valueOf(currentPage));
        map.put("include_adult", "0");
        Call<RootObject> call = apiInterface.getMovies(map);
        call.enqueue(new Callback<RootObject>() {
            @Override
            public void onResponse(Call<RootObject> call, Response<RootObject> response) {
                if(response.isSuccessful()){
                    //remove loading view
                    RootObject result = response.body();
                        results.addAll(result.getResults());
                        adapter.notifyDataChanged();
                    currentPage++;
                }else{
                    Log.e("MAinactivity"," Load More Response Error "+String.valueOf(response.code()));
                }
            }
            @Override
            public void onFailure(Call<RootObject> call, Throwable t) {
                Log.e("MAinactivity"," Load More Response Error "+t.getMessage());
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        mListState=recyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, mListState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mBundleRecyclerViewState != null) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    mListState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
                    recyclerView.getLayoutManager().onRestoreInstanceState(mListState);

                }
            }, 50);

        }
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            gridLayoutManager.setSpanCount(3);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            gridLayoutManager.setSpanCount(2);

        }
        recyclerView.setLayoutManager(gridLayoutManager);
    }

}
