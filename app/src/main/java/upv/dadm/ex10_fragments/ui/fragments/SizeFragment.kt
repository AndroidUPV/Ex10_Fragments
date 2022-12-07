/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package upv.dadm.ex10_fragments.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import upv.dadm.ex10_fragments.R
import upv.dadm.ex10_fragments.databinding.FragmentSizeBinding
import upv.dadm.ex10_fragments.ui.viewmodels.FroyoViewModel

/**
 * Displays a screen that lets the user select the size of the Froyo.
 * The user can proceed to select the desired toppings or cancel the order.
 */
class SizeFragment : Fragment() {

    /**
     * Defines the methods the Activity must implement to proceed to the next screen or
     * go back to the welcome screen.
     */
    interface SizeCallback {
        fun onSizeNextClicked()
        fun onSizeCancelClicked()
    }

    // Reference to a ViewModel shared between Fragments
    private val viewModel: FroyoViewModel by activityViewModels()

    // Reference to resource binding
    private var binding: FragmentSizeBinding? = null

    // Reference to the interface implementation
    private lateinit var callback: SizeCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Get the automatically generated view binding for the layout resource
        val fragmentBinding = FragmentSizeBinding.inflate(layoutInflater)

        // Set the size of the custom Froyo to small
        fragmentBinding.rbSmall.setOnClickListener {
            setSize(fragmentBinding.rbSmall.text.toString())
        }
        // Set the size of the custom Froyo to medium
        fragmentBinding.rbMedium.setOnClickListener {
            setSize(fragmentBinding.rbMedium.text.toString())
        }
        // Set the size of the custom Froyo to large
        fragmentBinding.rbLarge.setOnClickListener {
            setSize(fragmentBinding.rbLarge.text.toString())
        }
        // Set the size of the custom Froyo to extra large
        fragmentBinding.rbExtraLarge.setOnClickListener {
            setSize(fragmentBinding.rbExtraLarge.text.toString())
        }

        // Cancel the order and navigate to the Welcome screen
        fragmentBinding.bSizeCancel.setOnClickListener { cancel() }
        // Navigate to ToppingsFragment for the user to select the toppings of the Froyo
        fragmentBinding.bSizeNext.setOnClickListener { selectToppings() }

        // Set the selected size according to the state in the ViewModel
        viewModel.size.observe(viewLifecycleOwner) { size ->
            when (size) {
                getString(R.string.size_small) -> fragmentBinding.rbSmall.isChecked = true
                getString(R.string.size_medium) -> fragmentBinding.rbMedium.isChecked = true
                getString(R.string.size_large) -> fragmentBinding.rbLarge.isChecked = true
                getString(R.string.size_extra_large) ->
                    fragmentBinding.rbExtraLarge.isChecked = true
            }
        }
        // Enable the Button to proceed to the next screen when a size has been selected
        viewModel.sizeSelected.observe(viewLifecycleOwner) { enabled ->
            fragmentBinding.bSizeNext.isEnabled = enabled
        }

        // Hold a reference to resource binding for later use
        binding = fragmentBinding
        // Return the root element of the generated view
        return fragmentBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Get a reference to the interface implementation
        callback = context as SizeCallback
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear resources to make them eligible for garbage collection
        binding = null
    }

    /**
     * Updates the size of the selected Froyo in the ViewModel.
     */
    private fun setSize(size: String) {
        viewModel.setSize(size)
    }

    /**
     * Notifies the activity it must navigate to the screen for toppings selection.
     */
    private fun selectToppings() {
        //(requireActivity() as MainActivity).navigateToToppings()
        callback.onSizeNextClicked()
    }

    /**
     * Clears the state in the ViewModel and
     * notifies the activity it must navigate to the welcome screen.
     */
    private fun cancel() {
        viewModel.resetOrder()
        //(requireActivity() as MainActivity).navigateToWelcome()
        callback.onSizeCancelClicked()
    }
}