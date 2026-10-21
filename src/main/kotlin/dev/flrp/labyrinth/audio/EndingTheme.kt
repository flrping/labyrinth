package dev.flrp.labyrinth.audio

import dev.flrp.labyrinth.Labyrinth
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class EndingTheme(
    val labyrinth: Labyrinth,
    override val name: String = "Ending Theme",
    override val tracks: ConcurrentMap<String, Track> = ConcurrentHashMap()
) : Theme() {

    init {
        val names = listOf(
            "labyrinth_victory.wav",
        )

        for (track in names) {
            var audio = labyrinth.audioManager.loadAudio(track)
            tracks[track] = Track(track, audio, false)
        }
    }

}