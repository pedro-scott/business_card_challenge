package com.github.pedroscott.yourbusinesscard.extension

import android.widget.Button
import android.widget.EditText
import androidx.core.widget.doOnTextChanged

fun Button.enableByFieldsValidation(validationConditions: Map<EditText, () -> Boolean>) {
    val checkFieldsToEnableButton: () -> Unit = { this.isEnabled = validationConditions.values.all { condition -> condition() } }

    validationConditions.keys.forEach { editText ->
        editText.doOnTextChanged { _, _, _, _ ->
            checkFieldsToEnableButton()
        }
    }
}