package io.github.fourlastor.plugin

import io.github.fourlastor.plugin.model.Species
import io.github.fourlastor.plugin.model.Type
import org.pf4j.Extension

@Extension
class TestExtension: WildsExtensionPoint {
    override fun species(): List<Species> {
        return listOf(
            Species(
                name = "Bulbasaur",
                types = setOf(Type.GRASS),
                dexNumber = 1,
            ),
            Species(
                name = "Charmander",
                types = setOf(Type.FIRE),
                dexNumber = 1,
            ),
            Species(
                name = "Charizard",
                types = setOf(Type.FIRE),
                dexNumber = 1,
            ),
            Species(
                name = "Charmeleon",
                types = setOf(Type.FIRE),
                dexNumber = 1,
            ),
            Species(
                name = "Hitmonchan",
                types = setOf(Type.FIRE),
                dexNumber = 1,
            ),
            Species(
                name = "Hitmonlee",
                types = setOf(Type.FIRE),
                dexNumber = 1,
            ),
            Species(
                name = "Onix",
                types = setOf(Type.FIRE),
                dexNumber = 1,
            ),
            Species(
                name = "Pikachu",
                types = setOf(Type.FIRE),
                dexNumber = 1,
            ),
            Species(
                name = "Pinsir",
                types = setOf(Type.FIRE),
                dexNumber = 1,
            ),
        )
    }
}
