package com.techyourchance.androidviews.exercises._07_

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.techyourchance.androidviews.R
import com.techyourchance.androidviews.general.BaseFragment

class CouponsFragment : BaseFragment() {

    override val screenName get() = getString(R.string.screen_name_coupons)

    private lateinit var viewCoupons: CouponsView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return layoutInflater.inflate(R.layout.layout_coupons, container, false).apply {
            viewCoupons = findViewById(R.id.viewCoupons)
            viewCoupons.showBorder = true
            viewCoupons.setCoupons(10, 3)
        }
    }

    companion object {
        fun newInstance(): CouponsFragment {
            return CouponsFragment()
        }
    }

}