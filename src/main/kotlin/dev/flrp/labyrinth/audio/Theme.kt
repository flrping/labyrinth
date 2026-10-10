package dev.flrp.labyrinth.audio

import java.util.concurrent.ConcurrentMap

abstract class Theme {

    abstract val name: String

    abstract val length: Float

    abstract val tempo: Float

    abstract val tracks: ConcurrentMap<String, Track>

}