package jp.neechan.akari.dictionary.lint_checks.xml

import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.LayoutDetector
import com.android.tools.lint.detector.api.LintFix
import com.android.tools.lint.detector.api.Location
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import com.android.tools.lint.detector.api.XmlContext
import org.w3c.dom.Attr

/**
 * Finds hardcoded sizes in xml layout files. Hardcoded sizes can look like:
 *
 * android:layout_width="100dp"
 * android:textSize="20sp"
 * app:elevation="8dp"
 *
 * It's bad, because the app should look fine on all screens. And it's necessary to use sizes from
 * dimens.xml, because there may be several dimens.xml for each screen type.
 *
 * The lint has a whitelist:
 *
 * - 0dp value is frequently used, for example, in ConstraintLayout.
 * - 0sp value can be used with dynamic text size.
 * - All attributes from tools namespace.
 */
@Suppress("UnstableApiUsage")
class HardcodedSizeDetector : LayoutDetector() {

    companion object {

        private val HARDCODED_SIZE_PATTERN = Regex("\\d+(dp|sp)")

        private const val DESCRIPTION = "Hardcoded size"
        private const val EXPLANATION = "You should use sizes from dimens.xml to have " +
                                        "the correct layout on all screen densities"

        val ISSUE = Issue.create(
            id = "HardcodedSize",
            briefDescription = DESCRIPTION,
            explanation = EXPLANATION,
            category = Category.USABILITY,
            priority = 7,
            severity = Severity.WARNING,
            implementation = Implementation(
                HardcodedSizeDetector::class.java, Scope.RESOURCE_FILE_SCOPE
            )
        )
    }

    private val namespacesWhitelist = listOf("http://schemas.android.com/tools")
    private val valuesWhitelist = listOf("0dp", "0sp")

    override fun getApplicableAttributes(): Collection<String> = ALL

    override fun visitAttribute(context: XmlContext, attribute: Attr) {
        // Ignore attributes from whitelist namespaces.
        if (attribute.namespaceURI in namespacesWhitelist) {
            return
        }

        // Check if the attribute's value is a hardcoded size, which is not in the whitelist,
        // and report if so.
        val value = attribute.value
        val isHardcodedSize = value.matches(HARDCODED_SIZE_PATTERN) && value !in valuesWhitelist

        if (isHardcodedSize) {
            context.report(
                ISSUE,
                context.getLocation(attribute),
                DESCRIPTION,
                createLint(context.getValueLocation(attribute))
            )
        }
    }

    private fun createLint(valueLocation: Location): LintFix {
        return LintFix.create()
                      .name("Use dimens.xml")
                      .replace()
                      .range(valueLocation)
                      .with("@dimen/value")
                      .select("value")
                      .build()
    }
}
