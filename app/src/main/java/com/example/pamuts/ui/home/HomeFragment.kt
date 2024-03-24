package com.example.pamuts.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pamuts.R
import com.example.pamuts.data.source.remote.response.MovieResponse
import com.example.pamuts.util.ViewModelFactory

private const val t10 = "t1"
private const val t20 = "t2"

class HomeFragment : Fragment() {
    private var t1: String? = null
    private lateinit var adapter: MovieAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private val viewModel: HomeViewModel by viewModels { ViewModelFactory(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            t1 = it.getString(t10)
            t1 = it.getString(t20)
        }

    }

    override fun onResume() {
        super.onResume()
        setObserver()
    }

    private fun setObserver() {
        viewModel.query.observe(this) { query ->
            if (query.isEmpty()) {
                viewModel.getMovieNowPlaying().observe(this) {
                    setMovieList(it)
                }
            } else {
                viewModel.searchMovie(query).observe(this) {
                    setMovieList(it)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        searchView = view.findViewById(R.id.search_action)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.setQuery(it)
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.setQuery(it)
                }
                return false
            }

        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        t1 = null
    }

    private fun navigateToDetail(movieId: Int) {
        findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavMovieDetail(movieId))
    }

    private fun setMovieList(listMovie: PagingData<MovieResponse>) {
        adapter = MovieAdapter()
        adapter.onItemClick = {
            it.id?.let { t ->
                navigateToDetail(t)
            }
        }
        recyclerView.adapter = adapter
        adapter.submitData(lifecycle, listMovie)
    }
}