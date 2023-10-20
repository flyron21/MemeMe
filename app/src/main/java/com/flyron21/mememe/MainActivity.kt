package com.flyron21.mememe

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.mememe.R

class MainActivity : AppCompatActivity() {

    private var currentImageUrl: String?=null
    private  lateinit var ivMeme :ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        loadMeme()

    }

    private fun loadMeme(){
        val progressBar=findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility=View.VISIBLE

        val queue = Volley.newRequestQueue(this)

        val url = "https://meme-api.com/gimme"

// Request a string response from the provided URL .
        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                currentImageUrl= response.getString("url")
                ivMeme=findViewById(R.id.ivMeme)
                Glide.with(this).load(currentImageUrl).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBar.visibility=View.GONE
                        return false
                    }
                }).into(ivMeme)

            },
            {
                Toast.makeText(this,"Something went wrong!",Toast.LENGTH_LONG).show()
            })

// Add the request to the RequestQueue.
        queue.add(JsonObjectRequest)
    }

    fun shareMeme(view: View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey! Checkout this meme which I found on MemeMe\n\n $currentImageUrl")
        val chooser=Intent.createChooser(intent,"Share this using")
        startActivity(chooser)
    }
    fun nextMeme(view: View) {
        loadMeme()
    }
}