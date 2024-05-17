package com.example.todoapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class TodayFragment extends Fragment implements DataAdapter.OnItemRemovedListener  {

    RecyclerView recyclerView,recyclerView1;
    DataAdapter dataAdapter,dataAdapter1;
    Api api;

    List<Data> pendingItems ;

    List<Data> completedItems ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView= inflater.inflate(R.layout.fragment_today, container, false);

        pendingItems = new ArrayList<>();
        completedItems = new ArrayList<>();

        recyclerView=rootView.findViewById(R.id.rc_view);
        recyclerView1=rootView.findViewById(R.id.rc_view1);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));

        dataAdapter=new DataAdapter(pendingItems);
        dataAdapter1=new DataAdapter(completedItems);

        dataAdapter.setOnItemRemovedListener(this);


        recyclerView.setAdapter(dataAdapter);
        recyclerView1.setAdapter(dataAdapter1);

        Retrofit retrofit=new  Retrofit.Builder()
                .baseUrl("https://run.mocky.io/v3/8dd19376-9d63-4741-97e9-3d351e3fc202/").
                addConverterFactory(GsonConverterFactory.create()).build();

        api=retrofit.create(Api.class);
        fetchData();
        fetchData1();
        return  rootView;

    }

    public void fetchData() {
        Call <DataResponse> call=api.getData();
       call.enqueue(new Callback<DataResponse>() {
           @Override
           public void onResponse(@NonNull Call<DataResponse> call, @NonNull Response<DataResponse> response) {
               DataResponse dataResponse = response.body();
               if (dataResponse != null) {
                   List<Data> dataList = dataResponse.getData();
                   if (dataList != null && !dataList.isEmpty()) {

                       for (Data data : dataList) {
                           if ("PENDING".equals(data.getStatus())) {
                               pendingItems.add(data);
                           }else{
                               Log.d("Some error","Recheck");
                           }
                       }dataAdapter.setData(pendingItems);

                   } else {
                       Log.d("NoData", "No data received");
                   }
               } else {
                   Log.d("DataResponseNull", "DataResponse is null");
               }
           }

           @Override
           public void onFailure(@NonNull Call<DataResponse> call, @NonNull Throwable throwable) {
               Log.d("onFailure","Done");
           }
       });
    }



    public void fetchData1() {
        Call <DataResponse> call=api.getData();
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(@NonNull Call<DataResponse> call, @NonNull Response<DataResponse> response) {
                DataResponse dataResponse = response.body();
                if (dataResponse != null) {
                    List<Data> dataList = dataResponse.getData();
                    if (dataList != null && !dataList.isEmpty()) {

                        for (Data data : dataList) {
                            if ("COMPLETED".equals(data.getStatus())) {
                                completedItems.add(data);
                            }else{
                                Log.d("oh error","Double check");
                            }
                        }dataAdapter1.setData(completedItems);

                    } else {
                        Log.d("NoData", "No data received");
                    }
                } else {
                    Log.d("DataResponseNull", "DataResponse is null");
                }
            }

            @Override
            public void onFailure(@NonNull Call<DataResponse> call, @NonNull Throwable throwable) {
                Log.d("onFailure","Done");
            }
        });
    }

    @Override
    public void onItemRemoved(Data removedItem) {
       completedItems.add(removedItem);
        dataAdapter1.notifyItemInserted(completedItems.size() - 1);
    }

}