package com.meliodas.androidmaterialtransitionexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.MaterialContainerTransform

class FragmentC: Fragment() {

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        val transform = MaterialContainerTransform(requireContext())
        sharedElementEnterTransition = transform
    }

    override fun onCreateView(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup?,
        bundle: Bundle?
    ): View? {
        return layoutInflater.inflate(
            R.layout.frragment_c, viewGroup, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val container = view.findViewById<View>(R.id.container)
        val appBarLayout: AppBarLayout = view.findViewById(R.id.app_bar_layout)
        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        val albumImage =
            view.findViewById<ImageView>(R.id.album_image)
        val albumTitle = view.findViewById<TextView>(R.id.album_title)
        val albumArtist = view.findViewById<TextView>(R.id.album_artist)
        val songRecyclerView: RecyclerView = view.findViewById(R.id.song_recycler_view)
        val album: MusicData.Album = MusicData.getAlbumById(0L)

        // Set the transition name which matches the list/grid item to be transitioned from for
        // the shared element transition.
        // Set the transition name which matches the list/grid item to be transitioned from for
// the shared element transition.
        ViewCompat.setTransitionName(container, album.title)

        appBarLayout.addOnOffsetChangedListener(
            OnOffsetChangedListener { appBarLayout1: AppBarLayout, verticalOffset: Int ->
                val verticalOffsetPercentage =
                    Math.abs(verticalOffset).toFloat() / appBarLayout1.totalScrollRange.toFloat()
                if (verticalOffsetPercentage > 0.2f && fab.isOrWillBeShown) {
                    fab.hide()
                } else if (verticalOffsetPercentage <= 0.2f && fab.isOrWillBeHidden) {
                    fab.show()
                }
            }
        )

        // Set up toolbar
        // Set up toolbar

        // Set up album info area
        // Set up album info area
        albumImage.setImageResource(album.cover)
        albumTitle.setText(album.title)
        albumArtist.setText(album.artist)

        // Set up track list
        // Set up track list
        songRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter: TrackAdapter = TrackAdapter()
        songRecyclerView.adapter = adapter
        adapter.submitList(album.tracks)
    }

    /** An adapter to hold an albums list of tracks.  */
    internal class TrackAdapter :
        ListAdapter<MusicData.Track?, TrackAdapter.TrackViewHolder?>(MusicData.Track.DIFF_CALLBACK) {
        override fun onCreateViewHolder(
            viewGroup: ViewGroup,
            i: Int
        ): TrackViewHolder {
            return TrackViewHolder(
                LayoutInflater.from(viewGroup.context)
                    .inflate(R.layout.cat_transition_track_list_item, viewGroup, false)
            )
        }

        override fun onBindViewHolder(
            trackViewHolder: TrackViewHolder,
            i: Int
        ) {
            trackViewHolder.bind(getItem(i)!!)
        }

        /** A ViewHolder for a single track item.  */
        internal inner class TrackViewHolder(view: View) :
            RecyclerView.ViewHolder(view) {
            private val playingIcon: ImageView
            private val trackNumber: TextView
            private val trackTitle: TextView
            private val trackDuration: TextView
            fun bind(track: MusicData.Track) {
                playingIcon.visibility = if (track.playing) View.VISIBLE else View.INVISIBLE
                trackNumber.setText("thuan")
                trackTitle.setText(track.title)
                trackDuration.setText(track.duration)
            }

            init {
                playingIcon = view.findViewById(R.id.playing_icon)
                trackNumber = view.findViewById(R.id.track_number)
                trackTitle = view.findViewById(R.id.track_title)
                trackDuration = view.findViewById(R.id.track_duration)
            }
        }
    }
}