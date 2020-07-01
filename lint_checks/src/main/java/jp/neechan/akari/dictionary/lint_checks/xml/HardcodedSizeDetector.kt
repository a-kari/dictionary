package jp.neechan.akari.dictionary.lint_checks.xml

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.LayoutDetector
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.XmlContext
import org.w3c.dom.Attr

@Suppress("UnstableApiUsage")
class HardcodedSizeDetector : LayoutDetector() {

    companion object {

        private val HARDCODED_SIZE_PATTERN = Regex("\\d+(dp|sp)")

        private const val DESCRIPTION = "Hardcoded size"
        private const val EXPLANATION = "You should use sizes from dimens.xml to have " +
                                        "the correct layout on all screen densities"

        val ISSUE = Issue.create(
            "HardcodedSize",
            DESCRIPTION,
            EXPLANATION,
            Category.USABILITY,
            7,
            Severity.WARNING,
            Implementation(HardcodedSizeDetector::class.java, Scope.RESOURCE_FILE_SCOPE)
        )
    }

    private val whitelist = listOf("0dp", "0sp", "1dp")

    override fun getApplicableAttributes(): Collection<String> = ALL

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        val size = attribute.value
        val isHardcoded = size.matches(HARDCODED_SIZE_PATTERN) && size !in whitelist

        if (isHardcoded) {
            context.report(ISSUE, context.getLocation(attribute), DESCRIPTION)
        }
    }
}