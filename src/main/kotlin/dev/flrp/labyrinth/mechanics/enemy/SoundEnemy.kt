package dev.flrp.labyrinth.mechanics.enemy

import com.wyrmlings.rune.math.Vector2
import com.wyrmlings.rune.node.Sound
import com.wyrmlings.rune.ui.texture.ImageTexture
import dev.flrp.labyrinth.Labyrinth
import dev.flrp.labyrinth.screens.EnemyType
import kotlin.math.floor

class SoundEnemy(val labyrinth: Labyrinth) : Enemy() {

    override val type: EnemyType = EnemyType.SOUND
    override lateinit var encounterSound: Sound
    override lateinit var jumpscareSound: Sound
    lateinit var sound: Sound

    var isRecalling: Boolean = false
    var amount: Int = 0;
    var picked: Int = 0;
    var timer: Double = 0.0;

    override fun onReady() {
        texture = ImageTexture(labyrinth.assetManager.loadTexture("sound.png"))
        size = Vector2(labyrinth.width.toFloat(), labyrinth.height.toFloat())

        sound = Sound().apply {
            audio = labyrinth.audioManager.loadAudio("recall.wav")
        }
        addChild(sound)

        encounterSound = Sound().apply {
            audio = labyrinth.audioManager.loadAudio("the_sound_encounter.wav")
        }
        addChild(encounterSound)

        jumpscareSound = Sound().apply {
            audio = labyrinth.audioManager.loadAudio("the_sound_jumpscare.wav")
        }
        addChild(jumpscareSound)
    }

    override fun onUpdate(delta: Double) {
        if(isRecalling && amount < picked) {
            timer += delta

            if(timer >= 0.75) {
                sound.stop()
                sound.play()
                timer = 0.0
                amount++
            }
        }
    }

    fun recall(): Int {
        isRecalling = true
        amount = 0
        timer = 0.0
        picked = floor(Math.random() * 3).toInt() + 1
        return picked
    }

}