package com.paulcoding.devlauncher.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import java.net.InetAddress
import java.net.NetworkInterface

class IPAddressMonitor(
    context: Context, val onIPAddressChanged: (
        newIPAddress: String
    ) -> Unit
) {

    private var currentIPAddress: String? = null
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            checkIPAddress()
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            if (currentIPAddress != null) {
                currentIPAddress = null
                onIPAddressChanged("localhost")
            }
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            checkIPAddress()
        }
    }

    private fun checkIPAddress() {
        val newIPAddress = getDeviceIPAddress()
        if (newIPAddress != currentIPAddress) {
            currentIPAddress = newIPAddress
            onIPAddressChanged(newIPAddress)
        }
    }

    private fun getDeviceIPAddress(): String {
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            for (networkInterface in interfaces) {
                val addresses = networkInterface.inetAddresses
                for (inetAddress in addresses) {
                    if (!inetAddress.isLoopbackAddress && inetAddress is InetAddress) {
                        val ipAddress = inetAddress.hostAddress
                        if (ipAddress != null) {
                            if (ipAddress.indexOf(':') < 0) {
                                return ipAddress
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

    fun startMonitoring() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    fun stopMonitoring() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
