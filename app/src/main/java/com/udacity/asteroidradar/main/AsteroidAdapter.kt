package com.udacity.asteroidradar.main

/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.ItemBinding

class AsteroidAdapter(val onClickListener: OnClickListener) : androidx.recyclerview.widget.ListAdapter<Asteroid, AsteroidAdapter.AsteroidViewHolder>(DiffCallback) {
    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }
        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }
    class AsteroidViewHolder(private var binding: ItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(property: Asteroid) {
            binding.asteroid = property
            binding.executePendingBindings()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidAdapter.AsteroidViewHolder {
        return AsteroidViewHolder(ItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AsteroidAdapter.AsteroidViewHolder, position: Int) {
        val Property = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(Property)
        }
        holder.bind(Property)
    }
    class OnClickListener(val clickListener: (Property: Asteroid) -> Unit) {
        fun onClick(Property: Asteroid) = clickListener(Property)
    }
}


