package com.geogebra

import androidx.test.core.app.ActivityScenario
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.geogebra.code.BaseUiTest
import com.geogebra.ui.compose.ComposeActivity
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class ComposeFeature : BaseUiTest() {

    var activity : ActivityScenario<ComposeActivity> = ActivityScenario.launch(ComposeActivity::class.java)

    @Test
    fun testShowingHelloWorld() {
        assertDisplayed("Hello world!")
    }
}