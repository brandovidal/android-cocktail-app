package com.brandovidal.tragosapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.brandovidal.tragosapp.R
import com.brandovidal.tragosapp.data.DataSource
import com.brandovidal.tragosapp.domain.RepoImpl
import com.brandovidal.tragosapp.ui.viewmodel.MainViewModel
import com.brandovidal.tragosapp.ui.viewmodel.VMFactory
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.brandovidal.tragosapp.AppDatabase
import com.brandovidal.tragosapp.data.model.Drink
import com.brandovidal.tragosapp.vo.Resource
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : Fragment(), MainAdapter.OnDrinkClickListener {
    private val viewModel by activityViewModels<MainViewModel>() { VMFactory(RepoImpl(DataSource(
        AppDatabase.getDatabase(requireActivity().applicationContext)))) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchView()
        setupObservers()
        onDrinkFavorites()
    }

    private fun setupObservers() {
        viewModel.fetchDrinkList.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    progressBar.visibility = View.GONE
                    rv_drinks.adapter = MainAdapter(requireContext(), result.data.toMutableList(), this)
                }
                is Resource.Failure -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Ocurrio un error al obtener los datos ${result.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
               viewModel.setDrink(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               return false
            }
        })
    }

    private fun onDrinkFavorites() {
        btn_go_favorite.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_favoriteFragment)
        }
    }

    override fun onDrinkClick(drink: Drink, position: Int) {
        val bundle = Bundle()
        bundle.putParcelable("drink", drink)

        findNavController().navigate(R.id.action_mainFragment_to_drinkDetailFragment, bundle)
    }

    private fun setupRecyclerView() {
        rv_drinks.layoutManager = LinearLayoutManager(requireContext())
        rv_drinks.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
    }
}