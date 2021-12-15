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

    //当按返回按钮时弹出一个提示，来确保无误操作，采用常见的对话框样式。
    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认退出吗？")
                .setTitle("提示")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create().show();
    }

    //改变了对话框的图表，添加了三个按钮
    protected void threeButtonDialog() {
        Dialog dialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.btn_star).setTitle("喜好调查")
                .setMessage("你喜欢李连杰的电影吗？")
                .setPositiveButton("很喜欢", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this, "我很喜欢他的电影。", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("不喜欢", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this, "我不喜欢他的电影。", Toast.LENGTH_LONG).show();
                    }
                })
                .setNeutralButton("一般", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(DialogActivity.this, "谈不上喜欢不喜欢。", Toast.LENGTH_LONG).show();
                    }
                }).create();

        dialog.show();
    }

    //信息内容是一个简单的View类型
    protected void editTextDialog() {
        final EditText editText = new EditText(this);
        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle("请输入")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(editText)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Logs.log("点击确定，输入的信息是：" + editText.getText().toString() + "");
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Logs.log("点击取消，输入的信息是：" + editText.getText().toString() + "");
                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    //信息内容是一组单选框
    protected void singleCheckDialog(){
        final String[] strings = new String[] { "轮回", "天葬", "涅槃"};
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("单选框")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setSingleChoiceItems(strings, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //which：int型整数，是点击的列表的位置参数index；
                        Logs.log("默认setSingleChoiceItems选取的信息是：" + which);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //which：int型整数，取消操作NegativeButton时为-2；
                        Logs.log("点取消，选取的信息是：" + which);
                        dialog.dismiss();
                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //which：int型整数，确定操作PositiveButton时为-1；
                        Logs.log("点确定，选取的信息是：" + which);
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }

    //信息内容是一组多选框
    protected void multipleDialog(){
        final String[] strings = new String[]{"轮回", "天葬", "涅槃"};
        Dialog dialog = new AlertDialog.Builder(this)
                .setTitle("复选框")
                .setMultiChoiceItems(strings, null, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        //which：int型，点击的item的位置index。
                        //isChecked：item被选中的时候为true，取消的时候为False。
                        Logs.log("选取的信息是：" + which + "    isChecked===" + isChecked);
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //which：int型整数，确定操作PositiveButton时为-1；
                        Logs.log("点确定，选取的信息是：which===" + which);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //which：int型整数，确定操作PositiveButton时为-2；
                        Logs.log("点取消，选取的信息是：which===" + which);
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

    //信息内容是一组简单列表项
    protected void singleList(){
        final String[] strings = new String[] { "轮回", "天葬", "涅槃" };
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("列表框")
                .setItems(strings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Logs.log("setItems，选取的信息是：" + which);
                    }
                })
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //which：int型整数，确定操作PositiveButton时为-2；
                        Logs.log("点确定，选取的信息是：" + which);
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
                .setTitle("自定义布局")
                .setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //which：int型整数，确定操作PositiveButton时为-2；
                        Logs.log("点确定，选取的信息是：" + editText.getText().toString() + "");
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //which：int型整数，确定操作PositiveButton时为-2；
                        Logs.log("点取消，选取的信息是：" + editText.getText().toString() + "");
                        dialog.dismiss();
                    }
                })
                .create();
        alertDialog.show();
    }
}