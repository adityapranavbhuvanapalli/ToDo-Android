package com.example.todo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.todo.model.Task;

public class NewTaskActivity extends AppCompatActivity {

    Task newTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
    }

    @SuppressLint("NonConstantResourceId")
    public void handleBtnClick(View view) {
        Intent intent = new Intent();
        switch(view.getId()) {
            case R.id.btnSave:
                Bundle bundle = getIntent().getExtras();
                int taskId = bundle.getInt("id",0);
                TextView txtTaskName = findViewById(R.id.taskName);
                String taskName = txtTaskName.getText().toString();
                TextView txtTaskDesc = findViewById(R.id.taskDesc);
                String taskDesc = txtTaskDesc.getText().toString();

                if(taskName.isEmpty() || taskDesc.isEmpty()){
                    AlertDialog alertDialog = new AlertDialog.Builder(this)
                            .setTitle("Error")
                            .setMessage("Enter all details")
                            .setPositiveButton("Ok", (dialog, which) -> dialog.cancel())
                            .create();
                    alertDialog.show();
                    return;
                }
            newTask = new Task(taskId, taskName, taskDesc);
                intent.putExtra("newTask", newTask);
                setResult(RESULT_OK, intent);
                break;

            case R.id.btnCancel:
                setResult(RESULT_CANCELED);
                break;
        }
        finish();
    }
}