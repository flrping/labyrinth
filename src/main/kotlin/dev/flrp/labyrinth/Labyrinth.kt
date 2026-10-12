package dev.flrp.labyrinth

import com.wyrmlings.rune.Game
import com.wyrmlings.rune.event.EventBus
import com.wyrmlings.rune.node.Sound
import dev.flrp.labyrinth.audio.AudioMaster
import dev.flrp.labyrinth.screens.TitleScreen

class Labyrinth : Game() {

    val eventBus = EventBus()

    override fun config() {
        title = "Labyrinth"
        width = 1366
        height = 768
    }

    override fun start() {
        val font = javaClass.classLoader.getResourceAsStream("JockeyOne-Regular.ttf")
        requireNotNull(font) { "Missing font JockeyOne-Regular.ttf" }
        assetManager.registerFont("JockeyOne", font)

        Assets.loadAllTextures(this)
        Assets.loadAllGIFs(this)
        Assets.loadAllSounds(this)

        val audioMaster = AudioMaster()
        globals.addChild(audioMaster)

        setInitialScreen(TitleScreen(this, audioMaster))
    }

}
