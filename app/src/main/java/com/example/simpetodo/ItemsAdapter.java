package com.example.simpetodo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

//ItemsAdapter is responsible for taking data at a particular position and putting it into a viewholder
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    public interface OnLongClickListener{
        void onItemLongClicked(int position);
    }
    //Create an ArrayList of string items
    List<String>items;
    OnLongClickListener longClickListener;
    public ItemsAdapter(List<String>items, OnLongClickListener longClickListener) {
        //setting the member variable equal to to the variable being passed in
        this.items = items;
        //setting the member variable (longClickListener) to the variable being passed
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        // Use layout inflator to inflate a view
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1,parent,false);
        // wrap it inside a View Holder and return it
        return new ViewHolder(todoView) ;
    }

    // Tells the RV how many items are in the list
    @Override
    public int getItemCount() {
        return items.size();
    }
    //responsible for binding data to a particular viewholder
    @Override
    public void onBindViewHolder(@NonNull  ViewHolder holder, int position) {
        // grab the item at the position
        String item = items.get(position);
        //bind the item into the specified view holder
        holder.bind(item);

    }

    //Container to provide easy access to views that represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvItem;
        // create a constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //finds the itemView by ID
            tvItem = itemView.findViewById(android.R.id.text1);
        }
        //Update the view inside of the view holder with this data
        public void bind(String item) {
            // Sets the text to be displayed using a string resource identifier.
            tvItem.setText(item);
            //setOnLongClickListener is called when a view has been clicked and held.
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // Remove the item from the recycler view
                    //Notify the listener which item was longpressed
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
