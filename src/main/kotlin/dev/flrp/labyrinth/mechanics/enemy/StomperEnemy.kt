package dev.flrp.labyrinth.mechanics.enemy

import com.wyrmlings.rune.math.Vector2
import com.wyrmlings.rune.node.Sound
import com.wyrmlings.rune.ui.texture.ImageTexture
import dev.flrp.labyrinth.Labyrinth
import dev.flrp.labyrinth.screens.EnemyType

class StomperEnemy(val labyrinth: Labyrinth) : Enemy() {

    override val type: EnemyType = EnemyType.STOMPER
    override lateinit var encounterSound: Sound
    override lateinit var jumpscareSound: Sound
    lateinit var resolutionSound: Sound
    override var description: String = "You cannot see it. It is behind you."
    override var resolution: String = "To counter this creature, you need to move when it bangs."

    override fun onReady() {
        texture = ImageTexture(labyrinth.assetManager.loadTexture("stomper.png"))
        size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())

        encounterSound = Sound().apply {
            audio = labyrinth.audioManager.loadAudio("the_stomper_encounter.wav")
            volume = labyrinth.enemyVolume.toFloat()
        }
        addChild(encounterSound)

        jumpscareSound = Sound().apply {
            audio = labyrinth.audioManager.loadAudio("giggle7.wav")
            volume = labyrinth.enemyVolume.toFloat()
        }
        addChild(jumpscareSound)

        resolutionSound = Sound().apply {
            audio = labyrinth.audioManager.loadAudio("run_away_resolution.wav")
            volume = labyrinth.enemyVolume.toFloat()
        }
        addChild(resolutionSound)
    }

}