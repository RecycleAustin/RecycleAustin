package com.austindroids.recycleaustin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.austindroids.recycleaustin.R;


public class DashboardAdapter extends BaseAdapter {
    private Context mContext;

    static final LauncherIcon[] ICONS = {
            new LauncherIcon(R.drawable.ic_launcher, "Schedule"),
            new LauncherIcon(R.drawable.ic_launcher, "Recycling Locations"),
            new LauncherIcon(R.drawable.ic_launcher, "Find a Recycling Service"),
            new LauncherIcon(R.drawable.ic_launcher, "Can I Recycle It?"),
    };

    public DashboardAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return ICONS.length;
    }

    @Override
    public LauncherIcon getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // Create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);

            v = vi.inflate(R.layout.dashboard_icon, null);
            holder = new ViewHolder();
            holder.text = (TextView) v.findViewById(R.id.dashboard_icon_text);
            holder.icon = (ImageView) v.findViewById(R.id.dashboard_icon_img);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.icon.setImageResource(ICONS[position].imgId);
        holder.text.setText(ICONS[position].text);

        return v;
    }

    static class ViewHolder {
        public ImageView icon;
        public TextView text;
    }

    static class LauncherIcon {
        final String text;
        final int imgId;

        public LauncherIcon(int imgId, String text) {
            super();
            this.imgId = imgId;
            this.text = text;
        }

    }
}
