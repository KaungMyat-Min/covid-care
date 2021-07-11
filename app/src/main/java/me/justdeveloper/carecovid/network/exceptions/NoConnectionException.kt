package me.justdeveloper.carecovid.network.exceptions

import java.io.IOException

class NoConnectionException : IOException() {
    override val message: String?
        get() = "No Internet Connection"
}
