package com.codennamdi.roomdemoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.codennamdi.roomdemoapp.databinding.ItemDetailBinding

class ItemDetailsAdapter(
    private val items: ArrayList<StudentEntity>,
    //private val deleteListener: (id: Int) -> Unit,
    //private val updateListener: (id: Int) -> Unit
) : RecyclerView.Adapter<ItemDetailsAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        val linearLayoutMain = binding.linearLayoutItem
        val nameTv = binding.textViewName
        val matricNumber = binding.textViewMatricNumb
        val emailTv = binding.textViewEmail
        val genderTv = binding.textViewGender
        val ageTv = binding.textViewAge
        val schoolNameTv = binding.textViewSchName
        val deleteImageView = binding.imageViewDelete
        val editImageView = binding.imageViewEdit
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context = holder.itemView.context
        val item = items[position]

        holder.nameTv.text = item.name
        holder.matricNumber.text = item.matricNumber.toString()
        holder.emailTv.text = item.email
        holder.genderTv.text = item.gender
        holder.ageTv.text = item.age.toString()
        holder.schoolNameTv.text = item.schoolName

        if (position % 2 == 0) {
            holder.linearLayoutMain.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.lightGrey
                )
            )
        } else {
            holder.linearLayoutMain.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                )
            )
        }

        holder.editImageView.setOnClickListener {
            //updateListener.invoke(item.id)
        }

        holder.deleteImageView.setOnClickListener {
            //deleteListener.invoke(item.id)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}