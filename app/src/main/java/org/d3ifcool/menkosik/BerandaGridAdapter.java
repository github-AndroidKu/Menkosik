package org.d3ifcool.menkosik;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.d3ifcool.menkosik.model.DataKos;

import java.util.ArrayList;

/**
 * Created by Faizal on 10/17/2018.
 */

public class BerandaGridAdapter extends ArrayAdapter<DataKos> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<DataKos> data = new ArrayList();


    public BerandaGridAdapter(Context context, int layoutResourceId, ArrayList<DataKos> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        this.data = data;
    }

    static class ViewHolder {
        TextView mNamaKos;
         ImageView image;
    }

    @Override
    // Create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        BerandaGridAdapter.ViewHolder holder = null;
        DataKos currentKos = getItem(position);

        if (convertView == null) {
            // If it's not recycled, initialize some attributes
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new BerandaGridAdapter.ViewHolder();

            holder.mNamaKos = (TextView) convertView.findViewById(R.id.kos_grid_name);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (BerandaGridAdapter.ViewHolder) convertView.getTag();;
        }

        holder.mNamaKos.setText(currentKos.getmNamaKost());

        Picasso.get().load(currentKos.getmFotoKost()).resize(100,100).centerCrop().into(holder.image);

        return convertView;
    }
}
