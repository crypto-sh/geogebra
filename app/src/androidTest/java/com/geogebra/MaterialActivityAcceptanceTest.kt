package com.geogebra

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.geogebra.code.BaseUiTest
import com.geogebra.code.childOf
import com.geogebra.core.dto.Creator
import com.geogebra.core.dto.Material
import com.geogebra.core.utils.getForegroundFragment
import com.geogebra.core.utils.toShortString
import com.geogebra.ui.material.MaterialActivity
import com.geogebra.ui.material.fragments.MaterialListFragment
import junit.framework.TestCase.*
import org.hamcrest.CoreMatchers.allOf
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runners.MethodSorters
import java.util.*

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class MaterialActivityAcceptanceTest : BaseUiTest() {

    private val expected = Material("m9cvbqgp", "Parallel Lines in the Coordinate Plane: Quick Exploration", "ws", Date(), Date(), "O", "https://www.geogebra.org/resource/m9cvbqgp/jwu26zO0VaMqZUpt/material-m9cvbqgp-thumb$1.png", Creator(5743822, "GeoGebra Team", "geogebrateam"))

    lateinit var activity: ActivityScenario<MaterialActivity>

    override fun before() {
        super.before()
        activity = ActivityScenario.launch(MaterialActivity::class.java)
    }

    @Test
    fun material_00_PageTitle() {
        activity.moveToState(Lifecycle.State.RESUMED)
        assertDisplayed("Title")
    }

    @Test
    fun material_01_list_fragment_showing(){
        activity.moveToState(Lifecycle.State.RESUMED)
        var current : Fragment? = null
        activity.onActivity {
            current = it.getForegroundFragment(R.id.material_nav_host)
            assertTrue(current != null)
        }
        assertNotNull(current)
        assertEquals(MaterialListFragment::class.java, current!!::class.java)
        onView(withId(R.id.material_recyclerView)).check(matches(isDisplayed()))
    }

    @Test
    fun material_02_ItemsLoad() {
        materialListSuccessCase()
        assertRecyclerViewItemCount(R.id.material_recyclerView, 5)
    }

    @Test
    fun material_03_ItemCheck() {
        materialListSuccessCase()

        onView(allOf(withId(R.id.material_item_title_textView),
            isDescendantOfA(childOf(withId(R.id.material_recyclerView), 0))
        ))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.material_item_subtitle_textView),
            isDescendantOfA(childOf(withId(R.id.material_recyclerView), 0))
        ))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.material_item_imageView),
            isDescendantOfA(childOf(withId(R.id.material_recyclerView), 0))
        ))
            .check(matches(isDisplayed()))
    }

    @Test
    fun material_05_list_fragment_navigation_items(){
        materialListSuccessCase()
        onView(allOf(withId(R.id.material_item_title_textView), isDescendantOfA(childOf(withId(R.id.material_recyclerView), 0))))
            .check(matches(isDisplayed()))

        onView(allOf(withId(R.id.material_item_imageView), isDescendantOfA(childOf(withId(R.id.material_recyclerView), 0))))
            .check(matches(isDisplayed()))
            .perform(click())

        assertDisplayed("Details")
    }

    @Test
    fun material_06_details_details_showing(){
        activity.moveToState(Lifecycle.State.RESUMED)
        materialDetailsSuccessCase()
        assertDisplayed(R.id.material_details_recyclerView)
        assertRecyclerViewItemCount(R.id.material_details_recyclerView, 5)
    }

    @Test
    fun material_07_details_item_id_checking(){
        materialDetailsSuccessCase()
        var header = ""
        activity.onActivity {
            header = it.getString(R.string.material_details_header_id)
        }
        onView(allOf(withId(R.id.material_details_header_textView), isDescendantOfA(childOf(withId(R.id.material_details_recyclerView), 0))))
            .check(matches(isDisplayed()))
            .check(matches(withText(header)))

        onView(allOf(withId(R.id.material_details_value_textView), isDescendantOfA(childOf(withId(R.id.material_details_recyclerView), 0))))
            .check(matches(isDisplayed()))
            .check(matches(withText(expected.id)))
    }

    @Test
    fun material_08_details_item_title_checking(){
        materialDetailsSuccessCase()
        var header = ""
        activity.onActivity {
            header = it.getString(R.string.material_details_header_title)
        }
        onView(allOf(withId(R.id.material_details_header_textView), isDescendantOfA(childOf(withId(R.id.material_details_recyclerView), 1))))
            .check(matches(isDisplayed()))
            .check(matches(withText(header)))

        onView(allOf(withId(R.id.material_details_value_textView), isDescendantOfA(childOf(withId(R.id.material_details_recyclerView), 1))))
            .check(matches(isDisplayed()))
            .check(matches(withText(expected.title)))
    }

    @Test
    fun material_09_details_item_author_checking(){
        materialDetailsSuccessCase()
        var header = ""
        activity.onActivity {
            header = it.getString(R.string.material_details_header_author)
        }
        onView(allOf(withId(R.id.material_details_header_textView), isDescendantOfA(childOf(withId(R.id.material_details_recyclerView), 2))))
            .check(matches(isDisplayed()))
            .check(matches(withText(header)))

        onView(allOf(withId(R.id.material_details_value_textView), isDescendantOfA(childOf(withId(R.id.material_details_recyclerView), 2))))
            .check(matches(isDisplayed()))
            .check(matches(withText(expected.creator.name)))
    }

    @Test
    fun material_10_details_item_create_date_checking(){
        materialDetailsSuccessCase()
        var header = ""
        activity.onActivity {
            header = it.getString(R.string.material_details_header_create_date)
        }
        onView(allOf(withId(R.id.material_details_header_textView), isDescendantOfA(childOf(withId(R.id.material_details_recyclerView), 3))))
            .check(matches(isDisplayed()))
            .check(matches(withText(header)))

        onView(allOf(withId(R.id.material_details_value_textView), isDescendantOfA(childOf(withId(R.id.material_details_recyclerView), 3))))
            .check(matches(isDisplayed()))
            .check(matches(withText(expected.dateCreated.toShortString())))
    }

    @Test
    fun material_10_details_item_modified_date_checking(){
        materialDetailsSuccessCase()
        var header = ""
        activity.onActivity {
            header = it.getString(R.string.material_details_header_modified_date)
        }
        onView(allOf(withId(R.id.material_details_header_textView), isDescendantOfA(childOf(withId(R.id.material_details_recyclerView), 4))))
            .check(matches(isDisplayed()))
            .check(matches(withText(header)))

        onView(allOf(withId(R.id.material_details_value_textView), isDescendantOfA(childOf(withId(R.id.material_details_recyclerView), 4))))
            .check(matches(isDisplayed()))
            .check(matches(withText(expected.dateModified.toShortString())))
    }

    private fun materialListSuccessCase() {
        var recyclerView: RecyclerView? = null
        activity.onActivity {
            recyclerView = it.findViewById(R.id.material_recyclerView)
        }
        activity.moveToState(Lifecycle.State.RESUMED)
        assertNotNull(recyclerView)
        waitForAdapterChange(recyclerView!!)
    }

    private fun materialDetailsSuccessCase() {
        activity.moveToState(Lifecycle.State.RESUMED)
        activity.onActivity {
            val fragment = it.getForegroundFragment<MaterialListFragment>(R.id.material_nav_host)
            assertNotNull(fragment)
            fragment.navigateDetails(expected)
        }
    }
}