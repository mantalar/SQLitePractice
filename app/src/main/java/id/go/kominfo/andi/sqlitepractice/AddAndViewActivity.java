package id.go.kominfo.andi.sqlitepractice;

import static id.go.kominfo.andi.sqlitepractice.MainActivity.MODE_ADD;
import static id.go.kominfo.andi.sqlitepractice.MainActivity.MODE_VIEW;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import id.go.kominfo.andi.sqlitepractice.helper.FriendDBOne;
import id.go.kominfo.andi.sqlitepractice.model.Friend;

public class AddAndViewActivity extends AppCompatActivity {
    private TextInputEditText tieName;
    private TextInputEditText tieAddress;
    private TextInputEditText tiePhone;

    private Button btSave;
    private Friend temp;
    private int mode = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_view);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tieName = findViewById(R.id.tie_name);
        tieAddress = findViewById(R.id.tie_address);
        tiePhone = findViewById(R.id.tie_phone);

        btSave = findViewById(R.id.bt_save);
        btSave.setOnClickListener(this::saveData);
    }

    private void saveData(View view) {
        if (tieName.getText().toString().isEmpty() ||
                tieAddress.getText().toString().isEmpty()) {
            Toast.makeText(this, "Name dan Address tidak boleh kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        Friend friend = new Friend(
                tieName.getText().toString(),
                tieAddress.getText().toString(),
                tiePhone.getText().toString()
        );

        new FriendDBOne(this).insert(friend);
        finish(); //keluar dari AddAnViewActivity
    }

    @Override
    protected void onStart() {
        super.onStart();
        assert getIntent() != null;
        mode = getIntent().getIntExtra("mode", 0);
        if (mode == MODE_VIEW) {
            assert getSupportActionBar() != null;
            getSupportActionBar().setTitle("View Friend");

            Friend friend = (Friend) getIntent().getSerializableExtra("friend");
            tieName.setText(friend.getName());
            tieAddress.setText(friend.getAddress());
            tiePhone.setText(friend.getPhone());

            temp = (Friend) getIntent().getSerializableExtra("friend");

            btSave.setVisibility(View.GONE);
        } else if (mode == MODE_ADD) {
            assert getSupportActionBar() != null;
            getSupportActionBar().setTitle("Add Friend");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        int id = temp.getId();
        Friend friend = new Friend(
                id,
                tieName.getText().toString(),
                tieAddress.getText().toString(),
                tiePhone.getText().toString()
        );

        if (mode == MODE_ADD) {
            super.onBackPressed();
        } else if (!temp.equals(friend)) {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmation")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage("Simpan Perubahan?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", (dialog, which) -> {
                        new FriendDBOne(this).update(friend);
                        super.onBackPressed();
                    })
                    .show();
        }
        else
            super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}