package jp.neechan.akari.dictionary.lint_checks.xml

import com.android.tools.lint.checks.infrastructure.TestFiles.xml
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Enclosed::class)
@Suppress("UnstableApiUsage")
class HardcodedSizeDetectorTest {

    @RunWith(Parameterized::class)
    class DetectorTest(
        private val inputTvWidth: String,
        private val inputTvHeight: String,
        private val inputTvMargin: String,
        private val inputTvPadding: String,
        private val inputTvDrawablePadding: String,
        private val inputTvElevation: String,
        private val inputTvTextSize: String,
        private val expectedWarningsCount: Int) {

        @Test
        fun `should report expected count of warnings`() {
            lint()
                .allowMissingSdk()
                .files(
                    xml(
                        "res/layout/layout.xml",
                        """<?xml version="1.0" encoding="utf-8"?>
                        <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
                            xmlns:app="http://schemas.android.com/apk/res-auto"
                            xmlns:tools="http://schemas.android.com/tools"
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content">

                            <TextView
                                android:layout_width="$inputTvWidth"
                                android:layout_height="$inputTvHeight"
                                android:layout_margin="$inputTvMargin"
                                android:drawablePadding="$inputTvDrawablePadding"
                                android:padding="$inputTvPadding"
                                android:textSize="$inputTvTextSize"
                                app:elevation="$inputTvElevation"
                                tools:textSize="20sp" />

                        </FrameLayout>"""
                    )
                )
                .issues(HardcodedSizeDetector.ISSUE)
                .run()
                .expectWarningCount(expectedWarningsCount)
                .expectErrorCount(0)
        }

        companion object {

            private const val HARDCODED_DP = "8dp"
            private const val HARDCODED_SP = "16sp"
            private const val DIMEN = "@dimen/some_dimen"
            private const val MATCH_PARENT = "match_parent"
            private const val WRAP_CONTENT = "wrap_content"
            private const val VALUE_0_DP = "0dp"
            private const val VALUE_0_SP = "0sp"

            @Parameterized.Parameters
            @JvmStatic
            fun getParams(): Collection<Array<Any>> {
                return listOf(
                    //      width         height        margin        padding     drawablePadding elevation     textSize      warnings
                    arrayOf(DIMEN,        MATCH_PARENT, WRAP_CONTENT, VALUE_0_DP,   DIMEN,        DIMEN,        VALUE_0_SP,   0),
                    arrayOf(HARDCODED_DP, MATCH_PARENT, WRAP_CONTENT, VALUE_0_DP,   DIMEN,        DIMEN,        VALUE_0_SP,   1),
                    arrayOf(HARDCODED_DP, HARDCODED_DP, WRAP_CONTENT, VALUE_0_DP,   DIMEN,        DIMEN,        VALUE_0_SP,   2),
                    arrayOf(HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, VALUE_0_DP,   DIMEN,        DIMEN,        VALUE_0_SP,   3),
                    arrayOf(HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, DIMEN,        DIMEN,        VALUE_0_SP,   4),
                    arrayOf(HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, DIMEN,        VALUE_0_SP,   5),
                    arrayOf(HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, VALUE_0_SP,   6),
                    arrayOf(HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, HARDCODED_SP, 7)
                )
            }
        }
    }

    class LintFixTest {

        @Test
        fun `should suggest a lint fix`() {
            val filename = "res/layout/layout.xml"

            val inputFile = """<?xml version="1.0" encoding="utf-8"?>
                            <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
                                android:layout_width="match_parent"
                                android:layout_height="20dp" />"""

            val expectedFile = """<?xml version="1.0" encoding="utf-8"?>
                            <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
                                android:layout_width="match_parent"
                                android:layout_height="@dimen/[value]" />"""

            lint()
                .allowMissingSdk()
                .files(xml(filename, inputFile))
                .issues(HardcodedSizeDetector.ISSUE)
                .run()
                .checkFix("Use dimens.xml", xml(filename, expectedFile))
        }
    }
}