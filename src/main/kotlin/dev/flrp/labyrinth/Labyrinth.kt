package dev.flrp.labyrinth

import com.wyrmlings.rune.Game
import com.wyrmlings.rune.event.EventBus
import com.wyrmlings.rune.render.TextureFilter
import dev.flrp.labyrinth.audio.AudioMaster
import dev.flrp.labyrinth.screens.TitleScreen

class Labyrinth : Game() {

    val eventBus = EventBus()

    var gameGamma: Double = 1.0
        set(value) {
            field = value.coerceIn(0.5, 2.0)
            postProcessor.gamma = value.toFloat()
        }

    var musicVolume: Double = 1.0
        set(value) {
            field = value.coerceIn(0.0, 2.0)
            val am = globals.findChild("AudioMaster", false) as? AudioMaster
            am?.volume(value.toFloat())
        }

    var enemyVolume: Double = 1.0
        set(value) {
            field = value.coerceIn(0.0, 2.0)
        }

    var sfxVolume: Double = 1.0
        set(value) {
            field = value.coerceIn(0.0, 2.0)
        }

    override fun config() {
        title = "Labyrinth"
        width = 1366
        height = 768
        textureFilter = TextureFilter.NEAREST
        gamma = gameGamma.toFloat()
    }

    override fun start() {
        val font = javaClass.classLoader.getResourceAsStream("JockeyOne-Regular.ttf")
        requireNotNull(font) { "Missing font JockeyOne-Regular.ttf" }
        assetManager.registerFont("JockeyOne", font)

        Assets.loadAllTextures(this)
        Assets.loadAllGIFs(this)
        Assets.loadAllSounds(this)

        val audioMaster = AudioMaster().apply { name = "AudioMaster" }
        globals.addChild(audioMaster)

        setInitialScreen(TitleScreen(this, audioMaster))
    }

}
