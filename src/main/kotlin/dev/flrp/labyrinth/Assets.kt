package dev.flrp.labyrinth

object Assets {
    
    val TEXTURES = arrayOf(
        "background.png",
        "background_end.png",
        "background_end_1.png",
        "background_end_2.png",
        "background_SL.png",
        "background_SR.png",
        "background.png",
        "hands_1.png",
        "hands_2.png",
        "pole_1.png",
        "pole_2.png",
        "eyes.png",
        "hearing.png",
        "sound.png",
    )

    fun loadAllTextures(labyrinth: Labyrinth) {
        for(texture in TEXTURES) {
            val stream = javaClass.classLoader.getResourceAsStream("assets/images/${texture}")
            requireNotNull(stream) { "Missing resource for: $texture" }
            labyrinth.assetManager.registerTexture(texture, stream)
        }
    }

    val SOUNDS = arrayOf(
        "bass.wav",
        "keys.wav",
        "kick.wav",
        "snare1.wav",
        "snare2.wav",
        "474014__petebarry__fx_vent_foley.wav",
        "812600__modusmogulus__rooftop-vent-fan-seamless-loop.wav",
        "815606__squarfington__quiet-metal-creaking.wav",
        "movement.wav"
    )

    fun loadAllSounds(labyrinth: Labyrinth) {
        for(sound in SOUNDS) {
            val stream = javaClass.classLoader.getResourceAsStream("assets/sounds/${sound}")
            requireNotNull(stream) { "Missing resource for: $sound" }
            labyrinth.audioManager.registerAudio(sound, stream)
        }
    }

}