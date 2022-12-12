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
import upv.dadm.ex10_fragments.databinding.FragmentToppingsBinding
import upv.dadm.ex10_fragments.ui.viewmodels.FroyoViewModel

/**
 * Displays a screen that lets the user select the toppings for the Froyo.
 * The user can proceed to select the desired sauce or cancel the order.
 */
class ToppingsFragment : Fragment() {

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
    private val binding get() = _binding!!

    // Reference to the interface implementation
    private lateinit var callback: ToppingsCallback

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Get the automatically generated view binding for the layout resource
        _binding = FragmentToppingsBinding.inflate(layoutInflater)
        // Return the root element of the generated view
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Get a reference to the interface implementation
        callback = context as ToppingsCallback
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        // Set the selected topping according to the state in the ViewModel
        viewModel.topping.observe(viewLifecycleOwner) { topping ->
            when (topping) {
                getString(R.string.strawberries) -> binding.rbStrawberries.isChecked = true
                getString(R.string.kiwi) -> binding.rbKiwi.isChecked = true
                getString(R.string.almonds) -> binding.rbAlmonds.isChecked = true
                getString(R.string.oreo) ->
                    binding.rbOreo.isChecked = true
            }
        }
        // Enable the Button to proceed to the next screen when a topping has been selected
        viewModel.toppingSelected.observe(viewLifecycleOwner) { enabled ->
            binding.bToppingsNext.isEnabled = enabled
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
    private fun setTopping(topping: String) {
        viewModel.setTopping(topping)
    }

    /**
     * Notifies the activity it must navigate to the screen for sauce selection.
     */
    private fun selectSauce() {
        callback.onToppingsNextClicked()
    }

    /**
     * Clears the state in the ViewModel and
     * notifies the activity it must navigate to the welcome screen.
     */
    private fun cancel() {
        viewModel.resetOrder()
        callback.onToppingsCancelClicked()
    }
}