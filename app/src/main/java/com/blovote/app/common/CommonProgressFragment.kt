package com.blovote.app.common

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.blovote.R

class CommonProgressFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_progress_framgent, container, false)

        view.findViewById<TextView>(R.id.text_view_progress_descr).text = arguments!!.getString(KEY_TEXT)

        return view
    }


    companion object {

        private val KEY_TEXT = "text"

        fun newInstance(text: String) : CommonProgressFragment {
            val fragment = CommonProgressFragment()

            val bundle = Bundle()
            bundle.putString(KEY_TEXT, text)

            fragment.arguments = bundle

            return fragment
        }

    }
}