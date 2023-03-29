package com.example.news

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment :Fragment() {
    private val newsTypeList = listOf("shehui","guonei","guoji","yule","keji","junshi")
    val titleList = listOf("社会","国内","国际","娱乐","科技","军事")
    val fragmentList = ArrayList<NewsFragment>()
    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_home,container,false)
        tabLayout = view.findViewById(R.id.news_tab_layout)
        viewPager = view.findViewById(R.id.news_view_pager)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        home_tool_bar.inflateMenu(R.menu.home_tool_bar_menu)
        home_tool_bar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.message->Toast.makeText(context,"message",Toast.LENGTH_SHORT).show()
                R.id.game->Toast.makeText(context,"game",Toast.LENGTH_SHORT).show()
            }
            false
        }
        for (newsType in newsTypeList){
            fragmentList.add(NewsFragment(newsType))
        }
        //缓存
        viewPager.offscreenPageLimit = titleList.size
        viewPager.adapter = MyAdapter(childFragmentManager)
        //引用标题
        tabLayout.setupWithViewPager(viewPager)
        edit.keyListener=null
        edit.setOnClickListener {
            val intent = Intent(context,Search::class.java)
            startActivity(intent)
        }
        home_name.setOnClickListener {
            Toast.makeText(context,"你点击了头像",Toast.LENGTH_SHORT).show()
        }
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
        //重写标题
        override fun getPageTitle(position: Int): CharSequence {
            return titleList[position]
        }
    }
}