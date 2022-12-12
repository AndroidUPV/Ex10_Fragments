/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package upv.dadm.ex10_fragments.ui.fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import upv.dadm.ex10_fragments.R

/**
 * Displays a dialog to ask the user for confirmation before cancelling the current order.
 */
class ConfirmationDialogFragment : DialogFragment() {

    /**
     * Defines the methods the parent Fragment must implement to cancel or keep the current order.
     */
    interface ConfirmationDialogCallback {
        fun onCancel()
        fun onDoNotCancel()
    }

    // Reference to the interface implementation
    private lateinit var callback: ConfirmationDialogCallback

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create the desired dialog
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_title)
            .setPositiveButton(R.string.dialog_yes) { _, _ ->
                // Yes, the user wants to cancel the order
                callback.onCancel()
            }
            .setNegativeButton(R.string.dialog_no) { _, _ ->
                // No, the user wants to keep the order
                callback.onDoNotCancel()
            }
            .create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Get a reference to the interface implementation
        callback = parentFragment as ConfirmationDialogCallback
    }
}