/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package upv.dadm.ex10_fragments.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import upv.dadm.ex10_fragments.R
import upv.dadm.ex10_fragments.databinding.ActivityMainBinding
import upv.dadm.ex10_fragments.ui.fragments.*

// Constants to tag the existing Fragments in the FragmentTransactions
const val WELCOME = "upv.dadm.ex10_fragments.ui.activities.WELCOME"
const val SIZE = "upv.dadm.ex10_fragments.ui.activities.SIZE"
const val TOPPINGS = "upv.dadm.ex10_fragments.ui.activities.TOPPINGS"
const val SAUCE = "upv.dadm.ex10_fragments.ui.activities.SAUCE"
const val CHECKOUT = "upv.dadm.ex10_fragments.ui.activities.CHECKOUT"

/**
 * Let users customize their Froyo (size, toppings, and sauce) and place their order.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the automatically generated view binding for the layout resource
        val binding = ActivityMainBinding.inflate(layoutInflater)
        // Set the activity content to the root element of the generated view
        setContentView(binding.root)

        // Execute operations on a FragmentTransaction and commit it
        supportFragmentManager.commit {
            // Optimise animations
            setReorderingAllowed(true)
            // Replace the Fragment in the provide container with and instance of WelcomeFragment
            replace(R.id.fcvMain, WelcomeFragment())
            // Add the transaction to the BackStack, so it can be reversed with the Back button
            addToBackStack(WELCOME)
        }

    }

    /**
     * Navigates to the screen to select the size of the Froyo.
     */
    fun navigateToSize() {
        // Execute operations on a FragmentTransaction and commit it
        supportFragmentManager.commit {
            // Optimise animations
            setReorderingAllowed(true)
            // Replace the Fragment in the provide container with and instance of SizeFragment
            replace(R.id.fcvMain, SizeFragment())
            // Add the transaction to the BackStack, so it can be reversed with the Back button
            addToBackStack(SIZE)
        }
    }

    /**
     * Navigates to the screen to select the toppings for the Froyo.
     */
    fun navigateToToppings() {
        // Execute operations on a FragmentTransaction and commit it
        supportFragmentManager.commit {
            // Optimise animations
            setReorderingAllowed(true)
            // Replace the Fragment in the provide container with and instance of ToppingsFragment
            replace(R.id.fcvMain, ToppingsFragment())
            // Add the transaction to the BackStack, so it can be reversed with the Back button
            addToBackStack(TOPPINGS)
        }
    }

    /**
     * Navigates to the screen to select the sauce for the Froyo.
     */
    fun navigateToSauce() {
        // Execute operations on a FragmentTransaction and commit it
        supportFragmentManager.commit {
            // Optimise animations
            setReorderingAllowed(true)
            // Replace the Fragment in the provide container with and instance of SauceFragment
            replace(R.id.fcvMain, SauceFragment())
            // Add the transaction to the BackStack, so it can be reversed with the Back button
            addToBackStack(SAUCE)
        }
    }

    /**
     * Navigates to the checkout screen to place the order.
     */
    fun navigateToCheckout() {
        // Execute operations on a FragmentTransaction and commit it
        supportFragmentManager.commit {
            // Optimise animations
            setReorderingAllowed(true)
            // Replace the Fragment in the provide container with and instance of CheckoutFragment
            replace(R.id.fcvMain, CheckoutFragment())
            // Add the transaction to the BackStack, so it can be reversed with the Back button
            addToBackStack(CHECKOUT)
        }
    }

    /**
     * Navigates to the welcome screen.
     */
    fun navigateToWelcome() {
        // Pop transactions from the BackStack until reaching, and including, that labelled WELCOME
        supportFragmentManager.popBackStack(WELCOME, 0)
    }

}