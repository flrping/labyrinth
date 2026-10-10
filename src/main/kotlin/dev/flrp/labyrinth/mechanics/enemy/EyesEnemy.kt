package dev.flrp.labyrinth.mechanics.enemy

import com.wyrmlings.rune.math.Vector2
import com.wyrmlings.rune.ui.texture.ImageTexture
import dev.flrp.labyrinth.Labyrinth

class EyesEnemy(val labyrinth: Labyrinth) : Enemy() {

    override fun onReady() {
        texture = ImageTexture(labyrinth.assetManager.loadTexture("eyes.png"))
        size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())
    }

}