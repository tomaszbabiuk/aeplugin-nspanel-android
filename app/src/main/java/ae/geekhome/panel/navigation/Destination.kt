/*
 * Copyright (c) 2023 ASSA ABLOY AB,
 * Klarabergsviadukten 90,
 * Stockholm, Sweden.
 * All rights reserved
 *
 *
 * File        Destination.kt
 *
 * Author      GWL Team
 *
 */

package ae.geekhome.panel.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.navDeepLink

abstract class Destination {
    open val arguments: List<NamedNavArgument>
        get() = listOf()

    abstract val titleRes: Int

    companion object {
        fun buildRoutePrefix(clazz: Class<*>) =
            clazz.simpleName.lowercase().replace("destination", "")
    }

    open val route: String
        get() =
            buildRoutePrefix(this.javaClass) +
                if (arguments.isNotEmpty()) {
                    arguments.joinToString(separator = "&", prefix = "?") {
                        "${it.name}={${it.name}}"
                    }
                } else {
                    ""
                }

    open val deepLinks = listOf(navDeepLink { uriPattern = "aep://control/${route}" })

    @Composable abstract fun Content(navController: NavController)
}
