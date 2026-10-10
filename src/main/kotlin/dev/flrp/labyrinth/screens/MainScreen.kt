package dev.flrp.labyrinth.screens

import com.wyrmlings.rune.input.Input
import com.wyrmlings.rune.input.Key
import com.wyrmlings.rune.math.Vector2
import com.wyrmlings.rune.node.Node
import com.wyrmlings.rune.node.Sound
import com.wyrmlings.rune.node.xy.ui.TextureRect
import com.wyrmlings.rune.ui.texture.ImageTexture
import dev.flrp.labyrinth.Labyrinth
import dev.flrp.labyrinth.audio.AudioMaster
import dev.flrp.labyrinth.audio.MainTheme
import dev.flrp.labyrinth.event.ChangeRoomEvent
import dev.flrp.labyrinth.mechanics.Hands
import dev.flrp.labyrinth.mechanics.structure.Room

class MainScreen(val labyrinth: Labyrinth) : Node() {

    val directions = listOf("up", "right", "down", "left")
    var direction = "up"
    var currRoom: Room? = null

    var room1: Room = Room().apply { name = "Room1" }
    var room2: Room = Room().apply { name = "Room2" }
    var room3: Room = Room().apply { name = "Room3" }
    var room4: Room = Room().apply { name = "Room4" }
    var room5: Room = Room().apply { name = "Room5" }
    var room6: Room = Room().apply { name = "Room6" }
    var room7: Room = Room().apply { name = "Room7" }
    var room8: Room = Room().apply { name = "Room8" }
    var room9: Room = Room().apply { name = "Room9" }
    var room10: Room = Room().apply { name = "Room10" }
    var room11: Room = Room().apply { name = "Room11" }
    var room12: Room = Room().apply { name = "Room12" }
    var room13: Room = Room().apply { name = "Room13" }
    var room14: Room = Room().apply { name = "Room14" }
    var room15: Room = Room().apply { name = "Room15" }
    var room16: Room = Room().apply { name = "Room16" }
    var room17: Room = Room().apply { name = "Room17" }
    var room18: Room = Room().apply { name = "Room18" }
    var room19: Room = Room().apply { name = "Room19" }
    var room20: Room = Room().apply { name = "Room20" }
    var room21: Room = Room().apply { name = "Room21" }
    var room22: Room = Room().apply { name = "Room22" }
    var room23: Room = Room().apply { name = "Room23" }
    var room24: Room = Room().apply { name = "Room24" }
    var room25: Room = Room().apply { name = "Room25" }

    lateinit var background: TextureRect
    lateinit var pole1: TextureRect
    lateinit var pole2: TextureRect
    lateinit var hands: TextureRect

    lateinit var movement: Sound

    lateinit var bg: ImageTexture
    lateinit var rightBg: ImageTexture
    lateinit var leftBg: ImageTexture
    lateinit var bothBg: ImageTexture
    lateinit var upLeftBg: ImageTexture
    lateinit var upRightBg: ImageTexture

    override fun onReady() {
        // Tree of doom...
        room1.upRoom = room2
        room2.upRoom = room3
        room3.leftRoom = room4
        room4.leftRoom = room5
        room5.upRoom = room6
        room6.upRoom = room7
        room7.rightRoom = room8
        room8.rightRoom = room9
        room9.upRoom = room15
        room9.rightRoom = room10
        room10.rightRoom = room11
        room11.downRoom = room12
        room12.downRoom = room13
        room12.rightRoom = room25
        room13.leftRoom = room14
        room14.leftRoom = room3
        room15.upRoom = room16
        room16.rightRoom = room17
        room17.rightRoom = room18
        room18.rightRoom = room19
        room19.rightRoom = room20
        room20.downRoom = room21
        room21.downRoom = room22
        room22.downRoom = room23
        room23.downRoom = room24
        room24.leftRoom = room25

        currRoom = room1

        bg = ImageTexture(labyrinth.assetManager.loadTexture("background.png"))
        leftBg = ImageTexture(labyrinth.assetManager.loadTexture("background_end_2.png"))
        rightBg = ImageTexture(labyrinth.assetManager.loadTexture("background_end_1.png"))
        bothBg = ImageTexture(labyrinth.assetManager.loadTexture("background_end.png"))
        upLeftBg = ImageTexture(labyrinth.assetManager.loadTexture("background_SL.png"))
        upRightBg = ImageTexture(labyrinth.assetManager.loadTexture("background_SR.png"))

        background = TextureRect().apply {
            texture = bg
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
        }
        addChild(background)

        pole1 = TextureRect().apply {
            texture = ImageTexture(labyrinth.assetManager.loadTexture("pole_1.png"))
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
            visible = false
        }
        addChild(pole1)

        pole2 = TextureRect().apply {
            texture = ImageTexture(labyrinth.assetManager.loadTexture("pole_2.png"))
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
            visible = false
        }
        addChild(pole2)

        movement = Sound().apply {
            name = "Movement"
            volume = 1.0f
            audio = labyrinth.audioManager.loadAudio("movement.wav")
        }
        labyrinth.globals.addChild(movement)

        hands = Hands(labyrinth, movement)
        addChild(hands)

        addPoles()

        labyrinth.eventBus.subscribe { event ->
            when (event) {
                is ChangeRoomEvent -> {
                    onRoomChange(event.room, event.prev)
                }
            }
        }

        val audioMaster = AudioMaster()
        labyrinth.globals.addChild(audioMaster)

        val theme = MainTheme(labyrinth)
        audioMaster.theme = theme
        audioMaster.play("bass.wav", 1.0f)
        audioMaster.play("keys.wav", 1.0f)

        val ambience = Sound().apply {
            volume = 0.05f
            audio = labyrinth.audioManager.loadAudio("812600__modusmogulus__rooftop-vent-fan-seamless-loop.wav")
            loop = true
        }
        labyrinth.globals.addChild(ambience)
        ambience.play()
    }

    override fun onUpdate(delta: Double) {
        val currentRoom = currRoom ?: return

        val inputDirection = when {
            Input.wasKeyPressed(Key.UP) -> 0
            Input.wasKeyPressed(Key.RIGHT) -> 1
            Input.wasKeyPressed(Key.DOWN) -> 2
            Input.wasKeyPressed(Key.LEFT) -> 3
            else -> return
        }

        val facingIndex = directions.indexOf(direction)
        val targetIndex = (facingIndex + inputDirection) % 4
        val targetRoom = when (directions[targetIndex]) {
            "up" -> currentRoom.upRoom
            "right" -> currentRoom.rightRoom
            "down" -> currentRoom.downRoom
            "left" -> currentRoom.leftRoom
            else -> null
        }

        targetRoom?.let {
            val isReversing = inputDirection == 2

            if (!isReversing) {
                direction = directions[targetIndex]
            }

            labyrinth.eventBus.publish(ChangeRoomEvent(it, currentRoom))
        }
    }

    fun addPoles() {
        currRoom?.let {
            pole1.visible = false
            pole2.visible = false

            if(it.pole == 1) {
                pole1.visible = true
            }
            if(it.pole == 2) {
                pole2.visible = true
            }
        }
    }

    fun onRoomChange(room: Room, prev: Room) {
        currRoom = room
        println("Moved to Room: ${room.name}, " + "connections: ${room.connections}, facing: $direction")
        background.texture = getBackground(currRoom ?: return)
        addPoles()
    }

    fun getBackground(room: Room): ImageTexture {
        val forwardOpen: Boolean
        val leftOpen: Boolean
        val rightOpen: Boolean

        when (direction) {
            "up" -> {
                forwardOpen = room.upRoom != null
                leftOpen = room.leftRoom != null
                rightOpen = room.rightRoom != null
            }

            "right" -> {
                forwardOpen = room.rightRoom != null
                leftOpen = room.upRoom != null
                rightOpen = room.downRoom != null
            }

            "down" -> {
                forwardOpen = room.downRoom != null
                leftOpen = room.rightRoom != null
                rightOpen = room.leftRoom != null
            }

            "left" -> {
                forwardOpen = room.leftRoom != null
                leftOpen = room.downRoom != null
                rightOpen = room.upRoom != null
            }

            else -> return bg
        }

        return when {
            forwardOpen && leftOpen && rightOpen -> bothBg
            forwardOpen && leftOpen -> upLeftBg
            forwardOpen && rightOpen -> upRightBg
            leftOpen && rightOpen -> bothBg
            forwardOpen -> bg
            leftOpen -> leftBg
            rightOpen -> rightBg
            else -> bg
        }
    }

}