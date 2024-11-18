package edu.ucam.myapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.ucam.myapp.Activity.DetailActivity
import edu.ucam.myapp.Model.ItemsModel
import edu.ucam.myapp.databinding.ViewholderPopularBinding

class PopularAdpater(val items: MutableList<ItemsModel>) : RecyclerView.Adapter<PopularAdpater.Viewholder>() {
    private var context: Context? = null

    class Viewholder(val binding: ViewholderPopularBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularAdpater.Viewholder {
        context = parent.context
        val binding = ViewholderPopularBinding.inflate(LayoutInflater.from(context), parent, false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: PopularAdpater.Viewholder, position: Int) {
        holder.binding.ratingBar.rating = items[position].rating.toFloat()
        holder.binding.titleTxt.text = items[position].title
        holder.binding.extraTxt.text = items[position].extra
        holder.binding.priceTxt.text = items[position].price.toString() + "€"

        Glide.with(holder.itemView.context)
            .load(items[position].picUrl[0])
            .into(holder.binding.pic)

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("object", items[position])
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}