package com.example.w2d3_contentprovider

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.aziflaj.todo.data.TaskContract

class TaskDetailsActivity : AppCompatActivity() {

    var taskId = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)

        var taskUri = intent?.data as Uri
        taskId = TaskContract.TaskEntry.getIdFromUri(taskUri)

        val cursor = contentResolver.query(taskUri, null, null, null,null)
        cursor?.moveToFirst()
        val taskTitle = cursor?.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_TITLE))
        val taskDescr = cursor?.getString(cursor.getColumnIndex(TaskContract.TaskEntry.COL_DESCRIPTION))

        var titleTextView = findViewById<TextView>(R.id.detail_task_title)
        var descrTextView = findViewById<TextView>(R.id.detail_task_description)

        titleTextView.text = taskTitle
        descrTextView.text = taskDescr
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.task_details_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        when (id) {
            R.id.action_delete -> {
                val deleted = contentResolver.delete(
                        TaskContract.TaskEntry.CONTENT_URI,
                        "${TaskContract.TaskEntry._ID} = ?",
                        arrayOf(taskId.toString()))

                if (deleted == 1) {
                    startActivity(Intent(this, MainActivity::class.java))
                }

                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
