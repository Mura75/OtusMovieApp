package otus.movieapp.view

import android.content.Intent
import android.os.Build
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowActivity
import org.robolectric.shadows.ShadowApplication
import otus.main.MainActivity
import otus.movie_detail.view.MovieDetailActivity
import otus.movie_list.view.MovieListFragment
import otus.movieapp.R


@Config(sdk = [Build.VERSION_CODES.O_MR1])
@RunWith(RobolectricTestRunner::class)
class MovieListFragmentTest {

    @get:Rule
    var rule = activityScenarioRule<MainActivity>()


    private lateinit var mainActivity: MainActivity
    private lateinit var shadowActivity: ShadowActivity

    @Before
    fun setup() {
        mainActivity = MainActivity()
        shadowActivity = shadowOf(mainActivity)
    }

    @Test
    fun testFragment() {
        val scenario = launchFragmentInContainer<MovieListFragment>()
        scenario.onFragment { fragment ->
            val recyclerView = fragment.view?.findViewById<RecyclerView>(R.id.rvMovies)
            assertNotNull(recyclerView)
            recyclerView?.getChildAt(0)?.callOnClick()

            val expectedIntent = Intent(fragment.requireActivity(), MovieDetailActivity::class.java)

            val shadowActivity = shadowOf(fragment.requireActivity())
            assertNotNull(shadowActivity)
            val intent = shadowActivity.nextStartedActivity
            assertNotNull(intent)

        }
    }

    @Test
    fun testActivity() {
        rule.scenario.onActivity { activity ->
            val expectedIntent = Intent(activity, MovieDetailActivity::class.java)
            val actual: Intent = ShadowApplication().nextStartedActivity

            assertNotNull(actual)
        }
    }
}