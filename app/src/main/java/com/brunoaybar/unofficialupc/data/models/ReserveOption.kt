package com.brunoaybar.unofficialupc.data.models

import java.util.*

open class ReserveOption(val code: String,
                         val type: String,
                         val name: String,
                         val venue: String,
                         val datetime: Date,
                         val duration: Int)