package dev.flrp.labyrinth.mechanics.enemy

import com.wyrmlings.rune.math.Vector2
import com.wyrmlings.rune.node.Sound
import com.wyrmlings.rune.ui.texture.ImageTexture
import dev.flrp.labyrinth.Labyrinth
import dev.flrp.labyrinth.screens.EnemyType

class SneakerEnemy(val labyrinth: Labyrinth) : Enemy() {

    override val type: EnemyType = EnemyType.SNEAKER
    override lateinit var encounterSound: Sound
    override lateinit var jumpscareSound: Sound
    lateinit var resolutionSound: Sound
    override var description: String = "Mysterious, but welcoming. It wonders who is there."
    override var resolution: String = "To counter this creature, let it look at you. Do not move."

    override fun onReady() {
        texture = ImageTexture(labyrinth.assetManager.loadTexture("sneaker.png"))
        size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())

        encounterSound = Sound().apply {
            audio = labyrinth.audioManager.loadAudio("the_sneaker_encounter.wav")
            volume = labyrinth.enemyVolume.toFloat()
        }
        addChild(encounterSound)

        jumpscareSound = Sound().apply {
            audio = labyrinth.audioManager.loadAudio("giggle6.wav")
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