package wristonfroze.partyplaylist;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import wristonfroze.partyplaylist.R;
import wristonfroze.partyplaylist.Song;

public class SongListViewAdapter extends
        RecyclerView.Adapter<SongListViewAdapter.ViewHolder> {

    private final static String TAG = SongListViewAdapter.class.getSimpleName();

    private ArrayList<Song> mSongs;

    public SongListViewAdapter(ArrayList<Song> songList) {
        mSongs = songList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_songs, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Song p = mSongs.get(position);
        holder.song = p;
        holder.position = position;
        holder.songNameLabel.setText(p.name);
        if (p.selected){
            holder.songNameLabel.setTextColor(Color.RED);
        }
        else{
            holder.songNameLabel.setTextColor(Color.BLACK);
        }
        holder.songArtistLabel.setText(p.artist);
        holder.songScoreLabel.setText(Integer.toString(p.score));

    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public Song getItem(int i) {
        return mSongs.get(i);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public Song song;
        public int position;
        public final TextView songNameLabel;
        public final TextView songArtistLabel;
        public final TextView songScoreLabel;
        public boolean selectButton=true;

        public ViewHolder(View view) {
            super(view);

            songNameLabel = (TextView) view.findViewById(R.id.songNameLabel);
            /*songNameLabel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setTitle("Enter Name: ");

                    final EditText input = new EditText(v.getContext());
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String value = input.getText().toString();
                            if (value.isEmpty()) value = "";
                            song.name = value;
                            songNameLabel.setText(song.name);
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
            });*/
            songArtistLabel = (TextView) view.findViewById(R.id.songArtistLabel);
            songScoreLabel = (TextView) view.findViewById(R.id.songScoreLabel);
            song = null;
        }
    }

}
