package com.example.gestiondestaches;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TaskAdapter.OnTaskClickListener {

    private RecyclerView recyclerView;
    private FloatingActionButton addButton;
    private TaskAdapter taskAdapter;
    private DBtask myDBtask;
    private ArrayList<Task> tasks;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
            startActivity(intent);
        });

        myDBtask = new DBtask(new DatabaseHelper(this));
        tasks = new ArrayList<>();
        taskAdapter = new TaskAdapter(this, tasks, this);
        recyclerView.setAdapter(taskAdapter);

        storeDataInArrays();
    }

    @Override
    protected void onResume() {
        super.onResume();
        storeDataInArrays();
    }

    void storeDataInArrays() {
        tasks.clear();
        ArrayList<Task> newTasks = myDBtask.getAllTasks();

        if (newTasks.isEmpty()) {
            Toast.makeText(this, "Pas de tâches", Toast.LENGTH_SHORT).show();
        } else {
            tasks.addAll(newTasks);
        }

        taskAdapter.notifyDataSetChanged();
    }

    @Override
    public void onTaskClick(Task task) {
        Intent intent = new Intent(MainActivity.this, TaskDetailsActivity.class);
        intent.putExtra("TASK_ID", task.getId());
        intent.putExtra("TASK_TITLE", task.getTitle());
        intent.putExtra("TASK_DESCRIPTION", task.getDescription());
        intent.putExtra("TASK_Date", task.getDate());
        startActivity(intent);
    }
}
