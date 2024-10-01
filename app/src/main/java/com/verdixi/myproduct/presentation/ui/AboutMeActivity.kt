package com.verdixi.myproduct.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.faltenreich.skeletonlayout.Skeleton
import com.verdixi.myproduct.R
import com.verdixi.myproduct.databinding.ActivityAboutMeBinding
import com.verdixi.myproduct.presentation.util.applyWindowInsets

class AboutMeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAboutMeBinding.inflate(layoutInflater) }
    private lateinit var skeleton: Skeleton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        applyWindowInsets(findViewById(R.id.main))

        Glide.with(binding.root.context)
            .load("https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/small/avatar/dos-bcfcd0ed8e1b59cd6cb2cf8506c3f78720240930221707.png")
            .into(binding.imageView)

        binding.textView2.setOnClickListener {
            val intent: Intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:gioverdiansyh@gmail.com")
                putExtra(Intent.EXTRA_SUBJECT, "Contact message")
            }
            if (intent.resolveActivity(packageManager) != null) {
                startActivity(intent)
            }
        }
    }
}