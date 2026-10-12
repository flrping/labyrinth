package dev.flrp.labyrinth.mechanics.enemy

import com.wyrmlings.rune.math.Vector2
import com.wyrmlings.rune.node.Sound
import com.wyrmlings.rune.ui.texture.ImageTexture
import dev.flrp.labyrinth.Labyrinth
import dev.flrp.labyrinth.screens.EnemyType

class EyesEnemy(val labyrinth: Labyrinth) : Enemy() {

    override val type: EnemyType = EnemyType.EYES
    override lateinit var encounterSound: Sound
    override lateinit var jumpscareSound: Sound

    override fun onReady() {
        texture = ImageTexture(labyrinth.assetManager.loadTexture("eyes.png"))
        size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())

        encounterSound = Sound().apply {
            audio = labyrinth.audioManager.loadAudio("the_eyes_encounter.wav")
        }
        addChild(encounterSound)

        jumpscareSound = Sound().apply {
            audio = labyrinth.audioManager.loadAudio("the_eyes_jumpscare.wav")
        }
        addChild(jumpscareSound)
    }

}