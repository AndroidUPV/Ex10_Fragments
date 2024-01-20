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

package upv.dadm.ex10_fragments.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Holds whether the order should be cancelled and methods to update it.
 */
class CancelOrderViewModel: ViewModel() {

    // mutable: cancel order (do not cancel by default)
    private val _cancelOrder = MutableStateFlow(false)
    // Backing property (immutable)
    val cancelOrder = _cancelOrder.asStateFlow()

    /**
     * Sets the state to cancel the order.
     */
    fun cancelOrder() {
        viewModelScope.launch {
            _cancelOrder.update { true }
        }
    }

    /**
     * Sets the state to not cancel the order.
     */
    fun proceedWithOrder() {
        viewModelScope.launch {
            _cancelOrder.update { false }
        }
    }

    /**
     * Sets the state to not cancel the order (default).
     */
    fun resetCancel() {
        proceedWithOrder()
    }
}