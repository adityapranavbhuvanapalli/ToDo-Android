package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.model.Task;

import java.util.List;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    List<Task> taskList = new Vector<>();

    private void displayTasks() {
        TextView displayMsg = findViewById(R.id.taskListEmptyMsg);
        ListView listView = findViewById(R.id.taskListView);
        if(taskList.isEmpty()) {
            listView.setVisibility(View.INVISIBLE);
            displayMsg.setVisibility(View.VISIBLE);
            return;
        }
        listView.setVisibility(View.VISIBLE);
        displayMsg.setVisibility(View.INVISIBLE);

        listView = findViewById(R.id.taskListView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this,
                R.layout.activity_taskview,
                taskList.stream().map(Task::getName).collect(Collectors.toList()));

        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedItem = (String) parent.getItemAtPosition(position);
            displayTaskPopUp(selectedItem);
        });
    }

    private void displayTaskPopUp(String name) {
        Task selectedTask = taskList.stream().filter(task -> task.getName().equals(name)).collect(Collectors.toList()).get(0);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(selectedTask.getName())
                .setMessage(selectedTask.getDesc())
                .setPositiveButton("Ok", (dialog, which) -> dialog.cancel())
                .setNegativeButton("Delete", ((dialog, which) -> {
                    Toast.makeText(this, selectedTask.getName() + " deleted", Toast.LENGTH_SHORT).show();
                    taskList.remove(selectedTask);
                    displayTasks();
                }))
                .create();
        alertDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayTasks();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && resultCode == RESULT_OK && Objects.requireNonNull(data).hasExtra("newTask")) {
            Task newTask = (Task) data.getSerializableExtra("newTask");
            taskList.add(newTask);
            Toast.makeText(this, newTask.getName() + " created", Toast.LENGTH_SHORT).show();
            displayTasks();
        }
    }

    @SuppressWarnings("deprecation")
    public void onAddBtnClick(View view) {
        int newId = taskList.isEmpty()? 1: taskList.get(taskList.size()-1).getId()+1;
        Intent intent = new Intent(this, NewTaskActivity.class);
        intent.putExtra("id", newId);
        startActivityForResult(intent, 0);
    }
}