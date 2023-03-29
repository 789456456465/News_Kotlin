package com.example.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_search.*

class Search : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        supportActionBar?.hide()
        search_cancel_button.setOnClickListener {
            finish()
        }
        edit_search.setOnEditorActionListener { _, keyCode, _ ->
            if (keyCode == EditorInfo.IME_ACTION_DONE){
                Toast.makeText(this,"你输入了",Toast.LENGTH_SHORT).show()
                true
            }else{
                false
            }
        }


    }
}