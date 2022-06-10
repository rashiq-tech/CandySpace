package com.appslet.candyspace.ui

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.appslet.candyspace.UserDetailActivity
import com.appslet.candyspace.databinding.RowItemHomeBinding
import com.appslet.candyspace.model.User
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class HomeRvAdapter constructor(val context: Context) :
    RecyclerView.Adapter<HomeRvAdapter.MainViewHolder>() {

    var userList = mutableListOf<User>()

    fun setHomePostList(posts: List<User>) {
        this.userList = posts.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowItemHomeBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val user = userList[position]
        Glide.with(holder.itemView.context)
            .load(user.profile_image)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.binding.ivUser)
        holder.binding.tvUserName.text = user.display_name

        holder.itemView.setOnClickListener{
            val intent = Intent(context, UserDetailActivity::class.java)
            intent.putExtra(UserDetailActivity.USER_TAG, Json.encodeToString(user))
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class MainViewHolder(
        val binding: RowItemHomeBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
    }


}

