package com.blovote.app.common

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.blovote.R
import org.jetbrains.anko.sdk25.coroutines.onClick

class CommonSuccessFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_success_fragment, container, false)

        view.findViewById<TextView>(R.id.text_view_message).text = arguments!!.getString(KEY_MESSAGE)
        view.findViewById<Button>(R.id.button_ok).onClick { activity?.finish() }

        return view
    }


    companion object {

        val KEY_MESSAGE = "key_message"


        fun newInstance(message: String) : CommonSuccessFragment {
            val fragment = CommonSuccessFragment()

            val bundle = Bundle()
            bundle.putString(KEY_MESSAGE, message)
            fragment.arguments = bundle

            return fragment
        }

    }
}