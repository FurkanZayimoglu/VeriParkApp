package com.example.veriparkapp.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.veriparkapp.util.Cryption;
import com.example.veriparkapp.util.OnItemClickListener;
import com.example.veriparkapp.R;
import com.example.veriparkapp.adapter.ImkbAdapter;
import com.example.veriparkapp.model.handshake.HandshakeModel;
import com.example.veriparkapp.model.handshake.HandshakeReguestBody;
import com.example.veriparkapp.model.list.ListBody;
import com.example.veriparkapp.model.list.ListModel;
import com.example.veriparkapp.model.list.Stocks;
import com.example.veriparkapp.network.ApiService;
import com.example.veriparkapp.network.HandshakeService;
import com.example.veriparkapp.ui.activity.DetailScreenActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DusenlerFragment extends Fragment implements OnItemClickListener {

    private ApiService apiService;
    private ArrayList<Stocks> dusenlerList;
    public  ArrayList<Stocks> dusenlerListnew;
    public ImkbAdapter adapter;
    public RecyclerView recyclerView;
    private EditText editText;
    private HandshakeModel handshakeModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dusenler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiService = HandshakeService.getRetrofitInstance().create(ApiService.class);
        dusenlerList = new ArrayList<>();
        dusenlerListnew = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recylerDusenler);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        editText = view.findViewById(R.id.searchImkb);
        getHandshakeService();
        doSearch();
    }


    private void doSearch() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void setAdapter(ArrayList<Stocks> dusenlerList) {
        adapter = new ImkbAdapter(getActivity(), dusenlerList);
        recyclerView.setAdapter(adapter);
        adapter.setListener(this);
        adapter.notifyDataSetChanged();
    }


    private void getHandshakeService() {
        HandshakeReguestBody body = new HandshakeReguestBody();
        body.setDeviceId(Build.ID);
        body.setSystemVersion(String.valueOf(android.os.Build.VERSION.SDK_INT));
        body.setPlatformName("Android");
        body.setDeviceModel(Build.MODEL);
        body.setManifacturer(Build.MANUFACTURER);

        Call<HandshakeModel> call = apiService.getFirstService(body);
        call.enqueue(new Callback<HandshakeModel>() {
            @Override
            public void onResponse(Call<HandshakeModel> call, Response<HandshakeModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        handshakeModel = response.body();
                        Log.i(" verileeeerrrr", String.valueOf(handshakeModel.getStatus().getIsSuccess()));
                        Log.i(" autttt", handshakeModel.getAuthorization());
                        Log.i(" key", handshakeModel.getAesKey());
                        Log.i(" dort", handshakeModel.getAesIV());
                        getListService(handshakeModel);
                    }
                }
            }

            @Override
            public void onFailure(Call<HandshakeModel> call, Throwable t) {

            }
        });
    }

    private void getListService(final HandshakeModel model) {

        try {
            String ciphertextString = Cryption.encrypt("decreasing".getBytes(),model.getAesKey().getBytes(),model.getAesIV().getBytes());

            ListBody body1 = new ListBody();
            body1.setPeriod(ciphertextString);

            Call<ListModel> call = apiService.getSecondListService(model.getAuthorization(), "application/json", body1);
            call.enqueue(new Callback<ListModel>() {
                @Override
                public void onResponse(@NonNull Call<ListModel> call, @NonNull Response<ListModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            Log.i(" basarı durumu ", String.valueOf(response.body().getStatus().getIsSuccess()));
                            Log.i(" listeeeeee", response.body().getStatus().getError().getMessage());
                            System.out.println(response.body().getStocks().get(0).getPrice());
                            ListModel listModel = response.body();
                            dusenlerList = listModel.getStocks();
                            for (Stocks stocksItem : dusenlerList){
                                try {
                                    stocksItem.setSymbol(Cryption.decrypt(stocksItem.getSymbol().getBytes(),model.getAesKey().getBytes(),model.getAesIV().getBytes()));
                                    dusenlerListnew.add(stocksItem);
                                    System.out.println("deneme Stocks" + stocksItem.getSymbol());
                                    setAdapter(dusenlerListnew);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            System.out.println(listModel.getStocks().get(0).getSymbol());


                        }
                    }
                }

                @Override
                public void onFailure(Call<ListModel> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), DetailScreenActivity.class);
        intent.putExtra("id",dusenlerList.get(position).getId());
        intent.putExtra("aeskey",handshakeModel.getAesKey());
        intent.putExtra("aesIV",handshakeModel.getAesIV());
        intent.putExtra("auth",handshakeModel.getAuthorization());
        startActivity(intent);
    }
}
