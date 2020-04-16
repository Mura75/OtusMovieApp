package com.otus.lint_check

import com.android.tools.lint.detector.api.*
import org.w3c.dom.Element
import kotlin.math.abs

class WrongDimensionDetector : ResourceXmlDetector() {

    companion object {
        private const val NAMESPACE = "http://schemas.android.com/apk/res/android"
        private const val DIMENSIONS = "dp"

        private const val DIVIDER = 4

        private const val DESCRIPTION = "All xml size values must be divisible by 4"
        private const val EXPLANATION = "All xml size values must be divisible by 4"

        val ISSUE = Issue.create(
            id = "WrongDimensionDetector",
            briefDescription = DESCRIPTION,
            explanation = EXPLANATION,
            category = Category.TYPOGRAPHY,
            priority = 10,
            severity = Severity.ERROR,
            implementation = Implementation(
                WrongDimensionDetector::class.java,
                Scope.RESOURCE_FILE_SCOPE
            )
        )
    }

    private val tags = setOf(
        "layout_margin",
        "layout_marginTop",
        "layout_marginBottom",
        "layout_marginStart",
        "layout_marginEnd",
        "layout_marginLeft",
        "layout_marginRight",

        "padding",
        "paddingTop",
        "paddingBottom",
        "paddingStart",
        "paddingEnd",
        "paddingTopLeft",
        "paddingTopRight",

        "layout_width",
        "layout_height"
    )

    private val skipValues = setOf(
        "match_parent",
        "wrap_content",
        "0dp"
    )

    override fun getApplicableElements(): Collection<String> = ALL

    override fun visitElement(context: XmlContext, element: Element) {

        fun validateDimensions(value: String) {
            val numericValue = value.split(DIMENSIONS)[0].toIntOrNull()
            numericValue?.let { number ->
                if (number % DIVIDER != 0) {
                    context.report(
                        ISSUE,
                        context.getLocation(element),
                        DESCRIPTION,
                        createFix(number)
                    )
                }
            }
        }

        tags.map { element.getAttributeNS(NAMESPACE, it) }
            .filter { it !in skipValues }
            .forEach { validateDimensions(it) }
    }

    private fun createFix(wringDimension: Int): LintFix {
        return LintFix.create()
            .replace()
            .text("$wringDimension$DIMENSIONS")
            .with(findClosestDivision(wringDimension))
            .build()
    }

    private fun findClosestDivision(number: Int): String {
        val divisionResult = number / DIVIDER
        val number1 = DIVIDER * divisionResult
        val number2 = if (number * DIVIDER > 0)
            DIVIDER * (divisionResult + 1)
        else
            DIVIDER * (divisionResult - 1)
        return if (abs(number - number1) < (number - number2)) {
            "$number1$DIMENSIONS"
        } else {
            "$number2$DIMENSIONS"
        }
    }
}