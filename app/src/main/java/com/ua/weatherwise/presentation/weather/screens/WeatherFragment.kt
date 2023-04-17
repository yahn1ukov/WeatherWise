package com.ua.weatherwise.presentation.weather.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.ua.weatherwise.R
import com.ua.weatherwise.data.remote.models.entities.Weather
import com.ua.weatherwise.databinding.FragmentWeatherBinding
import com.ua.weatherwise.domain.models.NetworkResult
import com.ua.weatherwise.presentation.weather.viewmodels.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherFragment : Fragment(), SearchView.OnQueryTextListener {
    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var weatherViewModel: WeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        fetchWeather()

        binding.swipeRefreshLayout.setOnRefreshListener {
            fetchWeather()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.fragment_weather_menu, menu)

                    val search = menu.findItem(R.id.menu_search)
                    val searchView = search.actionView as SearchView
                    searchView.apply {
                        isSubmitButtonEnabled = true
                        setOnQueryTextListener(this@WeatherFragment)
                    }
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return true
                }

            }, viewLifecycleOwner, Lifecycle.State.RESUMED
        )
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        return true
    }

    private fun fetchWeather() {
        weatherViewModel.fetchWeatherByCoordinates()
        weatherViewModel.weather.observe(viewLifecycleOwner) { response ->
            handleUIState(response)
        }
    }

    private fun handleUIState(response: NetworkResult<Weather>) {
        when (response) {
            is NetworkResult.Success -> {
                hideProgressBar()
                response.data?.let { weather -> binding.weather = weather }
            }

            is NetworkResult.Error -> {
                hideProgressBar()
                binding.error = response
            }

            is NetworkResult.Loading -> {
                showProgressBar()
            }
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}