package com.example.simpletodolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    var listOfTasks= mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val OnLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                 // 1.remove the item from the list
                listOfTasks.removeAt(position)
                // 2.notify the adapter our data set has changes
                adapter.notifyDataSetChanged()

                saveItems()
            }

        }

        loadItems()
//        listOfTasks.add("Do launry")
//        listOfTasks.add("Go for work")

        // Lookup the recyclerview in  layout
        val recyclerView = findViewById<RecyclerView>(R.id.RecyclerView)

        // Initialize contacts
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks,OnLongClickListener)

        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter=adapter

        // Set layout manager to position the items
        recyclerView.layoutManager= LinearLayoutManager(this)

        //set up the button and input field, so the user can enter a task and add it to the list


        val inputTextFiled = findViewById<EditText>(R.id.AddTaskField)

        // Get a reference to the button
        //and then set on click listener
        findViewById<Button>(R.id.button).setOnClickListener{
            //1.get the text the user has inputted into the @+id/AddTaskField
            val userInputtedTask = inputTextFiled.text.toString()

            //2.Add the string to our list of tasks: listOfTask
            listOfTasks.add(userInputtedTask)

            // notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size -1)

            //3. reset the text filed
            inputTextFiled.setText("")
            saveItems()

        }

    }
    //save the data that user has putt
    // save but reading and writing from a file
    // 1. create a method to get the file we need
    fun getDatafile(): File{

        // every line is going to be represent a specific task in our list of tasks
        return File(filesDir,"data.txt")
    }
    // 2. load the items by reading every line in the dara file
    fun loadItems(){

        try {
            listOfTasks = FileUtils.readLines(getDatafile(), Charset.defaultCharset())
        }catch (ioException:IOException){
            ioException.printStackTrace()
        }

    }
    // 3. save items by writing them into our file
    fun saveItems(){
        try {
            FileUtils.writeLines(getDatafile(),listOfTasks)
        }catch (ioException: IOException){
            ioException.printStackTrace()
        }


    }
}