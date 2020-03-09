package com.example.veriparkapp.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.veriparkapp.util.Cryption;
import com.example.veriparkapp.R;
import com.example.veriparkapp.model.detail.DetailBody;
import com.example.veriparkapp.model.detail.DetailModel;
import com.example.veriparkapp.model.detail.GraphicDataModel;
import com.example.veriparkapp.network.ApiService;
import com.example.veriparkapp.network.HandshakeService;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailScreenActivity extends AppCompatActivity {
    private int id;
    private String aeskey;
    private String aesIV;
    private String auth;
    ApiService apiService;
    public TextView symboltext;
    public TextView lowestText;
    public TextView highestText;
    public TextView priceText;
    public TextView maxText;
    public TextView diffrenceText;
    public TextView minText;
    public TextView countText;
    public TextView volumeText;
    public TextView offerText;
    public TextView bidText;
    public ImageView changeImage;
    public List<GraphicDataModel> graphicData;
    private LineChart lineChart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_detail);

        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        aeskey = intent.getStringExtra("aeskey");
        aesIV = intent.getStringExtra("aesIV");
        auth = intent.getStringExtra("auth");
        apiService = HandshakeService.getRetrofitInstance().create(ApiService.class);
        graphicData = new ArrayList<>();

        viewInit();
        getDetailScreenService();

    }

    public  void viewInit() {
        symboltext = findViewById(R.id.symbolText);
        lowestText = findViewById(R.id.gunlukDusukText);
        highestText = findViewById(R.id.gunlukYuksekText);
        priceText = findViewById(R.id.fiyatText);
        maxText = findViewById(R.id.tavanText);
        minText = findViewById(R.id.tabanText);
        diffrenceText =findViewById(R.id.farkText);
        volumeText = findViewById(R.id.hacimText);
        bidText = findViewById(R.id.alisText);
        offerText = findViewById(R.id.satisText);
        changeImage = findViewById(R.id.degisimImage);
        countText = findViewById(R.id.adetText);
        lineChart = findViewById(R.id.lineChart);
        lineChart.setTouchEnabled(false);
        lineChart.setPinchZoom(false);

    }

    public void setData(DetailModel detailModel){
        try {
            symboltext.setText(Cryption.decrypt(detailModel.getSymbol().getBytes(),aeskey.getBytes(),aesIV.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        lowestText.setText(String.valueOf(detailModel.getLowest()));
        highestText.setText(String.valueOf(detailModel.getHighest()));
        priceText.setText(String.valueOf(detailModel.getPrice()));
        maxText.setText(String.valueOf(detailModel.getMaximum()));
        minText.setText(String.valueOf(detailModel.getMinumum()));
        diffrenceText.setText(String.valueOf(detailModel.getDifference()));
        countText.setText(String.valueOf(detailModel.getCount()));
        volumeText.setText(String.valueOf(detailModel.getVolume()));
        bidText.setText(String.valueOf(detailModel.getBid()));
        offerText.setText(String.valueOf(detailModel.getOffer()));
        if (detailModel.isUp()){
            changeImage.setImageDrawable(getResources().getDrawable(R.drawable.up));
        }
        else{
            changeImage.setImageDrawable(getResources().getDrawable(R.drawable.down));
        }

        graphicData = detailModel.getGraphicData();
        if (graphicData.size() != 0){
            drawLineChart(graphicData);
        }else
            lineChart.setVisibility(View.INVISIBLE);

    }

    private void drawLineChart(List<GraphicDataModel> graphicDataModelList) {
        ArrayList<Entry> values = new ArrayList<>();
        for (GraphicDataModel graphicDataModel : graphicDataModelList){
            values.add(new Entry((float) graphicDataModel.getDay(), (float) graphicDataModel.getValue()));
        }
        LineDataSet lineDataSet = new LineDataSet(values, getResources().getString(R.string.arrow));
        lineDataSet.setFillAlpha(110);

        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);

        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private void getDetailScreenService() {

        try {
            String idR = Cryption.encrypt(String.valueOf(id).getBytes(),aeskey.getBytes(),aesIV.getBytes());
            final DetailBody detailBody = new DetailBody();
            detailBody.setId(idR);

            Call<DetailModel> call = apiService.getDetailService(auth,"application/json", detailBody);
            call.enqueue(new Callback<DetailModel>() {
                @Override
                public void onResponse(@NonNull Call<DetailModel> call, @NonNull Response<DetailModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            DetailModel detailModel = response.body();
                            Log.i("detail", String.valueOf(detailModel.getGraphicData().get(0).getDay()));
                            setData(detailModel);
                        }
                    }
                }
                @Override
                public void onFailure(@NonNull Call<DetailModel> call, @NonNull Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }




    }
}
