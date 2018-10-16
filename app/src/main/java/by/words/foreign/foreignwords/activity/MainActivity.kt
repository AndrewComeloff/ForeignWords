package by.words.foreign.foreignwords.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import by.words.foreign.foreignwords.R
import by.words.foreign.foreignwords.speech.GoogleSpeaker
import by.words.foreign.foreignwords.speech.Speaker
import by.words.foreign.foreignwords.speech.YandexSpeaker
import kotlinx.android.synthetic.main.activity_cards.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val PRIMARY_LANGUAGE = Locale("ru")
    private val SECONDARY_LANGUAGE = Locale.ENGLISH

    private var speaker: Speaker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        cv_card_prim_lang.setOnClickListener { view ->
            speaker!!.say("Алиса мелафон", Speaker.Language.PRIMARY)
        }

        cv_card_second_lang.setOnClickListener { view ->
            speaker!!.say("Hi! I am Alyss", Speaker.Language.SECONDARY)
        }

        // Initialization speaker
        speaker = if (false) YandexSpeaker else GoogleSpeaker
        speaker!!.init(this, PRIMARY_LANGUAGE, SECONDARY_LANGUAGE)

    }

    override fun onDestroy() {
        super.onDestroy()
        speaker!!.release()
    }
}