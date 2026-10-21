package dev.flrp.labyrinth

import com.wyrmlings.rune.Game
import com.wyrmlings.rune.event.EventBus
import com.wyrmlings.rune.input.Key
import com.wyrmlings.rune.input.Keybind
import com.wyrmlings.rune.input.KeybindManager
import com.wyrmlings.rune.render.TextureFilter
import dev.flrp.labyrinth.audio.AudioMaster
import dev.flrp.labyrinth.screens.TitleScreen

class Labyrinth : Game() {

    val eventBus: EventBus = EventBus()
    val keybindManager: KeybindManager<ActionType> = KeybindManager()

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

    var startedStory = false
    var beatLevelOne = false
    var beatLevelTwo = false
    var beatLevelThree = false

    var sawIntro = false
    var sawSecondCutscene = false
    var sawThirdCutscene = false
    var sawFinalCutscene = false

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

        val keybinds = mapOf(
            ActionType.MOVE_UP to listOf(Key.UP, Key.W),
            ActionType.MOVE_DOWN to listOf(Key.DOWN, Key.S),
            ActionType.MOVE_LEFT to listOf(Key.LEFT, Key.A),
            ActionType.MOVE_RIGHT to listOf(Key.RIGHT, Key.D),
            ActionType.USE_MASK to listOf(Key.NUM_1),
            ActionType.USE_WHISTLE to listOf(Key.NUM_2),
            ActionType.USE_EARPLUGS to listOf(Key.NUM_3),
            ActionType.USE_MAP to listOf(Key.M),
            ActionType.INTERACT to listOf(Key.F),
            ActionType.SKIP to listOf(Key.ENTER),
        )

        for(keybind in keybinds) {
            keybindManager.addAction(keybind.key, Keybind(keybind.key.toString(), keybind.value))
        }

        setInitialScreen(TitleScreen(this, audioMaster))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Labyrinth().run()
        }
    }

}
