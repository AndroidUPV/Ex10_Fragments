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

package upv.dadm.ex10_fragments.model

/**
 * A Froyo object containing its size, topping, and sauce.
 */
data class Froyo(
    val size: String,
    val topping: String,
    val sauce: String
)
