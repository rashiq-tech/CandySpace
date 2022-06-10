package com.appslet.candyspace

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.appslet.candyspace.databinding.ActivityUserDetailBinding
import com.appslet.candyspace.model.User
import com.appslet.candyspace.utils.MainRepository
import com.appslet.candyspace.utils.RetrofitService
import com.appslet.candyspace.utils.ViewModelFactory
import com.appslet.candyspace.viewmodel.MainViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString
import java.lang.Long.parseLong
import java.text.SimpleDateFormat
import java.util.*

class UserDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding

    private lateinit var viewModel: MainViewModel
    lateinit var retrofitService: RetrofitService

    companion object {
        val USER_TAG = "USER_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_back_white)

        retrofitService = RetrofitService.getInstance()
        viewModel =
            ViewModelProvider(this, ViewModelFactory(MainRepository(retrofitService))).get(
                MainViewModel::class.java
            )

        viewModel.topTagList.observe(this) {
            if (it != null) {
                binding.cgTopTags.removeAllViews()
                it.forEach() { tag ->
                    val chip = Chip(this)
                    chip.text = tag.tag_name
                    chip.isClickable = false
                    chip.isCheckable = false
                    binding.cgTopTags.addView(chip)
                }
            }

        }
        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        val user = intent.getStringExtra(USER_TAG)?.let { Json.decodeFromString<User>(it) }

        if (user != null) {
            title = user.display_name
            binding.tvUserName.text = user.display_name
            binding.tvUserName.text = user.display_name
            binding.tvGoldBadgeCount.text = user.badge_counts.gold.toString()
            binding.tvSilverBadgeCount.text = user.badge_counts.silver.toString()
            binding.tvBronzeBadgeCount.text = user.badge_counts.bronze.toString()
            binding.tvCreatedDate.text = getDateTime(user.creation_date.toString())
            if (user.location == null || user.location == "")
                binding.tvLocation.text = "N/A"
            else
                binding.tvLocation.text = user.location

            Glide.with(this)
                .load(user.profile_image)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivUser)

            user.user_id?.let { viewModel.getTopTags(it.toInt()) }
        }

    }

    private fun getDateTime(s: String): String? {
        return try {
            val sdf = SimpleDateFormat("MM/dd/yyyy")
            val netDate = Date(parseLong(s) * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}