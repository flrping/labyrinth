package dev.flrp.labyrinth

import com.wyrmlings.rune.Game
import com.wyrmlings.rune.event.EventBus
import dev.flrp.labyrinth.screens.MainScreen

class Labyrinth : Game() {

    val eventBus = EventBus()

    override fun config() {
        title = "Labyrinth"
        width = 1366
        height = 768
    }

    override fun start() {
        val font = javaClass.classLoader.getResourceAsStream("JetBrainsMono-Regular.ttf")
        requireNotNull(font) { "Missing font JetBrainsMono-Regular.ttf" }
        assetManager.registerFont("JetBrains Mono", font)

        Assets.loadAllTextures(this)
        Assets.loadAllSounds(this)

        setInitialScreen(MainScreen(this))
    }

}
