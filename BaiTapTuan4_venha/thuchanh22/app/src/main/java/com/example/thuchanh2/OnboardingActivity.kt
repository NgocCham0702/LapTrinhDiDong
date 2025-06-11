package com.example.thuchanh2

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import me.relex.circleindicator.CircleIndicator

class OnboardingActivity : AppCompatActivity() {

    private lateinit var tvSkip: TextView
    private lateinit var nextLayout: LinearLayout
    private lateinit var viewPager: ViewPager
    private lateinit var circleIndicator: CircleIndicator
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oboarding) // Đảm bảo đúng tên file XML

        initUI()

        viewPagerAdapter = ViewPagerAdapter(
            supportFragmentManager,
            androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        )

        viewPager.adapter = viewPagerAdapter
        circleIndicator.setViewPager(viewPager)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                if (position == 2) {
                    tvSkip.visibility = View.GONE
                    nextLayout.visibility = View.GONE
                } else {
                    tvSkip.visibility = View.VISIBLE
                    nextLayout.visibility = View.VISIBLE
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        tvSkip.setOnClickListener {
            val current = viewPager.currentItem
            if (current < 2) {
                viewPager.currentItem = current + 1
            }
        }
    }

    private fun initUI() {
        tvSkip = findViewById(R.id.tv_skip)
        nextLayout = findViewById(R.id.layou_next)
        viewPager = findViewById(R.id.view_pager)
        circleIndicator = findViewById(R.id.circle_indicator)
    }
}
