package com.example.inventory

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.data.Item
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.android.synthetic.main.list_item.*

class ItemAdapter internal constructor(context: Context, listener: OnItemClickListener) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    private var mItem: ArrayList<Item> = ArrayList()
    val mListener: OnItemClickListener = listener
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            inflater.inflate(R.layout.list_item, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = mItem[position]
        holder.productName.text = currentItem.name
        holder.productSupplier.text = currentItem.supplier
        holder.productQuantity.text = currentItem.quantity
        holder.productPrice.text = currentItem.price
        holder.productImage.setImageURI(Uri.parse(currentItem.imageUri))
        holder.bind(mItem[position], mListener)
    }

    override fun getItemCount(): Int {
        return mItem.size
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.product_image
        val productName: TextView = itemView.product_name
        val productSupplier: TextView = itemView.product_supplier
        val productQuantity: TextView = itemView.product_quantity
        val productPrice: TextView = itemView.product_price

        fun bind(item: Item, listener: OnItemClickListener) {
            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View) {
                    listener.onItemClick(item)
                }
            })
        }
    }

    fun setData(item: ArrayList<Item>) {
        mItem.addAll(item)
        notifyDataSetChanged()
    }
}