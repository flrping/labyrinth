package dev.flrp.labyrinth.mechanics.enemy

import com.wyrmlings.rune.node.xy.ui.TextureRect
import kotlin.math.floor

open class Enemy : TextureRect() {

    open var aiValue = 5

    open fun roll(): Boolean {
        return aiValue > floor(Math.random() * 21)
    }

}