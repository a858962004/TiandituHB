package com.gangbeng.tiandituhb.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gangbeng.tiandituhb.R;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

/**
 * @author zhanghao
 * @date 2018-10-23
 */

public class ShowDialog {

    public static void update(final Context mContext, final String httpUrl) {
        CacheUtil.clearAllCache(mContext);
//        SharedUtil.clearData();
        DataCleanManager.cleanDatabases(mContext);
        double v = Math.random() * 100000;
        final String fileName = "up" + v + ".apk";
        String sdPublic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

        File f = new File(sdPublic);
        final File file = new File(sdPublic + "/" + fileName);

        if (httpUrl != null) {
            if (!TextUtils.isEmpty(httpUrl)) {
                if (!f.exists()) {
                    f.mkdirs();
                }
//                AlertDialog.Builder builder = new AlertDialog.Builder(mContext).setTitle("软件更新").setMessage("您的版本过低,需升级版本才可使用").setCancelable(false).setPositiveButton("更新", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
                final ProgressDialog progressDialog = new ProgressDialog(mContext);
                progressDialog.setTitle("下载更新");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setProgress(0);
                progressDialog.setMax(100);
                progressDialog.setCancelable(false);
                progressDialog.show();
                FileDownloader.getImpl().create(httpUrl)
                        .setPath(file.getAbsolutePath(), false)
                        .setListener(new FileDownloadListener() {
                            @Override
                            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                MyLogUtil.showLog("pending");
                            }

                            @Override
                            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                progressDialog.setProgress((int) (((float) soFarBytes / totalBytes) * 100));
                            }

                            @Override
                            protected void completed(BaseDownloadTask task) {
                                progressDialog.setProgress(100);
                                openFile(mContext, file);
                            }

                            @Override
                            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                MyLogUtil.showLog("paused");
                            }

                            @Override
                            protected void error(BaseDownloadTask task, Throwable e) {
                                MyLogUtil.showLog("下载报错：" + e.getMessage());
                                ToastUtil.ToastMessage(mContext, "网络连接失败，请检查网络");

                            }

                            @Override
                            protected void warn(BaseDownloadTask task) {

                            }
                        }).start();
            }
//                });
//
//                builder.show();
//            }
        }
    }


    private static void openFile(Context mContext, File file) {
        // TODO Auto-generated method stub
        Log.e("OpenFile", file.getName());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

    public static void showAttention(final Context context, String title, String stirng, final DialogCallBack callBack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(stirng);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callBack.dialogSure(dialog);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callBack.dialogCancle(dialogInterface);
            }
        });
        builder.show();
    }

    public static void showCreateGroup(final Context context, final CreateDialogCallBack callBack) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle("组队");
        View view = View.inflate(context, R.layout.dialog_sharegroup, null);
        Button createBT = view.findViewById(R.id.bt_creategroup);
        final EditText addED = view.findViewById(R.id.ed_addgroup);
        final Button addBT = view.findViewById(R.id.bt_addgroup);
        alertDialog.setView(view);
        final AlertDialog dialog = alertDialog.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                MyLogUtil.showLog("cancel");
                Activity activity = (Activity) context;
                activity.finish();
            }
        });
        addED.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 5)
                    addBT.setBackground(context.getResources().getDrawable(R.drawable.login_btn));
                else addBT.setBackgroundColor(context.getResources().getColor(R.color.grey));
            }
        });
        addBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commend = String.valueOf(addED.getText());
                if (commend.length() > 5) {
                    callBack.addGroup(dialog, commend);
                }
            }
        });
        createBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.createGroup(dialog);
            }
        });
    }


    public static void showDeletProgress(final Context context) {
        final ProgressDialog proDialog = ProgressDialog.show(context, "缓存删除中..",
                "删除中..请稍后....", true, true);
        proDialog.setCanceledOnTouchOutside(false);
        proDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final boolean b = Util.deleteDirectory(context, Environment.getExternalStorageDirectory() + File.separator + "Tianditu_LF" + File.separator);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (b) {
                            proDialog.cancel();
                            ToastUtil.toast("删除成功");

                        } else {
                            proDialog.cancel();
                            ToastUtil.toast("删除失败");
                        }
                    }
                });

            }
        }).start();

    }

    public interface DialogCallBack {
        void dialogSure(DialogInterface dialog);

        void dialogCancle(DialogInterface dialog);
    }

    public interface CreateDialogCallBack {
        void addGroup(AlertDialog dialog, String commend);

        void createGroup(AlertDialog dialog);
    }

}
