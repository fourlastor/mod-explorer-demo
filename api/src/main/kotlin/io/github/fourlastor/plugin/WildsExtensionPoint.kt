package io.github.fourlastor.plugin

import io.github.fourlastor.plugin.model.Species
import org.pf4j.ExtensionPoint

interface WildsExtensionPoint : ExtensionPoint {

    fun species(): List<Species>
}
