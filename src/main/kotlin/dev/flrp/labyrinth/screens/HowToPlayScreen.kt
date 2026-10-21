package dev.flrp.labyrinth.screens

import com.wyrmlings.rune.input.MouseButton
import com.wyrmlings.rune.math.Vector2
import com.wyrmlings.rune.node.Node
import com.wyrmlings.rune.node.xy.ui.ColorRect
import com.wyrmlings.rune.node.xy.ui.Label
import com.wyrmlings.rune.property.Color
import com.wyrmlings.rune.ui.style.container.FlatContainer
import com.wyrmlings.rune.ui.style.text.FontSettings
import com.wyrmlings.rune.ui.style.text.TextAlign
import com.wyrmlings.rune.ui.style.text.TextDecoration
import com.wyrmlings.rune.ui.style.text.TextPosition
import com.wyrmlings.rune.ui.texture.ImageTexture
import dev.flrp.labyrinth.Labyrinth
import dev.flrp.labyrinth.audio.AudioMaster
import dev.flrp.labyrinth.mechanics.enemy.*
import dev.flrp.labyrinth.ui.NineSliceButton

class HowToPlayScreen(val labyrinth: Labyrinth, val audioMaster: AudioMaster) : Node() {

    var page = 0
    lateinit var eyesEnemy: EyesEnemy
    lateinit var soundEnemy: SoundEnemy
    lateinit var hearingEnemy: HearingEnemy
    lateinit var stomperEnemy: StomperEnemy
    lateinit var sneakerEnemy: SneakerEnemy

    lateinit var label: Label
    lateinit var enemyLabel: Label

    override fun onReady() {

        val background = ColorRect().apply {
            container = FlatContainer().apply {
                fillColor = Color.fromHex("#424242")
            }
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
        }
        addChild(background)

        label = Label().apply {
            text = """
                How to Play:
                Movement:
                WASD or Arrow to Move.
                [M] to open the Map.
                [1] equips or unequips your gas mask.
                [2] blows your whistle.
                [3] equips or unequips your ear plugs.
                
                Equipment:
                Gas Mask: Handy mask for avoiding hazardous gasses. Also useful for masking your face.
                Whistle: An item every officer has. Handy for making noise.
                Ear Plugs: Useful when handling loud equipment. Great for blocking sound.
            """.trimIndent()
            position = Vector2(0f, 0f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 36)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
        }
        addChild(label)

        val nineSliceTexture = ImageTexture(labyrinth.assetManager.loadTexture("nineslicebutton.png"))
        val nineSliceTextureActive = ImageTexture(labyrinth.assetManager.loadTexture("nineslicebutton_active.png"))

        val backButton = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                labyrinth.changeScreen(TitleScreen(labyrinth, audioMaster))
            }
        }.apply {
            text = "Back to Title"
            position = Vector2((labyrinth.width - 200f) / 2, 640f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 24)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            size = Vector2(200f, 50f)
        }
        addChild(backButton)

        val nextPageButton = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                page = (page + 1) % 6
                onPageChange()
            }
        }.apply {
            text = "Next Page"
            position = Vector2(((labyrinth.width - 200f) / 2) + 225, 640f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 24)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            size = Vector2(200f, 50f)
        }
        addChild(nextPageButton)

        val previousPageButton = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                page = (page - 1 + 6) % 6
                onPageChange()
            }
        }.apply {
            text = "Previous Page"
            position = Vector2(((labyrinth.width - 200f) / 2) - 225, 640f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 24)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            size = Vector2(200f, 50f)
        }
        addChild(previousPageButton)

        eyesEnemy = EyesEnemy(labyrinth).apply { visible = false; position = Vector2(-300f, 0f) }
        addChild(eyesEnemy)

        soundEnemy = SoundEnemy(labyrinth).apply { visible = false; position = Vector2(-300f, 0f) }
        addChild(soundEnemy)

        hearingEnemy = HearingEnemy(labyrinth).apply { visible = false; position = Vector2(-300f, 0f) }
        addChild(hearingEnemy)

        stomperEnemy = StomperEnemy(labyrinth).apply { visible = false; position = Vector2(-300f, 0f) }
        addChild(stomperEnemy)

        sneakerEnemy = SneakerEnemy(labyrinth).apply { visible = false; position = Vector2(-300f, 0f) }
        addChild(sneakerEnemy)

        enemyLabel = Label().apply {
            text = ""
            position = Vector2((labyrinth.width.toFloat() / 2) + 50, 0f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 36)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            size = Vector2((labyrinth.width.toFloat() / 2) - 100, labyrinth.height.toFloat())
        }
        addChild(enemyLabel)

    }

    fun onPageChange() {
        label.visible = false
        eyesEnemy.visible = false
        soundEnemy.visible = false
        hearingEnemy.visible = false
        stomperEnemy.visible = false
        sneakerEnemy.visible = false
        when (page) {
            0 -> {
                label.visible = true
                enemyLabel.text = ""
            }

            1 -> {
                eyesEnemy.visible = true
                enemyLabel.text = """
                    Description:
                    ${eyesEnemy.description}
                    
                    Counterplay:
                    ${eyesEnemy.resolution}
                """.trimIndent()
            }

            2 -> {
                soundEnemy.visible = true
                enemyLabel.text = """
                    Description:
                    ${soundEnemy.description}
                    
                    Counterplay:
                    ${soundEnemy.resolution}
                """.trimIndent()
            }

            3 -> {
                hearingEnemy.visible = true
                enemyLabel.text = """
                    Description:
                    ${hearingEnemy.description}
                    
                    Counterplay:
                    ${hearingEnemy.resolution}
                """.trimIndent()
            }

            4 -> {
                enemyLabel.text = """
                    Description:
                    ${stomperEnemy.description}
                    
                    Counterplay:
                    ${stomperEnemy.resolution}
                """.trimIndent()
            }

            5 -> {
                sneakerEnemy.visible = true
                enemyLabel.text = """
                    Description:
                    ${sneakerEnemy.description}
                    
                    Counterplay:
                    ${sneakerEnemy.resolution}
                """.trimIndent()
            }
        }
    }

}