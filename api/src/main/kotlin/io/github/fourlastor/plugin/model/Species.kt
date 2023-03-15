package io.github.fourlastor.plugin.model

data class Species(
    val name: String,
    val types: Set<Type>,
    val stats: Stats = Stats(78,  84,  78,  100,  109,  85),
    val catchRate: Int = 45,
    val baseExp: Int = 240,
    val genderRatio: GenderRatio = GenderRatio.DEFAULT,
    val eggCyclesToHatch: Int = 1,
    val growthRate: GrowthRate = GrowthRate.MEDIUM_SLOW,
    val eggGroups: Set<EggGroup> = setOf(EggGroup.MONSTER),
    val dexNumber: Int,
    val dexEntry: String = "It spits fire that is hot enough to melt boulders.",
    val weight: Float = 135.5f,
    val height: Float = 2.2f,
)
