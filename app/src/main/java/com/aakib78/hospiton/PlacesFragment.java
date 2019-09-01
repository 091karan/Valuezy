package com.aakib78.hospiton;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlacesFragment extends Fragment {

    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private PlacesAdapter adapter;
    private ArrayList<StoreListModel> storeList;
    private ProgressDialog progressDialog;
    private EditText etSearch;
    View view;


    public PlacesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       if(view==null){
           view= inflater.inflate(R.layout.fragment_places, container, false);

           progressDialog=new ProgressDialog(getContext());
           progressDialog.setMessage("Fetching nearby stores..");
           progressDialog.show();
           reference= FirebaseDatabase.getInstance().getReference("places");
           reference.keepSynced(true);
           etSearch=(EditText)view.findViewById(R.id.editText);
           recyclerView=(RecyclerView)view.findViewById(R.id.recyclerView);
           recyclerView.setHasFixedSize(true);
           recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));
           storeList=new ArrayList<StoreListModel>();
           storeList.clear();

           reference.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   if(dataSnapshot.exists()){
                       for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                           StoreListModel b =dataSnapshot1.getValue(StoreListModel.class);
                           storeList.add(b);
                       }
                       adapter=new PlacesAdapter(getContext(),storeList);
                       recyclerView.setAdapter(adapter);
                       progressDialog.dismiss();
                   }

               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {
                   Toast.makeText(getContext(), "Something went wrong...", Toast.LENGTH_SHORT).show();
                   progressDialog.dismiss();
               }
           });

           etSearch.addTextChangedListener(new TextWatcher() {
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
       return view;
    }

    private void filter(String text) {

    }

}
