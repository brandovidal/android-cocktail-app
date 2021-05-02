package com.brandovidal.tragosapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.brandovidal.tragosapp.AppDatabase
import com.brandovidal.tragosapp.R
import com.brandovidal.tragosapp.data.DataSource
import com.brandovidal.tragosapp.data.model.Drink
import com.brandovidal.tragosapp.data.model.DrinkEntity
import com.brandovidal.tragosapp.domain.RepoImpl
import com.brandovidal.tragosapp.ui.viewmodel.MainViewModel
import com.brandovidal.tragosapp.ui.viewmodel.VMFactory
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.drinks_row.*
import kotlinx.android.synthetic.main.drinks_row.img_drink
import kotlinx.android.synthetic.main.fragment_drink_detail.*

class DrinkDetailFragment : Fragment() {
    private val viewModel by activityViewModels<MainViewModel>() { VMFactory(RepoImpl(DataSource(
        AppDatabase.getDatabase(requireActivity().applicationContext)))) }

    private lateinit var drink: Drink

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireArguments().let {
            drink = it.getParcelable("drink")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drink_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireContext()).load(drink.image).centerCrop().into(img_drink)
        drink_title.text = drink.name
        drink_description.text = drink.description
        if (drink.hasAlcohol.equals("Non_Alcoholic")) {
            drink_has_alcohol.text = "Bebida sin acohol"
        } else {
            drink_has_alcohol.text = "Bebida con acohol"
        }

        btn_save_drink.setOnClickListener {
            viewModel.saveDrink(
                DrinkEntity(
                    drink.drinkId,
                    drink.image,
                    drink.name,
                    drink.description,
                    drink.hasAlcohol
                )
            )

            Toast.makeText(requireContext(), "Se guardo el trago a fovoritos", Toast.LENGTH_SHORT)
                .show()
        }
    }

}