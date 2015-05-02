package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.azilen.android.materialdesignsample.R;

import java.util.ArrayList;


public class NavigationDrawer_Adapter extends BaseAdapter {

    ArrayList<String> menu = new ArrayList<String>();

    public NavigationDrawer_Adapter(){
        menu.add("First Menu");
        menu.add("Second Menu");
        menu.add("Third Menu");
        menu.add("Fourth Menu");

    }

    @Override
    public int getCount() {
        return menu.size();
    }

    @Override
    public Object getItem(int position) {
        return menu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drawerlist,parent, false);

        Holder vh = new Holder();
        //vh.image = (ImageView) view.findViewById(R.id.icon);
        vh.Menu = (TextView) view.findViewById(R.id.tvDrawerList);
        vh.Menu.setText(menu.get(position));

        return view;
    }

    static class Holder {
        public TextView Menu;
        public ImageView image;
    }
}
