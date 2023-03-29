package com.example.news


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val fragmentList = listOf(HomeFragment(),TestFragment("1"),TestFragment("2"))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        //页面缓存
        content_viewpager.offscreenPageLimit=fragmentList.size
        content_viewpager.adapter = MyAdapter(supportFragmentManager)
        bottom_nav.setOnNavigationItemSelectedListener{
            when(it.itemId){
                R.id.nav_home -> content_viewpager.currentItem=0
                R.id.nav_test1->content_viewpager.currentItem=1
                R.id.nav_test2->content_viewpager.currentItem=2
            }
            false
        }
        content_viewpager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {}
            override fun onPageSelected(position: Int) {
                bottom_nav.menu.getItem(position).isChecked=true
            }
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    inner class MyAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ){
        override fun getCount(): Int {
            return fragmentList.size
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }
    }


}