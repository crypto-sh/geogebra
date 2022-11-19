package com.geogebra

import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.geogebra.code.BaseUiTest
import com.geogebra.code.childOf
import com.geogebra.core.dto.Creator
import com.geogebra.core.dto.Material
import com.geogebra.ui.details.MaterialDetailsActivity
import com.geogebra.ui.details.viewModel.bundle_material_details_key
import junit.framework.TestCase.assertNotNull
import org.hamcrest.CoreMatchers.allOf
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import java.util.*


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MaterialsDetailsAcceptanceTest : BaseUiTest() {

    private val createDate = Date()
    private val modifiedDate = Date()
    private val fakeUrl = "https://www.geogebra.org/resource/m9cvbqgp/jwu26zO0VaMqZUpt/material-m9cvbqgp-thumb1.png"
    private var expected = Material("m9cvbqgp", "Parallel Lines in the Coordinate Plane: Quick Exploration", "ws", createDate, modifiedDate, "O", fakeUrl, Creator(5743822, "GeoGebra Team", "geogebrateam"))

    lateinit var activity: ActivityScenario<MaterialDetailsActivity>

    override fun before() {
        super.before()
        activity = ActivityScenario.launch(Intent(ApplicationProvider.getApplicationContext(), MaterialDetailsActivity::class.java).apply {
            putExtra(bundle_material_details_key, expected)
        })
    }

    @Test
    fun details_00_title_check(){
        activity.moveToState(Lifecycle.State.RESUMED)
        assertDisplayed("Details")
    }

    @Test
    fun details_01_details_showing(){
        activity.moveToState(Lifecycle.State.RESUMED)
        materialDetailsSuccessCase()
        assertRecyclerViewItemCount(R.id.material_details_recyclerView, 5)
    }

    @Test
    fun details_02_details_item_id_checking(){
        activity.moveToState(Lifecycle.State.RESUMED)
        var idHeader = ""
        activity.onActivity {
            idHeader = it.getString(R.string.material_details_header_id)
        }
        materialDetailsSuccessCase()
        onView(allOf(withId(R.id.material_details_header_textView), isDescendantOfA(childOf(withId(R.id.material_details_recyclerView), 0))))
            .check(matches(isDisplayed()))
            .check(matches(withText(idHeader)))

        onView(allOf(withId(R.id.material_details_value_textView), isDescendantOfA(childOf(withId(R.id.material_details_recyclerView), 0))))
            .check(matches(isDisplayed()))
            .check(matches(withText(expected.id)))
    }

    private fun materialDetailsSuccessCase() {
        var recyclerView: RecyclerView? = null
        activity.onActivity {
            recyclerView = it.findViewById(R.id.material_details_recyclerView)
        }
        activity.moveToState(Lifecycle.State.RESUMED)
        assertNotNull(recyclerView)
        waitForAdapterChange(recyclerView!!)
    }
}