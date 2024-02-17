package com.example.myriadmirror.util

import com.example.myriadmirror.R
import java.time.format.DateTimeFormatter

object Constants {
    val BOTTOM_NAVIGATION_ITEMS = listOf(
        R.string.navigation_chat_title to R.drawable.icon_chat,
        R.string.navigation_role_title to R.drawable.icon_role,
        R.string.navigation_setting_title to R.drawable.icon_setting,
    )

    const val CREATE_ROLE_ID = -1

    val TODAY_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    val YESTERDAY_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("昨天 HH:mm")
    val IN_YEAR_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd")
    val OUT_YEAR_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    val IN_WEEK_LIST = listOf(
        "星期一",
        "星期二",
        "星期三",
        "星期四",
        "星期五",
        "星期六",
        "星期天",
    )

    const val TYPE_USER = "user"
    const val TYPE_SYSTEM = "system"
    const val TYPE_ASSISTANT = "assistant"

    const val PAGE_SIZE = 10
    const val TOKEN_SIZE = 5
    const val DEFAULT_BASEURL = "https://api.openai.com"
    const val DEFAULT_TEMPERATURE = 0.7
    const val DEFAULT_TOP_P = 1.0

    const val DEFAULT_CONTENT = "You are a helpful assistant. You can help me by answering my questions. You can also ask me questions."

    val SETTING_VALUES: List<Double> by lazy {
        (0..100).map { it / 100.0 }
    }

    val TEMPERATURE_CALIBRATIONS = listOf(0.2, 0.8)
}
