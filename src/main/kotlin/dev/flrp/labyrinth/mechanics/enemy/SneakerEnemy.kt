package dev.flrp.labyrinth.mechanics.enemy

import com.wyrmlings.rune.math.Vector2
import com.wyrmlings.rune.node.Sound
import dev.flrp.labyrinth.Labyrinth
import dev.flrp.labyrinth.screens.EnemyType

class SneakerEnemy(val labyrinth: Labyrinth) : Enemy() {

    override val type: EnemyType = EnemyType.SNEAKER
    override lateinit var encounterSound: Sound
    override lateinit var jumpscareSound: Sound
    lateinit var goSound: Sound

    override fun onReady() {
        size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())

        encounterSound = Sound().apply {
            audio = labyrinth.audioManager.loadAudio("the_sneaker_encounter.wav")
            volume = labyrinth.enemyVolume.toFloat()
        }
        addChild(encounterSound)

        goSound = Sound().apply {
            audio = labyrinth.audioManager.loadAudio("go.wav")
            volume = labyrinth.enemyVolume.toFloat()
        }
        addChild(goSound)

        jumpscareSound = Sound().apply {
            audio = labyrinth.audioManager.loadAudio("giggle6.wav")
            volume = labyrinth.enemyVolume.toFloat()
        }
        addChild(jumpscareSound)
    }

}