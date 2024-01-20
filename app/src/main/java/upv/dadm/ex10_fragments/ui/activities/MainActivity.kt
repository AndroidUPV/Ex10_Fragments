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

package upv.dadm.ex10_fragments.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import upv.dadm.ex10_fragments.R
import upv.dadm.ex10_fragments.databinding.ActivityMainBinding
import upv.dadm.ex10_fragments.ui.fragments.CheckoutFragment
import upv.dadm.ex10_fragments.ui.fragments.SauceFragment
import upv.dadm.ex10_fragments.ui.fragments.SizeFragment
import upv.dadm.ex10_fragments.ui.fragments.ToppingsFragment
import upv.dadm.ex10_fragments.ui.fragments.USERNAME
import upv.dadm.ex10_fragments.ui.fragments.WelcomeFragment
import upv.dadm.ex10_fragments.ui.viewmodels.FroyoViewModel

// Constants to tag the existing Fragments in the FragmentTransactions
const val WELCOME = "upv.dadm.ex10_fragments.ui.activities.WELCOME"
const val SIZE = "upv.dadm.ex10_fragments.ui.activities.SIZE"
const val TOPPINGS = "upv.dadm.ex10_fragments.ui.activities.TOPPINGS"
const val SAUCE = "upv.dadm.ex10_fragments.ui.activities.SAUCE"
const val CHECKOUT = "upv.dadm.ex10_fragments.ui.activities.CHECKOUT"

/**
 * Let users customize their Froyo (size, toppings, and sauce) and place their order.
 */
class MainActivity : AppCompatActivity(),
    WelcomeFragment.WelcomeCallback,
    SizeFragment.SizeCallback,
    ToppingsFragment.ToppingsCallback,
    SauceFragment.SauceCallback,
    CheckoutFragment.CheckoutCallback {

    // Reference to a ViewModel shared with the fragments
    private val viewModel: FroyoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the automatically generated view binding for the layout resource
        val binding = ActivityMainBinding.inflate(layoutInflater)
        // Set the activity content to the root element of the generated view
        setContentView(binding.root)

        // FragmentManager automatically saves and restores Fragment over configuration changes
        if (savedInstanceState == null) {
            // Define a Bundle to pass the current user name to the welcome screen
            // This emulates that the user has actually logged in
            val bundle = bundleOf(USERNAME to "David")
            // Execute operations on a FragmentTransaction and commit it
            supportFragmentManager.commit {
                // Optimise animations
                setReorderingAllowed(true)
                // Replace the Fragment in the provide container with and instance of WelcomeFragment
                // with the provided arguments
                add(R.id.fcvMain, WelcomeFragment::class.java, bundle)
                // Add the transaction to the BackStack, so it can be reversed with the Back button
                addToBackStack(WELCOME)
            }
        }
    }

    // Implements the WelcomeFragmentCallback to manage the next Button click
    override fun onWelcomeNextClicked() = navigateToSize()

    // Implements the SizeFragmentCallback to manage the next Button click
    override fun onSizeNextClicked() = navigateToToppings()

    // Implements the ToppingsFragmentCallback to manage the next Button click
    override fun onToppingsNextClicked() = navigateToSauce()

    // Implements the SauceFragmentCallback to manage the next Button click
    override fun onSauceNextClicked() = navigateToCheckout()

    // Implements the CheckoutFragmentCallback to manage the submit Button click
    override fun onCheckoutSubmitClicked() = navigateToWelcome()

    // Implements the WelcomeFragmentCallback to manage the Back Button click
    override fun onWelcomeBackClicked() = finish()

    // Implements the SizeFragmentCallback to manage the cancel Button click
    override fun onSizeCancelClicked() = navigateToWelcome()

    // Implements the ToppingsFragmentCallback to manage the cancel Button click
    override fun onToppingsCancelClicked() = navigateToWelcome()

    // Implements the SauceFragmentCallback to manage the cancel Button click
    override fun onSauceCancelClicked() = navigateToWelcome()

    // Implements the CheckoutFragmentCallback to manage the cancel Button click
    override fun onCheckoutCancelClicked() = navigateToWelcome()

    /**
     * Navigates to the screen to select the size of the Froyo.
     */
    private fun navigateToSize() {
        // Execute operations on a FragmentTransaction and commit it
        supportFragmentManager.commit {
            // Optimise animations
            setReorderingAllowed(true)
            // Replace the Fragment in the provide container with and instance of SizeFragment
            replace(R.id.fcvMain, SizeFragment::class.java, null)
            // Add the transaction to the BackStack, so it can be reversed with the Back button
            addToBackStack(SIZE)
        }
    }

    /**
     * Navigates to the screen to select the toppings for the Froyo.
     */
    private fun navigateToToppings() {
        // Execute operations on a FragmentTransaction and commit it
        supportFragmentManager.commit {
            // Optimise animations
            setReorderingAllowed(true)
            // Replace the Fragment in the provide container with and instance of ToppingsFragment
            replace(R.id.fcvMain, ToppingsFragment::class.java, null)
            // Add the transaction to the BackStack, so it can be reversed with the Back button
            addToBackStack(TOPPINGS)
        }
    }

    /**
     * Navigates to the screen to select the sauce for the Froyo.
     */
    private fun navigateToSauce() {
        // Execute operations on a FragmentTransaction and commit it
        supportFragmentManager.commit {
            // Optimise animations
            setReorderingAllowed(true)
            // Replace the Fragment in the provide container with and instance of SauceFragment
            replace(R.id.fcvMain, SauceFragment::class.java, null)
            // Add the transaction to the BackStack, so it can be reversed with the Back button
            addToBackStack(SAUCE)
        }
    }

    /**
     * Navigates to the checkout screen to place the order.
     */
    private fun navigateToCheckout() {
        // Execute operations on a FragmentTransaction and commit it
        supportFragmentManager.commit {
            // Optimise animations
            setReorderingAllowed(true)
            // Replace the Fragment in the provide container with and instance of CheckoutFragment
            replace(R.id.fcvMain, CheckoutFragment::class.java, null)
            // Add the transaction to the BackStack, so it can be reversed with the Back button
            addToBackStack(CHECKOUT)
        }
    }

    /**
     * Navigates to the welcome screen.
     */
    private fun navigateToWelcome() {
        // Reset the order
        viewModel.resetOrder()
        // Pop transactions from the BackStack until reaching, and including, that labelled WELCOME
        supportFragmentManager.popBackStack(WELCOME, 0)
    }

}