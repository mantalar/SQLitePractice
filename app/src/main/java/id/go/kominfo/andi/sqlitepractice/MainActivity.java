package id.go.kominfo.andi.sqlitepractice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import id.go.kominfo.andi.sqlitepractice.adapter.FriendAdapter;
import id.go.kominfo.andi.sqlitepractice.helper.FriendDBOne;
import id.go.kominfo.andi.sqlitepractice.model.Friend;

public class MainActivity extends AppCompatActivity {
    public static final int MODE_VIEW = 0;
    public static final int MODE_ADD = 1;

    //object FriendAdapter
    private FriendAdapter adapter;
    //object List<Friend>
    private final List<Friend> ls = new ArrayList<>();
    //object FriendDBOne
    private FriendDBOne db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        assert getSupportActionBar()!=null;
        getSupportActionBar().setTitle("SQLite Practices");

        //deklarasikan object FriendAdapter
        adapter = new FriendAdapter(this, ls);

        //deklarasikan object ListView
        ListView listView = findViewById(R.id.listview);
        //tentukan adapter untuk object listview
        listView.setAdapter(adapter);

        //pasang lister OnItemClickListerner pada listview
        listView.setOnItemClickListener(this::itemOnClick);
        //pasang lister OnItemLongClickListerner pada listview
        listView.setOnItemLongClickListener(this::itemOnLongClick);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this::fabOnClick);

        db = new FriendDBOne(this);
    }

    //ketika tombol fab di klik maka akan memanggil AddAndViewActivity
    private void fabOnClick(View view) {
        //buat intent yang akan memanggil AddAndViewActivity
        Intent intent = new Intent(this, AddAndViewActivity.class);
        //dengan mode tambah data
        intent.putExtra("mode", MODE_ADD);
        //jalankan intent
        startActivity(intent);
    }

    //method digunakan untuk menghapus record atau data
    private boolean itemOnLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Friend friend = (Friend) adapterView.getAdapter().getItem(i);

        //tampilkan dialog
        new AlertDialog.Builder(this)
                .setTitle("Delete Confirmation?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(String.format("Delete item %s", friend.getName()))
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", (dialog, which) -> {
                    //hapus friend berdasar id
                    db.delete(friend.getId());
                    //buang friend dari ls
                    ls.remove(friend);
                    //tampilkan perubhan data di listview
                    adapter.notifyDataSetChanged();
                })
                .show();

        return true;
    }

    //method ini digunakan untuk menampilkan data di AddAndViewActivity
    private void itemOnClick(AdapterView<?> adapterView, View view, int i, long l) {
        //tangkap item dan berikan ke object friend
        Friend friend = (Friend) adapterView.getAdapter().getItem(i);

        //buat intent yang nanti akan memanggil AddAndViewActivity
        Intent intent = new Intent(this, AddAndViewActivity.class);
        //kirim data key mode dengan nilai MODE_VIEW
        intent.putExtra("mode", MODE_VIEW);
        //kirim data key friend dengan nilai friend
        intent.putExtra("friend", friend);
        //jalankan intent
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ls.clear();
        ls.addAll(db.getAllFriend());
        adapter.notifyDataSetChanged();
    }
}