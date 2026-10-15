package dev.flrp.labyrinth.mechanics.structure

import com.wyrmlings.rune.resource.Audio

data class DialogueLine(val target: String, val line: String, val noise: Audio? = null)
