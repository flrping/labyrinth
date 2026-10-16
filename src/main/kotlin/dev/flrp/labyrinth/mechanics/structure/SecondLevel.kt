package dev.flrp.labyrinth.mechanics.structure

class SecondLevel : Level() {

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
    var room38: Room = Room().apply { name = "Room 38" }.also { rooms += it }
    var room39: Room = Room().apply { name = "Room 39" }.also { rooms += it }
    var room40: Room = Room().apply { name = "Room 40" }.also { rooms += it }
    var room41: Room = Room().apply { name = "Room 41" }.also { rooms += it }

    override var initialDirection: String = "right"
    override var initialRoom: Room = room26
    override var exitRooms = mutableListOf(initialRoom, room36);
    override var exemptInitialClueRooms: MutableList<Room>
    override var exemptClueRooms = exitRooms
    override var mapTexture: String = "map_2.png"

    override fun setupRooms() {
        room1.leftRoom = room2
        room2.leftRoom = room3
        room3.leftRoom = room4
        room4.leftRoom = room5
        room5.upRoom = room6
        room6.upRoom = room7
        room7.leftRoom = room8
        room8.leftRoom = room9
        room9.upRoom = room10
        room10.upRoom = room11
        room11.upRoom = room12
        room12.rightRoom = room13
        room13.rightRoom = room14
        room14.rightRoom = room15
        room15.rightRoom = room16
        room16.rightRoom = room17
        room17.rightRoom = room18
        room18.rightRoom = room19
        room19.rightRoom = room20
        room20.rightRoom = room21
        room21.downRoom = room22
        room22.downRoom = room23
        room23.downRoom = room24
        room24.leftRoom = room25
        room25.leftRoom = room26
        room26.leftRoom = room27
        room27.leftRoom = room28
        room28.leftRoom = room29
        room29.leftRoom = room30
        room30.leftRoom = room7
        room27.downRoom = room31
        room31.downRoom = room1
        room29.upRoom = room32
        room32.upRoom = room33
        room33.upRoom = room16
        room14.upRoom = room34
        room35.rightRoom = room36
        room36.rightRoom = room37
        room37.rightRoom = room38
        room38.rightRoom = room39
        room39.rightRoom = room40
        room40.downRoom = room41
        room41.downRoom = room19

        initialRoom = room26
    }

    init {
        setupRooms()
        exemptInitialClueRooms = mutableListOf()
        exemptInitialClueRooms.add(initialRoom)
        exemptInitialClueRooms.addAll(exitRooms)

    }

}