package dev.flrp.labyrinth.mechanics.structure

import kotlin.math.floor

class Room {

    var name = "Room"
    val connections: Int
        get() {
            var count = 0;
            if(leftRoom !== null) count++
            if(rightRoom !== null) count++
            if(upRoom !== null) count++
            if(downRoom !== null) count++
            return count;
        }
    var leftRoom: Room? = null
        set(value) {
            field = value
            if(value?.rightRoom !== this) {
                value?.rightRoom = this
            }
        }
    var rightRoom: Room? = null
        set(value) {
            field = value
            if(value?.leftRoom !== this) {
                value?.leftRoom = this
            }
        }
    var upRoom: Room? = null
        set(value) {
            field = value
            if(value?.downRoom !== this) {
                value?.downRoom = this
            }
        }
    var downRoom: Room? = null
        set(value) {
            field = value
            if(value?.upRoom !== this) {
                value?.upRoom = this
            }
        }
    var pole: Int = 0

    init {
        pole = floor(Math.random() * 3).toInt()
    }

}