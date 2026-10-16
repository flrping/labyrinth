package dev.flrp.labyrinth.mechanics.structure

import dev.flrp.labyrinth.mechanics.enemy.Enemy

abstract class Level {

    open var rooms: MutableList<Room> = mutableListOf()
    open var exitRooms: MutableList<Room> = mutableListOf()
    open var occupiedRooms: MutableList<Room> = mutableListOf()
    open var enemies: MutableList<Enemy> = mutableListOf()
    open var exemptInitialClueRooms: MutableList<Room> = mutableListOf()
    open var exemptClueRooms: MutableList<Room> = mutableListOf()

    abstract var initialRoom: Room
    abstract var initialDirection: String
    abstract var mapTexture: String

    abstract fun setupRooms()

    open fun getRoom(name: String): Room? {
        return rooms.firstOrNull { it.name == name }
    }

    open fun getRoomsInRadius(room: Room, amount: Int): List<Room> {
        require(amount >= 0) { "amount must be non-negative" }
        val result = mutableListOf<Room>()
        val visited = mutableListOf<Room>()

        val breadth = 0
        val queue = ArrayDeque<Pair<Room, Int>>()
        queue.add(room to 0)


        while (queue.isNotEmpty() && breadth < amount) {
            val (currRoom, breadth) = queue.removeFirst()
            result.add(currRoom)
            visited.add(room)

            if (breadth >= amount) continue

            val neighbours = listOfNotNull(
                currRoom.upRoom,
                currRoom.downRoom,
                currRoom.leftRoom,
                currRoom.rightRoom,
            )

            for(neighbor in neighbours) {
                if(visited.add(neighbor)) {
                    queue.add(neighbor to breadth + 1)
                }
            }

        }

        return result
    }

    open fun getRoomsInRange(room: Room, min: Int, max: Int): List<Room> {
        require(min >= 0) { "min must be non-negative" }
        require(max >= min) { "max must be greater than or equal to min" }

        val result = mutableListOf<Room>()
        val visited = mutableSetOf<Room>()
        val queue = ArrayDeque<Pair<Room, Int>>()

        queue.add(room to 0)
        visited.add(room)

        while (queue.isNotEmpty()) {
            val (currRoom, distance) = queue.removeFirst()

            if (distance in min..max) {
                result.add(currRoom)
            }

            if (distance >= max) continue

            val neighbours = listOfNotNull(
                currRoom.upRoom,
                currRoom.downRoom,
                currRoom.leftRoom,
                currRoom.rightRoom
            )

            for (neighbor in neighbours) {
                if (visited.add(neighbor)) {
                    queue.add(neighbor to distance + 1)
                }
            }
        }

        return result
    }

}