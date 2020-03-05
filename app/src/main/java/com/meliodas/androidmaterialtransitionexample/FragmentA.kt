package com.meliodas.androidmaterialtransitionexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import com.google.android.material.transition.Hold
import kotlinx.android.synthetic.main.fragment_a.*

class FragmentA : Fragment() {



    override fun onCreateView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View? {
        return layoutInflater.inflate(
            R.layout.fragment_a, viewGroup, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btA.setOnClickListener {
            fragmentManager!!
                .beginTransaction()
                .addSharedElement(rootA, "thuan")
                .replace(R.id.container, FragmentB(), "TAG")
                .addToBackStack("TAG")
                .commit()
        }
    }

}