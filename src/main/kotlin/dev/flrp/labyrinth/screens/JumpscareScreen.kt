package dev.flrp.labyrinth.screens

import com.wyrmlings.rune.node.Node
import com.wyrmlings.rune.node.Timer
import dev.flrp.labyrinth.Labyrinth
import dev.flrp.labyrinth.audio.AudioMaster
import dev.flrp.labyrinth.mechanics.enemy.Enemy
import dev.flrp.labyrinth.mechanics.enemy.EyesEnemy
import dev.flrp.labyrinth.mechanics.enemy.HearingEnemy
import dev.flrp.labyrinth.mechanics.enemy.SneakerEnemy
import dev.flrp.labyrinth.mechanics.enemy.SoundEnemy
import dev.flrp.labyrinth.mechanics.enemy.StomperEnemy

class JumpscareScreen(
    val labyrinth: Labyrinth,
    val audioMaster: AudioMaster,
    val enemyType: EnemyType,
    val didTransform: Boolean,
    val isStoryMode: Boolean,
    val levelNum: Int,
    val enemyValues: Map<EnemyType, Int>
) : Node() {

    lateinit var enemy: Enemy
    lateinit var timer: Timer

    override fun onReady() {
        enemy = when(enemyType) {
            EnemyType.EYES -> {
                EyesEnemy(labyrinth)
            }

            EnemyType.SOUND -> {
                SoundEnemy(labyrinth)
            }

            EnemyType.HEARING -> {
                HearingEnemy(labyrinth)
            }

            EnemyType.STOMPER -> {
                StomperEnemy(labyrinth)
            }
            EnemyType.SNEAKER -> {
                SneakerEnemy(labyrinth)
            }
        }
        enemy.scale = 1.2f
        addChild(enemy)

        enemy.jumpscareSound.play()

        timer = Timer()
        addChild(timer)

        timer.start(5.0) {
            val type = if(didTransform) {
                GameOverType.TRANSFORMED
            } else {
                GameOverType.DIED
            }
            labyrinth.changeScreen(GameOverScreen(labyrinth, audioMaster, type, isStoryMode, levelNum, enemyValues))
        }
    }

}