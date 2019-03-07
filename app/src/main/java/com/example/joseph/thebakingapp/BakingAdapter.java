package com.example.joseph.thebakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BakingAdapter extends RecyclerView.Adapter<BakingAdapter.BakingViewHolder> {

    private static final String TAG = "BakingAdapter";
    // an onclick handler that we define to make it easy for an activity to interface with our recyclerView
    private final ListItemClickListener mOnClickListener;
    private  ArrayList<Baking> mBakings;
    private  Context mContext;


    //Create a constructor for BakingAdapter that accepts an int as a parameter for numberOfItems which stores
    // the numberofItems parameter in mNumberOfItems
    public BakingAdapter(Context c, ArrayList<Baking> data, ListItemClickListener listener) {
        mContext = c;
        mBakings = data;
        mOnClickListener = listener;
    }

    // This gets called when each new ViewHolder is created. This happens when the RecyclerView is laid out.
    // Enough ViewHolders will be created to fill the screen and allow for scrolling.
    // @param viewGroup The ViewGroup that these ViewHolders are contained within.
    // @param viewType If your RecyclerView has more than one type of item (which ours doesn't) you can
    //                  use this viewType integer to provide a different layout. See
    //                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(intI)}
    //                  for more details
    // @return      A new BakingViewHolder that holds the View for each list item
    @Override
    public BakingAdapter.BakingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        BakingViewHolder vh = new BakingViewHolder(v);
        return vh;
    }


    //OnBindViewHolder is called by the RecyclerView to dispLay the data at the specified position. In this method,
    // update the contents of the ViewHolder to display the correct indices in the list for this particular position,
    // using the 'position' argument that is conveniently passed into us.
    @Override
    public void onBindViewHolder(BakingViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder called");

        holder.bindData(position);

        // TODO: This is another method of binding data instead of using the bindData(position) method
        // bind data
        // holder.idTextView.setText(mBakings.get(position).getmId());
        //holder.nameTextView.setText(mBakings.get(position).getmName())
    }

    @Override
    public int getItemCount() {
        if (mBakings == null) return 0;
        return mBakings.size();
    }

    public void setBaking(ArrayList<Baking> bakings) {
        mBakings = bakings;
        notifyDataSetChanged();
    }


    // adding an interface that receives onClick messages called ListItemClickListener
    public interface ListItemClickListener {
        void onListItemClick(Baking  clickedItemIndex);
    }


    // Provides a reference to the views for each data item
    // Complex data items may need more than one view per item, and you provide access to all the
    // views for a data item in a view holder
    public class BakingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView idTextView;
        TextView nameTextView;

        public BakingViewHolder(View itemView) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.id);
            nameTextView = itemView.findViewById(R.id.name);
            itemView.setOnClickListener(this);
        }

        // BIND DATA
        void bindData(int listIndex) {
            Baking baking = mBakings.get(listIndex);

            nameTextView.setText(String.valueOf(baking.getmName()));
            idTextView.setText(String.valueOf(baking.getmId()));
        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            Baking clickeditem = mBakings.get(clickedPosition);
            mOnClickListener.onListItemClick(clickeditem);
        }
    }
}