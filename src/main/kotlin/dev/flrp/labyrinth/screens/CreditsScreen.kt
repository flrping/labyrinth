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

class CreditsScreen(val labyrinth: Labyrinth, val audioMaster: AudioMaster) : Node() {

    override fun onReady() {

        val background = TextureRect().apply {
            texture = ImageTexture(labyrinth.assetManager.loadTexture("gameoverbg.png"))
            size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
        }
        addChild(background)

        val label = Label().apply {
            text = """
                Programmer: Jaelyn (flrp)
                Composer: Jaelyn (flrp)
                Artist: Jaelyn (flrp)
                Vocal Artist: Hannah Chapin - https://linktr.ee/hannahdoesvo
                Vocal Artist: Hollis Lee - https://linktr.ee/hollis.lee.va
                Vocal Artist: Nathalie Emmanuel - https://x.com/nepvoices
                SFX: PeteBarry, squarfington, modusmogulus, vestibule-door, designerschoice, 
                HOrvi64, WasabiWielder, Samantha_Dolman, bouncyballblue, and others from freesound.org
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
            position = Vector2((labyrinth.width - 200f) / 2, 570f)
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