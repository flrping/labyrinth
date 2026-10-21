package dev.flrp.labyrinth.screens

import com.wyrmlings.rune.input.MouseButton
import com.wyrmlings.rune.math.Vector2
import com.wyrmlings.rune.node.Node
import com.wyrmlings.rune.node.xy.ui.ColorRect
import com.wyrmlings.rune.node.xy.ui.Label
import com.wyrmlings.rune.node.xy.ui.TextureRect
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
import dev.flrp.labyrinth.mechanics.structure.FirstLevel
import dev.flrp.labyrinth.mechanics.structure.SecondLevel
import dev.flrp.labyrinth.mechanics.structure.ThirdLevel
import dev.flrp.labyrinth.ui.NineSliceButton

class FreePlayScreen(val labyrinth: Labyrinth, val audioMaster: AudioMaster) : Node() {

    lateinit var eyesEnemy: EyesEnemy
    lateinit var soundEnemy: SoundEnemy
    lateinit var hearingEnemy: HearingEnemy
    lateinit var stomperEnemy: StomperEnemy
    lateinit var sneakerEnemy: SneakerEnemy

    lateinit var label: Label

    var selectedMap: Int = 1
    var enemyValues = mutableMapOf(
        EnemyType.EYES to 1,
        EnemyType.SOUND to 1,
        EnemyType.HEARING to 1,
        EnemyType.SNEAKER to 1,
        EnemyType.STOMPER to 1
    )

    override fun onReady() {
        val background = ColorRect().apply {
            container = FlatContainer().apply {
                fillColor = Color.fromHex("#424242")
            }
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
        }
        addChild(background)

        val nineSliceTexture = ImageTexture(labyrinth.assetManager.loadTexture("nineslicebutton.png"))
        val nineSliceTextureActive = ImageTexture(labyrinth.assetManager.loadTexture("nineslicebutton_active.png"))

        val backButton = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                labyrinth.changeScreen(TitleScreen(labyrinth, audioMaster))
            }
        }.apply {
            text = "Back to Title"
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
        addChild(backButton)

        val startButton = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                when (selectedMap) {
                    2 -> {
                        labyrinth.changeScreen(
                            GameScreen(
                                labyrinth,
                                audioMaster,
                                SecondLevel(),
                                false,
                                2,
                                enemyValues
                            )
                        )
                    }

                    3 -> {
                        labyrinth.changeScreen(
                            GameScreen(
                                labyrinth,
                                audioMaster,
                                ThirdLevel(),
                                false,
                                3,
                                enemyValues
                            )
                        )
                    }

                    else -> {
                        labyrinth.changeScreen(
                            GameScreen(
                                labyrinth,
                                audioMaster,
                                FirstLevel(),
                                false,
                                1,
                                enemyValues
                            )
                        )
                    }
                }
            }
        }.apply {
            text = "Start"
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
        addChild(startButton)

        eyesEnemy = EyesEnemy(labyrinth).apply { scale = 0.3f; position = Vector2(-20f, 0f) }
        addChild(eyesEnemy)

        soundEnemy = SoundEnemy(labyrinth).apply { scale = 0.3f; position = Vector2(240f, 0f) }
        addChild(soundEnemy)

        hearingEnemy = HearingEnemy(labyrinth).apply { scale = 0.3f; position = Vector2(480f, 10f) }
        addChild(hearingEnemy)

        stomperEnemy = StomperEnemy(labyrinth).apply { scale = 0.3f; visible = false; position = Vector2(700f, 0f) }
        addChild(stomperEnemy)

        sneakerEnemy = SneakerEnemy(labyrinth).apply { scale = 0.3f; position = Vector2(980f, 20f) }
        addChild(sneakerEnemy)

        val map1 = TextureRect().apply {
            texture = ImageTexture(labyrinth.assetManager.loadTexture("map_1.png"))
            scale = 0.3f
            position = Vector2(240f, 300f)
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
        }
        addChild(map1)

        val map2 = TextureRect().apply {
            texture = ImageTexture(labyrinth.assetManager.loadTexture("map_2.png"))
            scale = 0.3f
            position = Vector2(490f, 300f)
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
        }
        addChild(map2)

        val map3 = TextureRect().apply {
            texture = ImageTexture(labyrinth.assetManager.loadTexture("map_3.png"))
            scale = 0.3f
            position = Vector2(740f, 300f)
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
        }
        addChild(map3)

        val customFontSettings = FontSettings().apply {
            font = labyrinth.assetManager.loadFont("JockeyOne", 24)
            textDecoration = TextDecoration().apply {
                strokeColor = Color.WHITE
                textPosition = TextPosition.CENTER
                textAlign = TextAlign.CENTER
            }
        }

        val selectedMapLabel = Label().apply {
            text = "Selected Map: $selectedMap"
            position = Vector2(520f, 530f)
            fontSettings = customFontSettings
            size = Vector2(200f, 50f)
        }
        addChild(selectedMapLabel)

        val mapMinus = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                selectedMap--
                if (selectedMap <= 0) selectedMap = 1
                selectedMapLabel.text = "Selected Map: $selectedMap"
            }
        }.apply {
            text = "-"
            position = Vector2(680f, 540f)
            fontSettings = customFontSettings
            size = Vector2(30f, 30f)
        }
        addChild(mapMinus)

        val mapPlus = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                selectedMap++
                if (selectedMap >= 4) selectedMap = 3
                selectedMapLabel.text = "Selected Map: $selectedMap"
            }
        }.apply {
            text = "+"
            position = Vector2(715f, 540f)
            fontSettings = customFontSettings
            size = Vector2(30f, 30f)
        }
        addChild(mapPlus)

        val eyesLabel = Label().apply {
            text = "EYES Enemy: ${enemyValues[EnemyType.EYES]}"
            position = Vector2(50f, 170f)
            fontSettings = customFontSettings
            size = Vector2(200f, 50f)
        }
        addChild(eyesLabel)

        val eyesMinus = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                updateEnemyCount(EnemyType.EYES, eyesLabel, -1)
            }
        }.apply {
            text = "-"
            position = Vector2(215f, 180f)
            fontSettings = customFontSettings
            size = Vector2(30f, 30f)
        }
        addChild(eyesMinus)

        val eyesPlus = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                updateEnemyCount(EnemyType.EYES, eyesLabel, 1)
            }
        }.apply {
            text = "+"
            position = Vector2(255f, 180f)
            fontSettings = customFontSettings
            size = Vector2(30f, 30f)
        }
        addChild(eyesPlus)


        val soundLabel = Label().apply {
            text = "SOUND Enemy: ${enemyValues[EnemyType.SOUND]}"
            position = Vector2(300f, 170f)
            fontSettings = customFontSettings
            size = Vector2(200f, 50f)
        }
        addChild(soundLabel)

        val soundMinus = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                updateEnemyCount(EnemyType.SOUND, soundLabel, -1)
            }
        }.apply {
            text = "-"
            position = Vector2(465f, 180f)
            fontSettings = customFontSettings
            size = Vector2(30f, 30f)
        }
        addChild(soundMinus)

        val soundPlus = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                updateEnemyCount(EnemyType.SOUND, soundLabel, 1)
            }
        }.apply {
            text = "+"
            position = Vector2(500f, 180f)
            fontSettings = customFontSettings
            size = Vector2(30f, 30f)
        }
        addChild(soundPlus)


        val hearingLabel = Label().apply {
            text = "HEARING Enemy: ${enemyValues[EnemyType.HEARING]}"
            position = Vector2(550f, 170f)
            fontSettings = customFontSettings
            size = Vector2(200f, 50f)
        }
        addChild(hearingLabel)

        val hearingMinus = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                updateEnemyCount(EnemyType.HEARING, hearingLabel, -1)
            }
        }.apply {
            text = "-"
            position = Vector2(715f, 180f)
            fontSettings = customFontSettings
            size = Vector2(30f, 30f)
        }
        addChild(hearingMinus)

        val hearingPlus = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                updateEnemyCount(EnemyType.HEARING, hearingLabel, 1)
            }
        }.apply {
            text = "+"
            position = Vector2(750f, 180f)
            fontSettings = customFontSettings
            size = Vector2(30f, 30f)
        }
        addChild(hearingPlus)


        val stomperLabel = Label().apply {
            text = "STOMPER Enemy: ${enemyValues[EnemyType.STOMPER]}"
            position = Vector2(800f, 170f)
            fontSettings = customFontSettings
            size = Vector2(200f, 50f)
        }
        addChild(stomperLabel)

        val stomperMinus = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                updateEnemyCount(EnemyType.STOMPER, stomperLabel, -1)
            }
        }.apply {
            text = "-"
            position = Vector2(965f, 180f)
            fontSettings = customFontSettings
            size = Vector2(30f, 30f)
        }
        addChild(stomperMinus)

        val stomperPlus = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                updateEnemyCount(EnemyType.STOMPER, stomperLabel, 1)
            }
        }.apply {
            text = "+"
            position = Vector2(1000f, 180f)
            fontSettings = customFontSettings
            size = Vector2(30f, 30f)
        }
        addChild(stomperPlus)


        val sneakerLabel = Label().apply {
            text = "SNEAKER Enemy: ${enemyValues[EnemyType.SNEAKER]}"
            position = Vector2(1050f, 170f)
            fontSettings = customFontSettings
            size = Vector2(200f, 50f)
        }
        addChild(sneakerLabel)

        val sneakerMinus = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                updateEnemyCount(EnemyType.SNEAKER, sneakerLabel, -1)
            }
        }.apply {
            text = "-"
            position = Vector2(1215f, 180f)
            fontSettings = customFontSettings
            size = Vector2(30f, 30f)
        }
        addChild(sneakerMinus)

        val sneakerPlus = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                updateEnemyCount(EnemyType.SNEAKER, sneakerLabel, 1)
            }
        }.apply {
            text = "+"
            position = Vector2(1250f, 180f)
            fontSettings = customFontSettings
            size = Vector2(30f, 30f)
        }
        addChild(sneakerPlus)
    }

    private fun updateEnemyCount(type: EnemyType, label: Label, amount: Int) {
        enemyValues[type] = (enemyValues.getValue(type) + amount).coerceIn(0, 20)
        label.text = "${type.name} Enemy: ${enemyValues.getValue(type)}"
    }

}