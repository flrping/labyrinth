package dev.flrp.labyrinth.mechanics.structure

class FirstLevel : Level() {

    var room1: Room = Room().apply { name = "Room 1" }.also { rooms += it }
    var room2: Room = Room().apply { name = "Room 2" }.also { rooms += it }
    var room3: Room = Room().apply { name = "Room 3" }.also { rooms += it }
    var room4: Room = Room().apply { name = "Room 4" }.also { rooms += it }
    var room5: Room = Room().apply { name = "Room 5" }.also { rooms += it }
    var room6: Room = Room().apply { name = "Room 6" }.also { rooms += it }
    var room7: Room = Room().apply { name = "Room 7" }.also { rooms += it }
    var room8: Room = Room().apply { name = "Room 8" }.also { rooms += it }
    var room9: Room = Room().apply { name = "Room 9" }.also { rooms += it }
    var room10: Room = Room().apply { name = "Room 10" }.also { rooms += it }
    var room11: Room = Room().apply { name = "Room 11" }.also { rooms += it }
    var room12: Room = Room().apply { name = "Room 12" }.also { rooms += it }
    var room13: Room = Room().apply { name = "Room 13" }.also { rooms += it }
    var room14: Room = Room().apply { name = "Room 14" }.also { rooms += it }
    var room15: Room = Room().apply { name = "Room 15" }.also { rooms += it }
    var room16: Room = Room().apply { name = "Room 16" }.also { rooms += it }
    var room17: Room = Room().apply { name = "Room 17" }.also { rooms += it }
    var room18: Room = Room().apply { name = "Room 18" }.also { rooms += it }
    var room19: Room = Room().apply { name = "Room 19" }.also { rooms += it }
    var room20: Room = Room().apply { name = "Room 20" }.also { rooms += it }
    var room21: Room = Room().apply { name = "Room 21" }.also { rooms += it }
    var room22: Room = Room().apply { name = "Room 22" }.also { rooms += it }
    var room23: Room = Room().apply { name = "Room 23" }.also { rooms += it }
    var room24: Room = Room().apply { name = "Room 24" }.also { rooms += it }
    var room25: Room = Room().apply { name = "Room 25" }.also { rooms += it }
    var room26: Room = Room().apply { name = "Room 26" }.also { rooms += it }
    var room27: Room = Room().apply { name = "Room 27" }.also { rooms += it }
    var room28: Room = Room().apply { name = "Room 28" }.also { rooms += it }
    var room29: Room = Room().apply { name = "Room 29" }.also { rooms += it }
    var room30: Room = Room().apply { name = "Room 30" }.also { rooms += it }
    var room31: Room = Room().apply { name = "Room 31" }.also { rooms += it }
    var room32: Room = Room().apply { name = "Room 32" }.also { rooms += it }
    var room33: Room = Room().apply { name = "Room 33" }.also { rooms += it }
    var room34: Room = Room().apply { name = "Room 34" }.also { rooms += it }
    var room35: Room = Room().apply { name = "Room 35" }.also { rooms += it }
    var room36: Room = Room().apply { name = "Room 36" }.also { rooms += it }
    var room37: Room = Room().apply { name = "Room 37" }.also { rooms += it }

    override var initialDirection: String = "left"
    override var initialRoom: Room = room1
    override var exitRooms = mutableListOf(initialRoom, room25, room33);
    override var exemptInitialClueRooms: MutableList<Room>
    override var exemptClueRooms = exitRooms
    override var mapTexture: String = "map_1.png"

    override fun setupRooms() {
        room1.leftRoom = room2
        room2.upRoom = room3
        room3.upRoom = room4
        room4.leftRoom = room5
        room5.upRoom = room6
        room6.upRoom = room7
        room7.upRoom = room8
        room8.upRoom = room9
        room9.rightRoom = room10
        room10.rightRoom = room11
        room11.rightRoom = room12
        room12.rightRoom = room13
        room13.downRoom = room14
        room14.downRoom = room15
        room15.downRoom = room16
        room16.downRoom = room17
        room17.leftRoom = room18
        room18.leftRoom = room19
        room18.downRoom = room20
        room20.downRoom = room21
        room21.leftRoom = room1
        room19.leftRoom = room4
        room7.leftRoom = room31
        room31.leftRoom = room32
        room32.upRoom = room33
        room33.upRoom = room34
        room34.upRoom = room35
        room35.rightRoom = room36
        room36.rightRoom = room37
        room37.rightRoom = room22
        room11.upRoom = room22
        room22.upRoom = room23
        room23.rightRoom = room24
        room24.rightRoom = room25
        room25.rightRoom = room26
        room26.downRoom = room27
        room27.downRoom = room28
        room28.downRoom = room29
        room29.downRoom = room30
        room30.leftRoom = room15

        initialRoom = room1
    }

    init{
        setupRooms()
        exemptInitialClueRooms = mutableListOf()
        exemptInitialClueRooms.add(initialRoom)
        exemptInitialClueRooms.addAll(exitRooms)

    }

}