package com.zocarro.myvideobook.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.zocarro.myvideobook.Model.feedlinkmodel;
import com.zocarro.myvideobook.R;

public class feedVideoAdapter extends RecyclerView.Adapter<feedVideoAdapter.holder> {

    public static feedlinkmodel data[];
    Context context;
    String videolink;



    public feedVideoAdapter(feedlinkmodel[] data, Context context) {
        this.data = data;
        this.context = context;

    }


    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layout = LayoutInflater.from(parent.getContext());
        View view = layout.inflate(R.layout.feedvideo_raw ,parent , false);
        return new holder(view);
    }

    @Override


    public void onBindViewHolder(@NonNull holder holder, @SuppressLint("RecyclerView") int position) {

        holder.title.setText(data[position].getTitle());
        holder.date.setText(data[position].getDate());



        holder.youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {


            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);


//                DefaultPlayerUiController defaultPlayerUiController = new DefaultPlayerUiController(holder.youTubePlayerView, youTubePlayer);
//                holder.youTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.getRootView());

                youTubePlayer.cueVideo(data[position].getLink(),0);

            }
        });


    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    class holder extends RecyclerView.ViewHolder{


        YouTubePlayerView youTubePlayerView;
        TextView title,date;



        public holder(@NonNull View itemView) {
            super(itemView);

            youTubePlayerView = itemView.findViewById(R.id.youtube_player_view);
            youTubePlayerView.setLongClickable(false);
            title = itemView.findViewById(R.id.titletxt);
            date = itemView.findViewById(R.id.datetxt);




        }
    }
}

