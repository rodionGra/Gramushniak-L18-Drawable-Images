package com.a10acdhmwdrawableimages_glide

import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.Transition
import androidx.appcompat.app.AppCompatActivity
import com.a10acdhmwdrawableimages_glide.databinding.ActivityMainBinding
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import java.io.ByteArrayOutputStream
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupListeners()
        drawOnImg()
    }

    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setupListeners() {
        binding.btnDownloadImage.setOnClickListener {
            downloadImageWithGlide()
        }
        binding.btnImgToByteArray.setOnClickListener {
            bitMapToArrayText(BitmapFactory.decodeResource(resources, R.drawable.porsche))
        }
    }

    private fun downloadImageWithGlide() {
        Glide.with(this)
            .load("https://images.drive.ru/i/0/58b0d01bec05c44358000019.jpg")
            .placeholder(R.drawable.preview_picture_img)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .error(ColorDrawable(Color.RED))
            .circleCrop()
            .transition(GenericTransitionOptions.with(android.R.anim.slide_in_left))
            .into(binding.ivGlide)
    }

    private fun drawOnImg() {
        val image = BitmapFactory.decodeResource(resources, R.drawable.mini_cooper)
        val bmp = image.copy(Bitmap.Config.RGB_565, true)
        val c = Canvas(bmp)
        val p = Paint()
        p.color = Color.RED
        p.strokeWidth = 400F
        c.drawLine(0F, 0F, image.width.toFloat(), image.height.toFloat(), p)
        c.drawLine(image.width.toFloat(), 0F, 0F, image.height.toFloat(), p)
        binding.ivPaintImg.setImageBitmap(bmp)
    }

    private fun bitMapToArray(bitmap: Bitmap): ByteArray {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            return stream.toByteArray()
    }

    private fun bitMapToArrayText(bitmap: Bitmap) {
        val ex = Executors.newSingleThreadExecutor()
        ex.execute {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val byteArray = stream.toByteArray()
            runOnUiThread {
                binding.tvByteArray.text = byteArray.take(100).toList().toString()
            }
        }
    }
}