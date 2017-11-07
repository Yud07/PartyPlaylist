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
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import wristonfroze.partyplaylist.R;
import wristonfroze.partyplaylist.Song;

public class VoteListViewAdapter extends
        RecyclerView.Adapter<VoteListViewAdapter.ViewHolder> {

    private final static String TAG = VoteListViewAdapter.class.getSimpleName();

    private ArrayList<Song> mVotes;

    public VoteListViewAdapter(ArrayList<Song> voteList) {
        mVotes = voteList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem_votes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Song p = mVotes.get(position);
        holder.vote = p;
        holder.position = position;
        holder.voteNameLabel.setText(p.name);
        if (p.selected){
            holder.voteNameLabel.setTextColor(Color.RED);
        }
        else{
            holder.voteNameLabel.setTextColor(Color.BLACK);
        }
        holder.voteArtistLabel.setText(p.artist);
        holder.voteScoreLabel.setText(Integer.toString(p.score));

    }

    @Override
    public int getItemCount() {
        return mVotes.size();
    }

    public Song getItem(int i) {
        return mVotes.get(i);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public Song vote;
        public int position;
        public final TextView voteNameLabel;
        public final TextView voteArtistLabel;
        public final TextView voteScoreLabel;
        public final Button voteUpButton;
        public final Button voteDownButton;
        public boolean selectButton=true;

        public ViewHolder(View view) {
            super(view);

            voteNameLabel = (TextView) view.findViewById(R.id.voteNameLabel);
            /*voteNameLabel.setOnClickListener(new View.OnClickListener() {
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
                            vote.name = value;
                            voteNameLabel.setText(vote.name);
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
            voteArtistLabel = (TextView) view.findViewById(R.id.voteArtistLabel);
            voteScoreLabel = (TextView) view.findViewById(R.id.voteScoreLabel);
            voteUpButton = (Button) view.findViewById(R.id.voteUpButton);
            voteDownButton = (Button) view.findViewById(R.id.voteDownButton);
            vote = null;
        }
    }

}