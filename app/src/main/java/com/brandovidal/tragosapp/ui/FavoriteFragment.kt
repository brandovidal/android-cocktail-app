package com.brandovidal.tragosapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.brandovidal.tragosapp.AppDatabase
import com.brandovidal.tragosapp.R
import com.brandovidal.tragosapp.data.DataSource
import com.brandovidal.tragosapp.data.model.Drink
import com.brandovidal.tragosapp.data.model.DrinkEntity
import com.brandovidal.tragosapp.domain.RepoImpl
import com.brandovidal.tragosapp.ui.viewmodel.MainViewModel
import com.brandovidal.tragosapp.ui.viewmodel.VMFactory
import com.brandovidal.tragosapp.vo.Resource
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_main.*

class FavoriteFragment : Fragment(), MainAdapter.OnDrinkClickListener {
    lateinit var adapter: MainAdapter

    private val viewModel by activityViewModels<MainViewModel>() {
        VMFactory(
            RepoImpl(
                DataSource(
                    AppDatabase.getDatabase(requireActivity().applicationContext)
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObservers()

    }

    private fun setupObservers() {
        viewModel.getDrinkFavorites().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    var list = result.data.map {
                        Drink(it.drinkId, it.image, it.name, it.description, it.hasAlcohol)
                    }.toMutableList()
                    adapter = MainAdapter(requireContext(), list, this)
                    rv_drink_favorites.adapter = adapter
                }
                is Resource.Failure -> {
                }
            }
        })
    }

    private fun setupRecyclerView() {
        rv_drink_favorites.layoutManager = LinearLayoutManager(requireContext())
        rv_drink_favorites.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }

    override fun onDrinkClick(drink: Drink, position: Int) {
        viewModel.deleteDrink(DrinkEntity(drink.drinkId, drink.name))
        adapter.deleteDrink(position)
        Toast.makeText(requireContext(), "Se borró el trago favorito", Toast.LENGTH_SHORT).show()
    }
}