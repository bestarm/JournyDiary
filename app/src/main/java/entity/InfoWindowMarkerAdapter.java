package entity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import thanhcong.com.nhatkydulich.R;

/**
 * Created by HP on 8/28/2016.
 */
public class InfoWindowMarkerAdapter implements GoogleMap.InfoWindowAdapter {

    private LayoutInflater inflater;

    public InfoWindowMarkerAdapter(Context context){
        inflater = LayoutInflater.from(context);
    }
    @Override
    public View getInfoWindow(Marker marker) {
//        View view = inflater.inflate(R.layout.item,null);
//        TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
//        TextView tvSnippet = (TextView) view.findViewById(R.id.tvSnippet);
//        tvTitle.setText(marker.getTitle());
//        tvSnippet.setText(marker.getSnippet());
//        return view;
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = inflater.inflate(R.layout.item_window_marker_google_map,null);
        TextView tvTitle = (TextView) view.findViewById(R.id.txt_title_item_window_marker_google_map);
        TextView tvSnippet = (TextView) view.findViewById(R.id.txt_snippet_item_window_marker_google_map);
        tvTitle.setText(marker.getTitle());
        tvSnippet.setText(marker.getSnippet());
        return view;
    }
}
