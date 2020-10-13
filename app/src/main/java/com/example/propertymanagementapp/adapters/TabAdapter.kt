package com.example.propertymanagementapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.propertymanagementapp.ui.fragments.LandlordRegisterFragment
import com.example.propertymanagementapp.ui.fragments.TenantRegisterFragment

class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> TenantRegisterFragment()
            else -> LandlordRegisterFragment()

        }
    }

    override fun getPageTitle(position: Int) : CharSequence? {
        return when(position){
            0 -> "Tenant"
            else -> "Landlord"

        }
    }
}