package com.techyourchance.androidviews.demonstrations._08_path_animation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techyourchance.androidviews.general.BaseFragment
import com.techyourchance.androidviews.R

class PathAnimationFragment : BaseFragment() {

    override val screenName get() = getString(R.string.screen_name_path_animation)

    private lateinit var viewAnimation: PathAnimationView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return layoutInflater.inflate(R.layout.layout_path_animation, container, false).apply {
            viewAnimation = findViewById(R.id.viewPathAnimation)
        }
    }

    override fun onResume() {
        super.onResume()
        viewAnimation.startAnimation(ANIMATION_DURATION_MS)
    }

    override fun onPause() {
        super.onPause()
        viewAnimation.stopAnimation()
    }

    companion object {

        const val ANIMATION_DURATION_MS = 2000L

        fun newInstance(): PathAnimationFragment {
            return PathAnimationFragment()
        }
    }

}