package com.template.project.views.content_sample

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.template.project.R
import kotlinx.android.synthetic.main.item_list_entry.view.*

class ContentAdapter(private val myDataset: Array<String>) : RecyclerView.Adapter<ContentAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val root: LinearLayout) : RecyclerView.ViewHolder(root)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentAdapter.MyViewHolder {
        // create a new view
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_entry, parent, false) as LinearLayout

        // set the view's size, margins, paddings and layout parameters
        // ...

        return MyViewHolder(itemView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.root.title.text = myDataset[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}