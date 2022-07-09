package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.ui.add.AddTaskViewModel
import com.dicoding.todoapp.utils.DatePickerFragment
import com.dicoding.todoapp.utils.TASK_ID
import com.dicoding.todoapp.utils.formatMilliToDateText
import com.dicoding.todoapp.utils.showOkBackDialog
import java.text.SimpleDateFormat
import java.util.*

class DetailTaskActivity : AppCompatActivity() {
    private lateinit var detailTaskViewModel: DetailTaskViewModel
    private lateinit var titleEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var dueDateText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        val factory = ViewModelFactory.getInstance(this)
        detailTaskViewModel = ViewModelProvider(this, factory).get(DetailTaskViewModel::class.java)
        titleEditText = findViewById(R.id.detail_ed_title)
        descriptionEditText = findViewById(R.id.detail_ed_description)
        dueDateText = findViewById(R.id.detail_ed_due_date)

        val idTask = intent.getIntExtra(TASK_ID, 0)
        detailTaskViewModel.setTaskId(idTask)
        detailTaskViewModel.task.observe(this, Observer(this::displayDetailTask))
    }

    private fun displayDetailTask(task: Task?) {
        titleEditText.setText(task?.title)
        descriptionEditText.setText(task?.description)
        dueDateText.setText(task?.dueDateMillis?.formatMilliToDateText("dd/MM/yyyy"))
    }

    fun deleteTask(view: View) {
        detailTaskViewModel.deleteTask()
    }
}