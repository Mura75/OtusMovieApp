package com.otus.lint_check

import com.android.tools.lint.checks.infrastructure.LintDetectorTest.xml
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import com.android.tools.lint.detector.api.*
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.w3c.dom.Element
import kotlin.math.abs

@RunWith(Enclosed::class)
class WrongDimensionDetectorTest {

    @RunWith(Parameterized::class)
    class WrongDimensionDetectorTestParametrized(private val value: String) {

       companion object {
           @JvmStatic
           @Parameterized.Parameters
           fun data() = listOf(
               "wrap_content",
               "match_parent",
               "0dp"
           )
       }

        @Test
        fun `pass test with success`() {
            lint()
                .allowMissingSdk()
                .files(
                    xml(
                        "res/layout/layout.xml",
                        """
                            <FrameLayout
                                    xmlns:android="http://schemas.android.com/apk/res/android"
                                    xmlns:app="http://schemas.android.com/apk/res-auto"
                                    xmlns:tools="http://schemas.android.com/tools"
                                    android:layout_width="match_parent"
                                    android:layout_height="wrap_content">

                                <TextView
                                    android:id="@+id/tvName"
                                    android:layout_width="$value"
                                    android:layout_height="wrap_content"
                                    android:layout_marginStart="16dp"
                                    android:layout_marginTop="24dp"
                                    android:layout_marginEnd="16dp"
                                    android:textColor="@android:color/black"
                                    android:textSize="18sp"
                                    app:layout_constraintEnd_toEndOf="parent"
                                    app:layout_constraintStart_toStartOf="parent"
                                    app:layout_constraintTop_toBottomOf="@+id/ivPoster"
                                    tools:text="TextView" />

                            </FrameLayout>
                            """
                    )
                )
                .issues(WrongDimensionDetector.ISSUE)
                .run()
                .expect("No warnings.")
        }

        @Test
        fun `pass test with error`() {
            lint()
                .allowMissingSdk()
                .files(
                    xml(
                        "res/layout/layout.xml",
                        """
                            <FrameLayout
                                    xmlns:android="http://schemas.android.com/apk/res/android"
                                    xmlns:app="http://schemas.android.com/apk/res-auto"
                                    xmlns:tools="http://schemas.android.com/tools"
                                    android:layout_width="match_parent"
                                    android:layout_height="wrap_content">

                                <TextView
                                    android:id="@+id/tvName"
                                    android:layout_width="37dp"
                                    android:layout_height="wrap_content"
                                    android:layout_marginStart="16dp"
                                    android:layout_marginTop="24dp"
                                    android:layout_marginEnd="16dp"
                                    android:textColor="@android:color/black"
                                    android:textSize="18sp"
                                    app:layout_constraintEnd_toEndOf="parent"
                                    app:layout_constraintStart_toStartOf="parent"
                                    app:layout_constraintTop_toBottomOf="@+id/ivPoster"
                                    tools:text="TextView" />

                            </FrameLayout>
                            """
                    )
                )
                .issues(WrongDimensionDetector.ISSUE)
                .run()
                .expect(
                    """
                        res/layout/layout.xml:9: Error: All xml size values must be divisible by 4 [WrongDimensionDetector]
                                                        <TextView
                                                        ^
                        1 errors, 0 warnings
                        """
                        .trimIndent()
                )
        }
    }
}