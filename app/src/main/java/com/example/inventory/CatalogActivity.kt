package com.example.inventory

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.data.Item
import kotlinx.android.synthetic.main.activity_catalog.*

lateinit var activityViewModel: ActivityViewModel

class CatalogActivity : AppCompatActivity() {
    private val newItemActivityRequestCode = 1
    private val editItemRequestCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)

        val recyclerView = findViewById<RecyclerView>(R.id.list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ItemAdapter(this, clickListener)
        recyclerView.adapter = adapter


        activityViewModel = ViewModelProvider(this).get(ActivityViewModel::class.java)
        activityViewModel.allItems.observe(this, { items ->
            items?.let { adapter.setData(it as ArrayList<Item>) }
        })

        fab.setOnClickListener {
            val intent = Intent(this, EditorActivity::class.java)
            startActivityForResult(intent, newItemActivityRequestCode)
        }
    }

    private val clickListener = object : OnItemClickListener {
        override fun onItemClick(item: Item) {
            val intent = Intent(this@CatalogActivity, EditorActivity::class.java)
            intent.putExtra(EditorActivity.EXTRA_NAME, item.name)
            intent.putExtra(EditorActivity.EXTRA_IMAGE, item.imageUri)
            intent.putExtra(EditorActivity.EXTRA_QUANTITY, item.quantity)
            intent.putExtra(EditorActivity.EXTRA_PRICE, item.price)
            intent.putExtra(EditorActivity.EXTRA_SUPPLIER, item.supplier)
            startActivityForResult(intent, editItemRequestCode)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_catalog, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_delete_all_items) {
            showAlertDialog()

            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle("Confirmation")
        alert.setMessage("Do you want to delete all items?")
        alert.setPositiveButton("Yes") { _, _ ->
            activityViewModel.delete()

        }
        alert.setNegativeButton(
            "No"
        ) { dialogInterface, _ -> dialogInterface.dismiss() }
        alert.create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == newItemActivityRequestCode && resultCode == Activity.RESULT_OK) {
            val name = data?.getStringExtra(EditorActivity.EXTRA_NAME)
            val supplier = data?.getStringExtra(EditorActivity.EXTRA_SUPPLIER)
            val price = data?.getStringExtra(EditorActivity.EXTRA_PRICE)
            val quantity = data?.getStringExtra(EditorActivity.EXTRA_QUANTITY)
            val image = data?.getStringExtra(EditorActivity.EXTRA_IMAGE)
            name?.let {
                val item = supplier?.let { it1 ->
                    quantity?.let { it2 ->
                        price?.let { it3 ->
                            image?.let { it4 ->
                                Item(
                                    it4, it,
                                    it1, it2, it3
                                )
                            }
                        }
                    }
                }
                if (item != null) {
                    activityViewModel.insert(item)
                }
            }
        } else if (requestCode == editItemRequestCode && resultCode == RESULT_OK) {
            val name = data?.getStringExtra(EditorActivity.EXTRA_NAME)
            val supplier = data?.getStringExtra(EditorActivity.EXTRA_SUPPLIER)
            val price = data?.getStringExtra(EditorActivity.EXTRA_PRICE)
            val quantity = data?.getStringExtra(EditorActivity.EXTRA_QUANTITY)
            val image = data?.getStringExtra(EditorActivity.EXTRA_IMAGE)
            name?.let {
                val item = supplier?.let { it1 ->
                    quantity?.let { it2 ->
                        price?.let { it3 ->
                            image?.let { it4 ->
                                Item(
                                    it4, it,
                                    it1, it2, it3
                                )
                            }
                        }
                    }
                }
                if (item != null) {
                    activityViewModel.update(item)
                }
            }
        } else {
            Toast.makeText(applicationContext, "empty", Toast.LENGTH_SHORT).show()
        }
    }

}