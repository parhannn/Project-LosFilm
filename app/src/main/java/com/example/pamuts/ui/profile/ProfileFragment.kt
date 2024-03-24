package com.example.pamuts.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.pamuts.LoginActivity
import com.example.pamuts.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private var currentImageUri: Uri? = null
    private var photoUri: Uri? = null
    private val launcherIntentCamera =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == 200) {
                showImage()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.logoutButton.setOnClickListener {
            activity?.let {
                val intent = Intent(it, LoginActivity::class.java)
                it.startActivity(intent)
                onDestroy()
            }
        }
        binding.changePhotoButton.setOnClickListener{
            activity?.let {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                launcherIntentCamera.launch(intent)
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showImage() {
        photoUri?.let {
            Log.d("Photo URI", "showImage: $it")
            binding.profilePhoto.setImageURI(it)
        }
    }
}