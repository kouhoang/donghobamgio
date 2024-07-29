package com.example.donghobamgio.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.donghobamgio.R
import com.example.donghobamgio.viewmodel.TimerViewModel
import kotlinx.coroutines.CoroutineScope

class TimerAdapter(
    private val viewModels: List<TimerViewModel>,
    private val coroutineScope: CoroutineScope
) : RecyclerView.Adapter<TimerAdapter.TimerViewHolder>() {

    inner class TimerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timerText: TextView = itemView.findViewById(R.id.timerText)
        private val buttonStartPause: Button = itemView.findViewById(R.id.buttonStartPause)
        private val buttonReset: Button = itemView.findViewById(R.id.buttonReset)

        fun bind(viewModel: TimerViewModel) {
            viewModel.timeLiveData.observeForever {
                timerText.text = it
            }

            viewModel.isRunning.observeForever { running ->
                if (running) {
                    buttonStartPause.setBackgroundResource(R.drawable.ic_pause)
                } else {
                    buttonStartPause.setBackgroundResource(R.drawable.ic_start)
                }
            }

            buttonStartPause.setOnClickListener {
                if (viewModel.isRunning.value == true) {
                    viewModel.pause()
                } else {
                    viewModel.start()
                }
            }

            buttonReset.setOnClickListener {
                viewModel.reset()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_timer, parent, false)
        return TimerViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimerViewHolder, position: Int) {
        holder.bind(viewModels[position])
    }

    override fun getItemCount(): Int = viewModels.size
}
