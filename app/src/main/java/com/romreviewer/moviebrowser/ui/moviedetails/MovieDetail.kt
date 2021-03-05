package com.romreviewer.moviebrowser.ui.moviedetails

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.romreviewer.moviebrowser.R
import com.romreviewer.moviebrowser.databinding.ActivityMovieDetailBinding


class MovieDetail : AppCompatActivity() {
    lateinit var binding:ActivityMovieDetailBinding
    lateinit var viewModel:MovieDetailViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
        viewModel = ViewModelProvider(this).get(MovieDetailViewPager::class.java)
        binding.vm=viewModel
        binding.lifecycleOwner=this
        uiSetter()
        //Log.d("LogTag",extras?.getString("overview").toString())
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
    private fun uiSetter()
    {
        val extras=intent.extras
        viewModel.movieName.postValue(extras?.getString("name"))
        viewModel.overview.postValue(extras?.getString("overview"))
        viewModel.releaseDate.postValue(extras?.getString("releaseDate"))
        viewModel.genre=extras?.getIntegerArrayList("genre")
        viewModel.genre()
        binding.genreRecycler.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        binding.genreRecycler.setHasFixedSize(true)
        binding.genreRecycler.adapter=GenreAdapter(viewModel.geneList)
        Glide.with(this)
            //.load("https://image.tmdb.org/t/p/w500" + extras?.getString("url"))
            .load(extras?.getString("url"))
            .centerCrop()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    Log.d("LogTag", e?.message.toString())
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.progressBar2.visibility = View.GONE
                    return false
                }

            })
            .into(binding.imageView)
    }
}