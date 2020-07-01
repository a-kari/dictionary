package jp.neechan.akari.dictionary.lint_checks.xml

import com.android.tools.lint.checks.infrastructure.TestFiles.xml
import com.android.tools.lint.checks.infrastructure.TestLintTask.lint
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
@Suppress("UnstableApiUsage")
class HardcodedSizeDetectorTest(
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
                            android:layout_width="match_parent"
                            android:layout_height="wrap_content">

                            <TextView
                                android:layout_width="$inputTvWidth"
                                android:layout_height="$inputTvHeight"
                                android:layout_margin="$inputTvMargin"
                                android:drawablePadding="$inputTvDrawablePadding"
                                android:padding="$inputTvPadding"
                                android:textSize="$inputTvTextSize"
                                app:elevation="$inputTvElevation" />

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
        private const val VALUE_1_DP = "1dp"

        @Parameterized.Parameters
        @JvmStatic
        fun getParams(): Collection<Array<Any>> {
            return listOf(
                //      width         height        margin        padding     drawablePadding elevation     textSize      warnings
                arrayOf(DIMEN,        MATCH_PARENT, WRAP_CONTENT, VALUE_0_DP,   VALUE_1_DP,   DIMEN,        DIMEN,        0),
                arrayOf(HARDCODED_DP, MATCH_PARENT, WRAP_CONTENT, VALUE_0_DP,   VALUE_1_DP,   DIMEN,        DIMEN,        1),
                arrayOf(HARDCODED_DP, HARDCODED_DP, WRAP_CONTENT, VALUE_0_DP,   VALUE_1_DP,   DIMEN,        DIMEN,        2),
                arrayOf(HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, VALUE_0_DP,   VALUE_1_DP,   DIMEN,        DIMEN,        3),
                arrayOf(HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, VALUE_1_DP,   DIMEN,        DIMEN,        4),
                arrayOf(HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, DIMEN,        DIMEN,        5),
                arrayOf(HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, DIMEN,        6),
                arrayOf(HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, HARDCODED_DP, HARDCODED_SP, 7)
            )
        }
    }
}