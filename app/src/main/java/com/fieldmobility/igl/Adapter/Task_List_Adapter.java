package com.fieldmobility.igl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fieldmobility.igl.MataData.VideoListData;
import com.fieldmobility.igl.R;

import androidx.recyclerview.widget.RecyclerView;

public class Task_List_Adapter extends RecyclerView.Adapter<Task_List_Adapter.ViewHolder>{
    private VideoListData[] videolistdata;
    Context context;
    // RecyclerView recyclerView;
    public Task_List_Adapter(Context context,VideoListData[] videolistdata) {
        this.videolistdata = videolistdata;
        this.context = context;
    }
    @Override
    public Task_List_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.task_list_adapter, parent, false);
        Task_List_Adapter.ViewHolder viewHolder = new Task_List_Adapter.ViewHolder(listItem);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listItem.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Task_List_Adapter.ViewHolder holder, int position) {
        final VideoListData VideoListData = videolistdata[position];
        holder.date_text.setText(VideoListData.getImgId());
        holder.title_text.setText(VideoListData.getDescription());

       /* holder.video_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription(),Toast.LENGTH_LONG).show();
            }
        });*/

       /* holder.comment_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1=new Intent(context, Comment_Activity.class);
                context.startActivity(intent1);
                // Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription(),Toast.LENGTH_LONG).show();
            }
        });*/
    }


    @Override
    public int getItemCount() {
        return videolistdata.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView video_image;
        public TextView title_text,date_text;
        public LinearLayout video_layout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.title_text = (TextView) itemView.findViewById(R.id.title_text);
            this.date_text = (TextView) itemView.findViewById(R.id.date_text);
            //  video_layout = (LinearLayout)itemView.findViewById(R.id.video_layout);
        }
    }
}