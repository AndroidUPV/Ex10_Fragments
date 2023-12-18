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
import upv.dadm.ex10_fragments.R
import upv.dadm.ex10_fragments.databinding.FragmentSauceBinding
import upv.dadm.ex10_fragments.ui.viewmodels.FroyoViewModel

/**
 * Displays a screen that lets the user select the sauce for the Froyo.
 * The user can proceed to the checkout or cancel the order.
 */
class SauceFragment : Fragment(R.layout.fragment_sauce) {

    /**
     * Defines the methods the Activity must implement to proceed to the next screen or
     * go back to the welcome screen.
     */
    interface SauceCallback {
        fun onSauceNextClicked()
        fun onSauceCancelClicked()
    }

    // Reference to a ViewModel shared between Fragments
    private val viewModel: FroyoViewModel by activityViewModels()

    // Backing property to resource binding
    private var _binding: FragmentSauceBinding? = null

    // Property valid between onCreateView() and onDestroyView()
    private val binding
        get() = _binding!!

    // Reference to the interface implementation
    private lateinit var callback: SauceCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Get a reference to the interface implementation
        callback = context as SauceCallback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get the automatically generated view binding for the layout resource
        _binding = FragmentSauceBinding.bind(view)
        // Set the sauce of the custom Froyo to chocolate
        binding.rbChocolate.setOnClickListener {
            setSauce(binding.rbChocolate.text.toString())
        }
        // Set the sauce of the custom Froyo to forest fruits
        binding.rbFruit.setOnClickListener {
            setSauce(binding.rbFruit.text.toString())
        }
        // Set the sauce of the custom Froyo to honey
        binding.rbHoney.setOnClickListener {
            setSauce(binding.rbHoney.text.toString())
        }
        // Set the sauce of the custom Froyo to mango
        binding.rbMango.setOnClickListener {
            setSauce(binding.rbMango.text.toString())
        }

        // Cancel the order and navigate to the Welcome screen
        binding.bSauceCancel.setOnClickListener { cancel() }
        // Navigate to CheckoutFragment for the user to submit the order
        binding.bSauceNext.setOnClickListener { proceedToCheckout() }

        // Set the selected sauce according to the state in the ViewModel
        viewModel.sauce.observe(viewLifecycleOwner) { sauce ->
            when (sauce) {
                getString(R.string.chocolate) -> binding.rbChocolate.isChecked = true
                getString(R.string.fruits) -> binding.rbFruit.isChecked = true
                getString(R.string.honey) -> binding.rbHoney.isChecked = true
                getString(R.string.mango) -> binding.rbMango.isChecked = true
            }
        }
        // Enable the Button to proceed to the next screen when a sauce has been selected
        viewModel.sauceSelected.observe(viewLifecycleOwner) { enabled ->
            binding.bSauceNext.isEnabled = enabled
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear resources to make them eligible for garbage collection
        _binding = null
    }

    /**
     * Updates the sauce of the selected Froyo in the ViewModel.
     */
    private fun setSauce(sauce: String) = viewModel.setSauce(sauce)

    /**
     * Notifies the activity it must navigate to the screen for checkout.
     */
    private fun proceedToCheckout() = callback.onSauceNextClicked()

    /**
     * Clears the state in the ViewModel and
     * notifies the activity it must navigate to the welcome screen.
     */
    private fun cancel() {
        viewModel.resetOrder()
        callback.onSauceCancelClicked()
    }

}