package com.geogebra

import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.geogebra.code.BaseUiTest
import com.geogebra.code.childOf
import com.geogebra.ui.materials.MaterialsActivity
import junit.framework.TestCase.assertNotNull
import org.hamcrest.CoreMatchers.allOf
import org.junit.Test


class MaterialsAcceptanceTest : BaseUiTest() {

    lateinit var activity: ActivityScenario<MaterialsActivity>

    override fun before() {
        super.before()
        activity = ActivityScenario.launch(MaterialsActivity::class.java)
    }

    @Test
    fun material_00_PageTitle() {
        activity.moveToState(Lifecycle.State.RESUMED)
        assertDisplayed("Title")
    }

    @Test
    fun material_01_ItemsLoad() {
        materialSuccessCase()
        assertRecyclerViewItemCount(R.id.material_recyclerView, 5)
    }

    @Test
    fun materials_02_ItemCheck() {
        materialSuccessCase()

        onView(allOf(withId(R.id.material_item_title_textView), isDescendantOfA(childOf(withId(R.id.material_recyclerView), 0))))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.material_item_subtitle_textView), isDescendantOfA(childOf(withId(R.id.material_recyclerView), 0))))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.material_item_imageView), isDescendantOfA(childOf(withId(R.id.material_recyclerView), 0))))
            .check(matches(isDisplayed()))
    }



    private fun materialSuccessCase() {
        var recyclerView: RecyclerView? = null
        activity.onActivity {
            recyclerView = it.findViewById(R.id.material_recyclerView)
        }
        activity.moveToState(Lifecycle.State.RESUMED)
        assertNotNull(recyclerView)
        waitForAdapterChange(recyclerView!!)
    }
}