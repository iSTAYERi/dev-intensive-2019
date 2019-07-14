package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.extensions.hideKeyboard
import ru.skillbranch.devintensive.extensions.isKeyboardClosed
import ru.skillbranch.devintensive.models.Bender

class MainActivity : AppCompatActivity() {

    private lateinit var benderImage: ImageView
    private lateinit var textTxt: TextView
    private lateinit var messageEt: EditText
    private lateinit var sendBtn: ImageView

    private lateinit var benderObj: Bender

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textTxt = tv_text
        benderImage = iv_bender.apply {
            setOnClickListener { onClick(it) }
        }
        sendBtn = iv_send.apply {
            setOnClickListener { onClick(it) }
        }
        messageEt = et_message.apply {
            setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onClick(this)
                }
                return@setOnEditorActionListener false
            }
        }

        val status = savedInstanceState?.getString("STATUS") ?: Bender.Status.NORMAL.name
        val question = savedInstanceState?.getString("QUESTION") ?: Bender.Question.NAME.name
        benderObj = Bender(Bender.Status.valueOf(status), Bender.Question.valueOf(question))

        Log.d("test1", "onCreate, $status $question")

        val (r, g, b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)

        textTxt.text = benderObj.askQuestion()
    }

    override fun onStart() {
        super.onStart()
        Log.d("test1", "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("test1", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("test1", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("test1", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("test1", "onDestroy")
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putString("STATUS", benderObj.status.name)
        outState?.putString("QUESTION", benderObj.question.name)
        Log.d("test1", "onSaveInstanceState ${benderObj.status.name} ${benderObj.question.name}")
    }

    private fun onClick(view: View) {
        when (view.id) {
            R.id.iv_send -> {
                sendAnswerToBender()
                hideKeyboard()
            }
            R.id.et_message -> sendAnswerToBender()
            R.id.iv_bender -> {
                Log.d("test1", "isKeyboardClosed: ${isKeyboardClosed()}")
                if (!isKeyboardClosed()) hideKeyboard()
            }
        }
    }

    private fun sendAnswerToBender() {
        //TODO перенести toLowerCase в реализацию
        val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString())
        messageEt.setText("")
        val (r, g, b) = color
        benderImage.setColorFilter(Color.rgb(r,g,b), PorterDuff.Mode.MULTIPLY)
        textTxt.text = phrase
    }

}
