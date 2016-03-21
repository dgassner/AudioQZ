package com.davidgassner.audioqz.layout;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.davidgassner.audioqz.R;
import com.davidgassner.audioqz.model.Cue;

import java.util.List;

public class CueListAdapter extends ArrayAdapter<Cue> {

    private static final String TAG = "CueListAdapter";
    private List<Cue> cueList;
    private Context context;

    public CueListAdapter(Context context, List<Cue> cueList) {
        super(context, R.layout.cue_list_item, cueList);
        this.context = context;
        this.cueList = cueList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.i(TAG, "getView: row displayed " + position);

//      inflate layout if necessary
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cue_list_item, parent, false);
        }

//      Inject data to views
        Cue cue = cueList.get(position);

        TextView tvCueNumber = (TextView) convertView.findViewById(R.id.tvCueNumber);
        tvCueNumber.setText(String.format("%s%s",
                context.getString(R.string.cue_number_label), cue.getCueNumber()));
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        tvTitle.setText(cue.getTitle());

        return convertView;
    }
}
