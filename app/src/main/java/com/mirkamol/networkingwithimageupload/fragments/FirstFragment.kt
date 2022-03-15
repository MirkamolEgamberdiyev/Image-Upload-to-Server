package com.mirkamol.networkingwithimageupload.fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pinterest.helper.EndlessRecyclerViewScrollListener
import com.mirkamol.networkingwithimageupload.R
import com.mirkamol.networkingwithimageupload.adapter.ImagesAdapter
import com.mirkamol.networkingwithimageupload.model.CatItem
import com.mirkamol.networkingwithimageupload.networking.ApiClient
import com.mirkamol.networkingwithimageupload.networking.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FirstFragment : Fragment(R.layout.fragment_first), SwipeRefreshLayout.OnRefreshListener {
    lateinit var recyclerView: RecyclerView
    lateinit var edtSearch: EditText
    lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    lateinit var list: ArrayList<CatItem>
    lateinit var imageAdapter: ImagesAdapter
    lateinit var swipe: SwipeRefreshLayout
    var catId: Int = 1

    companion object {
        var page = 1
        var limit = 20
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recyclerView)
        swipe = view.findViewById(R.id.swipe)
        edtSearch = view.findViewById(R.id.et_search)
        swipe.setOnRefreshListener(this)
        swipe.isRefreshing = true
        imageAdapter = ImagesAdapter(requireContext())
        initViews()


    }

    private fun initViews() {

        staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = staggeredGridLayoutManager

        getPhotoes()


        edtSearch.setOnEditorActionListener { _, actionId, keyEvent ->
            if ((keyEvent != null && (keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) || (actionId == EditorInfo.IME_ACTION_SEARCH)) {

                hideKeyboard(requireActivity(), edtSearch)

                if (edtSearch.text.isNotBlank()) {
                    catId = edtSearch.text.toString().toInt()
                    getPhotoes()
                }
            }
            false
        }

        refreshAdapter()
        loadingMore()

    }

    override fun onResume() {
        super.onResume()
        edtSearch.post {
            edtSearch.requestFocus()
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(edtSearch, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun hideKeyboard(activity: Activity, viewToHide: View) {
        val imm = activity
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(viewToHide.windowToken, 0)
    }


    private fun refreshAdapter() {
        recyclerView.adapter = imageAdapter
    }

    fun loadingMore() {

        val scrollListener = object : EndlessRecyclerViewScrollListener(
            staggeredGridLayoutManager
        ) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                getPhotoes()
            }
        }
        recyclerView.addOnScrollListener(scrollListener)
    }

    private fun getPhotoes() {

        ApiClient.postService.getPhotoes(catId, page++, limit)
            .enqueue(object : Callback<List<CatItem>> {
                override fun onResponse(
                    call: Call<List<CatItem>>,
                    response: Response<List<CatItem>>
                ) {
                    Log.d("responce", response.body().toString())
                    imageAdapter.addList(response.body()!!)

                    swipe.isRefreshing = false

                }

                override fun onFailure(call: Call<List<CatItem>>, t: Throwable) {
                    Log.d("error", t.localizedMessage)
                }

            })

    }

    override fun onRefresh() {
        initViews()
    }
}