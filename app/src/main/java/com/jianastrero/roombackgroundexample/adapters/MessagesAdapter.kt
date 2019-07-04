package com.jianastrero.roombackgroundexample.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.jianastrero.roombackgroundexample.R
import com.jianastrero.roombackgroundexample.databinding.ItemMessageBinding
import com.jianastrero.roombackgroundexample.models.Message
import java.text.SimpleDateFormat
import java.util.*

class MessagesAdapter(
    var messages: List<Message>
) : RecyclerView.Adapter<MessagesAdapter.ViewHolder>() {

    private val timeFormat = SimpleDateFormat("MMMM dd, yyyy hh:mm aa", Locale.US)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_message,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = messages.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = messages[holder.adapterPosition]
        holder.itemMessageBinding.name = message.username
        holder.itemMessageBinding.message = message.message
        holder.itemMessageBinding.time = timeFormat.format(message.time)
    }

    class ViewHolder(val itemMessageBinding: ItemMessageBinding) : RecyclerView.ViewHolder(itemMessageBinding.root)
}