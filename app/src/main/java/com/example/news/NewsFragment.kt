package com.example.news

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.concurrent.thread

class NewsFragment(var newType : String):Fragment() {

    private lateinit var newsRecyclerView: RecyclerView

    private val newsList = ArrayList<News>()

    private lateinit var refresh : SwipeRefreshLayout

    @SuppressLint("NotifyDataSetChanged")
    private fun refresh() {
        thread {
            val request = Request.Builder()
                .url("http://v.juhe.cn/toutiao/index?type="+newType+"&page=&page_size=&is_filter=&key=" + MyApplication.Key)
                .build()
            val response = OkHttpClient().newCall(request).execute()
            val json = response.body?.string()
            val newsResponse = Gson().fromJson(json, NewsResponse::class.java)
            if (newsResponse != null&&newsResponse.result!=null) {
                val data = newsResponse.result.data
                newsList.clear()
                newsList.addAll(data)
                activity?.runOnUiThread {
                    newsRecyclerView.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news,container,false)
        newsRecyclerView = view.findViewById(R.id.news_recycler_view)
        refresh = view.findViewById(R.id.news_refresh)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newsRecyclerView.layoutManager = LinearLayoutManager(context)
        newsRecyclerView.adapter = NewsMyAdapter(newsList)
        refresh()
        refresh.setColorSchemeColors(Color.parseColor("#c75450"))
        refresh.setOnRefreshListener {
            thread {
                Thread.sleep(700)
                activity?.runOnUiThread {
                    refresh()
                    refresh.isRefreshing = false
                }

            }

        }
    }

    inner class NewsMyAdapter(val newsList: List<News>) :
        RecyclerView.Adapter<NewsMyAdapter.NewsViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.news_item_one_image, parent, false)
            return NewsViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
            val news = newsList[position]
            holder.title.text = news.title
            holder.descriotion.text = news.author_name
            Glide.with(this@NewsFragment).load(news.thumbnail_pic_s).into(holder.imageView)
            //holder.adapterPosition下标
            holder.title.setOnClickListener {
//                Toast.makeText(MyApplication.context, "ok" + holder.adapterPosition, Toast.LENGTH_SHORT)
//                    .show()
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("url=", newsList[holder.adapterPosition].url)
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return newsList.size
        }

        inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView = itemView.findViewById(R.id.news_title)
            val descriotion: TextView = itemView.findViewById(R.id.news_disc)
            val imageView: com.makeramen.roundedimageview.RoundedImageView =
                itemView.findViewById(R.id.news_image)
        }
    }
}