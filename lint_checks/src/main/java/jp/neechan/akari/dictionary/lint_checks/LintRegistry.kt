package jp.neechan.akari.dictionary.lint_checks

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.detector.api.CURRENT_API
import jp.neechan.akari.dictionary.lint_checks.xml.HardcodedSizeDetector

@Suppress("UnstableApiUsage")
class LintRegistry : IssueRegistry() {

    override val issues = listOf(HardcodedSizeDetector.ISSUE)

    override val api = CURRENT_API
}