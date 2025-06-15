package com.dev.touyou.ffmultiplier.models

import kotlinx.serialization.Serializable

@Serializable
data class User(val name: String, val uuid: String)
