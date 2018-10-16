package by.words.foreign.foreignwords.speech

import android.content.Context
import android.util.Log
import android.widget.Toast
import by.words.foreign.foreignwords.R
import ru.yandex.speechkit.*
import java.util.*

object YandexSpeaker : Speaker() {

    val TAG: String = "YandexSpeaker"

    private var vocalizer: OnlineVocalizer? = null
    private val vocalizerListener = AssistantVocalizerListener()

    override fun init(context: Context, primaryLanguage: Locale, secondaryLanguage: Locale) {
        Log.d(TAG, "init")
        try {
            SpeechKit.getInstance().init(context, context.getString(R.string.yandex_developer_api_key))
            SpeechKit.getInstance().uuid = UUID.randomUUID().toString()
        } catch (ignored: Exception) {
            Toast.makeText(context, "Oops! I cant speak at all.", Toast.LENGTH_SHORT).show()
        }

        vocalizer = OnlineVocalizer.Builder(ru.yandex.speechkit.Language.RUSSIAN, vocalizerListener)
                .setEmotion(Emotion.GOOD)
                .setVoice(Voice.ALYSS)
                .setAutoPlay(true)
                .build()
        vocalizer!!.prepare()
    }

    override fun say(text: String, language: Speaker.Language) {
        Log.d(TAG, "say - $text")
        if (vocalizer != null) {
            vocalizer!!.synthesize(text, Vocalizer.TextSynthesizingMode.INTERRUPT)
        } else {
            throw IllegalAccessException("First of all, you must to initialize the Speaker")
        }
    }

    override fun hush() {
        Log.d(TAG, "hush")
        if (vocalizer != null) {
            vocalizer!!.cancel()
        } else {
            throw IllegalAccessException("First of all, you must to initialize the Speaker")
        }
    }

    override fun release() {
        Log.d(TAG, "release")
        // Nothing to do
    }

    class AssistantVocalizerListener : VocalizerListener {

        override fun onPlayingBegin(vocalizer: Vocalizer) {
            Log.d(YandexSpeaker.TAG, "onPlayingBegin:")
        }

        override fun onVocalizerError(vocalizer: Vocalizer, error: Error) {
            Log.d(YandexSpeaker.TAG, "onVocalizerError: $error")
        }

        override fun onSynthesisDone(vocalizer: Vocalizer) {
            Log.d(YandexSpeaker.TAG, "onSynthesisDone:")
        }

        override fun onPartialSynthesis(vocalizer: Vocalizer, synthesis: Synthesis) {
            Log.d(YandexSpeaker.TAG, "onPartialSynthesis:")
        }

        override fun onPlayingDone(vocalizer: Vocalizer) {
            Log.d(YandexSpeaker.TAG, "onPlayingDone:")
        }
    }
}