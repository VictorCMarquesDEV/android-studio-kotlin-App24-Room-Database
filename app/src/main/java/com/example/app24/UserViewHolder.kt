package com.example.app24

import androidx.recyclerview.widget.RecyclerView
import com.example.app24.databinding.RowUserBinding

class UserViewHolder(private val bind: RowUserBinding, private val listener: OnUserListener) :
    RecyclerView.ViewHolder(bind.root) {

    fun bind(user: UserModel) {
        bind.textUsername.text = user.username
        bind.textUsername.setOnClickListener {
            listener.OnClick(user.id)
        }
    }
}