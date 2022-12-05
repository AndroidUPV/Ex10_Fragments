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
import upv.dadm.ex10_fragments.databinding.FragmentToppingsBinding
import upv.dadm.ex10_fragments.ui.activities.MainActivity
import upv.dadm.ex10_fragments.ui.viewmodels.FroyoViewModel

/**
 * Displays a screen that lets the user select the toppings for the Froyo.
 * The user can proceed to select the desired sauce or cancel the order.
 */
class ToppingsFragment : Fragment() {

    // Reference to a ViewModel shared between Fragments
    private val viewModel: FroyoViewModel by activityViewModels()

    // Reference to resource binding
    private var binding: FragmentToppingsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Get the automatically generated view binding for the layout resource
        val fragmentBinding = FragmentToppingsBinding.inflate(layoutInflater)

        // Set the topping of the custom Froyo to strawberries
        fragmentBinding.rbStrawberries.setOnClickListener {
            setTopping(fragmentBinding.rbStrawberries.text.toString())
        }
        // Set the topping of the custom Froyo to kiwi
        fragmentBinding.rbKiwi.setOnClickListener {
            setTopping(fragmentBinding.rbKiwi.text.toString())
        }
        // Set the topping of the custom Froyo to almonds
        fragmentBinding.rbAlmonds.setOnClickListener {
            setTopping(fragmentBinding.rbAlmonds.text.toString())
        }
        // Set the topping of the custom Froyo to oreo
        fragmentBinding.rbOreo.setOnClickListener {
            setTopping(fragmentBinding.rbOreo.text.toString())
        }

        // Cancel the order and navigate to the Welcome screen
        fragmentBinding.bToppingsCancel.setOnClickListener { cancel() }
        // Navigate to SauceFragment for the user to select the sauce of the Froyo
        fragmentBinding.bToppingsNext.setOnClickListener { selectSauce() }

        // Set the selected topping according to the state in the ViewModel
        viewModel.topping.observe(viewLifecycleOwner) { topping ->
            when (topping) {
                getString(R.string.strawberries) -> fragmentBinding.rbStrawberries.isChecked = true
                getString(R.string.kiwi) -> fragmentBinding.rbKiwi.isChecked = true
                getString(R.string.almonds) -> fragmentBinding.rbAlmonds.isChecked = true
                getString(R.string.oreo) ->
                    fragmentBinding.rbOreo.isChecked = true
            }
        }
        // Enable the Button to proceed to the next screen when a topping has been selected
        viewModel.toppingSelected.observe(viewLifecycleOwner) { enabled ->
            fragmentBinding.bToppingsNext.isEnabled = enabled
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
     * Updates the topping of the selected Froyo in the ViewModel.
     */
    private fun setTopping(topping: String) {
        viewModel.setTopping(topping)
    }

    /**
     * Notifies the activity it must navigate to the screen for sauce selection.
     */
    private fun selectSauce() {
        (requireActivity() as MainActivity).navigateToSauce()
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