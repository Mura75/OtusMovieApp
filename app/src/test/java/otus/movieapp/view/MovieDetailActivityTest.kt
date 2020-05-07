package otus.movieapp.view

import android.os.Build
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadow.api.Shadow
import otus.main.MainActivity
import otus.movie_detail.view.MovieDetailActivity
import otus.movieapp.R
import java.util.regex.Pattern.matches


@Config(
    sdk = [Build.VERSION_CODES.O_MR1],
    shadows = [ShadowCustomTextView::class]
)
@RunWith(RobolectricTestRunner::class)
class MovieDetailActivityTest {

    @get:Rule
    var rule = activityScenarioRule<MovieDetailActivity>()

    @Test
    fun `is views exist`() {
        val activity: MovieDetailActivity = Robolectric.buildActivity(MovieDetailActivity::class.java)
            .setup()
            .get()

        val expectedName = ""
        val actualName = Shadow.extract<ShadowCustomTextView>(activity.tvName).textString

        Assert.assertEquals(expectedName, actualName)
        onView(withId(R.id.tvName)).check(matches(withText(expectedName)))
    }
}