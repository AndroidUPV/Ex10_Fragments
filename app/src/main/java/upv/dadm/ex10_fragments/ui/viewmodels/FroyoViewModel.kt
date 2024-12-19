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

package upv.dadm.ex10_fragments.ui.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import upv.dadm.ex10_fragments.model.Froyo

/**
 * Holds the Froyo's properties and provides methods for updating them.
 */
class FroyoViewModel : ViewModel() {

    // UI state (mutable): Froyo's properties
    private val _froyoUiState = MutableStateFlow(Froyo("", "", ""))
    // Backing property (immutable)
    val froyoUiState = _froyoUiState.asStateFlow()

    /**
     * Clears the selection for all the backing properties.
     */
    fun resetOrder() {
        _froyoUiState.update {
            Froyo("", "", "")
        }
    }

    /**
     * Sets the selected size.
     */
    fun setSize(size: String) {
        _froyoUiState.update { froyo ->
            froyo.copy(size = size)
        }
    }

    /**
     * Sets the selected topping.
     */
    fun setTopping(topping: String) {
        _froyoUiState.update { froyo ->
            froyo.copy(topping = topping)
        }
    }

    /**
     * Sets the selected sauce.
     */
    fun setSauce(sauce: String) {
        _froyoUiState.update { froyo ->
            froyo.copy(sauce = sauce)
        }
    }
}