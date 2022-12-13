/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package upv.dadm.ex10_fragments.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import upv.dadm.ex10_fragments.R
import upv.dadm.ex10_fragments.databinding.FragmentCheckoutBinding
import upv.dadm.ex10_fragments.ui.viewmodels.FroyoViewModel

/**
 * Displays a screen that lets the user submit or cancel the order.
 */
class CheckoutFragment : Fragment(R.layout.fragment_checkout),
    ConfirmationDialogFragment.ConfirmationDialogCallback {

    /**
     * Defines the methods the Activity must implement to proceed to the next screen or
     * go back to the welcome screen.
     */
    interface CheckoutCallback {
        fun onCheckoutSubmitClicked()
        fun onCheckoutCancelClicked()
    }

    // Reference to a ViewModel shared between Fragments
    private val viewModel: FroyoViewModel by activityViewModels()

    // Backing property to resource binding
    private var _binding: FragmentCheckoutBinding? = null

    // Property valid between onCreateView() and onDestroyView()
    private val binding
        get() = _binding!!

    // Reference to the interface implementation
    private lateinit var callback: CheckoutCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Get a reference to the interface implementation
        callback = context as CheckoutCallback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get the automatically generated view binding for the layout resource
        _binding = FragmentCheckoutBinding.bind(view)
        // Display a dialog to ask the user for confirmation before cancelling the order
        binding.bCancel.setOnClickListener { displayConfirmationDialog() }
        // Submit the order and navigate to the Welcome screen
        binding.bSubmit.setOnClickListener { submitOrder() }

        // Display the selected size according to the state in the ViewModel
        viewModel.size.observe(viewLifecycleOwner) { size ->
            binding.tvCheckoutSize.text = getString(R.string.checkout_size, size)
        }
        // Display the selected topping according to the state in the ViewModel
        viewModel.topping.observe(viewLifecycleOwner) { topping ->
            binding.tvCheckoutTopping.text = getString(R.string.checkout_toppings, topping)
        }
        // Display the selected sauce according to the state in the ViewModel
        viewModel.sauce.observe(viewLifecycleOwner) { sauce ->
            binding.tvCheckoutSauce.text = getString(R.string.checkout_sauce, sauce)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear resources to make them eligible for garbage collection
        _binding = null
    }

    /**
     * Clears the state in the ViewModel and
     * notifies the activity it must navigate to the welcome screen.
     * It has the same effect as cancel(), but this is supposed to actually submit the order.
     */
    private fun submitOrder() {
        viewModel.resetOrder()
        callback.onCheckoutSubmitClicked()
    }

    /**
     * Clears the state in the ViewModel and
     * notifies the activity it must navigate to the welcome screen.
     */
    private fun cancel() {
        viewModel.resetOrder()
        callback.onCheckoutCancelClicked()
    }

    /**
     * Displays a dialog asking the user for confirmation before cancelling the order.
     */
    private fun displayConfirmationDialog() =
        ConfirmationDialogFragment().show(childFragmentManager, null)

    // Implements the ConfirmationDialogCallback to deal with order cancellation
    override fun onCancelOrder() = cancel()

    // Implements the ConfirmationDialogCallback for the user to keep the order
    override fun onDoNotCancelOrder() {
        // Do nothing, as the dialog is automatically dismissed
    }
}