package otus.movieapp.view

import android.widget.TextView
import org.robolectric.annotation.Implementation
import org.robolectric.annotation.Implements
import org.robolectric.annotation.RealObject
import org.robolectric.shadow.api.Shadow.directlyOn
import org.robolectric.shadows.ShadowTextView

@Implements(TextView::class)
class ShadowCustomTextView : ShadowTextView() {
    @RealObject
    lateinit var realTextView: TextView

    var textString: String = ""
        private set

    @Implementation
    fun setText(text: CharSequence, type: TextView.BufferType) {
        this.textString = text.toString()
        directlyOn(realTextView, TextView::class.java).setText(text, type)
    }

    fun getTextLength() = textString.length
}