package dev.flrp.labyrinth.screens

import com.wyrmlings.rune.input.MouseButton
import com.wyrmlings.rune.math.Vector2
import com.wyrmlings.rune.node.Node
import com.wyrmlings.rune.node.xy.ui.Label
import com.wyrmlings.rune.node.xy.ui.TextureRect
import com.wyrmlings.rune.property.Color
import com.wyrmlings.rune.ui.style.text.FontSettings
import com.wyrmlings.rune.ui.style.text.TextAlign
import com.wyrmlings.rune.ui.style.text.TextDecoration
import com.wyrmlings.rune.ui.style.text.TextPosition
import com.wyrmlings.rune.ui.texture.ImageTexture
import dev.flrp.labyrinth.Labyrinth
import dev.flrp.labyrinth.audio.AudioMaster
import dev.flrp.labyrinth.ui.NineSliceButton

class SettingsScreen(val labyrinth: Labyrinth, val audioMaster: AudioMaster) : Node() {

    lateinit var background: TextureRect

    lateinit var gammaLabel: Label
    lateinit var gammaMinus: NineSliceButton
    lateinit var gammaPlus: NineSliceButton

    lateinit var musicVolumeLabel: Label
    lateinit var musicVolumeMinus: NineSliceButton
    lateinit var musicVolumePlus: NineSliceButton

    lateinit var enemyVolumeLabel: Label
    lateinit var enemyVolumeMinus: NineSliceButton
    lateinit var enemyVolumePlus: NineSliceButton

    lateinit var sfxVolumeLabel: Label
    lateinit var sfxVolumeMinus: NineSliceButton
    lateinit var sfxVolumePlus: NineSliceButton

    override fun onReady() {

        background = TextureRect().apply {
            texture = ImageTexture(labyrinth.assetManager.loadTexture("mainmenu.png"))
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
        }
        addChild(background)

        val nineSliceTexture = ImageTexture(labyrinth.assetManager.loadTexture("nineslicebutton.png"))
        val nineSliceTextureActive = ImageTexture(labyrinth.assetManager.loadTexture("nineslicebutton_active.png"))

        gammaLabel = Label().apply {
            text = "Gamma: ${String.format("%.2f", labyrinth.gameGamma)}"
            position = Vector2(140f, 100f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 36)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                }
            }
            size = Vector2(200f, 50f)
        }
        addChild(gammaLabel)

        gammaMinus = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                labyrinth.gameGamma -= 0.1
                gammaLabel.text = "Gamma: ${String.format("%.2f", labyrinth.gameGamma)}"
            }
        }.apply {
            text = "-"
            position = Vector2(265f, 110f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 24)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            size = Vector2(30f, 30f)
        }
        addChild(gammaMinus)

        gammaPlus = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                labyrinth.gameGamma += 0.1
                gammaLabel.text = "Gamma: ${String.format("%.2f", labyrinth.gameGamma)}"
            }
        }.apply {
            text = "+"
            position = Vector2(305f, 110f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 24)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            size = Vector2(30f, 30f)
        }
        addChild(gammaPlus)

        musicVolumeLabel = Label().apply {
            text = "Music Volume: ${String.format("%.2f", labyrinth.musicVolume)}"
            position = Vector2(80f, 150f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 36)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                }
            }
            size = Vector2(200f, 50f)
        }
        addChild(musicVolumeLabel)

        musicVolumeMinus = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                labyrinth.musicVolume -= 0.10
                musicVolumeLabel.text = "Music Volume: ${String.format("%.2f", labyrinth.musicVolume)}"
            }
        }.apply {
            text = "-"
            position = Vector2(265f, 160f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 24)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            size = Vector2(30f, 30f)
        }
        addChild(musicVolumeMinus)

        musicVolumePlus = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                labyrinth.musicVolume += 0.10
                musicVolumeLabel.text = "Music Volume: ${String.format("%.2f", labyrinth.musicVolume)}"
            }
        }.apply {
            text = "+"
            position = Vector2(305f, 160f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 24)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            size = Vector2(30f, 30f)
        }
        addChild(musicVolumePlus)

        enemyVolumeLabel = Label().apply {
            text = "Enemy Volume: ${String.format("%.2f", labyrinth.enemyVolume)}"
            position = Vector2(70f, 200f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 36)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                }
            }
            size = Vector2(250f, 50f)
        }
        addChild(enemyVolumeLabel)

        enemyVolumeMinus = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                labyrinth.enemyVolume -= 0.1
                enemyVolumeLabel.text = "Enemy Volume: ${String.format("%.2f", labyrinth.enemyVolume)}"
            }
        }.apply {
            text = "-"
            position = Vector2(265f, 210f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 24)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            size = Vector2(30f, 30f)
        }
        addChild(enemyVolumeMinus)

        enemyVolumePlus = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                labyrinth.enemyVolume += 0.1
                enemyVolumeLabel.text = "Enemy Volume: ${String.format("%.2f", labyrinth.enemyVolume)}"
            }
        }.apply {
            text = "+"
            position = Vector2(305f, 210f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 24)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            size = Vector2(30f, 30f)
        }
        addChild(enemyVolumePlus)

        sfxVolumeLabel = Label().apply {
            text = "SFX Volume: ${String.format("%.2f", labyrinth.sfxVolume)}"
            position = Vector2(100f, 250f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 36)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                }
            }
            size = Vector2(200f, 50f)
        }
        addChild(sfxVolumeLabel)

        sfxVolumeMinus = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                labyrinth.sfxVolume -= 0.1
                sfxVolumeLabel.text = "SFX Volume: ${String.format("%.2f", labyrinth.sfxVolume)}"
            }
        }.apply {
            text = "-"
            position = Vector2(265f, 260f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 24)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            size = Vector2(30f, 30f)
        }
        addChild(sfxVolumeMinus)

        sfxVolumePlus = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                labyrinth.sfxVolume += 0.1
                sfxVolumeLabel.text = "SFX Volume: ${String.format("%.2f", labyrinth.sfxVolume)}"
            }
        }.apply {
            text = "+"
            position = Vector2(305f, 260f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 24)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            size = Vector2(30f, 30f)
        }
        addChild(sfxVolumePlus)

        val backButton = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                labyrinth.changeScreen(TitleScreen(labyrinth, audioMaster))
            }
        }.apply {
            text = "Back to Title"
            position = Vector2(115f, 570f)
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
    }

}