package com.konstant.tool.lite.module.translate.server

/**
 * Created by konstant on 2018/4/4.
 */
data class TranslateRequest(val q: String, val from: String, val to: String, val appid: String, val salt: Int, val sign: String)