package dev.flrp.labyrinth.screens

import com.wyrmlings.rune.input.MouseButton
import com.wyrmlings.rune.math.Vector2
import com.wyrmlings.rune.node.Node
import com.wyrmlings.rune.node.xy.ui.Label
import com.wyrmlings.rune.node.xy.ui.Sprite2D
import com.wyrmlings.rune.node.xy.ui.TextureRect
import com.wyrmlings.rune.property.Color
import com.wyrmlings.rune.ui.style.text.FontSettings
import com.wyrmlings.rune.ui.style.text.TextAlign
import com.wyrmlings.rune.ui.style.text.TextDecoration
import com.wyrmlings.rune.ui.style.text.TextPosition
import com.wyrmlings.rune.ui.texture.ImageTexture
import dev.flrp.labyrinth.Defaults
import dev.flrp.labyrinth.Labyrinth
import dev.flrp.labyrinth.audio.AudioMaster
import dev.flrp.labyrinth.audio.EndingTheme
import dev.flrp.labyrinth.mechanics.structure.FirstLevel
import dev.flrp.labyrinth.mechanics.structure.SecondLevel
import dev.flrp.labyrinth.mechanics.structure.ThirdLevel
import dev.flrp.labyrinth.ui.NineSliceButton

class GameOverScreen(
    val labyrinth: Labyrinth,
    val audioMaster: AudioMaster,
    val gameOverType: GameOverType,
    val isStoryMode: Boolean,
    val levelNum: Int,
    val enemyValues: Map<EnemyType, Int>
) : Node() {

    lateinit var heading: Label
    lateinit var description: Label
    lateinit var textureRect: TextureRect
    lateinit var sprite2D: Sprite2D
    lateinit var runAgainButton: NineSliceButton
    lateinit var titleButton: NineSliceButton

    var frameSpeed: Int = 1

    override fun onReady() {
        if(gameOverType == GameOverType.WIN && isStoryMode) {
            when(levelNum) {
                2 -> labyrinth.beatLevelTwo = true
                3 -> labyrinth.beatLevelThree = true
                else -> labyrinth.beatLevelOne = true
            }
        }

        textureRect = TextureRect().apply {
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
            texture = ImageTexture(labyrinth.assetManager.loadTexture("gameoverbg.png"))
        }
        addChild(textureRect)

        heading = Label().apply {
            text = when(gameOverType) {
                GameOverType.WIN -> {
                    "YOU ESCAPED"
                }
                GameOverType.RAN_AWAY -> {
                    "YOU RAN AWAY"
                }
                GameOverType.DIED -> {
                    "YOU DIED"
                }
                GameOverType.TRANSFORMED -> {
                    "YOU TRANSFORMED"
                }
            }
            position = Vector2(0f, 200f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 60)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            size = Vector2(labyrinth.width.toFloat(), 50f)
        }
        addChild(heading)

        description = Label().apply {
            text = when(gameOverType) {
                GameOverType.WIN -> {
                    "You escaped with all clues."
                }
                GameOverType.RAN_AWAY -> {
                    "You escaped without finding all the clues."
                }
                GameOverType.DIED -> {
                    "You were very unfortunate."
                }
                GameOverType.TRANSFORMED -> {
                    "You became something else."
                }
            }
            position = Vector2((labyrinth.width - 200f) / 2, 250f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 36)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.fromHex("#e0e0e0")
                    textAlign = TextAlign.CENTER
                }
            }
            size = Vector2(200f, 200f)
        }
        addChild(description)

        val selectedSheets = when(gameOverType) {
            GameOverType.WIN -> {
                frameSpeed = 5
                labyrinth.assetManager.loadTexturesFromGIF("good_ending.gif")
            }

            GameOverType.RAN_AWAY -> {
                frameSpeed = 5
                labyrinth.assetManager.loadTexturesFromGIF("bad_ending_1.gif")
            }

            GameOverType.DIED -> {
                frameSpeed = 4
                labyrinth.assetManager.loadTexturesFromGIF("bad_ending_3.gif")
            }

            GameOverType.TRANSFORMED -> {
                frameSpeed = 0
                listOf(labyrinth.assetManager.loadTexture("bad_ending_2.png"))
            }
        }

        sprite2D = Sprite2D().apply {
            frames = selectedSheets
            loop = true
            framesPerSecond = frameSpeed
        }
        addChild(sprite2D)

        val nineSliceTexture = ImageTexture(labyrinth.assetManager.loadTexture("nineslicebutton.png"))
        val nineSliceTextureActive = ImageTexture(labyrinth.assetManager.loadTexture("nineslicebutton_active.png"))

        titleButton = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                labyrinth.changeScreen(TitleScreen(labyrinth, audioMaster))
            }
        }.apply {
            position = Vector2(520f, 550f)
            size = Vector2(150f, 50f)
            text = "Title Screen"
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 36)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.fromHex("#e0e0e0")
                    textAlign = TextAlign.CENTER
                    textPosition = TextPosition.CENTER
                }
            }
        }
        addChild(titleButton)

        runAgainButton  = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                if(isStoryMode) {
                    when {
                        labyrinth.beatLevelOne && !labyrinth.beatLevelTwo -> {
                            if (labyrinth.sawSecondCutscene) {
                                labyrinth.changeScreen(
                                    GameScreen(
                                        labyrinth,
                                        audioMaster,
                                        SecondLevel(),
                                        true,
                                        2,
                                        Defaults.LEVEL_TWO_DEFAULTS
                                    )
                                )
                            } else {
                                labyrinth.changeScreen(SecondStoryScreen(labyrinth, audioMaster))
                            }
                        }

                        labyrinth.beatLevelTwo && !labyrinth.beatLevelThree -> {
                            if (labyrinth.sawThirdCutscene) {
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
                            } else {
                                labyrinth.changeScreen(ThirdStoryScreen(labyrinth, audioMaster))
                            }
                        }

                        labyrinth.beatLevelThree -> {
                            labyrinth.changeScreen(FinalStoryScreen(labyrinth, audioMaster))
                        }

                        else -> {
                            if (labyrinth.sawIntro) {
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
                            } else {
                                labyrinth.changeScreen(IntroStoryScreen(labyrinth, audioMaster))
                            }
                        }
                    }
                } else {
                    val level = when(levelNum) {
                        2 -> SecondLevel()
                        3 -> ThirdLevel()
                        else -> FirstLevel()
                    }
                    labyrinth.changeScreen(GameScreen(labyrinth, audioMaster, level, false, levelNum, enemyValues))
                }
            }
        }.apply {
            position = Vector2(700f, 550f)
            size = Vector2(150f, 50f)
            text = if(isStoryMode && gameOverType == GameOverType.WIN) "Continue" else "Run Again"
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 36)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.fromHex("#e0e0e0")
                    textAlign = TextAlign.CENTER
                    textPosition = TextPosition.CENTER
                }
            }
        }
        addChild(runAgainButton)

        val theme = EndingTheme(labyrinth)
        audioMaster.theme = theme
        audioMaster.volume(labyrinth.musicVolume.toFloat())
        audioMaster.play()

    }

}