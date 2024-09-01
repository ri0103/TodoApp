package app.ishizaki.dragon.todoapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Recycler
import app.ishizaki.dragon.todoapp.databinding.ActivityMainBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var adapter: RecyclerViewAdapter

    private val list: MutableList<TodoData> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RecyclerViewAdapter(this)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        binding.addButton.setOnClickListener {
            val todo = binding.taskEditText.text.toString()
            val format = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
            val date = format.format(LocalDateTime.now()).toLong()
            list.add(TodoData(date, todo))
            adapter.submitList(list.toMutableList())
        }

        adapter.checkEvent.observe(this){ id ->
            list.removeIf{it.id == id}
            adapter.submitList(list.toMutableList())
        }

    }
}