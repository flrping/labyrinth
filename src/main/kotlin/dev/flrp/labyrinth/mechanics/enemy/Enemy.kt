package dev.flrp.labyrinth.mechanics.enemy

import com.wyrmlings.rune.node.Sound
import com.wyrmlings.rune.node.xy.ui.TextureRect
import com.wyrmlings.rune.resource.Audio
import dev.flrp.labyrinth.mechanics.structure.Room
import dev.flrp.labyrinth.screens.EnemyType
import kotlin.math.floor

abstract class Enemy : TextureRect() {

    abstract val type: EnemyType
    abstract var encounterSound: Sound
    abstract var jumpscareSound: Sound
    abstract var description: String
    abstract var resolution: String

    open var room: Room? = null
    open var aiValue = 10

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