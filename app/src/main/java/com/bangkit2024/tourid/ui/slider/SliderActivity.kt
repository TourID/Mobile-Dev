package com.bangkit2024.tourid.ui.slider

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.bangkit2024.tourid.R
import com.bangkit2024.tourid.adapter.SliderAdapter
import com.bangkit2024.tourid.ui.login.LoginActivity

class SliderActivity : AppCompatActivity() {

    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var dotsTy: ArrayList<TextView>
    private lateinit var layouts: IntArray
    private lateinit var sliderPager: ViewPager
    private lateinit var btnNext: Button
    private lateinit var btnSkip: Button
    private lateinit var dotsLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slider)

        val actionBar: ActionBar? = supportActionBar
        actionBar?.hide()

        statusBarTransparent()

        // Initialize the slider layouts
        layouts = intArrayOf(
            R.layout.slide_1,  // Replace with actual layout resource IDs
            R.layout.slide_2,
            R.layout.slide_3
        )

        // Setup the SliderAdapter
        sliderAdapter = SliderAdapter(layouts, this)
        sliderPager = findViewById(R.id.slider_pager)
        sliderPager.adapter = sliderAdapter

        btnNext = findViewById(R.id.btn_next)
        btnSkip = findViewById(R.id.btn_skip)
        dotsLayout = findViewById(R.id.dotsLayout)

        // Set up the button to skip to LoginActivity
        btnSkip.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnNext.setOnClickListener {
            val currentItem = sliderPager.currentItem
            if (currentItem < layouts.size - 1) {
                sliderPager.currentItem = currentItem + 1
            } else {
                // Last slide, start LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        sliderPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            //btn gone after slide 2
            override fun onPageSelected(position: Int) {
                if (position == layouts.size - 1) {
                    btnSkip.visibility = View.GONE
                } else {
                    btnSkip.visibility = View.VISIBLE
                }
                addDotsIndicator(position)
            }


            override fun onPageScrollStateChanged(state: Int) {
            }
        })

        addDotsIndicator(0) // Initialize dots
    }

    private fun addDotsIndicator(position: Int) {
        dotsLayout.removeAllViews()
        dotsTy = ArrayList()

        for (i in layouts.indices) {
            val dot = TextView(this)
            dot.text = "•"
            dot.textSize = 35f
            dot.setTextColor(ContextCompat.getColor(this, R.color.colorTransparentWhite))
            dotsTy.add(dot)
            dotsLayout.addView(dot)
        }

        if (dotsTy.isNotEmpty()) {
            dotsTy[position].setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
        }
    }

    private fun statusBarTransparent() {
        if (Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }
    }
}
