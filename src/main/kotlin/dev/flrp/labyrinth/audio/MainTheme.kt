package dev.flrp.labyrinth.audio

import dev.flrp.labyrinth.Labyrinth
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class MainTheme(
    val labyrinth: Labyrinth,
    override val name: String = "Main Theme",
    override val length: Float = 6.85f,
    override val tempo: Float = 140.00f,
    override val tracks: ConcurrentMap<String, Track> = ConcurrentHashMap()
) : Theme() {

    init {
        val names = listOf(
            "bass.wav",
            "keys.wav",
            "kick.wav",
            "snare1.wav",
            "snare2.wav"
        )

        for (track in names) {
            var audio = labyrinth.audioManager.loadAudio(track)
            tracks[track] = Track(track, audio, false)
        }
    }

}