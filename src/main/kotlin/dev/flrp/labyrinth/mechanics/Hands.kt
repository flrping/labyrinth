package dev.flrp.labyrinth.mechanics

import com.wyrmlings.rune.input.Input
import com.wyrmlings.rune.input.Key
import com.wyrmlings.rune.math.Vector2
import com.wyrmlings.rune.node.Sound
import com.wyrmlings.rune.node.xy.ui.TextureRect
import com.wyrmlings.rune.ui.texture.ImageTexture
import dev.flrp.labyrinth.Labyrinth

class Hands(val labyrinth: Labyrinth, val sound: Sound) : TextureRect() {

    var isRightPlaced: Boolean = false
    lateinit var hand1Texture: ImageTexture
    lateinit var hand2Texture: ImageTexture

    override fun onReady() {
        hand1Texture = ImageTexture(labyrinth.assetManager.loadTexture("hands_1.png"))
        hand2Texture = ImageTexture(labyrinth.assetManager.loadTexture("hands_2.png"))

        texture = hand1Texture
        size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
    }

    override fun onUpdate(delta: Double) {
        if (Input.wasKeyPressed(Key.UP) || Input.wasKeyPressed(Key.DOWN) || Input.wasKeyPressed(Key.LEFT) || Input.wasKeyPressed(Key.RIGHT)) {
            isRightPlaced = !isRightPlaced
            texture = if(isRightPlaced) {
                hand2Texture
            } else {
                hand1Texture
            }

            sound.play()
        }
    }

}