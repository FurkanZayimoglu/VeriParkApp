package com.example.veriparkapp.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.veriparkapp.util.OnItemClickListener;
import com.example.veriparkapp.R;
import com.example.veriparkapp.model.list.Stocks;

import java.util.ArrayList;
import java.util.List;

public class ImkbAdapter extends RecyclerView.Adapter<ImkbAdapter.MyViewHolder> implements Filterable {

        private List<Stocks> imkbList;
        private List<Stocks> filterImkbList;
        private LayoutInflater layoutInflater;
        private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private Context context;


        public ImkbAdapter (Context context, ArrayList<Stocks> arrayList){
            this.imkbList = arrayList;
            this.filterImkbList = new ArrayList<>(arrayList);
            this.layoutInflater = LayoutInflater.from(context);
            this.context = context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = layoutInflater.inflate(R.layout.datatable,parent,false);
            return new MyViewHolder(view);

        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
         holder.symbolTxt.setText(imkbList.get(position).getSymbol());
         holder.fiyatTxt.setText( String.valueOf(imkbList.get(position).getPrice()));
         holder.farkTxt.setText(String.valueOf(imkbList.get(position).getDifference()) );
         holder.hacimTxt.setText(String.valueOf(imkbList.get(position).getVolume()));
         holder.alisTxt.setText(String.valueOf(imkbList.get(position).getBid()));
         holder.satisTxt.setText(String.valueOf(imkbList.get(position).getOffer()));
         Bitmap upIcon = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.up);
         Bitmap downIcon = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.down);
         if (imkbList.get(position).isDown()){
             holder.degisimImage.setImageBitmap(downIcon);
         }else{
             holder.degisimImage.setImageBitmap(upIcon);
         }

        }

        @Override
        public int getItemCount() {
            return imkbList.size();
        }


        @Override
        public Filter getFilter() {
            return exampleFilter;
        }

        private Filter exampleFilter =new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                    List<Stocks> list = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    list.addAll(filterImkbList);
                } else {
                    String filterPatern = constraint.toString().toLowerCase().trim();

                    for (Stocks stocksModel : filterImkbList) {
                        if (stocksModel.getSymbol().toLowerCase().contains(filterPatern)) {
                            list.add(stocksModel);
                        }

                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                filterResults.count = list.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                imkbList.clear();
                imkbList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
            TextView symbolTxt;
            TextView fiyatTxt;
            TextView farkTxt;
            TextView hacimTxt;
            TextView alisTxt;
            TextView satisTxt;
            ImageView degisimImage;

            MyViewHolder(@NonNull View itemView) {
                super(itemView);
                symbolTxt = itemView.findViewById(R.id.txtSymbolAdapter);
                fiyatTxt = itemView.findViewById(R.id.txtFiyatAdapter);
                farkTxt = itemView.findViewById(R.id.txtFarkAdapter);
                hacimTxt = itemView.findViewById(R.id.txtHacimAdapter);
                alisTxt = itemView.findViewById(R.id.txtAlisAdapter);
                satisTxt = itemView.findViewById(R.id.txtSatisAdapter);
                degisimImage = itemView.findViewById(R.id.imageDegisimAdapter);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                listener.onItemClick(getAdapterPosition());
            }
        }

    }

