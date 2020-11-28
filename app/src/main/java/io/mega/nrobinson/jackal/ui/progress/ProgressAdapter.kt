package io.mega.nrobinson.jackal.ui.progress

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.mega.nrobinson.jackal.R
import io.mega.nrobinson.jackal.api.model.*

data class ProgressViewHolder(val v: View): RecyclerView.ViewHolder(v) {
    private val progressType = itemView.findViewById(R.id.progress_type) as ImageView
    private val progressName = itemView.findViewById(R.id.progress_name) as TextView
    private val progressPercent = itemView.findViewById(R.id.progress_percent) as TextView
    fun bind(progress: Progress) {
        when (progress) {
            is TorrentProgress -> {
                progressType.setImageResource(R.drawable.ic_cloud_download_black_48dp)
                progressName.text = progress.name
                progressPercent.text = itemView.context.getString(
                    R.string.percentage,
                    (progress.current.toDouble() / progress.total) * 100)
            }
            is Pending -> {
                progressType.setImageResource(R.drawable.ic_cloud_queue_black_48dp)
                progressName.text = progress.name
                progressPercent.text = ""
            }
            is Calculating -> {
                progressType.setImageResource(R.drawable.ic_cloud_black_48dp)
                progressName.text = progress.name
                progressPercent.text = ""
            }
            is FTPProgress -> {
                progressType.setImageResource(R.drawable.ic_cloud_upload_black_48dp)
                progressName.text = progress.name
                progressPercent.text = itemView.context.getString(
                    R.string.percentage,
                    (progress.current.toDouble() / progress.total) * 100)
            }
            is Done -> {
                progressType.setImageResource(R.drawable.ic_cloud_done_black_48dp)
                progressName.text = progress.name
                progressPercent.text = ""
            }
        }
    }
}

class ProgressAdapter: ListAdapter<Progress, ProgressViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.progress_cell, parent, false)
        return ProgressViewHolder(v)
    }

    override fun onBindViewHolder(holder: ProgressViewHolder, position: Int) =
        holder.bind(getItem(position))

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Progress>() {
            override fun areContentsTheSame(
                oldItem: Progress,
                newItem: Progress
            ): Boolean = oldItem == newItem

            override fun areItemsTheSame(
                oldItem: Progress,
                newItem: Progress
            ): Boolean = oldItem.name == newItem.name
        }
    }
}