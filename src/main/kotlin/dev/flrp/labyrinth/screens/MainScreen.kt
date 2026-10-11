package dev.flrp.labyrinth.screens

import com.wyrmlings.rune.input.Input
import com.wyrmlings.rune.input.Key
import com.wyrmlings.rune.math.Vector2
import com.wyrmlings.rune.node.Node
import com.wyrmlings.rune.node.Sound
import com.wyrmlings.rune.node.Timer
import com.wyrmlings.rune.node.xy.ui.TextureRect
import com.wyrmlings.rune.ui.texture.ImageTexture
import dev.flrp.labyrinth.Labyrinth
import dev.flrp.labyrinth.audio.AudioMaster
import dev.flrp.labyrinth.audio.MainTheme
import dev.flrp.labyrinth.event.ChangeRoomEvent
import dev.flrp.labyrinth.mechanics.Hands
import dev.flrp.labyrinth.mechanics.enemy.Enemy
import dev.flrp.labyrinth.mechanics.enemy.EyesEnemy
import dev.flrp.labyrinth.mechanics.enemy.HearingEnemy
import dev.flrp.labyrinth.mechanics.enemy.SoundEnemy
import dev.flrp.labyrinth.mechanics.structure.Room

class MainScreen(val labyrinth: Labyrinth) : Node() {

    val directions = listOf("up", "right", "down", "left")
    var direction = "up"
    var currRoom: Room? = null

    var rooms = listOf<Room>()
    var room1: Room = Room().apply { name = "Room1" }.also { rooms += it }
    var room2: Room = Room().apply { name = "Room2" }.also { rooms += it }
    var room3: Room = Room().apply { name = "Room3" }.also { rooms += it }
    var room4: Room = Room().apply { name = "Room4" }.also { rooms += it }
    var room5: Room = Room().apply { name = "Room5" }.also { rooms += it }
    var room6: Room = Room().apply { name = "Room6" }.also { rooms += it }
    var room7: Room = Room().apply { name = "Room7" }.also { rooms += it }
    var room8: Room = Room().apply { name = "Room8" }.also { rooms += it }
    var room9: Room = Room().apply { name = "Room9" }.also { rooms += it }
    var room10: Room = Room().apply { name = "Room10" }.also { rooms += it }
    var room11: Room = Room().apply { name = "Room11" }.also { rooms += it }
    var room12: Room = Room().apply { name = "Room12" }.also { rooms += it }
    var room13: Room = Room().apply { name = "Room13" }.also { rooms += it }
    var room14: Room = Room().apply { name = "Room14" }.also { rooms += it }
    var room15: Room = Room().apply { name = "Room15" }.also { rooms += it }
    var room16: Room = Room().apply { name = "Room16" }.also { rooms += it }
    var room17: Room = Room().apply { name = "Room17" }.also { rooms += it }
    var room18: Room = Room().apply { name = "Room18" }.also { rooms += it }
    var room19: Room = Room().apply { name = "Room19" }.also { rooms += it }
    var room20: Room = Room().apply { name = "Room20" }.also { rooms += it }
    var room21: Room = Room().apply { name = "Room21" }.also { rooms += it }
    var room22: Room = Room().apply { name = "Room22" }.also { rooms += it }
    var room23: Room = Room().apply { name = "Room23" }.also { rooms += it }
    var room24: Room = Room().apply { name = "Room24" }.also { rooms += it }
    var room25: Room = Room().apply { name = "Room25" }.also { rooms += it }

    lateinit var background: TextureRect
    lateinit var ui: TextureRect
    lateinit var items: TextureRect
    lateinit var bunbun: TextureRect
    lateinit var bunbunFace: TextureRect
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
    lateinit var allBg: ImageTexture

    lateinit var normal: ImageTexture
    lateinit var unhappy1: ImageTexture
    lateinit var unhappy2: ImageTexture
    lateinit var scared1: ImageTexture
    lateinit var scared2: ImageTexture
    lateinit var agony: ImageTexture
    lateinit var acceptance: ImageTexture
    lateinit var look: ImageTexture

    lateinit var audioMaster: AudioMaster
    lateinit var timer: Timer

    var occupiedRooms = mutableListOf<Room>()
    var enemies = mutableListOf<Enemy>()
    lateinit var eyesEnemy: EyesEnemy
    lateinit var soundEnemy: SoundEnemy
    lateinit var hearingEnemy: HearingEnemy

    var fearLevel: Int = 0
    var isInDanger: Boolean = false
    var currentEnemy: Enemy? = null

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
        allBg = ImageTexture(labyrinth.assetManager.loadTexture("background_all.png"))

        normal = ImageTexture(labyrinth.assetManager.loadTexture("normal.png"))
        unhappy1 = ImageTexture(labyrinth.assetManager.loadTexture("unhappy1.png"))
        unhappy2 = ImageTexture(labyrinth.assetManager.loadTexture("unhappy2.png"))
        scared1 = ImageTexture(labyrinth.assetManager.loadTexture("scared1.png"))
        scared2 = ImageTexture(labyrinth.assetManager.loadTexture("scared2.png"))
        agony = ImageTexture(labyrinth.assetManager.loadTexture("agony.png"))
        acceptance = ImageTexture(labyrinth.assetManager.loadTexture("acceptance.png"))
        //look = ImageTexture(labyrinth.assetManager.loadTexture("look.png"))

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

        ui = TextureRect().apply {
            texture = ImageTexture(labyrinth.assetManager.loadTexture("ui.png"))
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
        }
        addChild(ui)

        items = TextureRect().apply {
            texture = ImageTexture(labyrinth.assetManager.loadTexture("items.png"))
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
        }
        addChild(items)

        bunbun = TextureRect().apply {
            texture = ImageTexture(labyrinth.assetManager.loadTexture("bunbun.png"))
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
        }
        addChild(bunbun)

        bunbunFace = TextureRect().apply {
            texture = normal
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
        }
        addChild(bunbunFace)

        timer = Timer()
        addChild(timer)

        addPoles()

        labyrinth.eventBus.subscribe { event ->
            when (event) {
                is ChangeRoomEvent -> {
                    onRoomChange(event.room, event.prev)
                }
            }
        }

        audioMaster = AudioMaster()
        labyrinth.globals.addChild(audioMaster)

        val theme = MainTheme(labyrinth)
        audioMaster.theme = theme
        audioMaster.volume(0f)
        audioMaster.play()
        audioMaster.volume("bass.wav", 1.0f)

        val ambience = Sound().apply {
            volume = 0.05f
            audio = labyrinth.audioManager.loadAudio("812600__modusmogulus__rooftop-vent-fan-seamless-loop.wav")
            loop = true
        }
        labyrinth.globals.addChild(ambience)
        ambience.play()

        eyesEnemy = EyesEnemy(labyrinth).apply { room = room16 }
        enemies += eyesEnemy
        soundEnemy = SoundEnemy(labyrinth).apply { room = room25 }
        enemies += soundEnemy
        hearingEnemy = HearingEnemy(labyrinth).apply { room = room7 }
        enemies += hearingEnemy

        timer.start(20.0) { onTimerDone() }
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

    fun onTimerDone() {
        for (enemy in enemies) {
            if(enemy.roll()) {
                val oldRoom = enemy.room
                val availableRooms = rooms.let { enemy.pickRoom() }
                    ?.let { listOf(it) }
                    ?.filter { it !in occupiedRooms || it == oldRoom }
                val selectedRoom = availableRooms?.randomOrNull() ?: continue
                enemy.room = selectedRoom
                occupiedRooms.add(selectedRoom)
                occupiedRooms.remove(oldRoom)

                println("$enemy enemy moved to: ${selectedRoom.name}")

                if(enemy.room === currRoom) {
                    onEnemyArrive(enemy)
                }
            }
        }

        timer.start(20.0) { onTimerDone() }
    }

    fun onRoomChange(room: Room, prev: Room) {
        currRoom = room

        for(enemy in enemies) {
            if(enemy.room === currRoom) {
                onEnemyArrive(enemy)
                break
            }
        }

        println("Moved to Room: ${room.name}, " + "connections: ${room.connections}, facing: $direction")
        background.texture = getBackground(currRoom ?: return)
        addPoles()
    }

    fun onEnemyArrive(enemy: Enemy) {
        if (isInDanger && currentEnemy === enemy) return

        currentEnemy = enemy
        isInDanger = true

        addChild(enemy)
        fearLevel++

        when (fearLevel) {
            1 -> {
                audioMaster.volume("keys.wav", 1f)
                bunbunFace.texture = unhappy1
            }
            2 -> {
                audioMaster.volume("kick.wav", 1f)
                bunbunFace.texture = unhappy2
            }
            3 -> {
                audioMaster.volume("snare1.wav", 1f)
                bunbunFace.texture = scared1
            }
            4 -> {
                audioMaster.volume("snare2.wav", 1f)
                bunbunFace.texture = scared2
            }
            5 -> {
                bunbunFace.texture = agony
            }
            6 -> {
                audioMaster.volume("beep.wav", 1f)
                bunbunFace.texture = acceptance
            }
            7 -> {
                audioMaster.volume("breathe.wav", 1f)
                // bunbunFace.texture = look
            }
        }

        println("Encountered enemy: ${enemy.name}, fear level: $fearLevel")
    }

    private fun addPoles() {
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

    private fun getBackground(room: Room): ImageTexture {
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
            forwardOpen && leftOpen && rightOpen -> allBg
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