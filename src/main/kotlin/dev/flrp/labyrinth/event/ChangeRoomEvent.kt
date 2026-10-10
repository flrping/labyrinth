package dev.flrp.labyrinth.event

import com.wyrmlings.rune.event.Event
import dev.flrp.labyrinth.mechanics.structure.Room

class ChangeRoomEvent(val room: Room, val prev: Room) : Event()