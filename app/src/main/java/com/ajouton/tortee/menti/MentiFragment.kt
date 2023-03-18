package com.ajouton.tortee.menti

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajouton.tortee.MainActivity
import com.ajouton.tortee.databinding.FragmentMentiBinding


class MentiFragment: Fragment() {
    private lateinit var binding: FragmentMentiBinding
    val mentithumlist : ArrayList<Menti_thumnail> = arrayListOf(
       Menti_thumnail("강하현","2023-3-17","객프 도와주세요"),
        Menti_thumnail("강하현","2023-3-17","객프 도와주세요"),
        Menti_thumnail("강하현","2023-3-17","객프 도와주세요"),
        )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMentiBinding.inflate(layoutInflater)

        binding.mentiRv.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false )
        binding.mentiRv.adapter =  MentiAdapter(activity as MainActivity, mentithumlist)



        return binding.root

    }

}