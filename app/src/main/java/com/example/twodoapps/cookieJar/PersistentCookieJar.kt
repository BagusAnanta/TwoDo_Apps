package com.example.twodoapps.cookieJar

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.util.concurrent.ConcurrentHashMap

class PersistentCookieJar : CookieJar {
    private val cookieStorage = ConcurrentHashMap<String, List<Cookie>>()

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStorage[url.host] ?: emptyList()
    }

    override fun saveFromResponse(
        url: HttpUrl,
        cookies: List<Cookie>
    ) {
        cookieStorage[url.host] = cookies
    }
}