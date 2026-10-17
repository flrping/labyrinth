package dev.flrp.labyrinth

object Assets {
    
    val TEXTURES = arrayOf(
        "background.png",
        "ui.png",
        "items.png",
        "bunbun.png",
        "background_end.png",
        "background_end_1.png",
        "background_end_2.png",
        "background_SL.png",
        "background_SR.png",
        "background.png",
        "background_all.png",
        "background_none.png",
        "hands_1.png",
        "hands_2.png",
        "pole_1.png",
        "pole_2.png",
        "eyes.png",
        "hearing.png",
        "sound.png",
        "normal.png",
        "unhappy1.png",
        "unhappy2.png",
        "scared1.png",
        "scared2.png",
        "agony.png",
        "acceptance.png",
        "look.png",
        "mainmenu.png",
        "mask.png",
        "map.png",
        "web_1.png",
        "web_2.png",
        "clue.png",
        "bad_ending_2.png",
        "gameoverbg.png",
        "nineslicebutton.png",
        "nineslicebutton_active.png",
        "earplugs.png",
        "story1.png",
        "story2.png",
        "story3.png",
        "story4.png",
        "map_1.png",
        "map_2.png",
        "map_3.png",
        "sneaker.png",
        "stomper.png",
        "story5.png",
        "story6.png",
        "story7.png"
    )

    fun loadAllTextures(labyrinth: Labyrinth) {
        for(texture in TEXTURES) {
            val stream = javaClass.classLoader.getResourceAsStream("assets/images/${texture}")
            requireNotNull(stream) { "Missing resource for: $texture" }
            labyrinth.assetManager.registerTexture(texture, stream)
        }
    }

    val TEXTURES_GIFS = arrayOf(
        "good_ending.gif",
        "bad_ending_1.gif",
        "bad_ending_3.gif",
    )

    fun loadAllGIFs(labyrinth: Labyrinth) {
        for(texture in TEXTURES_GIFS) {
            val stream = javaClass.classLoader.getResourceAsStream("assets/images/${texture}")
            requireNotNull(stream) { "Missing resource for: $texture" }
            labyrinth.assetManager.registerTexturesFromGIF(texture, stream)
        }
    }

    val SOUNDS = arrayOf(
        "bass.wav",
        "keys.wav",
        "kick.wav",
        "snare1.wav",
        "snare2.wav",
        "beep.wav",
        "breathe.wav",
        "474014__petebarry__fx_vent_foley.wav",
        "812600__modusmogulus__rooftop-vent-fan-seamless-loop.wav",
        "815606__squarfington__quiet-metal-creaking.wav",
        "movement.wav",
        "recall.wav",
        "whistle.wav",
        "the_eyes_encounter.wav",
        "the_eyes_jumpscare.wav",
        "the_hearing_jumpscare.wav",
        "the_sound_encounter.wav",
        "the_sound_jumpscare.wav",
        "hearingtune.wav",
        "labyrinth_main_theme.wav",
        "labyrinth_victory.wav",
        "cry1.wav",
        "cry2.wav",
        "cry3.wav",
        "cry4.wav",
        "cry5.wav",
        "giggle1.wav",
        "giggle2.wav",
        "giggle3.wav",
        "giggle4.wav",
        "giggle5.wav",
        "giggle6.wav",
        "giggle7.wav",
        "gasmaskbreathing.wav",
        "202085__deleted_user_3717932__whitenoise_1rlpf.wav",
        "334200__wasabiwielder__paper-crumple-3.wav",
        "the_stomper_encounter.wav",
        "the_sneaker_encounter.wav",
        "run_away_resolution.wav",
        "bubble.wav",
        "shoot.wav"
    )

    fun loadAllSounds(labyrinth: Labyrinth) {
        for(sound in SOUNDS) {
            val stream = javaClass.classLoader.getResourceAsStream("assets/sounds/${sound}")
            requireNotNull(stream) { "Missing resource for: $sound" }
            labyrinth.audioManager.registerAudio(sound, stream)
        }
    }

}