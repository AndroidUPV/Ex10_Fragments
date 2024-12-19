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
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import upv.dadm.ex10_fragments.R
import upv.dadm.ex10_fragments.databinding.FragmentWelcomeBinding

const val USERNAME = "upv.dadm.ex10_fragments.ui.fragments.WelcomeFragment.USERNAME"

/**
 * Displays a welcome screen that gives access to customize an order.
 */
class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    /**
     * Defines the methods the Activity must implement to proceed to the next screen or
     * finish the app.
     */
    interface WelcomeCallback {
        fun onWelcomeNextClicked()
        fun onWelcomeBackClicked()
    }

    // Backing property to resource binding
    private var _binding: FragmentWelcomeBinding? = null

    // Property valid between onCreateView() and onDestroyView()
    private val binding
        get() = _binding!!

    // Reference to the interface implementation
    private lateinit var callback: WelcomeCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Get a reference to the interface implementation
        callback = context as WelcomeCallback

        // Register a callback for finishing the app when Back is pressed.
        // Otherwise, WelcomeFragment will be popped from the BackStack and
        // we will end up with a blank activity.
        requireActivity().onBackPressedDispatcher.addCallback(
            this@WelcomeFragment, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    callback.onWelcomeBackClicked()
                }
            })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get the automatically generated view binding for the layout resource
        _binding = FragmentWelcomeBinding.bind(view)
        // Retrieve the received arguments to personalise the welcome message for the user
        binding.tvWelcome.text =
            getString(R.string.welcome, requireArguments().getString(USERNAME))
        // Navigate to SizeFragment for the user to select the size of the Froyo
        binding.bWelcomeNext.setOnClickListener {
            navigateToSizeSelection()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear resources to make them eligible for garbage collection
        _binding = null
    }

    /**
     * Notifies the activity it must navigate to the screen for size selection.
     */
    private fun navigateToSizeSelection() = callback.onWelcomeNextClicked()
}