package com.codewithre.simedit.data.entity

data class Faq(
    val question: String,
    val answer: String,
    var isExpanded: Boolean = false
)
