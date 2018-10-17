package by.words.foreign.foreignwords.speech

import android.content.Context
import java.util.*

abstract class Speaker {

    private var primaryLanguage: Locale? = null
    private var secondaryLanguage: Locale? = null

    abstract fun say(text: String, language: Language)
    abstract fun hush()
    abstract fun release()

    open fun init(context: Context, primaryLanguage: Locale, secondaryLanguage: Locale) {
        this.primaryLanguage = primaryLanguage
        this.secondaryLanguage = secondaryLanguage
    }

    fun getLocale(language: Speaker.Language) = if (language == Speaker.Language.PRIMARY) primaryLanguage else secondaryLanguage

    enum class Language {
        PRIMARY,
        SECONDARY
    }
}