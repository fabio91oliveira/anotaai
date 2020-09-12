//package me.fabiooliveira.getnotes.legacy.feature.onboarding.ui.activity
//
//import android.animation.ArgbEvaluator
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentManager
//import androidx.fragment.app.FragmentPagerAdapter
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.appcompat.app.AppCompatActivity
//import androidx.appcompat.widget.AppCompatImageView
//import androidx.viewpager.widget.ViewPager
//
//import me.fabiooliveira.getnotes.R
//import kotlinx.android.synthetic.main.activity_onboarding.*
//import androidx.core.content.ContextCompat
//import kotlinx.android.synthetic.main.fragment_onboarding.view.*
//import me.fabiooliveira.getnotes.legacy.util.ViewUtil
//
///**
// * Created by Fabio Oliveira
// * Email: fabio91oliveira@gmail.com
// * Mobile: +55 (21) 98191-4951
// * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
// */
//
//class OnboardingActivity : AppCompatActivity() {
//
//    private val TOTAL_PAGES = 5
//
//    private lateinit var colorList: IntArray
//    private var page = 0
//    private var indicators: Array<AppCompatImageView>? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_onboarding)
//        initColors()
//        initIndicators()
//        initViewPager()
//        initListeners()
//    }
//
//    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
//
//        override fun getItem(position: Int): Fragment {
//            return PlaceholderFragment.newInstance(position + 1)
//        }
//
//        override fun getCount(): Int {
//            return TOTAL_PAGES
//        }
//    }
//
//    private fun initColors() {
//        val color1 = ContextCompat.getColor(this, R.color.colorOnBoardingPageDefault)
//        val color2 = ContextCompat.getColor(this, R.color.colorRelevanceFirst)
//        val color3 = ContextCompat.getColor(this, R.color.colorRelevanceSecond)
//        val color4 = ContextCompat.getColor(this, R.color.colorRelevanceThird)
//        val color5 = ContextCompat.getColor(this, R.color.colorOnBoardingPageFinal)
//
//        colorList = intArrayOf(color1, color2, color3, color4, color5)
//    }
//
//    private fun initIndicators() {
//        indicators = arrayOf(ivIndicator0, ivIndicator1, ivIndicator2, ivIndicator3, ivIndicator4)
//    }
//
//    private fun initViewPager() {
//        mViewPager.adapter = SectionsPagerAdapter(supportFragmentManager)
//        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
//            override fun onPageScrollStateChanged(state: Int) {}
//            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
//                val colorUpdate = ArgbEvaluator().evaluate(positionOffset, colorList[position], colorList[if(position == 4) position else position + 1]).toString().toInt()
//                ViewUtil.changeStatusBarColor(this@OnboardingActivity,  colorUpdate, true)
//                mViewPager.setBackgroundColor(colorUpdate)
//            }
//            override fun onPageSelected(position: Int) {
//                page = position
//                updateIndicators(page)
//                mViewPager.setBackgroundColor(colorList[position])
//                ViewUtil.changeStatusBarColor(this@OnboardingActivity, colorList[position], true)
//                bvSkip.visibility = if(position == 4) View.GONE else View.VISIBLE
//                ibNext.visibility = if(position == 4) View.GONE else View.VISIBLE
//                bvFinish.visibility = if(position == 4) View.VISIBLE else View.GONE
//            }
//        })
//    }
//
//    private fun initListeners() {
//        ibNext.setOnClickListener {
//            page += 1
//            mViewPager.setCurrentItem(page, true)
//        }
//        bvSkip.setOnClickListener {
//            finish()
//        }
//        bvFinish.setOnClickListener {
//            finish()
//        }
//    }
//
//    private fun updateIndicators(position: Int) {
//        indicators?.let{
//            for (i in 0 until it.size) {
//                it[i].setBackgroundResource(
//                        if (i == position) R.drawable.indicator_selected else R.drawable.indicator_unselected
//                )
//            }
//        }
//    }
//
//    class PlaceholderFragment : Fragment() {
//
//        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                                  savedInstanceState: Bundle?): View? {
//            val rootView = inflater.inflate(R.layout.fragment_onboarding, container, false)
//            val position = arguments?.getInt(ARG_SECTION_NUMBER)
//
//            when(position) {
//                1-> {rootView.ivWelcome.setImageResource(R.drawable.img_welcome)
//                    rootView.tvTitle.text = context?.resources?.getString(R.string.onboarding_first_page_title)
//                    rootView.tvSubtitle.text = context?.resources?.getString(R.string.onboarding_first_page_subtitle)}
//                2-> {rootView.ivWelcome.setImageResource(R.drawable.img_high)
//                    rootView.tvTitle.text = context?.resources?.getString(R.string.onboarding_second_page_title)
//                    rootView.tvSubtitle.text = context?.resources?.getString(R.string.onboarding_second_page_subtitle)}
//                3-> {rootView.ivWelcome.setImageResource(R.drawable.img_medium)
//                    rootView.tvTitle.text = context?.resources?.getString(R.string.onboarding_third_page_title)
//                    rootView.tvSubtitle.text = context?.resources?.getString(R.string.onboarding_third_page_subtitle)}
//                4-> {rootView.ivWelcome.setImageResource(R.drawable.img_low)
//                    rootView.tvTitle.text = context?.resources?.getString(R.string.onboarding_fourth_page_title)
//                    rootView.tvSubtitle.text = context?.resources?.getString(R.string.onboarding_fourth_page_subtitle)}
//                5-> {rootView.ivWelcome.setImageResource(R.drawable.img_end)
//                    rootView.tvTitle.text = context?.resources?.getString(R.string.onboarding_fifth_page_title)
//                    rootView.tvSubtitle.text = context?.resources?.getString(R.string.onboarding_fifth_page_subtitle)}
//            }
//            return rootView
//        }
//
//        companion object {
//            private val ARG_SECTION_NUMBER = "section_number"
//            fun newInstance(sectionNumber: Int): PlaceholderFragment {
//                val fragment = PlaceholderFragment()
//                val args = Bundle()
//                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
//                fragment.arguments = args
//                return fragment
//            }
//        }
//    }
//}
