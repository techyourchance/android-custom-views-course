package com.techyourchance.androidviews.demonstrations._02_positioning

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.techyourchance.androidviews.R
import com.techyourchance.androidviews.general.BaseFragment
import timber.log.Timber


class PositioningFragment : BaseFragment() {

    override val screenName get() = getString(R.string.screen_name_positioning)

    private val uiHandler = Handler(Looper.getMainLooper())

    private lateinit var viewCircle: View
    private lateinit var edtLeft: EditText
    private lateinit var edtTranslationX: EditText
    private lateinit var edtX: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return layoutInflater.inflate(R.layout.layout_positioning, container, false).apply {
            viewCircle = findViewById(R.id.viewCircle)
            edtLeft = findViewById(R.id.edtLeft)
            edtTranslationX = findViewById(R.id.edtTranslationX)
            edtX = findViewById(R.id.edtX)

            edtLeft.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    viewCircle.left = try {
                        edtLeft.text.toString().toInt()
                    } catch (e: Exception) {
                        0
                    }
                    updateUiValues()
                    true
                } else {
                    false
                }
            }

            edtTranslationX.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    viewCircle.translationX  = try {
                        edtTranslationX.text.toString().toFloat()
                    } catch (e: Exception) {
                        0f
                    }
                    updateUiValues()
                    true
                } else {
                    false
                }
            }

            edtX.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    viewCircle.x  = try {
                        edtX.text.toString().toFloat()
                    } catch (e: Exception) {
                        0f
                    }
                    updateUiValues()
                    true
                } else {
                    false
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        uiHandler.post {
            updateUiValues()
        }
    }

    private fun updateUiValues() {
        val leftString = viewCircle.left.toString()
        val translationXString = viewCircle.translationX.toString()
        val xString = viewCircle.x.toString()
        edtLeft.setText(leftString)
        edtTranslationX.setText(translationXString)
        edtX.setText(xString)
    }

    companion object {
        fun newInstance(): PositioningFragment {
            return PositioningFragment()
        }
    }

}