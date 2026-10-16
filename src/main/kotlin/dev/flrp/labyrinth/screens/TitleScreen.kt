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
import dev.flrp.labyrinth.Defaults
import dev.flrp.labyrinth.Labyrinth
import dev.flrp.labyrinth.audio.AudioMaster
import dev.flrp.labyrinth.audio.TitleTheme
import dev.flrp.labyrinth.mechanics.structure.FirstLevel
import dev.flrp.labyrinth.mechanics.structure.SecondLevel
import dev.flrp.labyrinth.mechanics.structure.ThirdLevel
import dev.flrp.labyrinth.ui.NineSliceButton

class TitleScreen(val labyrinth: Labyrinth, val audioMaster: AudioMaster) : Node() {

    override fun onReady() {

        val background = TextureRect().apply {
            texture = ImageTexture(labyrinth.assetManager.loadTexture("mainmenu.png"))
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
        }
        addChild(background)

        val title = Label().apply {
            text = "LABYRINTH"
            position = Vector2(115f, 100f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 60)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            size = Vector2(180f, 50f)
        }
        addChild(title)

        val nineSliceTexture = ImageTexture(labyrinth.assetManager.loadTexture("nineslicebutton.png"))
        val nineSliceTextureActive = ImageTexture(labyrinth.assetManager.loadTexture("nineslicebutton_active.png"))

        val storyMode = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                labyrinth.startedStory = true
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
            }
        }.apply {
            text = "${if (labyrinth.startedStory) "Continue" else "Start"} Story Mode"
            position = Vector2(125f, 200f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 24)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            size = Vector2(150f, 50f)
        }
        addChild(storyMode)

        val freePlayButton = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                labyrinth.changeScreen(FreePlayScreen(labyrinth, audioMaster))
            }
        }.apply {
            text = "Free Play"
            position = Vector2(125f, 275f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 24)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            size = Vector2(150f, 50f)
        }
        addChild(freePlayButton)

        val settingsButton = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                labyrinth.changeScreen(SettingsScreen(labyrinth, audioMaster))
            }
        }.apply {
            text = "Settings"
            position = Vector2(125f, 350f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 24)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            size = Vector2(150f, 50f)
        }
        addChild(settingsButton)

        val creditsButton = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                labyrinth.changeScreen(CreditsScreen(labyrinth, audioMaster))
            }
        }.apply {
            text = "Credits"
            position = Vector2(125f, 425f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 24)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            size = Vector2(150f, 50f)
        }
        addChild(creditsButton)

        val howToPlayButton = object : NineSliceButton(nineSliceTexture, nineSliceTextureActive) {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                labyrinth.changeScreen(HowToPlayScreen(labyrinth, audioMaster))
            }
        }.apply {
            text = "How to Play"
            position = Vector2(125f, 500f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 24)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            size = Vector2(150f, 50f)
        }
        addChild(howToPlayButton)

        val theme = TitleTheme(labyrinth)
        if (audioMaster.theme?.name != theme.name) {
            audioMaster.theme = theme
            audioMaster.play()
        }
    }

}