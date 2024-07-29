package com.example.donghobamgio.view

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.donghobamgio.R
import com.example.donghobamgio.viewmodel.TimerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonAdd: Button
    private lateinit var buttonRemove: Button
    private lateinit var buttonStartAll: Button
    private lateinit var buttonPauseAll: Button
    private lateinit var buttonResetAll: Button
    private val viewModels = mutableListOf<TimerViewModel>()
    private lateinit var adapter: TimerAdapter

    override val coroutineContext = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        buttonAdd = findViewById(R.id.buttonAdd)
        buttonRemove = findViewById(R.id.buttonRemove)
        buttonStartAll = findViewById(R.id.buttonStartAll)
        buttonPauseAll = findViewById(R.id.buttonPauseAll)
        buttonResetAll = findViewById(R.id.buttonResetAll)

        adapter = TimerAdapter(viewModels, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        buttonAdd.setOnClickListener {
            addNewTimer()
        }

        buttonRemove.setOnClickListener {
            removeLastTimer()
        }

        buttonStartAll.setOnClickListener {
            startAllTimers()
        }

        buttonPauseAll.setOnClickListener {
            pauseAllTimers()
        }

        buttonResetAll.setOnClickListener {
            resetAllTimers()
        }
    }

    private fun addNewTimer() {
        val viewModel = TimerViewModel()
        viewModels.add(viewModel)
        adapter.notifyItemInserted(viewModels.size - 1)
    }

    private fun removeLastTimer() {
        if (viewModels.isNotEmpty()) {
            viewModels.removeAt(viewModels.size - 1)
            adapter.notifyItemRemoved(viewModels.size)
        }
    }

    private fun startAllTimers() {
        launch {
            for (viewModel in viewModels) {
                viewModel.start()
            }
        }
    }

    private fun pauseAllTimers() {
        launch {
            for (viewModel in viewModels) {
                viewModel.pause()
            }
        }
    }

    private fun resetAllTimers() {
        launch {
            for (viewModel in viewModels) {
                viewModel.reset()
            }
        }
    }
}
