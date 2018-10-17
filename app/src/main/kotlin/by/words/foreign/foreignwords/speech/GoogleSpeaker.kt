package by.words.foreign.foreignwords.speech

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.*

object GoogleSpeaker : Speaker() {

    const val MUST_INIT_WARNING = "First of all, you must to initialize the Speaker"
    const val TAG: String = "GoogleSpeaker"

    private var textToSpeech: TextToSpeech? = null

    override fun init(context: Context, primaryLanguage: Locale, secondaryLanguage: Locale) {
        super.init(context, primaryLanguage, secondaryLanguage)
        Log.d(TAG, "init ${primaryLanguage.displayLanguage} and ${secondaryLanguage.displayLanguage}")
        textToSpeech = createSpeech(context, primaryLanguage)
    }

    private fun createSpeech(context: Context, language: Locale): TextToSpeech {
        textToSpeech = TextToSpeech(context, TextToSpeech.OnInitListener {
            if (it == TextToSpeech.SUCCESS) {
                val result = textToSpeech!!.setLanguage(language)
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("error", "This Language (${language.displayLanguage}) is not supported")
                }
            } else {
                Log.e("TTS", "Initilization Failed!")
            }
        })
        return textToSpeech as TextToSpeech
    }

    @Suppress("DEPRECATION")
    override fun say(text: String, language: Speaker.Language) {
        Log.d(TAG, "say - $text on $language")
        textToSpeech!!.language = getLocale(language)
        if (textToSpeech != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null) ?: throw IllegalAccessException(MUST_INIT_WARNING)
            } else {
                textToSpeech?.speak(text, TextToSpeech.QUEUE_FLUSH, null) ?: throw IllegalAccessException(MUST_INIT_WARNING)
            }
        } else {

        }
    }

    override fun hush() {
        Log.d(TAG, "hush")
        textToSpeech?.stop()
    }

    override fun release() {
        Log.d(TAG, "release")
        textToSpeech?.stop()
        textToSpeech?.shutdown()
    }
}