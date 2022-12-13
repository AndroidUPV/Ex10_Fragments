/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package upv.dadm.ex10_fragments.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import upv.dadm.ex10_fragments.R

/**
 * Displays a dialog to ask the user for confirmation before cancelling the current order.
 */
class ConfirmationDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create the desired dialog
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_title)
            .setMessage(R.string.dialog_message)
            .setPositiveButton(R.string.dialog_yes) { _, _ ->
                // Yes, the user wants to cancel the order
                cancelOrder(true)
                // Dismiss the dialog
                dismiss()
            }
            .setNegativeButton(R.string.dialog_no) { _, _ ->
                // No, the user wants to keep the order
                cancelOrder(false)
                // Dismiss the dialog
                dismiss()
            }
            .create()
    }

    /**
     * Sets the cancel result for the give request key.
     */
    private fun cancelOrder(cancel: Boolean) {
        parentFragmentManager.setFragmentResult(
            CANCEL_CONFIRMATION_REQUEST, bundleOf(CANCEL_KEY to cancel)
        )
    }
}