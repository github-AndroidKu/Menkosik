package org.d3ifcool.menkosik;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.d3ifcool.menkosik.model.DataKos;

import java.util.ArrayList;

/**
 * Created by Faizal on 10/17/2018.
 */

public class AreaListAdapter extends ArrayAdapter<DataKos> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<DataKos> data = new ArrayList();

    public AreaListAdapter(Context context, int layoutResourceId, ArrayList<DataKos> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        this.data = data;
    }

    static class ViewHolder {
        TextView mKotaKos;
        TextView mDaerahKos;
    }

    @Override
    // Create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {

        AreaListAdapter.ViewHolder holder = null;
        DataKos currentKos = getItem(position);

        if (convertView == null) {
            // If it's not recycled, initialize some attributes
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
            holder = new AreaListAdapter.ViewHolder();

            holder.mKotaKos = (TextView) convertView.findViewById(R.id.Kota_area);
            holder.mDaerahKos = (TextView) convertView.findViewById(R.id.Daerah_area);

            convertView.setTag(holder);
        } else {
            holder = (AreaListAdapter.ViewHolder) convertView.getTag();;
        }

        holder.mKotaKos.setText(currentKos.getmNamaKost());
        holder.mDaerahKos.setText(currentKos.getmDaerahKost());
        return convertView;
    }

}
