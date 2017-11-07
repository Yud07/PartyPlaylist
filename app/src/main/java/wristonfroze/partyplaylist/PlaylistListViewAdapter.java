package wristonfroze.partyplaylist;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
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

public class PlaylistListViewAdapter extends
    RecyclerView.Adapter<PlaylistListViewAdapter.ViewHolder> {

    private final static String TAG = PlaylistListViewAdapter.class.getSimpleName();

    // Interface used to select the playlist
    public interface OnPlaylistSelectChange {
        void onPlaylistSelect(Playlist s);
        void onPlaylistDeSelect(Playlist s);
    }

    private ArrayList<Playlist> mPlaylists;
    private OnPlaylistSelectChange mListener;

    public PlaylistListViewAdapter(ArrayList<Playlist> playlistList, OnPlaylistSelectChange listener) {
            mListener = listener;
            mPlaylists = playlistList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listitem_playlists, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final Playlist p = mPlaylists.get(position);
            holder.playlist = p;
            holder.position = position;
            holder.playlistNameLabel.setText(p.name);
            if (p.selected){
                holder.playlistNameLabel.setTextColor(Color.RED);
            }
            else{
                holder.playlistNameLabel.setTextColor(Color.BLACK);
            }
            holder.playlistDateLabel.setText(p.date);
            holder.enablePlaylistButton.setChecked(p.selected);
            holder.enablePlaylistButton.setEnabled(holder.selectButton);

        }

        @Override
        public int getItemCount() {
            return mPlaylists.size();
        }

    public Playlist getItem(int i) {
        return mPlaylists.get(i);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public Playlist playlist;
        public int position;
        public final TextView playlistNameLabel;
        public final TextView playlistDateLabel;
        public final CompoundButton enablePlaylistButton;
        public boolean selectButton=true;

        public ViewHolder(View view) {
            super(view);

            playlistNameLabel = (TextView) view.findViewById(R.id.playlistNameLabel);
            playlistNameLabel.setOnClickListener(new View.OnClickListener() {
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
                            playlist.name = value;
                            playlistNameLabel.setText(playlist.name);
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
            });
            playlistDateLabel = (TextView) view.findViewById(R.id.playlistDateLabel);
            enablePlaylistButton = (CompoundButton) view.findViewById(R.id.enablePlaylistButton);
            enablePlaylistButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean status) {
                    if (status) {
                        mListener.onPlaylistSelect(playlist);
                        for (Playlist p : mPlaylists) {
                            if (p != playlist && p.selected){
                                p.selected = false;
                            }
                        }
                        playlistNameLabel.setTextColor(Color.RED);
                    }
                    else {
                        mListener.onPlaylistDeSelect(playlist);
                        playlistNameLabel.setTextColor(Color.BLACK);
                    }
                }
            });
            playlist = null;
        }
    }

}
