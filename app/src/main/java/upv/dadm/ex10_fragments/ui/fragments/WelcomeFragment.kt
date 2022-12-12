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
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import upv.dadm.ex10_fragments.R
import upv.dadm.ex10_fragments.databinding.FragmentWelcomeBinding
import upv.dadm.ex10_fragments.ui.activities.MainActivity

const val USERNAME = "upv.dadm.ex10_fragments.ui.fragments.WelcomeFragment.USERNAME"

/**
 * Displays a welcome screen that gives access to customize an order.
 */
class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    // Backing property to resource binding
    private var _binding: FragmentWelcomeBinding? = null

    // Property valid between onCreateView() and onDestroyView()
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Register a callback for finishing the app when Back is pressed.
        // Otherwise, WelcomeFragment will be popped from the BackStack and
        // we will end up with a blank activity.
        requireActivity().onBackPressedDispatcher.addCallback(
            this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Get the automatically generated view binding for the layout resource
        _binding = FragmentWelcomeBinding.inflate(layoutInflater)
        // Return the root element of the generated view
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
    private fun navigateToSizeSelection() {
        (requireActivity() as MainActivity).navigateToSize()
    }
}