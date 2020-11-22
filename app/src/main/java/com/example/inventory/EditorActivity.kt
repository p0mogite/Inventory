package com.example.inventory

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_editor.*

class EditorActivity : AppCompatActivity() {
    private val pickImage = 100
    private var imageUri: Uri? = null
    private val TAG = "Gallery"

    companion object {
        const val EXTRA_NAME = "com.example.android.inventory.EXTRA_NAME"
        const val EXTRA_SUPPLIER = "com.example.android.inventory.EXTRA_SUPPLIER"
        const val EXTRA_PRICE = "com.example.android.inventory.EXTRA_PRICE"
        const val EXTRA_QUANTITY = "com.example.android.inventory.EXTRA_QUANTITY"
        const val EXTRA_IMAGE = "com.example.android.inventory.EXTRA_IMAGE"
        const val RECORD_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        image.setOnClickListener {
            setupPermissions()
            val gallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied")
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            RECORD_REQUEST_CODE
        )
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            RECORD_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG, "Permission has been denied by user")
                } else {
                    Log.i(TAG, "Permission has been granted by user")
                    val gallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                    startActivityForResult(gallery, pickImage)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            image.setImageURI(imageUri)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_editor, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_save) {
            saveItem()
            return true
        }
        if (item.itemId == R.id.action_delete) {
            deleteItem()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveItem() {
        val replyIntent = Intent()
        val name = name.text.toString()
        val supplier = supplier.text.toString()
        val price = price.text.toString()
        val quantity = quantity.text.toString()
        replyIntent.putExtra(EXTRA_NAME, name)
        replyIntent.putExtra(EXTRA_SUPPLIER, supplier)
        replyIntent.putExtra(EXTRA_PRICE, price)
        replyIntent.putExtra(EXTRA_QUANTITY, quantity)
        replyIntent.putExtra(EXTRA_IMAGE, imageUri.toString())
        setResult(Activity.RESULT_OK, replyIntent)
        finish()
    }

    private fun deleteItem() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Confirmation")
        alert.setMessage("Do you want to delete an item?")
        alert.setPositiveButton("Yes") { _, _ ->
            activityViewModel.delete()

        }
        alert.setNegativeButton(
            "No"
        ) { dialogInterface, _ -> dialogInterface.dismiss() }
        alert.create().show()
    }
}

