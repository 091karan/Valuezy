package com.aakib78.hospiton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlacesViewHolder> implements Filterable {
    private Context context;
    private ArrayList<StoreListModel> storeList;
    private ArrayList<StoreListModel> storeListFiltered;

    PlacesAdapter(Context context, ArrayList<StoreListModel> storeList) {
        this.context = context;
        this.storeList = storeList;
        this.storeListFiltered=storeList;
    }

    @NonNull
    @Override
    public PlacesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.places_item,viewGroup,false);
        return new PlacesViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final PlacesViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.storeName.setText(storeListFiltered.get(position).getStoreName());
        holder.discount.setText(storeListFiltered.get(position).getDiscount()+"%");
        holder.address.setText(storeListFiltered.get(position).getAddress());
        holder.offerText.setText("Hurry up and snag "+storeListFiltered.get(position).getDiscount()+"% off by scanning QR code on this store.");

        holder.getDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "https://www.google.com/maps/search/?api=1&query="+storeListFiltered.get(position).getLatitude()+","+storeListFiltered.get(position).getLongitude()+"";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                context.startActivity(Intent.createChooser(intent, "Select an application"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeListFiltered.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key =constraint.toString();
                if(key.isEmpty()){
                    storeListFiltered=storeList;
                }
                else {
                    ArrayList<StoreListModel> filteredList=new ArrayList<>();
                    for(StoreListModel storeList1 :storeList){
                        if(storeList1.getStoreName().toLowerCase().contains(key)){
                            filteredList.add(storeList1);
                        }
                    }
                    storeListFiltered=filteredList;
                }
                FilterResults filterResults=new FilterResults();
                filterResults.values=storeListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                storeListFiltered= (ArrayList<StoreListModel>) results.values;
                notifyDataSetChanged();

            }
        };

    }

    public class PlacesViewHolder extends RecyclerView.ViewHolder{
        TextView storeName;
        TextView discount;
        TextView address;
        TextView offerText;
        Button getDirection;

        public PlacesViewHolder(@NonNull View itemView) {
            super(itemView);
            storeName=(TextView)itemView.findViewById(R.id.storeName);
            discount=(TextView)itemView.findViewById(R.id.discount);
            address=(TextView)itemView.findViewById(R.id.address);
            offerText=(TextView)itemView.findViewById(R.id.offerText);
            getDirection=(Button)itemView.findViewById(R.id.getDirection);

        }


    }
}
