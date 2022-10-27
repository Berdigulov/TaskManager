package com.example.taskmanager.ui.onBoard

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.taskmanager.R
import com.example.taskmanager.data.OnBoard
import com.example.taskmanager.data.loadImage
import com.example.taskmanager.databinding.ItemOnboardBinding

class OnBoardingAdapter(private val onClick:() -> Unit): RecyclerView.Adapter<OnBoardingAdapter.OnBoardingViewHolder>() {

    private val array = arrayListOf(
        OnBoard("Notes", "Here you can write whatever you want, your dream or fantasies", R.raw.anim_note),
        OnBoard("Convenient", "With this app you can easy write something you want", R.raw.anim_task),
        OnBoard("Pleasant interface", "Very simple interface for writing notes even you grandparents can use it easily", R.raw.anim_fin)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnBoardingViewHolder {
        return OnBoardingViewHolder(ItemOnboardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: OnBoardingViewHolder, position: Int) {
        holder.bind(array[position])
    }

    override fun getItemCount(): Int {
        return array.size
    }
    inner class OnBoardingViewHolder(private val binding: ItemOnboardBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(onBoard: OnBoard) {
            val item = array[adapterPosition]
            binding.tvTitle.text = onBoard.title
            binding.tvDescription.text = onBoard.desc
            binding.ivBoard.setAnimation(onBoard.image!!)
            binding.btnStart.isVisible = adapterPosition == array.lastIndex
            if(adapterPosition != array.lastIndex){
                binding.tvSkip.visibility = View.VISIBLE
            }else
                binding.tvSkip.visibility = View.INVISIBLE
            binding.btnStart.setOnClickListener {
                onClick()
                Log.d("dad","click")
            }
            binding.tvSkip.setOnClickListener {
                onClick()
            }
        }

    }
}