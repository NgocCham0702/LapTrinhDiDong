package com.example.thuchanh2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

// Assuming these are your Fragment classes (replace with actual names)
class FirstFragment : Fragment() { /* ... */ }
class SecondFragment : Fragment() { /* ... */ }
class ThirdFragment : Fragment() { /* ... */ }

class ViewPagerAdapter(fm: FragmentManager, behavior: Int) :
    FragmentStatePagerAdapter(fm, behavior) {

    // You could define the number of pages as a constant
    private val pageCount = 3

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> first() // Use descriptive fragment class names
            1 -> SecondFragment()
            2 -> ThirdFragment()
            else -> {
                // Handle unexpected positions gracefully.
                // Option 1: Throw an exception (safer if this shouldn't happen)
                // throw IllegalArgumentException("Invalid position: $position")
                // Option 2: Return a default fragment (as in the original, but be mindful)
                FirstFragment() // Or some other default/error fragment
            }
        }
    }

    override fun getCount(): Int {
        return pageCount // Or directly return 3 if it's truly fixed
    }

    // Optional: Override if you need page titles for a TabLayout
    // override fun getPageTitle(position: Int): CharSequence? {
    //     return when (position) {
    //         0 -> "Profile"
    //         1 -> "Settings"
    //         2 -> "Feed"
    //         else -> null
    //     }
    // }
}