package com.vickykdv.cashitdialog;

import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.vickykdv.cashitkdvdialog.Dlg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Dlg.Builder(MainActivity.this)
                        .setTitle("Im Title Center Dialog")
                        .setDlgPosition(Dlg.Position.CENTER)
                        .setDescription("I'm description Center Dialog")
                        .addButtonHoriz("Style 1 (Horizontal)",  Dlg.Style.STYLE1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        }).addButtonHoriz("Style 2 (Horizontal)",  Dlg.Style.STYLE2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                }).addButtonVert("Style 3 (Vertical)",  Dlg.Style.STYLE3, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                }).addButtonVert("Style 4 (Vertical)",  Dlg.Style.STYLE4, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                }).show();
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Dlg.Builder(MainActivity.this)
                        .setTitle("Im Title Bottom Dialog")
                        .setDlgPosition(Dlg.Position.BOTTOM)
                        .setDescription("I'm description Bottom Dialog")
                        .addButtonHoriz("Style 1 (Horizontal)",  Dlg.Style.STYLE1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        }).addButtonHoriz("Style 2 (Horizontal)",  Dlg.Style.STYLE2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                }).addButtonVert("Style 3 (Vertical)",  Dlg.Style.STYLE3, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                }).addButtonVert("Style 4 (Vertical)",  Dlg.Style.STYLE4, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                }).show();
            }
        });

        FloatingActionButton fab3 = findViewById(R.id.fab3);
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Dlg.Builder(MainActivity.this)
                        .setTitle("Im Title Top Dialog")
                        .setDlgPosition(Dlg.Position.TOP)
                        .setContentImage(R.mipmap.ic_launcher)
                        .setDescription("I'm description Top Dialog")
                        .addButtonHoriz("Style 1 (Horizontal)",  Dlg.Style.STYLE1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();

                            }
                        }).addButtonHoriz("Style 2 (Horizontal)",  Dlg.Style.STYLE2, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                }).addButtonVert("Style 3 (Vertical)",  Dlg.Style.STYLE3, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                }).addButtonVert("Style 4 (Vertical)",  Dlg.Style.STYLE4, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                }).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
