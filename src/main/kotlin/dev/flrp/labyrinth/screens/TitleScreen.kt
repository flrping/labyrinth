package dev.flrp.labyrinth.screens

import com.wyrmlings.rune.input.MouseButton
import com.wyrmlings.rune.math.Vector2
import com.wyrmlings.rune.node.Node
import com.wyrmlings.rune.node.xy.ui.Button
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

class TitleScreen(val labyrinth: Labyrinth) : Node() {

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

        val startButton = object : Button() {
            override fun onMouseClick(position: Vector2, action: MouseButton) {
                labyrinth.changeScreen(MainScreen(labyrinth))
            }
        }.apply {
            text = "Start"
            position = Vector2(125f, 200f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 24)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            container = FlatContainer().apply {
                fillColor = Color.GRAY
            }
            size = Vector2(150f, 50f)
        }
        addChild(startButton)

        val settingsButton = Button().apply {
            text = "Settings"
            position = Vector2(125f, 275f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 24)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            container = FlatContainer().apply {
                fillColor = Color.GRAY
            }
            size = Vector2(150f, 50f)
        }
        addChild(settingsButton)

        val creditsButton = Button().apply {
            text = "Credits"
            position = Vector2(125f, 350f)
            fontSettings = FontSettings().apply {
                font = labyrinth.assetManager.loadFont("JockeyOne", 24)
                textDecoration = TextDecoration().apply {
                    strokeColor = Color.WHITE
                    textPosition = TextPosition.CENTER
                    textAlign = TextAlign.CENTER
                }
            }
            container = FlatContainer().apply {
                fillColor = Color.GRAY
            }
            size = Vector2(150f, 50f)
        }
        addChild(creditsButton)

    }

}