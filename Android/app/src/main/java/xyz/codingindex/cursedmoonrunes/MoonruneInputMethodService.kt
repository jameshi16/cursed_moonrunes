package xyz.codingindex.cursedmoonrunes

import android.inputmethodservice.InputMethodService
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import xyz.codingindex.cursedmoonrunes.support.Keyboard
import xyz.codingindex.cursedmoonrunes.support.KeyboardView
import xyz.codingindex.cursedmoonrunes.support.KeyboardView.OnKeyboardActionListener

enum class MoonruneInputModes {
    MEME,
    USABLE,
    USABLE_SYMBOLS,
}

class MoonruneInputMethodService() : InputMethodService(), OnKeyboardActionListener {
    private var keyboardView: KeyboardView? = null;
    private var keyboard: Keyboard? = null;
    private var mode: MoonruneInputModes = MoonruneInputModes.MEME;

    override fun onCreateInputView(): View {
        return (layoutInflater.inflate(R.layout.keyboard_view, null) as KeyboardView).also {keyView ->
            keyboardView = keyView;
            keyboard = Keyboard(this, R.xml.moonrunes).also { key ->
                keyView.keyboard = key
                keyView.setOnKeyboardActionListener(this);
            }
        }
    }

    override fun onPress(primaryCode: Int) {
    }

    override fun onRelease(primaryCode: Int) {
    }

    override fun onKey(primaryCode: Int, keyCodes: IntArray?) {
        currentInputConnection?.let {ic ->
            when(primaryCode) {
                Keyboard.KEYCODE_DELETE ->
                    if (TextUtils.isEmpty(ic.getSelectedText(0)))
                        ic.deleteSurroundingText(1, 0)
                    else ic.commitText("", 1)
                Keyboard.KEYCODE_SHIFT ->
                    keyboard?.run {
                        this.setShifted(!(keyboard!!.isShifted))
                        keyboardView?.invalidateAllKeys()
                    }
                Keyboard.KEYCODE_MODE_CHANGE -> {
                    mode = when (mode) {
                        MoonruneInputModes.MEME -> {
                            keyboard = Keyboard(this, R.xml.qwerty).also { key ->
                                keyboardView?.keyboard = key
                            }
                            MoonruneInputModes.USABLE
                        }
                        MoonruneInputModes.USABLE -> {
                            keyboard = Keyboard(this, R.xml.symbol).also { key ->
                                keyboardView?.keyboard = key
                            }
                            MoonruneInputModes.USABLE_SYMBOLS
                        }
                        MoonruneInputModes.USABLE_SYMBOLS -> {
                            keyboard = Keyboard(this, R.xml.moonrunes).also { key ->
                                keyboardView?.keyboard = key
                            }
                            MoonruneInputModes.MEME
                        }
                    }
                    keyboard?.setShifted(false);
                }
                Keyboard.KEYCODE_NOOP -> return@let
                Keyboard.KEYCODE_DONE ->
                    ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                else ->
                    if (mode == MoonruneInputModes.MEME)
                        ic.commitText(MoonruneKeymap.getSymbol(primaryCode), 1)
                    else if (keyboard != null && keyboard!!.isShifted)
                        ic.commitText(primaryCode.toChar().toUpperCase().toString(), 1)
                    else ic.commitText(primaryCode.toChar().toString(), 1)
            }
        }
    }

    override fun onText(text: CharSequence?) {
    }

    override fun swipeLeft() {
    }

    override fun swipeRight() {
    }

    override fun swipeDown() {
    }

    override fun swipeUp() {
    }
}