package com.tipiz.toko_paerbe.ui.prelogin.profile

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tipiz.core.utils.state.launchAndCollectIn
import com.tipiz.core.utils.state.onError
import com.tipiz.core.utils.state.onLoading
import com.tipiz.core.utils.state.onSuccess
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentProfileBinding
import com.tipiz.toko_paerbe.ui.utils.BaseFragment
import com.tipiz.toko_paerbe.ui.utils.Constant.CAMERA_PERMISSION_CODE
import com.tipiz.toko_paerbe.ui.utils.Constant.GALLERY_PERMISSION_CODE
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class ProfileFragment :
    BaseFragment<FragmentProfileBinding, ProfileViewModel>(FragmentProfileBinding::inflate) {
    override val viewModel: ProfileViewModel by viewModel()
    private lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    private lateinit var galleryLauncher: ActivityResultLauncher<Intent>
    private var imageUri: Uri? = null

    override fun initView() {


        with(binding) {

            imgProfil.setOnClickListener {
                showAlertDialog()
            }

            edName.doAfterTextChanged { _ ->
                inputName.apply {
                    isErrorEnabled = false
                }
            }

            btnDone.setOnClickListener {
                val edName = binding.edName.text.toString()

                if (edName.isEmpty()) {
                    binding.inputName.error = getString(R.string.username_cannot_be_empty)
                } else if (imageUri == null) {
                    val invalid = getString(R.string.picture_cannot_be_empty)
                    Toast.makeText(context, invalid, Toast.LENGTH_SHORT).show()
                } else {
                    imageUri?.let {
                        handleProfile(edName, imageUri)
                    }
                }
            }
        }


        cameraLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                handleCameraResult(result.resultCode, result.data)
            }

        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                handleGalleryResult(result.resultCode, result.data)
            }
    }

    override fun initViewModel() {
        with(viewModel) {
            responseProfile.launchAndCollectIn(viewLifecycleOwner) { state ->
                state.onLoading{
                    binding.pbProfile.visibility = View.VISIBLE
                    binding.btnDone.visibility = View.INVISIBLE
                }.onSuccess { data ->
                    lifecycleScope.launch {
                        val profileResponse = data.userName
                        if (profileResponse.isNotEmpty()) {
                            Toast.makeText(context, getString(R.string.welcome), Toast.LENGTH_SHORT).show()
                            setUserName(data.userName)
                            findNavController().navigate(R.id.action_profileFragment_to_dashBoardFragment)
                        }
                    }

                }.onError {
                    Toast.makeText(context, getString(R.string.failed_profile), Toast.LENGTH_SHORT).show()
                    binding.pbProfile.visibility = View.INVISIBLE
                    binding.btnDone.visibility = View.VISIBLE
                }
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            CAMERA_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    camera()
                }
            }

            GALLERY_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gallery()
                }
            }
        }
    }

    private fun camera() {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            cameraLauncher.launch(intent)
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE
            )
        }
    }

    private fun gallery() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            galleryLauncher.launch(intent)
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), GALLERY_PERMISSION_CODE
            )
        }

    }

    private fun showAlertDialog() {
        val dialogBuilder = context?.let {
            MaterialAlertDialogBuilder(it)
        }
        dialogBuilder?.setTitle(R.string.select_image)

        val dialogItems = arrayOf(getString(R.string.camera), getString(R.string.gallery))
        dialogBuilder?.setItems(dialogItems) { _, wich ->
            when (wich) {
                0 -> camera()
                1 -> gallery()
            }
        }
        dialogBuilder?.show()

    }

    private fun handleCameraResult(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as? Bitmap

            imageBitmap?.let { saveBitmapToGallery(requireContext(), it) }
            imageBitmap?.let { loadBitmap(it) }
        }

    }

    private fun saveBitmapToGallery(context: Context, bitmap: Bitmap): Boolean {
//        val timestamp = System.currentTimeMillis()
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val displayName = "image_$timeStamp.jpg"


        val directory = Environment.DIRECTORY_PICTURES

        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, displayName)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, directory)
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        return try {
            uri?.let { contentUri ->
                imageUri = contentUri
                resolver.openOutputStream(contentUri)?.use { outputStream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                    true
                } ?: false
            } ?: false
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }

    }

    private fun loadBitmap(imageBitmap: Bitmap) {
        binding.imgProfil.scaleType = ImageView.ScaleType.CENTER_CROP
        Glide.with(this).asBitmap()
            .load(imageBitmap)
            .into(binding.imgProfil)
    }

    private fun handleGalleryResult(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val selectImageUri = data?.data
            imageUri = data?.data
            binding.imgProfil.setImageURI(selectImageUri)
            binding.imgProfil.scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }

    private fun handleProfile(username: String, image: Uri?) {

        image?.let {
            context?.let { ctx ->
                val imageFile = uriToFile(image, ctx)
                val requestFIle = imageFile.asRequestBody(
                    "multipart/form-data".toMediaTypeOrNull()
                )

                val imagePart = requestFIle.let {
                    MultipartBody.Part.createFormData(
                        "userImage",
                        imageFile.name,
                        it
                    )
                }

                imagePart.let { partImage ->
                    viewModel.fetchProfile(
                        username.toRequestBody("text/plain".toMediaType()),
                        partImage
                    )
                }
            }
        }
    }

    private fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createCustomTempFile(context)

        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()

        return myFile
    }

    private fun createCustomTempFile(context: Context): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return File.createTempFile(timeStamp, ".jpg", storageDir)
    }


}