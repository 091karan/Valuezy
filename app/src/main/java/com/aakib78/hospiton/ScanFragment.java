package com.aakib78.hospiton;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.Result;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScanFragment extends Fragment {

    View view;
    private CodeScanner mCodeScanner;
    private FirebaseAuth mAuth;
    public ScanFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view==null){
            view= inflater.inflate(R.layout.fragment_scan, container, false);
            setHasOptionsMenu(true);

            Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
            mAuth=FirebaseAuth.getInstance();
            CodeScannerView scannerView = view.findViewById(R.id.scanner_view);
            mCodeScanner = new CodeScanner(getActivity().getBaseContext(), scannerView);
            mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@androidx.annotation.NonNull final Result result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String finalResult=result.getText();
                            Toast.makeText(getContext(), finalResult, Toast.LENGTH_LONG).show();
                        }
                    });

                }
            });
            scannerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCodeScanner.startPreview();
                }
            });

        }


        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.signOut){
            mAuth.signOut();
            LoginManager.getInstance().logOut();
            updateUI();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu,menu);
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }


    private void updateUI() {
        Intent homeIntent=new Intent(getContext(),SelectLoginType.class);
        startActivity(homeIntent);
        getActivity().finish();
    }
}
