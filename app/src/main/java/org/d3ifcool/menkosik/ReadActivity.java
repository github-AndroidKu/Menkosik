package org.d3ifcool.menkosik;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.d3ifcool.menkosik.model.DataKos;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class ReadActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<DataKos> read;
    DataKos dataKos;
    ImageView image;
    TextView mPemilik, mNamaKos, mTelepon, mAlamat, mDaerah, mHarga, mFasilitas;
    MapView mMapView;
    GoogleMap mMap;

    public final static String EXTRA_KOST_NAME = "org.d3ifcool.menkosik.EXTRA_KOST_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivity.getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected()){
            //Toast.makeText(getApplicationContext(),"Terhubung Dengan Internet", Toast.LENGTH_LONG).show();
        }else{
            AlertDialog.Builder alert = new AlertDialog.Builder(this)
                    .setTitle("Tidak Ada Koneksi Internet")
                    .setMessage("Silakan periksa koneksi internet anda dan coba lagi")
                    .setPositiveButton("Tutup", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });
            alert.setCancelable(false);
            alert.show();
        }

        Toolbar ToolBarAtas2 = (Toolbar) findViewById(R.id.toolbar_detail);
        setSupportActionBar(ToolBarAtas2);
        ToolBarAtas2.setLogoDescription(getResources().getString(R.string.app_name));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dataKos = Temp.dataKos;

        mPemilik = findViewById(R.id.nama_pemilik_read);
        mPemilik.setText(dataKos.getmNamaPemilik());
        mNamaKos = findViewById(R.id.nama_kosan_read);
        mNamaKos.setText(dataKos.getmNamaKost());
        mTelepon = findViewById(R.id.telepon_read);
        mTelepon.setText(dataKos.getmHpPemilik());
        mDaerah = findViewById(R.id.kota_daerah_read);
        mDaerah.setText(dataKos.getmDaerahKost());
        mHarga = findViewById(R.id.bulan_tahun_read);
        mHarga.setText(dataKos.getmHargaKost());
        mAlamat = findViewById(R.id.alamat_read);
        mAlamat.setText(dataKos.getmAlamatKost());
        mFasilitas = findViewById(R.id.fasilitas_read);
        mFasilitas.setText(dataKos.getmFasilitas());

        image = findViewById(R.id.avatar_image_view_read);
        Picasso.get().load(dataKos.getmFotoKost()).resize(300, 250).centerCrop().into(image);

        mMapView = findViewById(R.id.mapView);
        mMapView.onCreate(Bundle.EMPTY);

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
//                mMap.getUiSettings().setZoomControlsEnabled(false);
                mMap.getUiSettings().setZoomGesturesEnabled(true);

                LatLng location = new LatLng(dataKos.getmLatitude(), dataKos.getmLongitude());
                googleMap.addMarker(new MarkerOptions().position(location)
                        .title(dataKos.getmNamaKost()));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15.5f));
            }
        });

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mMap.setMyLocationEnabled(true);

        mMapView.setClickable(false);

//        Toast.makeText(this, dataKos.getmNamaKost() , Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        mMapView.onLowMemory();
        super.onLowMemory();
    }
}
