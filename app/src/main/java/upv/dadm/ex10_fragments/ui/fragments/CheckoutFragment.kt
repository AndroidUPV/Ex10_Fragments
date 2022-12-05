/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package upv.dadm.ex10_fragments.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import upv.dadm.ex10_fragments.R
import upv.dadm.ex10_fragments.databinding.FragmentCheckoutBinding
import upv.dadm.ex10_fragments.ui.activities.MainActivity
import upv.dadm.ex10_fragments.ui.viewmodels.FroyoViewModel

/**
 * Displays a screen that lets the user submit or cancel the order.
 */
class CheckoutFragment : Fragment() {

    // Reference to a ViewModel shared between Fragments
    private val viewModel: FroyoViewModel by activityViewModels()

    // Reference to resource binding
    private var binding: FragmentCheckoutBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Get the automatically generated view binding for the layout resource
        val fragmentBinding = FragmentCheckoutBinding.inflate(layoutInflater)

        // Cancel the order and navigate to the Welcome screen
        fragmentBinding.bCancel.setOnClickListener { cancel() }
        // Submit the order and navigate to the Welcome screen
        fragmentBinding.bSubmit.setOnClickListener { submitOrder() }

        // Display the selected size according to the state in the ViewModel
        viewModel.size.observe(viewLifecycleOwner) { size ->
            fragmentBinding.tvCheckoutSize.text = getString(R.string.checkout_size, size)
        }
        // Display the selected topping according to the state in the ViewModel
        viewModel.topping.observe(viewLifecycleOwner) { topping ->
            fragmentBinding.tvCheckoutTopping.text = topping
        }
        // Display the selected sauce according to the state in the ViewModel
        viewModel.sauce.observe(viewLifecycleOwner) { sauce ->
            fragmentBinding.tvCheckoutSauce.text = sauce
        }

        // Hold a reference to resource binding for later use
        binding = fragmentBinding
        // Return the root element of the generated view
        return fragmentBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear resources to make them eligible for garbage collection
        binding = null
    }

    /**
     * Clears the state in the ViewModel and
     * notifies the activity it must navigate to the welcome screen.
     * It has the same effect as cancel(), but this is supposed to actually submit the order.
     */
    private fun submitOrder() {
        viewModel.resetOrder()
        (requireActivity() as MainActivity).navigateToWelcome()
    }

    /**
     * Clears the state in the ViewModel and
     * notifies the activity it must navigate to the welcome screen.
     */
    private fun cancel() {
        viewModel.resetOrder()
        (requireActivity() as MainActivity).navigateToWelcome()
    }

}