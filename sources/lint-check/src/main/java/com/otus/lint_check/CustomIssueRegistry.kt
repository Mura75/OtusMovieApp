package com.otus.lint_check

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue

class CustomIssueRegistry : IssueRegistry() {

    override val api = CURRENT_API

    override val issues get() = listOf(
        WrongDimensionDetector.ISSUE
    )
}