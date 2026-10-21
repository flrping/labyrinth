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
import dev.flrp.labyrinth.mechanics.structure.FirstLevel

class IntroStoryScreen(val labyrinth: Labyrinth, val audioMaster: AudioMaster) : Node() {

    lateinit var story1: ImageTexture
    lateinit var story2: ImageTexture
    lateinit var story3: ImageTexture
    lateinit var story4: ImageTexture

    lateinit var background: TextureRect
    lateinit var textBackground: NineSliceRect
    lateinit var speaker: Label
    lateinit var text: Label
    lateinit var skipText: Label

    lateinit var sound: Sound
    lateinit var chiefSound: Sound
    lateinit var bunbunSound: Sound

    val dialogueLines = listOf(
        DialogueLine("You", "You called?"),
        DialogueLine("Chief", "We have you on a new case. Here's the debrief."),
        DialogueLine("You", "Uhh... should I be solo? I am fairly new to this department."),
        DialogueLine(
            "Chief",
            "You have done good work these past few months. I think you can handle this and make a better impression."
        ),
        DialogueLine("Chief", "They have been discussing your promotion because of it. They will have you at my rank."),
        DialogueLine("Chief", "You have been wanting a promotion, no?"),
        DialogueLine("You", "Yes, but this seems a bit...."),
        DialogueLine(
            "Chief",
            "It isn't complicated, all you have to do is look around and report your findings. You'll find a way."
        ),
        DialogueLine("You", "This location does not seem stable though. We need more units if I were to go."),
        DialogueLine(
            "Chief",
            "You should be on your way now. We need units on other things. We shouldn't put too much on a recon job."
        ),
        DialogueLine("You", "But-"),
        DialogueLine("Chief", "Just do your job."),

        //
        DialogueLine("You", "I guess this is it. Depressing."),
        DialogueLine("You", "Many noise complaints come from this place. Civilians think it's the homeless."),
        DialogueLine("You", "If only we had better policies...."),
        DialogueLine("", "*Bang* *Bang*", labyrinth.audioManager.loadAudio("the_stomper_encounter.wav")),
        DialogueLine("", "*Bang* *Bang*", labyrinth.audioManager.loadAudio("the_stomper_encounter.wav")),
        DialogueLine("", "*Laughs*", labyrinth.audioManager.loadAudio("giggle3.wav")),
        DialogueLine("You", "What on earth?!"),

        //
        DialogueLine("You", "Those noises...."),
        DialogueLine("You", "The entrance is completely blocked by rubble."),
        DialogueLine("You", "Everywhere else is just dense foliage. I can't go anywhere else..."),

        //
        DialogueLine("You", "There is just this open vent, it seems to lead inside. Should I really do this alone?"),
        DialogueLine("You", "Well I can't go back empty-handed. The weird noises I heard too. Hmm."),
        DialogueLine("You", "I need to figure this out."),
    )

    var index = 0
    var visibleCharacters = 0
    var typingTimer = 0.0
    var finishedTimer = 0.0

    override fun onReady() {

        story1 = ImageTexture(labyrinth.assetManager.loadTexture("story1.png"))
        story2 = ImageTexture(labyrinth.assetManager.loadTexture("story2.png"))
        story3 = ImageTexture(labyrinth.assetManager.loadTexture("story3.png"))
        story4 = ImageTexture(labyrinth.assetManager.loadTexture("story4.png"))

        background = TextureRect().apply {
            texture = story1
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

        val theme = TitleTheme(labyrinth)
        if (audioMaster.theme?.name != theme.name) {
            audioMaster.theme = theme
            audioMaster.play()
        }
    }

    override fun onUpdate(delta: Double) {
        if (Input.wasKeyPressed(Key.ENTER)) {
            labyrinth.sawIntro = true
            labyrinth.changeScreen(
                GameScreen(
                    labyrinth,
                    audioMaster,
                    FirstLevel(),
                    true,
                    1,
                    Defaults.LEVEL_ONE_DEFAULTS
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
                    if (dialogueLines[index].noise !== null) {
                        sound.audio = dialogueLines[index].noise!!
                        sound.play()
                    }
                }

                onDialogueChange()
            }
        }
    }

    fun onDialogueChange() {
        when (index) {
            12 -> background.texture = story2
            19 -> background.texture = story3
            23 -> background.texture = story4
        }

        if (index >= dialogueLines.size) {
            labyrinth.sawIntro = true
            labyrinth.changeScreen(
                GameScreen(
                    labyrinth,
                    audioMaster,
                    FirstLevel(),
                    true,
                    1,
                    Defaults.LEVEL_ONE_DEFAULTS
                )
            )
        }
    }
}