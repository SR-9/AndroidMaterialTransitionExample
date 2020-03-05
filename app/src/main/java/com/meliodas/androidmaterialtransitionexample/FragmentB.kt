package com.meliodas.androidmaterialtransitionexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.Hold
import kotlinx.android.synthetic.main.fragment_b.*
import java.util.*

class FragmentB: Fragment(), AlbumsAdapter.AlbumAdapterListener {

    override fun onCreateView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View? {
        return layoutInflater.inflate(
            R.layout.fragment_b, viewGroup, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setList(true, true)
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        exitTransition = Hold()
    }

    override fun onAlbumClicked(view: View?, album: MusicData.Album?) {
        activity?.supportFragmentManager!!
            .beginTransaction()
            .addSharedElement(view!!, ViewCompat.getTransitionName(view)!!)
            .replace(R.id.container, FragmentC(), "TAG")
            .addToBackStack("TAG")
            .commit()
    }

    private fun setList(
        listTypeGrid: Boolean,
        listSorted: Boolean
    ) {
        val listTypeGrid = listTypeGrid
       val  listSorted = listSorted
        // Use a Transition to animate the removal and addition of the RecyclerViews.
        val recyclerView = createRecyclerView(listTypeGrid)
        //transition.addTarget(RecyclerView.class);
// TransitionManager.beginDelayedTransition(listContainer, transition);
        val adapter = AlbumsAdapter(this, listTypeGrid)
        recyclerView!!.adapter = adapter
        val albums: List<MusicData.Album> = MusicData.ALBUMS
        if (!listSorted) {
            Collections.reverse(albums)
        }
        adapter.submitList(albums)
        containerB.removeAllViews()
        containerB.addView(recyclerView)
    }

    private fun createRecyclerView(listTypeGrid: Boolean): RecyclerView? {
        val context = requireContext()
        val recyclerView = RecyclerView(context)
        recyclerView.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        val verticalPadding =
            context.resources.getDimensionPixelSize(R.dimen.album_list_padding_vertical)
        recyclerView.setPadding(0, verticalPadding, 0, verticalPadding)
        recyclerView.clipToPadding = false
        if (listTypeGrid) {
            recyclerView.layoutManager = GridLayoutManager(
                context,
                2
            )
        } else {
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            )
        }
        return recyclerView
    }
}