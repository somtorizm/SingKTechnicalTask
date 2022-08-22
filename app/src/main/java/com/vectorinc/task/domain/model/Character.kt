package com.vectorinc.task.domain.model


data class Character(
    val appearance: List<Any>?=null,
    val better_call_saul_appearance: List<Int>?=null,
    val birthday: String?=null,
    val category: String?=null,
    val char_id: Int?=null,
    val img: String,
    val name: String,
    val nickname: String?=null,
    val occupation: List<String>?=null,
    val portrayed: String?=null,
    val status: String?=null
)