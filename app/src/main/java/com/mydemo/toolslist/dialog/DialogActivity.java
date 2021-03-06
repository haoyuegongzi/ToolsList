package com.mydemo.toolslist.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mydemo.toolslist.BaseActivity;
import com.mydemo.toolslist.R;
import com.mydemo.toolslist.log.Logs;

import androidx.appcompat.app.AlertDialog;

public class DialogActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);

        findViewById(R.id.btnDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });

        findViewById(R.id.btnThreeButtonDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threeButtonDialog();
            }
        });

        findViewById(R.id.btnEditTextDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextDialog();
            }
        });

        findViewById(R.id.btnSingleCheckDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleCheckDialog();
            }
        });

        findViewById(R.id.btnMultipleDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleDialog();
            }
        });

        findViewById(R.id.btnSingleList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                singleList();
            }
        });

        findViewById(R.id.btnCustomViewDialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customViewDialog();
            }
        });
    }

    //???????????????????????????????????????????????????????????????????????????????????????????????????
    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("??????????????????")
                .setTitle("??????")
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    //???????????????????????????????????????????????????
    protected void threeButtonDialog() {
        Dialog dialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.btn_star).setTitle("????????????")
                .setMessage("?????????????????????????????????")
                .setPositiveButton("?????????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this, "???????????????????????????", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("?????????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this, "???????????????????????????", Toast.LENGTH_LONG).show();
                    }
                })
                .setNeutralButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this, "???????????????????????????", Toast.LENGTH_LONG).show();
                    }
                }).create();

        dialog.show();
    }

    //??????????????????????????????View??????
    protected void editTextDialog() {
        final EditText editText = new EditText(this);
        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle("?????????")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(editText)
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Logs.log("????????????????????????????????????" + editText.getText().toString() + "");
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Logs.log("????????????????????????????????????" + editText.getText().toString() + "");
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    //??????????????????????????????
    protected void singleCheckDialog(){
        final String[] strings = new String[] { "??????", "??????", "??????"};
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("?????????")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setSingleChoiceItems(strings, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //which???int?????????????????????????????????????????????index???
                        Logs.log("??????setSingleChoiceItems?????????????????????" + which);
                    }
                }).setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //which???int????????????????????????NegativeButton??????-2???
                        Logs.log("?????????????????????????????????" + which);
                        dialog.dismiss();
                    }
                }).setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //which???int????????????????????????PositiveButton??????-1???
                        Logs.log("?????????????????????????????????" + which);
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    //??????????????????????????????
    protected void multipleDialog(){
        final String[] strings = new String[]{"??????", "??????", "??????"};
        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle("?????????")
                .setMultiChoiceItems(strings, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        //which???int???????????????item?????????index???
                        //isChecked???item?????????????????????true?????????????????????False???
                        Logs.log("?????????????????????" + which + "    isChecked===" + isChecked);
                    }
                })
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //which???int????????????????????????PositiveButton??????-1???
                        Logs.log("?????????????????????????????????which===" + which);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //which???int????????????????????????PositiveButton??????-2???
                        Logs.log("?????????????????????????????????which===" + which);
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

    //????????????????????????????????????
    protected void singleList(){
        final String[] strings = new String[] { "??????", "??????", "??????" };
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("?????????")
                .setItems(strings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Logs.log("setItems????????????????????????" + which);
                    }
                })
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //which???int????????????????????????PositiveButton??????-2???
                        Logs.log("?????????????????????????????????" + which);
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    protected void customViewDialog(){
        LinearLayout linearLayout = findViewById(R.id.dialog);
        final View layout = getLayoutInflater().inflate(R.layout.dialog, linearLayout);
        final EditText editText = layout.findViewById(R.id.etname);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("???????????????")
                .setView(layout)
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //which???int????????????????????????PositiveButton??????-2???
                        Logs.log("?????????????????????????????????" + editText.getText().toString() + "");
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //which???int????????????????????????PositiveButton??????-2???
                        Logs.log("?????????????????????????????????" + editText.getText().toString() + "");
                        dialog.dismiss();
                    }
                })
                .create();
        alertDialog.show();
    }
}