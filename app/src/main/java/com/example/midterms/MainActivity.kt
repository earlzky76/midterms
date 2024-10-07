package com.example.midterms

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.midterms.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var taskAdapter: TaskAdapter
    private val tasks = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskAdapter = TaskAdapter(tasks)
        binding.recyclerView.adapter = taskAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                taskAdapter.removeItem(position)
                Snackbar.make(binding.recyclerView, "Task deleted", Snackbar.LENGTH_SHORT).show()
            }
        }
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(binding.recyclerView)

        binding.addTaskButton.setOnClickListener {
            val taskName = binding.inputTask.text.toString()
            if (taskName.isBlank()) {
                binding.inputTask.error = "Task name cannot be empty"
                return@setOnClickListener
            }

            val task = Task(tasks.size, taskName)
            tasks.add(task)
            taskAdapter.notifyItemInserted(tasks.size - 1)
            binding.inputTask.text.clear()
            Toast.makeText(this, "Task added", Toast.LENGTH_SHORT).show()
        }
    }
}
