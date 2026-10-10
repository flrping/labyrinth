package dev.flrp.labyrinth.audio

import com.wyrmlings.rune.node.Node
import com.wyrmlings.rune.node.Sound

class AudioMaster : Node() {

    private val sounds = mutableMapOf<String, Sound>()

    var theme: Theme? = null
        set(value) {
            children.toList().forEach {
                it.destroy()
            }
            sounds.clear()

            field = value

            if (value == null) {
                return
            }

            value.tracks.forEach { (name, track) ->
                val sound = Sound().apply {
                    audio = track.audio
                    volume = 1.0f
                    loop = true
                }

                sounds[name] = sound
                addChild(sound)
            }
        }

    fun play() {
        sounds.values.forEach(Sound::play)
    }

    fun pause() {
        sounds.values.forEach(Sound::pause)
    }

    fun stop() {
        sounds.values.forEach(Sound::stop)
    }

    fun play(track: String, volume: Float) {
        sounds[track]?.play()
        sounds[track]?.volume = volume
    }

    fun pause(track: String) {
        sounds[track]?.pause()
    }

    fun stop(track: String) {
        sounds[track]?.stop()
    }

    fun setEnabled(track: String, enabled: Boolean) {
        val theme = theme ?: return
        val trackData = theme.tracks[track] ?: return
        val sound = sounds[track] ?: return

        trackData.enabled = enabled

        if (enabled) {
            sound.play()
        } else {
            sound.stop()
        }
    }

}