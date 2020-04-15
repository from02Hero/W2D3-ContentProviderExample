package com.example.w2d3_contentprovider

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.aziflaj.todo.data.TaskContract.TaskEntry

class CreateTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        val saveBtn = findViewById<Button>(R.id.save_task_btn)

        saveBtn.setOnClickListener { view ->
            val taskTitleEditText = findViewById<EditText>(R.id.task_title)
            val taskTitle = taskTitleEditText.text.toString()

            val taskDescriptionEditText = findViewById<EditText>(R.id.task_description)
            val taskDescription: String = taskDescriptionEditText.text.toString()

            if (taskTitle.isEmpty() or taskDescription.isEmpty()) {
                val inputEmpty = getString(R.string.error_input_empty)

                Toast.makeText(applicationContext, inputEmpty, Toast.LENGTH_LONG).show()
            } else {
                val values = ContentValues()
                values.put(TaskEntry.COL_TITLE, taskTitle)
                values.put(TaskEntry.COL_DESCRIPTION, taskDescription)

                var inserted = contentResolver.insert(TaskEntry.CONTENT_URI, values)

                startActivity(Intent(this, MainActivity::class.java))

                Log.d("New Task", "inserted: $inserted")
            }
        }
    }
}
