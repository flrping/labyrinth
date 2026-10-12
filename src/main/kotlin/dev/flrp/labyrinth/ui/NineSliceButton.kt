package dev.flrp.labyrinth.ui

import com.wyrmlings.rune.math.Vector2
import com.wyrmlings.rune.node.xy.ui.Button
import com.wyrmlings.rune.node.xy.ui.NineSliceRect
import com.wyrmlings.rune.ui.texture.ImageTexture

open class NineSliceButton(val nineTexture: ImageTexture, val activeTexture: ImageTexture) : Button() {

    lateinit var nineSliceBg: NineSliceRect

    override fun onReady() {
        val btn = this

        nineSliceBg = NineSliceRect().apply {
            texture = nineTexture
            left = 4f
            right = 4f
            top = 4f
            bottom = 4f
            size = btn.size
        }
        addChild(nineSliceBg)
    }

    override fun onMouseEnter(position: Vector2) {
        if (!disabled) {
            nineSliceBg.texture = activeTexture
        }
    }

    override fun onMouseLeave(position: Vector2) {
        if (!disabled) {
            nineSliceBg.texture = nineTexture
        }
    }
}