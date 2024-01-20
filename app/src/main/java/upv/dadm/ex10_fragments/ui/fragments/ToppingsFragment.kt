/*
 * Copyright (c) 2022-2023 Universitat Politècnica de València
 * Authors: David de Andrés and Juan Carlos Ruiz
 *          Fault-Tolerant Systems
 *          Instituto ITACA
 *          Universitat Politècnica de València
 *
 * Distributed under MIT license
 * (See accompanying file LICENSE.txt)
 */

package upv.dadm.ex10_fragments.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import upv.dadm.ex10_fragments.R
import upv.dadm.ex10_fragments.databinding.FragmentToppingsBinding
import upv.dadm.ex10_fragments.ui.viewmodels.FroyoViewModel

/**
 * Displays a screen that lets the user select the toppings for the Froyo.
 * The user can proceed to select the desired sauce or cancel the order.
 */
class ToppingsFragment : Fragment(R.layout.fragment_toppings) {

    /**
     * Defines the methods the Activity must implement to proceed to the next screen or
     * go back to the welcome screen.
     */
    interface ToppingsCallback {
        fun onToppingsNextClicked()
        fun onToppingsCancelClicked()
    }

    // Reference to a ViewModel shared between Fragments
    private val viewModel: FroyoViewModel by activityViewModels()

    // Backing property to resource binding
    private var _binding: FragmentToppingsBinding? = null

    //Property valid between onCreateView() and onDestroyView()
    private val binding
        get() = _binding!!

    // Reference to the interface implementation
    private lateinit var callback: ToppingsCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Get a reference to the interface implementation
        callback = context as ToppingsCallback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get the automatically generated view binding for the layout resource
        _binding = FragmentToppingsBinding.bind(view)
        // Set the topping of the custom Froyo to strawberries
        binding.rbStrawberries.setOnClickListener {
            setTopping(binding.rbStrawberries.text.toString())
        }
        // Set the topping of the custom Froyo to kiwi
        binding.rbKiwi.setOnClickListener {
            setTopping(binding.rbKiwi.text.toString())
        }
        // Set the topping of the custom Froyo to almonds
        binding.rbAlmonds.setOnClickListener {
            setTopping(binding.rbAlmonds.text.toString())
        }
        // Set the topping of the custom Froyo to oreo
        binding.rbOreo.setOnClickListener {
            setTopping(binding.rbOreo.text.toString())
        }

        // Cancel the order and navigate to the Welcome screen
        binding.bToppingsCancel.setOnClickListener { cancel() }
        // Navigate to SauceFragment for the user to select the sauce of the Froyo
        binding.bToppingsNext.setOnClickListener { selectSauce() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.froyoUiState.collect { froyo ->
                    // Set the selected topping according to the state in the ViewModel
                    when (froyo.topping) {
                        getString(R.string.strawberries) -> binding.rbStrawberries.isChecked = true
                        getString(R.string.kiwi) -> binding.rbKiwi.isChecked = true
                        getString(R.string.almonds) -> binding.rbAlmonds.isChecked = true
                        getString(R.string.oreo) ->
                            binding.rbOreo.isChecked = true
                    }
                    // Enable the Button to proceed to the next screen when a topping has been selected
                    binding.bToppingsNext.isEnabled = froyo.topping.isNotEmpty()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear resources to make them eligible for garbage collection
        _binding = null
    }

    /**
     * Updates the topping of the selected Froyo in the ViewModel.
     */
    private fun setTopping(topping: String) = viewModel.setTopping(topping)

    /**
     * Notifies the activity it must navigate to the screen for sauce selection.
     */
    private fun selectSauce() = callback.onToppingsNextClicked()

    /**
     * Notifies the activity it must navigate to the welcome screen.
     */
    private fun cancel() {
        callback.onToppingsCancelClicked()
    }
}