package by.words.foreign.foreignwords

import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import ru.yandex.speechkit.*
import java.util.*


abstract class SpeechActivity : AppCompatActivity() {

    // yandex speech
    private val vocalizerListener = AssistantVocalizerListener()
    protected var vocalizer: OnlineVocalizer? = null
    //google speech
    private var textToSpeech: TextToSpeech? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initYandexSpeech()
        initGoogleSpeech()
    }

    private fun initYandexSpeech() {
        try {
            SpeechKit.getInstance().init(this, getString(R.string.yandex_developer_api_key))
            SpeechKit.getInstance().uuid = UUID.randomUUID().toString()
        } catch (ignored: Exception) {
            //do not ignore in a real app!
            //finish()
            Toast.makeText(this, "Oops!", Toast.LENGTH_SHORT).show()
        }

//        SpeechKit.getInstance().getInstance(applicationContext, getString(R.string.yandex_developer_api_key))

//        SpeechKit.getInstance().setLogLevel(BaseSpeechKit.LogLevel.LOG_DEBUG)

        vocalizer = OnlineVocalizer.Builder(Language.RUSSIAN, vocalizerListener)
                .setEmotion(Emotion.GOOD)
                .setVoice(Voice.ALYSS)
                .setAutoPlay(true)
                .build()
        vocalizer!!.prepare()
    }

    private fun initGoogleSpeech() {
        textToSpeech = TextToSpeech(this, TextToSpeech.OnInitListener {
            if (it == TextToSpeech.SUCCESS) {
                val result = textToSpeech!!.setLanguage(Locale.UK)
                if (result == TextToSpeech.LANG_MISSING_DATA ||
                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Log.e("error", "This Language is not supported")
                }
            } else {
                Log.e("TTS", "Initilization Failed!")
            }
        })
    }

    protected fun sayYandex(text: String) {
        vocalizer!!.synthesize(text, Vocalizer.TextSynthesizingMode.INTERRUPT)
    }

    protected fun sayGoogle(text: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech!!.speak(text,TextToSpeech.QUEUE_FLUSH,null,null)
        } else {
            textToSpeech!!.speak(text, TextToSpeech.QUEUE_FLUSH, null)
        }
    }

    class AssistantVocalizerListener : VocalizerListener {

        val TAG: String = "VocalizerListener"

        override fun onPlayingBegin(p0: Vocalizer) {
            Log.d(TAG, "onPlayingBegin:")
        }

        override fun onVocalizerError(p0: Vocalizer, p1: Error) {
            Log.d(TAG, "onVocalizerError: $p1")
        }

        override fun onSynthesisDone(p0: Vocalizer) {
            Log.d(TAG, "onSynthesisDone:")
        }

        override fun onPartialSynthesis(p0: Vocalizer, p1: Synthesis) {
            Log.d(TAG, "onPartialSynthesis:")
        }

        override fun onPlayingDone(p0: Vocalizer) {
            Log.d(TAG, "onPlayingDone:")
        }
    }
}