package dev.flrp.labyrinth.mechanics.enemy

import com.wyrmlings.rune.node.xy.ui.TextureRect
import dev.flrp.labyrinth.mechanics.structure.Room
import kotlin.math.floor

open class Enemy : TextureRect() {

    open var room: Room? = null
    open var aiValue = 5

    open fun roll(): Boolean {
        return aiValue > floor(Math.random() * 21)
    }

    open fun pickRoom(): Room? {
        if(room != null) {
            val rooms = mutableListOf<Room>()
            room!!.upRoom?.let { rooms += it }
            room!!.downRoom?.let { rooms += it }
            room!!.leftRoom?.let { rooms += it }
            room!!.rightRoom?.let { rooms += it }

            return rooms.random()
        }
        return null;
    }

}