package com.geogebra.code

import android.view.View
import android.view.ViewGroup
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher


fun childOf(view : Matcher<View>, childPosition : Int) : Matcher<View> {
    return object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description?) {
            description?.appendText("position $childPosition of parent")
            view.describeTo(description)
        }

        override fun matchesSafely(item: View): Boolean {
            if (item.parent !is ViewGroup) return false
            val parent = item.parent as ViewGroup
            return (view.matches(parent) && parent.childCount > childPosition && parent.getChildAt(childPosition) == item)
        }
    }
}