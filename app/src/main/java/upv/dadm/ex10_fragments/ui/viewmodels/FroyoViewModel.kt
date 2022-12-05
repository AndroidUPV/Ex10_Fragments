/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package upv.dadm.ex10_fragments.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

/**
 * Holds the state (size, topping, sauce) of the custom Froyo.
 */
class FroyoViewModel : ViewModel() {

    // Backing property for the size of the Froyo
    private val _size = MutableLiveData<String>()

    // Size of the Froyo
    val size: LiveData<String>
        get() = _size

    // Whether the size has been selected (not empty))
    val sizeSelected: LiveData<Boolean>
        get() = Transformations.map(_size) { _size.value?.isNotEmpty() }

    // Backing property for the size of the Froyo
    private val _topping = MutableLiveData<String>()

    // Topping for the Froyo
    val topping: LiveData<String>
        get() = _topping

    // Whether the topping has been selected (not empty))
    val toppingSelected: LiveData<Boolean>
        get() = Transformations.map(_topping) { _topping.value?.isNotEmpty() }

    // Backing property for the size of the Froyo
    private val _sauce = MutableLiveData<String>()

    // Sauce for the Froyo
    val sauce: LiveData<String>
        get() = _sauce

    // Whether the sauce has been selected (not empty))
    val sauceSelected: LiveData<Boolean>
        get() = Transformations.map(_sauce) { _sauce.value?.isNotEmpty() }

    // initialized all the backing properties
    init {
        resetOrder()
    }

    /**
     * Clears the selection for all the backing properties.
     */
    fun resetOrder() {
        _size.value = ""
        _topping.value = ""
        _sauce.value = ""
    }

    /**
     * Sets the selected size.
     */
    fun setSize(size: String) {
        _size.value = size
    }

    /**
     * Sets the selected topping.
     */
    fun setTopping(topping: String) {
        _topping.value = topping
    }

    /**
     * Sets the selected sauce.
     */
    fun setSauce(sauce: String) {
        _sauce.value = sauce
    }
}