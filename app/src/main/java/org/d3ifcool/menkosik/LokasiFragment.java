package org.d3ifcool.menkosik;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.d3ifcool.menkosik.model.DataKos;

import java.util.ArrayList;

import static org.d3ifcool.menkosik.Temp.dataKos;

/**
 * Created by sahid on 10/3/2018.
 */

public class LokasiFragment extends Fragment implements OnMapReadyCallback {

    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<DataKos> grid;
    SupportMapFragment mapFragment;
    GoogleMap mMap;
    private int REQUEST_LOCATION = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maps, container, false);

        ConnectivityManager connMgr = (ConnectivityManager) getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
        } else {
            AlertDialog.Builder alert = new AlertDialog.Builder(getContext())
                    .setTitle("Tidak Ada Koneksi Internet")
                    .setMessage("Silakan periksa koneksi internet anda dan coba lagi")
                    .setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
            alert.setCancelable(false);
            alert.show();
        }

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Kost");
        grid = new ArrayList<>();

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //dataKos = dataSnapshot.getValue(DataKos.class);
                grid.add(dataSnapshot.getValue(DataKos.class));
                if(mMap != null) addMarker(grid.get(grid.size()-1));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                dataKos = dataSnapshot.getValue(DataKos.class);
                int position = cariKos(dataKos);
                if(position!=-1){
                    grid.set(position,dataKos);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(grid.size() > 0){
            for(DataKos dataKos : grid){
                addMarker(dataKos);
            }
        }

        if(checkLocationPermission())
            mMap.setMyLocationEnabled(true);
    }

    private int cariKos(DataKos dataKos) {
        int hasil=-1;
        for (int i = 0; i <grid.size() ; i++) {
            if(grid.get(i).getmNamaKost().equalsIgnoreCase(dataKos.getmNamaKost())){
                hasil=i;
            }
        }
        return hasil;
    }

    private void addMarker(DataKos dataKos){
        LatLng location = new LatLng(dataKos.getmLatitude(), dataKos.getmLongitude());
        mMap.addMarker(new MarkerOptions().position(location)
                .title(dataKos.getmNamaKost()));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15.5f));
    }public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ActivityCompat.checkSelfPermission(getContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        if(mMap != null)
                            mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }
}