package com.binishmatheww.parenting.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.binishmatheww.parenting.R
import com.binishmatheww.parenting.databinding.FragmentSplashScreenBinding
import com.binishmatheww.parenting.popBackStack
import com.binishmatheww.parenting.replaceFragment

class SplashScreenFragment : Fragment() {


    private lateinit var layout : FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        layout = FragmentSplashScreenBinding.inflate(inflater,container,false)

        return layout.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animation()
    }

    private fun animation(){

        layout.splashScreenLabel.animate().rotationY(0f).scaleX(2.0f).scaleY(2.0f).setDuration(1000).withEndAction {

            try {

                activity.popBackStack()
                activity.replaceFragment(DashboardFragment())

            }
            catch (ignore : Exception){
                activity?.finish()
            }

        }.start()

    }

}