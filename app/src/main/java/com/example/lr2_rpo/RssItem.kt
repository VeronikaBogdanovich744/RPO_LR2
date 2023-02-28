package com.example.lr2_rpo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_rss_item.*

class RssItem : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rss_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /* val btnRead: Button = view.findViewById(R.id.btnRead)
        btnRead.setOnClickListener {
            val fragement = NewsFragment.newInstance()
            //fragement.setNote(notes)
            replaceFragment(fragement,true)
        }*/

    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(/*param1: String, param2: String*/) =
            RssItem().apply {
                arguments = Bundle().apply {
                }
            }
    }

    fun replaceFragment(fragment:Fragment, istransition:Boolean){
        val act = requireActivity()
        val fragmentTransition= act.supportFragmentManager.beginTransaction()
        if (istransition){
            fragmentTransition.setCustomAnimations(android.R.anim.slide_out_right,android.R.anim.slide_in_left)
        }
        fragmentTransition.replace(R.id.frame_layout,fragment).addToBackStack(fragment.javaClass.simpleName).commit()
    }
}