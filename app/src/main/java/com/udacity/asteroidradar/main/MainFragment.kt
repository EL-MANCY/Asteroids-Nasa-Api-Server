package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java) }

    private val asteroidAdapter=AsteroidAdapter(AsteroidAdapter.OnClickListener { asteroid ->
        viewModel.sendAsteroidDetails(asteroid) })


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.asteroidRecycler.adapter=asteroidAdapter
        viewModel.FullList.observe(viewLifecycleOwner, Observer { asteroid ->
            asteroid.apply {
                asteroidAdapter.submitList(this )
            } })

        viewModel.asteroidDetailNavigated.observe(viewLifecycleOwner, Observer {
            it?.let {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.AsteroidDetailsNavigated()
            }
        })
        setHasOptionsMenu(true)
        return binding.root
    }







    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.filterChanged(
            when (item.itemId) {
                R.id.show_today_menu -> { FilterAsteroid.TODAY }
                R.id.show_week_menu -> { FilterAsteroid.THAT_WEEK }
                else -> { FilterAsteroid.ALL }
            }
        )
        return true
    }
}
