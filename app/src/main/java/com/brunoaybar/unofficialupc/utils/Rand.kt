package com.brunoaybar.unofficialupc.utils

import java.util.*


object Rand {

    /**
     * random int inclusively [min,max]
     * @param min lower bound
     * @param max upper bound
     * @see [Random.nextInt]
     */
    fun int(min: Int, max: Int): Int = min + Random().nextInt((max - min) + 1)

    val int: Int
        get() {
            return int(0, 100)
        }

    /**
     * @see [Random.nextLong]
     */
    val long: Long
        get() {
            return Random().nextLong()
        }

    /**
     * @see [Random.nextDouble]
     * [0.0d,1.0d)
     */
    val double: Double
        get() {
            return Random().nextDouble()
        }
    /**
     * @see [Random.nextFloat]
     * [0.0f,1.0f)
     */
    val float: Float
        get() {
            return Random().nextFloat()
        }

    /**
     * @see [UUID.randomUUID]
     */
    val str: String
        get() {
            return UUID.randomUUID().toString().replace("-", "")
        }

    /**
     * @see [Random.nextBoolean]
     */
    val bool: Boolean
        get() {
            return Random().nextBoolean()
        }
}