package com.example.app24

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app24.databinding.ActivityMainBinding

// App24: Room Database

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private val adapter = UserAdapter()
    private var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerUsers.layoutManager = LinearLayoutManager(applicationContext)
        binding.recyclerUsers.adapter = adapter

        val listener = object : OnUserListener {
            override fun OnClick(id: Int) {
                Toast.makeText(applicationContext, id.toString(), Toast.LENGTH_SHORT).show()
                viewModel.get(id)
            }
        }

        adapter.attachListener(listener)

        binding.buttonInsert.setOnClickListener {
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()

            viewModel.insert(username, password)
        }

        binding.buttonUpdate.setOnClickListener {
            val username = binding.editUsername.text.toString()
            val password = binding.editPassword.text.toString()

            viewModel.update(id, username, password)
        }

        binding.buttonDelete.setOnClickListener {
            viewModel.delete(id)
        }

        observe()
        viewModel.getAll()
    }

    private fun observe() {
        viewModel.users.observe(this) {
            adapter.updateUsers(it)
        }

        viewModel.user.observe(this) {
            id = it.id
            binding.textId.text = id.toString()
            binding.editUsername.setText(it.username)
            binding.editPassword.setText(it.password)
        }

        viewModel.newChange.observe(this) {
            viewModel.getAll()
        }
    }
}