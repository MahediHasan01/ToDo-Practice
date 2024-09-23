package com.example.todopractice

import android.content.Context
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class FileHelper {

    // Name of the file where the data will be saved
    val FILENAME = "listinfo.dat"

    // Method to write data to the file
    fun writeData(item: ArrayList<String>, context: Context) {
        // Open a file output stream in private mode (only this app can access)
        val fos: FileOutputStream = context.openFileOutput(FILENAME, Context.MODE_PRIVATE)
        // Create an ObjectOutputStream to write the ArrayList object
        val oos = ObjectOutputStream(fos)
        // Write the ArrayList object to the file
        oos.writeObject(item)
        // Close the ObjectOutputStream
        oos.close()
    }

    // Method to read data from the file
    fun readData(context: Context): ArrayList<String> {
        // Declare an ArrayList to hold the read data
        var itemList: ArrayList<String>
        try {
            // Open a file input stream to read the file
            val fis: FileInputStream = context.openFileInput(FILENAME)
            // Create an ObjectInputStream to read the ArrayList object
            val ois = ObjectInputStream(fis)
            // Read the ArrayList object from the file and cast it to ArrayList<String>
            itemList = ois.readObject() as ArrayList<String>
        } catch (e: FileNotFoundException) {
            // If the file is not found, initialize an empty ArrayList
            itemList = ArrayList()
        }

        // Return the read or empty ArrayList
        return itemList
    }
}
