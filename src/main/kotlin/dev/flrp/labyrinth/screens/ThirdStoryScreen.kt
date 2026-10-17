package dev.flrp.labyrinth.screens

import com.wyrmlings.rune.input.Input
import com.wyrmlings.rune.input.Key
import com.wyrmlings.rune.math.Vector2
import com.wyrmlings.rune.node.Node
import com.wyrmlings.rune.node.Sound
import com.wyrmlings.rune.node.xy.ui.Label
import com.wyrmlings.rune.node.xy.ui.NineSliceRect
import com.wyrmlings.rune.node.xy.ui.TextureRect
import com.wyrmlings.rune.property.Color
import com.wyrmlings.rune.ui.style.text.FontSettings
import com.wyrmlings.rune.ui.style.text.TextDecoration
import com.wyrmlings.rune.ui.texture.ImageTexture
import dev.flrp.labyrinth.Defaults
import dev.flrp.labyrinth.Labyrinth
import dev.flrp.labyrinth.audio.AudioMaster
import dev.flrp.labyrinth.audio.TitleTheme
import dev.flrp.labyrinth.mechanics.structure.DialogueLine
import dev.flrp.labyrinth.mechanics.structure.ThirdLevel

class ThirdStoryScreen(val labyrinth: Labyrinth, val audioMaster: AudioMaster) : Node() {

    lateinit var story5: ImageTexture

    lateinit var background: TextureRect
    lateinit var textBackground: NineSliceRect
    lateinit var speaker: Label
    lateinit var text: Label
    lateinit var skipText: Label

    lateinit var sound: Sound
    lateinit var chiefSound: Sound
    lateinit var bunbunSound: Sound
    lateinit var unfamiliarSound: Sound

    val dialogueLines = listOf(
        DialogueLine("", "*Bzzzt*"),
        DialogueLine("You", "Chief, it is getting really bad over here."),
        DialogueLine("You", "I really need more units."),
        DialogueLine("", "*Static*"),
        DialogueLine("You", "Hello?"),
        DialogueLine("Unfamiliar Voice", "Help me."),
        DialogueLine("Unfamiliar Voice", "Save me."),
        DialogueLine("Unfamiliar Voice", "Help me. Save me. Help me."),
        DialogueLine("You", "Hello?? Where are you?"),
        DialogueLine("Unfamiliar Voice", "You were just here.... You were just here...."),
        DialogueLine("Unfamiliar Voice", "You have to come back...."),
        DialogueLine("You", "What?!? The vents?"),
        DialogueLine("", "*Static*"),
        DialogueLine("You", "Sigh...."),
    )

    var index = 0
    var visibleCharacters = 0
    var typingTimer = 0.0
    var finishedTimer = 0.0

    override fun onReady() {
        story5 = ImageTexture(labyrinth.assetManager.loadTexture("story5.png"))

        background = TextureRect().apply {
            texture = story5
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
        }
        addChild(background)

        textBackground = NineSliceRect().apply {
            texture = ImageTexture(labyrinth.assetManager.loadTexture("nineslicebutton.png"))
            size = Vector2(1000f, 175f)
            position = Vector2(180f, 25f)
            left = 4f
            right = 4f
            top = 4f
            bottom = 4f
        }
        addChild(textBackground)

        speaker = Label().apply {
            text = dialogueLines[index].target
            size = Vector2(900f, 50f)
            position = Vector2(230f, 50f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 36)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.ORANGE
                }
            }
        }
        addChild(speaker)

        text = Label().apply {
            text = ""
            size = Vector2(900f, 175f)
            position = Vector2(230f, 80f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 36)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                }
            }
        }
        addChild(text)

        skipText = Label().apply {
            text = "Press Enter to Skip"
            size = Vector2(200f, 175f)
            position = Vector2(25f, 710f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 36)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    opacity = 0.5f
                }
            }
        }
        addChild(skipText)

        sound = Sound().apply {
            volume = labyrinth.sfxVolume.toFloat()
        }
        addChild(sound)

        chiefSound = Sound().apply {
            audio = labyrinth.audioManager.loadAudio("bubble.wav")
            volume = (0.7 * labyrinth.sfxVolume).toFloat()
            pitch = 0.9f
        }
        addChild(chiefSound)

        bunbunSound = Sound().apply {
            audio = labyrinth.audioManager.loadAudio("bubble.wav")
            volume = (0.7 * labyrinth.sfxVolume).toFloat()
            pitch = 1.2f
        }
        addChild(bunbunSound)

        unfamiliarSound = Sound().apply {
            audio = labyrinth.audioManager.loadAudio("bubble.wav")
            volume = (0.7 * labyrinth.sfxVolume).toFloat()
            pitch = 0.5f
        }
        addChild(unfamiliarSound)

        val theme = TitleTheme(labyrinth)
        if (audioMaster.theme?.name != theme.name) {
            audioMaster.theme = theme
            audioMaster.play()
        }
    }

    override fun onUpdate(delta: Double) {
        if(Input.wasKeyPressed(Key.ENTER)) {
            labyrinth.sawThirdCutscene = true
            labyrinth.changeScreen(
                GameScreen(
                    labyrinth,
                    audioMaster,
                    ThirdLevel(),
                    true,
                    3,
                    Defaults.LEVEL_THREE_DEFAULTS
                )
            )
        }

        if (index >= dialogueLines.size) return
        val line = dialogueLines[index].line

        if (visibleCharacters < line.length) {
            typingTimer += delta

            val nextCharacter = line[visibleCharacters]
            val typingDelay = when (nextCharacter) {
                '.' -> 0.4
                ',' -> 0.2
                '!', '?' -> 0.3
                else -> 0.05
            }

            if (typingTimer >= typingDelay) {
                visibleCharacters++
                typingTimer = 0.0

                if (nextCharacter.isLetterOrDigit()) {
                    if (dialogueLines[index].target == "Chief") {
                        chiefSound.play()
                    } else if (dialogueLines[index].target == "You") {
                        bunbunSound.play()
                    } else if (dialogueLines[index].target == "Unfamiliar Voice") {
                        unfamiliarSound.play()
                    }
                }

                text.text = line.substring(0, visibleCharacters)
            }
        } else {
            finishedTimer += delta

            if (finishedTimer >= 1.0) {
                index++
                visibleCharacters = 0
                typingTimer = 0.0
                finishedTimer = 0.0

                if (index < dialogueLines.size) {
                    speaker.text = dialogueLines[index].target
                    text.text = ""
                    if(dialogueLines[index].noise !== null) {
                        sound.audio = dialogueLines[index].noise!!
                        sound.play()
                    }
                }

                onDialogueChange()
            }
        }
    }

    fun onDialogueChange() {
        if(index >= dialogueLines.size) {
            labyrinth.sawThirdCutscene = true
            labyrinth.changeScreen(
                GameScreen(
                    labyrinth,
                    audioMaster,
                    ThirdLevel(),
                    true,
                    3,
                    Defaults.LEVEL_THREE_DEFAULTS
                )
            )
        }
    }
}