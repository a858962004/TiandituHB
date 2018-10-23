package com.gangbeng.tiandituhb.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.gangbeng.tiandituhb.R;

/**
 * @author zhanghao
 * @date 2018-10-23
 */

public class ShowDialog {

    public static void showTucengDialog(Context context,boolean firstvisiable,boolean secondvisiable){
        LayoutInflater from = LayoutInflater.from(context);
        View title = from.inflate(R.layout.dialog_title, null);
        TextView textview = (TextView) title.findViewById(R.id.title_dialog);
        textview.setText("图层管理");
        View inflate = from.inflate(R.layout.dialog_tuceng, null);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setCustomTitle(title)
                .setView(inflate)
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                }).setPositiveButton("取消", null).show();
        dialog.setCanceledOnTouchOutside(false);
    }

}
