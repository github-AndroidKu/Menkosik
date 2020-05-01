package org.d3ifcool.menkosik;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.d3ifcool.menkosik.model.DataKos;

import java.io.Serializable;
import java.util.ArrayList;

//import static org.d3ifcool.menkosik.ReadActivity.EXTRA_KOST_NAME;
/**
 * Created by Faizal on 9/14/2018.
 */

public class BerandaFragment extends Fragment {

    GridView gridView;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<DataKos> grid;
    DataKos dataKos;
    ImageView image;

    BerandaGridAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.grid_item, container, false);

        dataKos = new DataKos();

        gridView = (GridView) view.findViewById(R.id.kos_grid_view);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Kost");
        grid = new ArrayList<>();

        adapter = new BerandaGridAdapter(getContext(),R.layout.grid_item_layout, grid);
        gridView.setAdapter(adapter);

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

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //dataKos = dataSnapshot.getValue(DataKos.class);
                grid.add(dataSnapshot.getValue(DataKos.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                dataKos = dataSnapshot.getValue(DataKos.class);
                int position = cariKos(dataKos);
                if(position!=-1){
                    grid.set(position,dataKos);
                }
                adapter.notifyDataSetChanged();
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
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds: dataSnapshot.getChildren())
//                {
//                    dataKos = ds.getValue(DataKos.class);
//                    grid.add("Kost : "+dataKos.getmNamaKost().toString());
//                }
//                gridView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mKosIntent = new Intent(getActivity(), ReadActivity.class);
                DataKos datakos = adapter.getItem(position);
                Temp.dataKos = datakos;
                startActivity(mKosIntent);
            }
        });

        return view;

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
}