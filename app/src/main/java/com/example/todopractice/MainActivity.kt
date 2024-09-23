package com.example.todopractice

import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.todopractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // Declare a binding variable for view binding, which allows us to interact with UI elements in the layout
    private lateinit var binding: ActivityMainBinding

    // ArrayList to store the to-do items
    var itemList = ArrayList<String>()

    // Instance of FileHelper to handle file operations for saving and loading data
    var fileHelper = FileHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize view binding to access UI components
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        // Set the content view to the root of the binding (our main layout)
        setContentView(view)

        // Read the saved data from the file and assign it to itemList
        itemList = fileHelper.readData(this)

        // Create an ArrayAdapter to bind the itemList to the ListView
        // An ArrayAdapter is a bridge between a ListView and the data, in this case, an ArrayList
        var arrayAdapter = ArrayAdapter(
            this, // Context: where this adapter is being used
            android.R.layout.simple_list_item_1, // Layout for each item in the list
            android.R.id.text1, // TextView in the layout where the item name will be displayed
            itemList // The data source (our to-do list)
        )
        // Set the ArrayAdapter to the ListView to display the items
        binding.listID.adapter = arrayAdapter

        // Set an OnClickListener for the 'Add' button to add new items to the list
        binding.addID.setOnClickListener {
            // Get the text entered by the user in the EditText
            var itemName: String = binding.textID.text.toString()
            // Add the new item to the itemList
            itemList.add(itemName)
            // Clear the EditText after adding the item to the list
            binding.textID.setText("")
            // Save the updated list to the file
            fileHelper.writeData(itemList, applicationContext)
            // Notify the adapter that the data has changed to update the ListView
            arrayAdapter.notifyDataSetChanged()
        }

        // Set an OnItemClickListener for the ListView items to delete an item on click
        binding.listID.setOnItemClickListener { parent, view, position, id ->
            // Create an AlertDialog to confirm deletion
            var alert = AlertDialog.Builder(this)
            alert.setTitle("Delete") // Set the title of the dialog
            alert.setMessage("Do you want to delete this item from the list?") // Set the message of the dialog
            alert.setCancelable(false) // Prevent the dialog from being canceled by clicking outside of it

            // Set a "No" button in the dialog to cancel the deletion
            alert.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                // If "No" is clicked, simply cancel the dialog
                dialog.cancel()
            })

            // Set a "Yes" button in the dialog to confirm the deletion
            alert.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                // If "Yes" is clicked, remove the item from the list
                itemList.removeAt(position)
                // Notify the adapter that the data has changed to update the ListView
                arrayAdapter.notifyDataSetChanged()
                // Save the updated list to the file
                fileHelper.writeData(itemList, applicationContext)
            })

            // Create and show the AlertDialog
            alert.create().show()
        }
    }
}
