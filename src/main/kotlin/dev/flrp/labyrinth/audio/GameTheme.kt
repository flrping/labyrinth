package dev.flrp.labyrinth.audio

import dev.flrp.labyrinth.Labyrinth
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class GameTheme(
    val labyrinth: Labyrinth,
    override val name: String = "Game Theme",
    override val tracks: ConcurrentMap<String, Track> = ConcurrentHashMap()
) : Theme() {

    init {
        val names = listOf(
            "bass.wav",
            "keys.wav",
            "kick.wav",
            "snare1.wav",
            "snare2.wav",
            "beep.wav",
            "breathe.wav",
        )

        for (track in names) {
            var audio = labyrinth.audioManager.loadAudio(track)
            tracks[track] = Track(track, audio, false)
        }
    }

}