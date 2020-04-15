package com.example.w2d3_contentprovider

import android.content.CursorLoader
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.aziflaj.todo.data.TaskContract
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), android.app.LoaderManager.LoaderCallbacks<Cursor> {
    override fun onCreateLoader(id: Int, args: Bundle?): android.content.Loader<Cursor>? {
        return CursorLoader(applicationContext,
                TaskContract.TaskEntry.CONTENT_URI,
                null, null, null, null)
    }

    override fun onLoadFinished(loader: android.content.Loader<Cursor>?, data: Cursor?) {
        taskAdapter?.swapCursor(data)
    }

    override fun onLoaderReset(loader: android.content.Loader<Cursor>?) {
        taskAdapter?.swapCursor(null)
    }

    companion object {
        val TASK_LOADER = 0 //the loader id
        var taskAdapter: TaskAdapter? = null
        var listView: ListView? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)

        loaderManager.initLoader(TASK_LOADER, null, this)

        listView = findViewById(R.id.task_listview)
        taskAdapter = TaskAdapter(applicationContext, null, 0)

        listView?.adapter = taskAdapter

        listView?.setOnItemClickListener { parent, view, position, id ->
            val currentTask: Cursor? = parent.getItemAtPosition(position) as Cursor

            var detailsIntent = Intent(this, TaskDetailsActivity::class.java)
            val TASK_ID_COL = currentTask?.getColumnIndex(TaskContract.TaskEntry._ID) as Int
            val _id = currentTask.getLong(TASK_ID_COL) as Long
            val taskUri = TaskContract.TaskEntry.buildWithId(_id)

            detailsIntent.setData(taskUri)
            startActivity(detailsIntent)
        }

        val newTaskFab = findViewById<FloatingActionButton>(R.id.fab)

        newTaskFab.setOnClickListener { view ->
            val newTaskIntent = Intent(applicationContext, CreateTaskActivity::class.java)
            startActivity(newTaskIntent)
        }
    }
}
