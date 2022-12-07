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
import upv.dadm.ex10_fragments.databinding.FragmentSauceBinding
import upv.dadm.ex10_fragments.ui.viewmodels.FroyoViewModel

/**
 * Displays a screen that lets the user select the sauce for the Froyo.
 * The user can proceed to the checkout or cancel the order.
 */
class SauceFragment : Fragment() {

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

    // Reference to resource binding
    private var binding: FragmentSauceBinding? = null

    // Reference to the interface implementation
    private lateinit var callback: SauceCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Get the automatically generated view binding for the layout resource
        val fragmentBinding = FragmentSauceBinding.inflate(layoutInflater)

        // Set the sauce of the custom Froyo to chocolate
        fragmentBinding.rbChocolate.setOnClickListener {
            setSauce(fragmentBinding.rbChocolate.text.toString())
        }
        // Set the sauce of the custom Froyo to forest fruits
        fragmentBinding.rbFruit.setOnClickListener {
            setSauce(fragmentBinding.rbFruit.text.toString())
        }
        // Set the sauce of the custom Froyo to honey
        fragmentBinding.rbHoney.setOnClickListener {
            setSauce(fragmentBinding.rbHoney.text.toString())
        }
        // Set the sauce of the custom Froyo to mango
        fragmentBinding.rbMango.setOnClickListener {
            setSauce(fragmentBinding.rbMango.text.toString())
        }

        // Cancel the order and navigate to the Welcome screen
        fragmentBinding.bSauceCancel.setOnClickListener { cancel() }
        // Navigate to CheckoutFragment for the user to submit the order
        fragmentBinding.bSauceNext.setOnClickListener { proceedToCheckout() }

        // Set the selected sauce according to the state in the ViewModel
        viewModel.sauce.observe(viewLifecycleOwner) { sauce ->
            when (sauce) {
                getString(R.string.chocolate) -> fragmentBinding.rbChocolate.isChecked = true
                getString(R.string.fruits) -> fragmentBinding.rbFruit.isChecked = true
                getString(R.string.honey) -> fragmentBinding.rbHoney.isChecked = true
                getString(R.string.mango) -> fragmentBinding.rbMango.isChecked = true
            }
        }
        // Enable the Button to proceed to the next screen when a sauce has been selected
        viewModel.sauceSelected.observe(viewLifecycleOwner) { enabled ->
            fragmentBinding.bSauceNext.isEnabled = enabled
        }

        // Hold a reference to resource binding for later use
        binding = fragmentBinding
        // Return the root element of the generated view
        return fragmentBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Get a reference to the interface implementation
        callback = context as SauceCallback
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear resources to make them eligible for garbage collection
        binding = null
    }

    /**
     * Updates the sauce of the selected Froyo in the ViewModel.
     */
    private fun setSauce(sauce: String) {
        viewModel.setSauce(sauce)
    }

    /**
     * Notifies the activity it must navigate to the screen for checkout.
     */
    private fun proceedToCheckout() {
        callback.onSauceNextClicked()
    }

    /**
     * Clears the state in the ViewModel and
     * notifies the activity it must navigate to the welcome screen.
     */
    private fun cancel() {
        viewModel.resetOrder()
        callback.onSauceCancelClicked()
    }

}