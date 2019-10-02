package com.example.kotlin_mvp_movie.ui.detail


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kotlin_mvp_movie.R
import com.example.kotlin_mvp_movie.network.model.DailyBoxOfficeList
import com.example.kotlin_mvp_movie.network.model.Item

class ShowDetailFragment : Fragment() {

    companion object {

        fun newInstance(item: Item, dailyBoxOfficeList: DailyBoxOfficeList): ShowDetailFragment {
            val args = Bundle()
            args.putSerializable("dailyInfo", dailyBoxOfficeList)
            args.putSerializable("item", item)
            val fragment = ShowDetailFragment()
            fragment.arguments = args
            return fragment
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e("new instance",""+arguments?.getSerializable("item"))
        super.onViewCreated(view, savedInstanceState)
    }

}
