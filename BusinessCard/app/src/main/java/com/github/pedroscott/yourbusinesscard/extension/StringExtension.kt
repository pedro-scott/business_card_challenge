package com.github.pedroscott.yourbusinesscard.extension

import android.text.Editable

fun String.toEditable() = Editable.Factory.getInstance().newEditable(this)

fun String.toSearchFormat() = "%$this%"