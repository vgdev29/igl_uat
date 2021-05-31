package com.fieldmobility.igl.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fieldmobility.igl.MataData.Bp_No_Item;
import com.fieldmobility.igl.R;

import java.util.ArrayList;
import java.util.List;

public class Learning_Adapter extends RecyclerView.Adapter<Learning_Adapter.ViewHolder> implements Filterable {
    Context context;
    // RecyclerView recyclerView;
    private List<Bp_No_Item> bp_no_list_array;
    private List<Bp_No_Item> New_bp_no_list_array;
    private New_BP_NO_Adapter.ContactsAdapterListener listener;
    public Learning_Adapter(Context context, List<Bp_No_Item> New_bp_no_list_array, New_BP_NO_Adapter.ContactsAdapterListener listener) {
        this.bp_no_list_array = New_bp_no_list_array;
        this.New_bp_no_list_array = New_bp_no_list_array;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public Learning_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.learning_item, parent, false);
        Learning_Adapter.ViewHolder viewHolder = new Learning_Adapter.ViewHolder(listItem);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        listItem.setLayoutParams(lp);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(Learning_Adapter.ViewHolder holder, int position) {
        final Bp_No_Item Bp_No_array = bp_no_list_array.get(position);
       // holder.date_text.setText(Bp_No_array.getBp_date());
        holder.title.setText(Bp_No_array.getText());
        holder.type_text.setText("Type: "+Bp_No_array.getType_url());
       holder.view_pdf_video.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

           }
       });

        //
    }
    @Override
    public int getItemCount() {
        return bp_no_list_array.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView date_text,title,view_pdf_video,type_text;
        public LinearLayout relativeLayout;
        public CardView linearLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            view_pdf_video = (TextView) itemView.findViewById(R.id.view_pdf_video);
            title = (TextView) itemView.findViewById(R.id.title);
            type_text = (TextView) itemView.findViewById(R.id.type_text);
            //linearLayout = (CardView)itemView.findViewById(R.id.linearLayout);
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    bp_no_list_array = New_bp_no_list_array;
                } else {
                    List<Bp_No_Item> filteredList = new ArrayList<>();
                    for (Bp_No_Item row : New_bp_no_list_array) {
                        if (row.getUrl_path().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    bp_no_list_array = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = bp_no_list_array;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                bp_no_list_array = (ArrayList<Bp_No_Item>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(Bp_No_Item contact);
    }
}