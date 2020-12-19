package com.app.itunesapisample.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.itunesapisample.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var mBinding: FragmentHomeBinding
    private lateinit var mHomeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mHomeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        mHomeViewModel.getTracks("Thriller")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHomeBinding.inflate(inflater)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mHomeViewModel.tracks.observe(viewLifecycleOwner, Observer { tracks ->
            mBinding.tracks = tracks
        })

        mHomeViewModel.status.observe(viewLifecycleOwner, Observer { status ->
            when (status) {
                ApiStatus.LOADING -> {
                    mBinding.apiStatus = "Loading"
                }
                ApiStatus.DONE -> {
                    mBinding.apiStatus = "Done"
                }
                ApiStatus.ERROR -> {
                    mBinding.apiStatus = "Error"
                    Toast.makeText(
                        requireContext(),
                        "Check your internet connection",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

        mBinding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                mBinding.searchView.clearFocus()
                if (query != null) {
                    mHomeViewModel.getTracks(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

}