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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.d3ifcool.menkosik.model.DataKos;

import java.util.ArrayList;

/**
 * Created by Faizal on 9/14/2018.
 */

public class AreaFragment  extends Fragment {

    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    ArrayList<DataKos> list;
    DataKos dataKos;
    AreaListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_item_layout, container, false);

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

        dataKos = new DataKos();

        listView = (ListView) view.findViewById(R.id.list_view);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Kost");
        list = new ArrayList<>();

        adapter = new AreaListAdapter(getContext(),R.layout.list_item, list);
        listView.setAdapter(adapter);

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                //dataKos = dataSnapshot.getValue(DataKos.class);
                list.add(dataSnapshot.getValue(DataKos.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                dataKos = dataSnapshot.getValue(DataKos.class);
                int position = cariKos(dataKos);
                if(position!=-1){
                    list.set(position,dataKos);
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        for (int i = 0; i <list.size() ; i++) {
            if(list.get(i).getmKotaKost().equalsIgnoreCase(dataKos.getmKotaKost())){
                hasil=i;
            }
        }
        return hasil;
    }
}
