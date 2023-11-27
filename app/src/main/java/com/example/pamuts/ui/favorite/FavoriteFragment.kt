package com.example.pamuts.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pamuts.R
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import com.example.pamuts.data.model.Movie
import com.example.pamuts.util.ViewModelFactory

private const val t10 = "t1"
private const val t20 = "t2"

class FavoriteFragment : Fragment() {
    private var t1: String? = null
    private lateinit var adapter: FavoriteAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private val viewModel: FavoriteViewModel by viewModels { ViewModelFactory(requireContext()) }

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
        viewModel.getAllFavoriteMovie()

        viewModel.query.observe(this) { query ->
            if (query.isEmpty()) {
                viewModel.getAllFavoriteMovie()
            } else {
                viewModel.searchFavoriteMovie(query)
            }
        }

        viewModel.movies.observe(this) {
            setMovieList(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(context)

        recyclerView = view.findViewById(R.id.fav_recycler_view)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        searchView = view.findViewById(R.id.fav_search_action)

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

    private fun navigateToDetail(movieID: Int){
        findNavController().navigate(FavoriteFragmentDirections.actionNavFavToNavMovieDetail(movieID))
    }

    private fun setMovieList(listMovie: List<Movie>) {
        adapter = FavoriteAdapter()
        adapter.onItemClick = {
            it.id.let { t ->
                navigateToDetail(t)
            }
        }
        recyclerView.adapter = adapter
        adapter.submitList(listMovie)
    }
}