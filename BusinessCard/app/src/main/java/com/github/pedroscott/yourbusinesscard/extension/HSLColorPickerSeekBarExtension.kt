package com.github.pedroscott.yourbusinesscard.extension

import codes.side.andcolorpicker.hsl.HSLColorPickerSeekBar
import codes.side.andcolorpicker.model.IntegerHSLColor
import codes.side.andcolorpicker.view.picker.ColorSeekBar

fun HSLColorPickerSeekBar.setupListener(
    onColorChanged: ((
        picker: ColorSeekBar<IntegerHSLColor>,
        color: IntegerHSLColor,
        value: Int
    ) -> Unit)? = null,
    onColorPicked: ((
        picker: ColorSeekBar<IntegerHSLColor>,
        color: IntegerHSLColor,
        value: Int,
        fromUser: Boolean
    ) -> Unit)? = null,
    onColorPicking: ((
        picker: ColorSeekBar<IntegerHSLColor>,
        color: IntegerHSLColor,
        value: Int,
        fromUser: Boolean
    ) -> Unit)? = null
) {
    addListener(object : ColorSeekBar.OnColorPickListener<ColorSeekBar<IntegerHSLColor>, IntegerHSLColor> {
        override fun onColorChanged(
            picker: ColorSeekBar<IntegerHSLColor>,
            color: IntegerHSLColor,
            value: Int
        ) { onColorChanged?.invoke(picker, color, value) }

        override fun onColorPicked(
            picker: ColorSeekBar<IntegerHSLColor>,
            color: IntegerHSLColor,
            value: Int,
            fromUser: Boolean
        ) { onColorPicked?.invoke(picker, color, value, fromUser) }

        override fun onColorPicking(
            picker: ColorSeekBar<IntegerHSLColor>,
            color: IntegerHSLColor,
            value: Int,
            fromUser: Boolean
        ) { onColorPicking?.invoke(picker, color, value, fromUser) }
    })
}
