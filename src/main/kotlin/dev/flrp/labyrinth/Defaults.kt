package dev.flrp.labyrinth

import dev.flrp.labyrinth.screens.EnemyType

object Defaults {

    val LEVEL_ONE_DEFAULTS = mapOf(
        EnemyType.EYES to 5,
        EnemyType.SOUND to 5,
        EnemyType.HEARING to 5,
        EnemyType.STOMPER to 0,
        EnemyType.SNEAKER to 0
    )

    val LEVEL_TWO_DEFAULTS = mapOf(
        EnemyType.EYES to 7,
        EnemyType.SOUND to 7,
        EnemyType.HEARING to 7,
        EnemyType.STOMPER to 5,
        EnemyType.SNEAKER to 0
    )

    val LEVEL_THREE_DEFAULTS = mapOf(
        EnemyType.EYES to 12,
        EnemyType.SOUND to 12,
        EnemyType.HEARING to 12,
        EnemyType.STOMPER to 7,
        EnemyType.SNEAKER to 5
    )

}