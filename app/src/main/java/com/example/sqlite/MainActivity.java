package com.example.sqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    ListView lstvCv;
    ArrayList<CongViec> listCongViec;
    Database database;
    AdapterCV adapterCV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        adapterCV = new AdapterCV(this, R.layout.dong_congviec, listCongViec);
        lstvCv.setAdapter(adapterCV);
        database = new Database(this, "dulieu.sqlite",null,1);
        database.QueryData("CREATE TABLE IF NOT EXISTS CongViec(Id INTEGER PRIMARY KEY AUTOINCREMENT, tenCV VARCHAR(250))");
//        database.QueryData("INSERT INTO CongViec VALUES(null,'php')");
//        database.QueryData("INSERT INTO CongViec VALUES(null,'node js')");
//        database.QueryData("INSERT INTO CongViec VALUES(null,'c++')");
//        database.QueryData("INSERT INTO CongViec VALUES(null,'java')");
//        database.QueryData("INSERT INTO CongViec VALUES(null,'python')");
        getDataCongViec();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void dialogAdd() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_add);
        Button btnAdd = (Button) dialog.findViewById(R.id.button);
        EditText edtAdd = (EditText) dialog.findViewById(R.id.editTextTextPersonName);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = edtAdd.getText().toString().trim();
                database.QueryData("INSERT INTO CongViec VALUES (null,'"+ a +"')");
                getDataCongViec();
            }
        });
        dialog.show();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAdd){
            dialogAdd();
        }
        return super.onOptionsItemSelected(item);
    }

    public void  dialogXoaCv(String ten, int id){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa môn học" + ten +" này không?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM CongViec WHERE Id ='"+ id +"'");
                Toast.makeText(MainActivity.this, "Đã Xóa", Toast.LENGTH_SHORT).show();
                getDataCongViec();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
    public void getDataCongViec(){
        Cursor dataCongViec = database.getData("SELECT *FROM CongViec");
        listCongViec.clear();
        while (dataCongViec.moveToNext()){
            String a = dataCongViec.getString(1);
            int b = dataCongViec.getInt(0);
            listCongViec.add(new CongViec(b, a));
        }
        adapterCV.notifyDataSetChanged();
    }
    public void DialogSuaCV(String ten, int id){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        EditText edtUpdate = (EditText) dialog.findViewById(R.id.editTextNhap);
        Button btnConfirm = (Button) dialog.findViewById(R.id.buttonConfirm);
        Button btnCancle = (Button) dialog.findViewById(R.id.button2);
        edtUpdate.setText(ten);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = edtUpdate.getText().toString().trim();
                database.QueryData("UPDATE CongViec SET TenCV = '"+ a + "' WHERE Id ='"+ id +"'");
                dialog.dismiss();
                getDataCongViec();
            }
        });
        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
       dialog.show();
    }
    public void anhXa(){
        lstvCv = (ListView) findViewById(R.id.listViewCv);
        listCongViec = new ArrayList<>();
    }

}