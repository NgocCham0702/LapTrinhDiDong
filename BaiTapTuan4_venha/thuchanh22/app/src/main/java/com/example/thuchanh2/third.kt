package com.example.thuchanh2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

/**
 * A simple [androidx.fragment.app.Fragment] subclass.
 * Use the [first.newInstance] factory method to
 * create an instance of this fragment.
 */
class third : Fragment() {
    private lateinit var btnGetStart: Button
    private lateinit var mView: View // Renaming to 'rootView' or 'binding.root' (with ViewBinding) is common


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_third, container, false)
        btnGetStart = mView.findViewById(R.id.btn_get_started);
        btnGetStart.setOnClickListener {
            val intent = Intent(getActivity(), MainActivity::class.java)
            activity?.startActivity((intent))
        }
        return mView
    }
}