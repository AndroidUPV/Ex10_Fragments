/*
 * Copyright (c) 2022-2024 Universitat Politècnica de València
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
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import upv.dadm.ex10_fragments.R
import upv.dadm.ex10_fragments.databinding.FragmentCheckoutBinding
import upv.dadm.ex10_fragments.ui.viewmodels.CancelOrderViewModel
import upv.dadm.ex10_fragments.ui.viewmodels.FroyoViewModel

/**
 * Displays a screen that lets the user submit or cancel the order.
 */
class CheckoutFragment : Fragment(R.layout.fragment_checkout) {

    // Reference to a ViewModel shared with the ConfirmationDialogFragment
    private val cancelOrderViewModel: CancelOrderViewModel by activityViewModels()

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.froyoUiState.collect { froyo ->
                    // Display the selected size, topping, and sauce according to the state in the ViewModel
                    binding.tvCheckoutSize.text = getString(R.string.checkout_size, froyo.size)
                    binding.tvCheckoutTopping.text =
                        getString(R.string.checkout_toppings, froyo.topping)
                    binding.tvCheckoutSauce.text = getString(R.string.checkout_sauce, froyo.sauce)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                cancelOrderViewModel.cancelOrder.collect { cancel ->
                    if (cancel) cancel()
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
     * Clears the state in the ViewModel and
     * notifies the activity it must navigate to the welcome screen.
     * It has the same effect as cancel(), but this is supposed to actually submit the order.
     */
    private fun submitOrder() {
        Toast.makeText(requireContext(), R.string.checked_out, Toast.LENGTH_SHORT).show()
        callback.onCheckoutSubmitClicked()
    }

    /**
     * Clears the state in the ViewModel,
     * resets the cancel state and
     * notifies the activity it must navigate to the welcome screen.
     */
    private fun cancel() {
        cancelOrderViewModel.resetCancel()
        callback.onCheckoutCancelClicked()
    }

    /**
     * Displays a dialog asking the user for confirmation before cancelling the order.
     */
    private fun displayConfirmationDialog() =
        ConfirmationDialogFragment().show(childFragmentManager, null)
}