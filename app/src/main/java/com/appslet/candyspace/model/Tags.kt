package com.appslet.candyspace.model

import kotlinx.serialization.Serializable

@Serializable
data class Tags(
    val user_id: Long?,
    val answer_count: Int,
    val answer_score: Int,
    val question_count: Int,
    val question_score: Int,
    val tag_name: String?
) {
}