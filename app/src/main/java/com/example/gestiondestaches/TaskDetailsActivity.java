package com.example.gestiondestaches;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;

import java.util.Calendar;

public class TaskDetailsActivity extends AppCompatActivity {
    private TextView detailTitle, detailDescription, detailDate, detailStatus;
    private DBtask myDBtask;
    private Task currentTask;
    private LinearLayout viewMode, editMode;
    private EditText editTitle, editDescription, editDate;
    private boolean isEditMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        myDBtask = new DBtask(new DatabaseHelper(this));
        initializeViews();
        loadTaskData();
        setupButtons();
    }

    private void initializeViews() {
        viewMode = findViewById(R.id.viewMode);
        detailTitle = findViewById(R.id.detailTitle);
        detailDescription = findViewById(R.id.detailDescription);
        detailDate = findViewById(R.id.detailDate);
        detailStatus = findViewById(R.id.detailStatus);

        editMode = findViewById(R.id.editMode);
        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        editDate = findViewById(R.id.editDate);
        editMode.setVisibility(View.GONE);
    }

    private void loadTaskData() {
        int taskId = getIntent().getIntExtra("TASK_ID", -1);
        if (taskId != -1) {
            currentTask = myDBtask.getTaskById(taskId);
            if (currentTask != null) {
                detailTitle.setText(currentTask.getTitle());
                detailDescription.setText(currentTask.getDescription());
                detailDate.setText(currentTask.getDate());
                detailStatus.setText(currentTask.getStatus());

                MaterialButton completeButton = findViewById(R.id.completeButton);
                if ("complet".equals(currentTask.getStatus())) {
                    completeButton.setText("Tâche complétée");
                    completeButton.setEnabled(false);
                } else {
                    completeButton.setText("Marquer comme complet");
                    completeButton.setEnabled(true);
                }
            } else {
                Toast.makeText(this, "Tâche introuvable", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Erreur lors de la récupération de la tâche", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void updateDisplayData() {
        if (currentTask != null) {
            detailTitle.setText(currentTask.getTitle());
            detailDescription.setText(currentTask.getDescription());
            detailDate.setText(currentTask.getDate());
            detailStatus.setText(currentTask.getStatus());

            //disable complete button
            MaterialButton completeButton = findViewById(R.id.completeButton);
            if ("complet".equals(currentTask.getStatus())) {
                completeButton.setText("Tâche complétée");
                completeButton.setEnabled(false);
            } else {
                completeButton.setText("Marquer comme complet");
                completeButton.setEnabled(true);
            }
        }
    }

    private void setupButtons() {
        findViewById(R.id.editButton).setOnClickListener(v -> toggleEditMode());
        findViewById(R.id.completeButton).setOnClickListener(v -> {
            if (currentTask != null) {
                myDBtask.updateTaskStatus(currentTask.getId(), "complet");
                currentTask.setStatus("complet");
                detailStatus.setText("complet");
                MaterialButton completeButton = findViewById(R.id.completeButton);
                completeButton.setText("Tâche complétée");
                completeButton.setEnabled(false); // Désactiver le bouton
                Toast.makeText(this, "Tâche marquée comme complétée", Toast.LENGTH_SHORT).show();
            }
        });
        //rappel
        findViewById(R.id.reminderButton).setOnClickListener(v -> showReminderDialog());

        //partage
        findViewById(R.id.shareButton).setOnClickListener(v -> shareTask());

        //suppression
        findViewById(R.id.deleteButton).setOnClickListener(v -> showDeleteConfirmation());

        //retour
        findViewById(R.id.backButton).setOnClickListener(v -> finish());

        //updates
        findViewById(R.id.saveButton).setOnClickListener(v -> saveChanges());
        findViewById(R.id.cancelButton).setOnClickListener(v -> cancelEdit());
    }

    private void toggleEditMode() {
        isEditMode = !isEditMode;
        if (isEditMode) {
            viewMode.setVisibility(View.GONE);
            editMode.setVisibility(View.VISIBLE);
            editTitle.setText((CharSequence) currentTask.getTitle());
            editDescription.setText(currentTask.getDescription());
            editDate.setText(currentTask.getDate());
        } else {
            viewMode.setVisibility(View.VISIBLE);
            editMode.setVisibility(View.GONE);
        }
    }

    private void saveChanges() {
        String newTitle = editTitle.getText().toString();
        String newDescription = editDescription.getText().toString();
        String newDate = editDate.getText().toString();

        if (newTitle.isEmpty()) {
            Toast.makeText(this, "Le titre ne peut pas être vide", Toast.LENGTH_SHORT).show();
            return;
        }

        currentTask.setTitle(newTitle);
        currentTask.setDescription(newDescription);
        currentTask.setDate(newDate);

        myDBtask.updateTask(currentTask);
        updateDisplayData();
        toggleEditMode();
        Toast.makeText(this, "Modifications enregistrées", Toast.LENGTH_SHORT).show();
    }

    private void cancelEdit() {
        toggleEditMode();
    }

    private void showReminderDialog() {
        Calendar calendar = Calendar.getInstance();

        DatePickerDialog dateDialog = new DatePickerDialog(this, (view, year, month, day) -> {
            TimePickerDialog timeDialog = new TimePickerDialog(this, (timeView, hour, minute) -> {
                String reminderDateTime = String.format("%02d/%02d/%d à %02d:%02d",
                        day, month + 1, year, hour, minute);
                TextView reminderTextView = findViewById(R.id.detailReminder);
                reminderTextView.setText("Rappel : " + reminderDateTime);
                reminderTextView.setVisibility(View.VISIBLE);

                Toast.makeText(this, "Rappel défini", Toast.LENGTH_SHORT).show();
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
            timeDialog.show();
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        dateDialog.show();
    }


    private void setAlarmForTask(long timeInMillis) {
        Toast.makeText(this, "Rappel défini", Toast.LENGTH_SHORT).show();
    }

    private void shareTask() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareBody = "Tâche: " + currentTask.getTitle() +
                "\nDescription: " + currentTask.getDescription() +
                "\nDate: " + currentTask.getDate() +
                "\nStatut: " + currentTask.getStatus();
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Détails de la tâche");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(shareIntent, "Partager via"));
    }

    private void showDeleteConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Supprimer la tâche")
                .setMessage("Êtes-vous sûr de vouloir supprimer cette tâche ?")
                .setPositiveButton("Oui", (dialog, which) -> {
                    myDBtask.deleteTask(currentTask);
                    Toast.makeText(this, "Tâche supprimée", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .setNegativeButton("Non", null)
                .show();
    }
}