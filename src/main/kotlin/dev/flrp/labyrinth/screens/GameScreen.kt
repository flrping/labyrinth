package dev.flrp.labyrinth.screens

import com.wyrmlings.rune.input.Input
import com.wyrmlings.rune.input.Key
import com.wyrmlings.rune.math.Vector2
import com.wyrmlings.rune.node.Node
import com.wyrmlings.rune.node.Sound
import com.wyrmlings.rune.node.Timer
import com.wyrmlings.rune.node.xy.ui.Label
import com.wyrmlings.rune.node.xy.ui.TextureRect
import com.wyrmlings.rune.property.Color
import com.wyrmlings.rune.resource.Audio
import com.wyrmlings.rune.ui.style.text.FontSettings
import com.wyrmlings.rune.ui.style.text.TextAlign
import com.wyrmlings.rune.ui.style.text.TextDecoration
import com.wyrmlings.rune.ui.style.text.TextPosition
import com.wyrmlings.rune.ui.texture.ImageTexture
import dev.flrp.labyrinth.Labyrinth
import dev.flrp.labyrinth.audio.AudioMaster
import dev.flrp.labyrinth.audio.GameTheme
import dev.flrp.labyrinth.event.ChangeRoomEvent
import dev.flrp.labyrinth.mechanics.Hands
import dev.flrp.labyrinth.mechanics.enemy.*
import dev.flrp.labyrinth.mechanics.structure.Level
import dev.flrp.labyrinth.mechanics.structure.Room
import kotlin.math.floor
import kotlin.random.Random

class GameScreen(
    val labyrinth: Labyrinth,
    val audioMaster: AudioMaster,
    val level: Level,
    val isStoryMode: Boolean,
    val levelNum: Int,
    val enemyValues: Map<EnemyType, Int> //= EnemyType.entries.associateWith { 5 }
) : Node() {

    // Direction
    val directions = listOf("up", "right", "down", "left")
    var direction = "left"
    var currRoom: Room? = null

    // Live scene elements
    lateinit var background: TextureRect
    lateinit var ui: TextureRect
    lateinit var items: TextureRect
    lateinit var bunbun: TextureRect
    lateinit var bunbunFace: TextureRect
    lateinit var pole1: TextureRect
    lateinit var pole2: TextureRect
    lateinit var web1: TextureRect
    lateinit var web2: TextureRect
    lateinit var hands: TextureRect
    lateinit var mask: TextureRect
    lateinit var map: TextureRect
    lateinit var clue: TextureRect
    lateinit var earplugs: TextureRect

    // Sound
    lateinit var movement: Sound

    // preloaded textures for swapping
    lateinit var bg: ImageTexture
    lateinit var rightBg: ImageTexture
    lateinit var leftBg: ImageTexture
    lateinit var bothBg: ImageTexture
    lateinit var upLeftBg: ImageTexture
    lateinit var upRightBg: ImageTexture
    lateinit var allBg: ImageTexture
    lateinit var noneBg: ImageTexture

    lateinit var normal: ImageTexture
    lateinit var unhappy1: ImageTexture
    lateinit var unhappy2: ImageTexture
    lateinit var scared1: ImageTexture
    lateinit var scared2: ImageTexture
    lateinit var agony: ImageTexture
    lateinit var acceptance: ImageTexture
    lateinit var look: ImageTexture

    // Special
    lateinit var timer: Timer
    lateinit var ambience: Sound
    lateinit var whistle: Sound
    lateinit var earPlugSound: Sound
    lateinit var gasMaskSound: Sound
    lateinit var resolveSound: Sound
    lateinit var paperCrumpleSound: Sound
    val resolveSoundList = listOf(
        "cry1.wav",
        "cry2.wav",
        "cry3.wav",
        "cry4.wav",
        "cry5.wav",
        "giggle1.wav",
        "giggle2.wav",
        "giggle3.wav",
        "giggle4.wav",
        "giggle5.wav",
        "giggle6.wav",
        "giggle7.wav"
    )
    var resolveAudios = mutableListOf<Audio>()

    // Screen text
    lateinit var roomName: Label
    lateinit var cluesCollected: Label
    lateinit var currentObjective: Label
    lateinit var pressKey: Label

    // Enemies
    lateinit var eyesEnemy: EyesEnemy
    lateinit var soundEnemy: SoundEnemy
    lateinit var hearingEnemy: HearingEnemy
    lateinit var stomperEnemy: StomperEnemy
    lateinit var sneakerEnemy: SneakerEnemy

    // Game trackers
    var musicVolume = labyrinth.musicVolume.toFloat()
    var previousFearLevel = 0
    var fearLevel: Int = 0
    var isInDanger: Boolean = false
    var currentEnemy: Enemy? = null
    var isMaskOn: Boolean = false
    var isEarPlugOn: Boolean = false
    var isBlowingWhistle: Boolean = false
    var isMapOpen: Boolean = false

    // TTL
    var resolveTimer: Double = 0.0

    // The eyes
    var eyesMaskTime: Double = 0.0

    // The sound
    var whistleNeeded: Int = 0
    var whistleCount: Int = 0
    var whistleTimer: Double = 0.0

    // The hearing
    var earPlugTime: Double = 0.0

    // The stomper
    var isHuntedByStomper: Boolean = false
    var movesNeeded: Int = 0
    var moveCount: Int = 0

    // The sneaker
    var sneakerTimeNeeded: Double = 0.0
    var sneakerTimeWaited: Double = 0.0

    var clueRoom: Room? = null
    var collected: Int = 0
    var cluesRequired: Int = 5

    override fun onReady() {
        currRoom = level.initialRoom
        direction = level.initialDirection

        bg = ImageTexture(labyrinth.assetManager.loadTexture("background.png"))
        leftBg = ImageTexture(labyrinth.assetManager.loadTexture("background_end_2.png"))
        rightBg = ImageTexture(labyrinth.assetManager.loadTexture("background_end_1.png"))
        bothBg = ImageTexture(labyrinth.assetManager.loadTexture("background_end.png"))
        upLeftBg = ImageTexture(labyrinth.assetManager.loadTexture("background_SL.png"))
        upRightBg = ImageTexture(labyrinth.assetManager.loadTexture("background_SR.png"))
        allBg = ImageTexture(labyrinth.assetManager.loadTexture("background_all.png"))
        noneBg = ImageTexture(labyrinth.assetManager.loadTexture("background_none.png"))

        normal = ImageTexture(labyrinth.assetManager.loadTexture("normal.png"))
        unhappy1 = ImageTexture(labyrinth.assetManager.loadTexture("unhappy1.png"))
        unhappy2 = ImageTexture(labyrinth.assetManager.loadTexture("unhappy2.png"))
        scared1 = ImageTexture(labyrinth.assetManager.loadTexture("scared1.png"))
        scared2 = ImageTexture(labyrinth.assetManager.loadTexture("scared2.png"))
        agony = ImageTexture(labyrinth.assetManager.loadTexture("agony.png"))
        acceptance = ImageTexture(labyrinth.assetManager.loadTexture("acceptance.png"))
        look = ImageTexture(labyrinth.assetManager.loadTexture("look.png"))

        background = TextureRect().apply {
            texture = bg
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
        }
        addChild(background)

        movement = Sound().apply {
            name = "Movement"
            volume = labyrinth.sfxVolume.toFloat()
            audio = labyrinth.audioManager.loadAudio("movement.wav")
        }
        labyrinth.globals.addChild(movement)

        clue = TextureRect().apply {
            texture = ImageTexture(labyrinth.assetManager.loadTexture("clue.png"))
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
            visible = false
        }
        addChild(clue)

        if (enemyValues.getValue(EnemyType.EYES) > 0) {
            eyesEnemy = EyesEnemy(labyrinth).apply {
                visible = false
                aiValue = enemyValues.getValue(EnemyType.EYES)
            }
            addChild(eyesEnemy)
            level.enemies += eyesEnemy
            moveEnemyRandom(eyesEnemy)
        } else {
            eyesEnemy = EyesEnemy(labyrinth).apply {
                visible = false
                aiValue = 0
            }
            addChild(eyesEnemy)
        }

        if (enemyValues.getValue(EnemyType.SOUND) > 0) {
            soundEnemy = SoundEnemy(labyrinth).apply {
                visible = false
                aiValue = enemyValues.getValue(EnemyType.SOUND)
            }
            addChild(soundEnemy)
            level.enemies += soundEnemy
            moveEnemyRandom(soundEnemy)
        } else {
            soundEnemy = SoundEnemy(labyrinth).apply {
                visible = false
                aiValue = 0
            }
            addChild(soundEnemy)
        }

        if (enemyValues.getValue(EnemyType.HEARING) > 0) {
            hearingEnemy = HearingEnemy(labyrinth).apply {
                visible = false
                aiValue = enemyValues.getValue(EnemyType.HEARING)
            }
            addChild(hearingEnemy)
            level.enemies += hearingEnemy
            moveEnemyRandom(hearingEnemy)
        } else {
            hearingEnemy = HearingEnemy(labyrinth).apply {
                visible = false
                aiValue = 0
            }
            addChild(hearingEnemy)
        }

        if (enemyValues.getValue(EnemyType.STOMPER) > 0) {
            stomperEnemy = StomperEnemy(labyrinth).apply {
                visible = false
                aiValue = enemyValues.getValue(EnemyType.STOMPER)
            }
            addChild(stomperEnemy)
            level.enemies += stomperEnemy
            moveEnemyRandom(stomperEnemy)
        } else {
            stomperEnemy = StomperEnemy(labyrinth).apply {
                visible = false
                aiValue = 0
            }
            addChild(stomperEnemy)
        }

        if (enemyValues.getValue(EnemyType.SNEAKER) > 0) {
            sneakerEnemy = SneakerEnemy(labyrinth).apply {
                visible = false
                aiValue = enemyValues.getValue(EnemyType.SNEAKER)
            }
            addChild(sneakerEnemy)
            level.enemies += sneakerEnemy
            moveEnemyRandom(sneakerEnemy)
        } else {
            sneakerEnemy = SneakerEnemy(labyrinth).apply {
                visible = false
                aiValue = 0
            }
            addChild(sneakerEnemy)
        }

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

        web1 = TextureRect().apply {
            texture = ImageTexture(labyrinth.assetManager.loadTexture("web_1.png"))
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
            visible = false
        }
        addChild(web1)

        web2 = TextureRect().apply {
            texture = ImageTexture(labyrinth.assetManager.loadTexture("web_2.png"))
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
            visible = false
        }
        addChild(web2)

        hands = Hands(labyrinth, movement)
        addChild(hands)

        mask = TextureRect().apply {
            texture = ImageTexture(labyrinth.assetManager.loadTexture("mask.png"))
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
            visible = false
        }
        addChild(mask)

        earplugs = TextureRect().apply {
            texture = ImageTexture(labyrinth.assetManager.loadTexture("earplugs.png"))
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
            visible = false
        }
        addChild(earplugs)

        map = TextureRect().apply {
            texture = ImageTexture(labyrinth.assetManager.loadTexture(level.mapTexture))
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
            visible = false
        }
        addChild(map)

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

        addEnvironmentFlair()

        labyrinth.eventBus.subscribe { event ->
            when (event) {
                is ChangeRoomEvent -> {
                    onRoomChange(event.room, event.prev)
                }
            }
        }

        roomName = Label().apply {
            text = currRoom?.name.toString()
            size = Vector2(125f, 200f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 48)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                }
            }
            position = Vector2(50f, 25f)
        }
        addChild(roomName)

        cluesCollected = Label().apply {
            text = "Clues Collected: 0/5"
            size = Vector2(250f, 200f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 36)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                }
            }
            position = Vector2(50f, 75f)
        }
        addChild(cluesCollected)

        clueRoom = level.rooms.filter { it !in level.exemptInitialClueRooms }.random()

        currentObjective = Label().apply {
            text = "Objective: Find the clue at ${clueRoom?.name.toString()}."
            size = Vector2(350f, 200f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 36)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                }
            }
            position = Vector2(50f, 115f)
        }
        addChild(currentObjective)

        pressKey = Label().apply {
            text = ""
            size = Vector2(350f, 200f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 36)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            position = Vector2((labyrinth.width - 350f) / 2, 600f)
            visible = false
        }
        addChild(pressKey)

        for (resolveSound in resolveSoundList) {
            resolveAudios += labyrinth.audioManager.loadAudio(resolveSound)
        }

        resolveSound = Sound().apply {
            volume = labyrinth.enemyVolume.toFloat()
        }
        addChild(resolveSound)

        val theme = GameTheme(labyrinth)
        audioMaster.theme = theme
        audioMaster.volume(0f)
        audioMaster.play()
        audioMaster.volume("bass.wav", musicVolume)

        whistle = Sound().apply {
            volume = labyrinth.sfxVolume.toFloat()
            audio = labyrinth.audioManager.loadAudio("whistle.wav")
        }
        addChild(whistle)

        gasMaskSound = Sound().apply {
            volume = labyrinth.sfxVolume.toFloat()
            audio = labyrinth.audioManager.loadAudio("gasmaskbreathing.wav")
            loop = true
        }
        addChild(gasMaskSound)

        earPlugSound = Sound().apply {
            volume = labyrinth.sfxVolume.toFloat()
            audio = labyrinth.audioManager.loadAudio("202085__deleted_user_3717932__whitenoise_1rlpf.wav")
            loop = true
        }
        addChild(earPlugSound)

        paperCrumpleSound = Sound().apply {
            volume = labyrinth.sfxVolume.toFloat()
            audio = labyrinth.audioManager.loadAudio("334200__wasabiwielder__paper-crumple-3.wav")
            loop = false
        }
        addChild(paperCrumpleSound)

        ambience = Sound().apply {
            volume = 0.05f * labyrinth.sfxVolume.toFloat()
            audio = labyrinth.audioManager.loadAudio("812600__modusmogulus__rooftop-vent-fan-seamless-loop.wav")
            loop = true
        }
        addChild(ambience)
        ambience.play()

        timer.start(20.0) { onTimerDone() }
    }

    override fun onUpdate(delta: Double) {
        if (isBlowingWhistle) {
            whistleTimer += delta

            if (whistleTimer >= 0.5f) {
                whistleTimer = 0.0
                isBlowingWhistle = false
            }
        }

        if (Input.wasKeyPressed(Key.NUM_1) && !isEarPlugOn && !isMapOpen) { // Put on mask
            isMaskOn = !isMaskOn
            mask.visible = isMaskOn
            if (isMaskOn) {
                gasMaskSound.play()
            } else {
                gasMaskSound.stop()
            }
        }

        if (Input.wasKeyPressed(Key.NUM_2) && !isMaskOn && !isEarPlugOn && !isBlowingWhistle && !isMapOpen) { // Blow whistle
            whistleCount++
            isBlowingWhistle = true
            whistle.play()
        }

        if (Input.wasKeyPressed(Key.NUM_3) && !isMaskOn && !isMapOpen) { // Earplug
            isEarPlugOn = !isEarPlugOn
            if (isEarPlugOn) {
                earPlugSound.play()
                earplugs.visible = true
                musicVolume = 0.4f * labyrinth.musicVolume.toFloat()
            } else {
                earPlugSound.stop()
                earplugs.visible = false
                musicVolume = labyrinth.musicVolume.toFloat()
            }
            updateMusicVolumes()
        }

        if (Input.wasKeyPressed(Key.M) && !isMaskOn && !isEarPlugOn && !isBlowingWhistle) {
            isMapOpen = !isMapOpen
            map.visible = isMapOpen
        }

        if (Input.wasKeyPressed(Key.F) && !isMaskOn && !isEarPlugOn && !isBlowingWhistle && !isMapOpen) {
            if (clueRoom === currRoom) {
                clue.visible = false
                collected++
                paperCrumpleSound.play()
                if (collected >= cluesRequired) {
                    clueRoom = null
                    cluesCollected.text = "All Clues Collected."
                    currentObjective.text = "Objective: Leave Through an Exit."
                } else {
                    clueRoom = level.getRoomsInRange(currRoom!!, 7, 8).filter { it !in level.exemptClueRooms }.random()
                    cluesCollected.text = "Clues Collected: $collected/$cluesRequired"
                    currentObjective.text = "Objective: Find the Clue in ${clueRoom?.name}."
                }
                pressKey.visible = false
            }
            if (currRoom in level.exitRooms) {
                if (collected >= cluesRequired) {
                    audioMaster.stop()
                    ambience.stop()
                    movement.stop()
                    currentEnemy?.encounterSound?.stop()
                    labyrinth.changeScreen(
                        GameOverScreen(
                            labyrinth,
                            audioMaster,
                            GameOverType.WIN,
                            isStoryMode,
                            levelNum,
                            enemyValues
                        )
                    )
                    return
                } else {
                    audioMaster.stop()
                    ambience.stop()
                    movement.stop()
                    currentEnemy?.encounterSound?.stop()
                    labyrinth.changeScreen(
                        GameOverScreen(
                            labyrinth,
                            audioMaster,
                            GameOverType.RAN_AWAY,
                            isStoryMode,
                            levelNum,
                            enemyValues
                        )
                    )
                    return
                }
            }
        }

        resolveEnemyMechanic(delta)
        resolveMovement()
    }

    fun onTimerDone() {
        for (enemy in level.enemies) {
            if (enemy.roll()) {
                val oldRoom = enemy.room
                val availableRooms = level.rooms.let { enemy.pickRoom() }
                    ?.let { listOf(it) }
                    ?.filter { it !in level.occupiedRooms || it == oldRoom }
                val selectedRoom = availableRooms?.randomOrNull() ?: continue
                enemy.room = selectedRoom
                level.occupiedRooms.add(selectedRoom)
                level.occupiedRooms.remove(oldRoom)

                println("$enemy enemy moved to: ${selectedRoom.name}")

                if (enemy.room === currRoom) {
                    onEnemyArrive(enemy)
                }
            }
        }

        if (!isInDanger) {
            fearLevel = (fearLevel - 1).coerceAtLeast(0)
            applyDynamicMusic()
        }

        timer.start(20.0) { onTimerDone() }
    }

    fun onRoomChange(room: Room, prev: Room) {
        currRoom = room

        roomName.text = room.name

        val isClueRoom = currRoom == clueRoom
        val isExitRoom = currRoom in level.exitRooms

        clue.visible = isClueRoom
        pressKey.visible = isClueRoom || isExitRoom

        if (clueRoom === currRoom) {
            pressKey.text = "Press F to Pick Up Clue."
        }
        if (currRoom in level.exitRooms) {
            pressKey.text = "Press F to Leave."
        }

        for (enemy in level.enemies) {
            if (enemy.room === currRoom) {
                if (enemy.type != EnemyType.STOMPER && isHuntedByStomper) {
                    moveEnemyRandom(enemy)
                    println("Stomper chase relocated $enemy to ${enemy.room?.name}")
                    break
                } else {
                    onEnemyArrive(enemy)
                    break
                }
            }
        }

        if (currentEnemy?.type == EnemyType.STOMPER && isHuntedByStomper) {
            if (moveCount >= movesNeeded) {
                isInDanger = false
                currentEnemy!!.visible = false
                (currentEnemy as StomperEnemy).encounterSound.stop()
                (currentEnemy as StomperEnemy).resolutionSound.play()
                moveEnemyRandom(currentEnemy!!)
                currentEnemy = null
                movesNeeded = 0
                moveCount = 0
                resolveTimer = 0.0
                isHuntedByStomper = false
            } else {
                currentEnemy!!.encounterSound.play()
                moveCount++
            }
        }

        println("Moved to Room: ${room.name}, " + "connections: ${room.connections}, facing: $direction")
        background.texture = getBackground(currRoom ?: return)
        addEnvironmentFlair()
    }

    fun onEnemyArrive(enemy: Enemy) {
        if (isInDanger && currentEnemy === enemy) return

        currentEnemy = enemy
        isInDanger = true

        enemy.visible = true

        enemy.encounterSound.play()

        fearLevel++

        if (fearLevel > 8) {
            handleGameOver(enemy, true)
            return
        }

        if (enemy is SneakerEnemy) {
            sneakerTimeNeeded = (Math.random() * 5.0) + 2.0
        }

        if (enemy is StomperEnemy) {
            isHuntedByStomper = true
            movesNeeded = (floor(Math.random() * 4) + 1).toInt()
        }

        if (enemy is HearingEnemy) {
            enemy.encounterSound.pitch = 1f
        }

        if (enemy is SoundEnemy) {
            whistleCount = 0
            whistleNeeded = enemy.recall()
        }

        applyDynamicMusic()

        println("Encountered enemy: ${enemy.name}, fear level: $fearLevel")
    }

    private fun resolveEnemyMechanic(delta: Double) {
        if (currentEnemy === null) return

        if (floor(Math.random()) > 0.8) {
            currentEnemy!!.encounterSound.pitch = Random.nextFloat().coerceIn(0.8f, 1f)
        }

        // Taking too long when in danger should GO you.
        if (resolveTimer > 7.0) {
            if (currentEnemy is SoundEnemy && whistleNeeded == whistleCount) {
                isInDanger = false
                currentEnemy!!.visible = false
                (currentEnemy as SoundEnemy).encounterSound.stop()
                resolveSound.audio = resolveAudios.random()
                resolveSound.play()
                moveEnemyRandom(currentEnemy!!)
                currentEnemy = null
                whistleCount = 0
                whistleNeeded = 0
                resolveTimer = 0.0
            } else {
                handleGameOver(currentEnemy!!, false)
                return
            }
        }

        when (currentEnemy) {
            is EyesEnemy -> {
                if (isMaskOn) {
                    eyesMaskTime += delta
                }

                if (eyesMaskTime > 3.5) {
                    isInDanger = false
                    currentEnemy!!.visible = false
                    (currentEnemy as EyesEnemy).encounterSound.stop()
                    resolveSound.audio = resolveAudios.random()
                    resolveSound.play()
                    moveEnemyRandom(currentEnemy!!)
                    currentEnemy = null
                    eyesMaskTime = 0.0
                    resolveTimer = 0.0
                }
            }

            is HearingEnemy -> {
                if (isEarPlugOn) {
                    earPlugTime += delta
                }

                if (earPlugTime > 3.5) {
                    isInDanger = false
                    currentEnemy!!.visible = false
                    (currentEnemy as HearingEnemy).encounterSound.stop()
                    resolveSound.audio = resolveAudios.random()
                    resolveSound.play()
                    moveEnemyRandom(currentEnemy!!)
                    currentEnemy = null
                    earPlugTime = 0.0
                    resolveTimer = 0.0
                }
            }

            is SneakerEnemy -> {
                if (sneakerTimeWaited >= sneakerTimeNeeded) {
                    isInDanger = false
                    currentEnemy!!.visible = false
                    (currentEnemy as SneakerEnemy).resolutionSound.play()
                    moveEnemyRandom(currentEnemy!!)
                    currentEnemy = null
                    sneakerTimeWaited = 0.0
                    sneakerTimeNeeded = 0.0
                    resolveTimer = 0.0
                } else {
                    sneakerTimeWaited += delta
                }
            }
        }

        resolveTimer += delta
    }

    private fun resolveMovement() {
        val currentRoom = currRoom ?: return

        val inputDirection = when {
            Input.wasKeyPressed(Key.UP) || Input.wasKeyPressed(Key.W) -> 0
            Input.wasKeyPressed(Key.RIGHT) || Input.wasKeyPressed(Key.D) -> 1
            Input.wasKeyPressed(Key.DOWN) || Input.wasKeyPressed(Key.S) -> 2
            Input.wasKeyPressed(Key.LEFT) || Input.wasKeyPressed(Key.A) -> 3
            else -> return
        }

        // Moving when in danger should GO you.
        if (isInDanger && currentEnemy != null && currentEnemy!!.type != EnemyType.STOMPER) {
            handleGameOver(currentEnemy!!, false)
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

    private fun handleGameOver(enemy: Enemy, didTransform: Boolean) {
        println("Killed by: $enemy")
        audioMaster.stop()
        ambience.stop()
        movement.stop()
        enemy.encounterSound.stop()
        if (didTransform) {
            labyrinth.changeScreen(
                JumpscareScreen(
                    labyrinth,
                    audioMaster,
                    enemy.type,
                    true,
                    isStoryMode,
                    levelNum,
                    enemyValues
                )
            )
        } else {
            labyrinth.changeScreen(
                JumpscareScreen(
                    labyrinth,
                    audioMaster,
                    enemy.type,
                    false,
                    isStoryMode,
                    levelNum,
                    enemyValues
                )
            )
        }

    }

    private fun addEnvironmentFlair() {
        currRoom?.let {
            pole1.visible = false
            pole2.visible = false
            web1.visible = false
            web2.visible = false

            if (it.pole == 1) {
                pole1.visible = true
            }
            if (it.pole == 2) {
                pole2.visible = true
            }
            if (it.web == 1) {
                web1.visible = true
            }
            if (it.web == 2) {
                web2.visible = true
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

    private fun updateMusicVolumes() {
        val volume = musicVolume

        audioMaster.volume("bass.wav", volume)
        for (level in 1..fearLevel) {
            when (level) {
                1 -> audioMaster.volume("keys.wav", volume)
                2 -> audioMaster.volume("kick.wav", volume)
                3 -> audioMaster.volume("snare1.wav", volume)
                4 -> audioMaster.volume("snare2.wav", volume)
                6 -> audioMaster.volume("beep.wav", volume)
                7 -> audioMaster.volume("breathe.wav", volume)
            }
        }
    }

    private fun applyDynamicMusic() {
        if (fearLevel > previousFearLevel) {
            for (level in (previousFearLevel + 1)..fearLevel) {
                applyFearLevel(level)
            }
        } else if (fearLevel < previousFearLevel) {
            for (level in previousFearLevel downTo (fearLevel + 1)) {
                removeFearLevel(level)
            }

            applyFearLevel(fearLevel)
        }

        previousFearLevel = fearLevel
        updateMusicVolumes()
    }

    private fun applyFearLevel(level: Int) {
        when (level) {
            0 -> bunbunFace.texture = normal
            1 -> bunbunFace.texture = unhappy1
            2 -> bunbunFace.texture = unhappy2
            3 -> bunbunFace.texture = scared1
            4 -> bunbunFace.texture = scared2
            5 -> bunbunFace.texture = agony
            6 -> bunbunFace.texture = acceptance
            7 -> bunbunFace.texture = look
        }
    }

    private fun removeFearLevel(level: Int) {
        when (level) {
            1 -> audioMaster.volume("keys.wav", 0f)
            2 -> audioMaster.volume("kick.wav", 0f)
            3 -> audioMaster.volume("snare1.wav", 0f)
            4 -> audioMaster.volume("snare2.wav", 0f)
            6 -> audioMaster.volume("beep.wav", 0f)
            7 -> audioMaster.volume("breathe.wav", 0f)
        }
    }

    private fun moveEnemyRandom(enemy: Enemy) {
        val oldRoom = enemy.room
        val availableRooms = level.rooms.filter { it !in level.occupiedRooms || it == oldRoom || it != currRoom }
        val selectedRoom = availableRooms.random()
        enemy.room = selectedRoom
        level.occupiedRooms.add(selectedRoom)
        level.occupiedRooms.remove(oldRoom)

        println("$enemy enemy relocated to: ${selectedRoom.name}, after successful counter.")
    }

}