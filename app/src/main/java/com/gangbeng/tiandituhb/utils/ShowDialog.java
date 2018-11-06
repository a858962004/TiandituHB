package com.gangbeng.tiandituhb.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

/**
 * @author zhanghao
 * @date 2018-10-23
 */

public class ShowDialog {

    public void update(final Context mContext, final String httpUrl){
        CacheUtil.clearAllCache(mContext);
        SharedUtil.clearData();
        DataCleanManager.cleanDatabases(mContext);
        double v = Math.random() * 100000;
        final String fileName = "up"+v+".apk";
        String sdPublic = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();

        File f = new File(sdPublic);
        final File file = new File(sdPublic + fileName);

        if (httpUrl != null) {
            if (!TextUtils.isEmpty(httpUrl)){
                if (!f.exists()) {
                    f.mkdirs();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext).setTitle("软件更新").setMessage("您的版本过低,需升级版本才可使用").setCancelable(false).setPositiveButton("更新", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
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

                                    }

                                    @Override
                                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                                        progressDialog.setProgress((int) (((float) soFarBytes / totalBytes) * 100));
                                    }

                                    @Override
                                    protected void completed(BaseDownloadTask task) {
                                        progressDialog.setProgress(100);
                                        openFile(mContext,file);
                                    }

                                    @Override
                                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

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
                });

                builder.show();
            }
        }
    }

    private void openFile(Context mContext,File file) {
        // TODO Auto-generated method stub
        Log.e("OpenFile", file.getName());
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }


}
