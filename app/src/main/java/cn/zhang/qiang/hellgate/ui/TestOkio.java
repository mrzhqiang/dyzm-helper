/*
 * Copyright (c) 2017.  mrZQ
 *
 * icensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package cn.zhang.qiang.hellgate.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhang.qiang.hellgate.R;
import cn.zhang.qiang.hellgate.utils.FileHelper;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * <p>
 * Created by mrZQ on 2017/3/31.
 */

public class TestOkio extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = "TestOkio";

    private static final int RC_WRITE_EXTERNAL_STORAGE = 100;

    @BindView(R.id.tv_show_something)
    TextView tvContent;

    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.btn_remove)
    Button btnRemove;
    @BindView(R.id.btn_changed)
    Button btnChanged;
    @BindView(R.id.btn_show)
    Button btnShow;

    File targetFile;

    AlertDialog customDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ButterKnife.bind(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @OnClick(R.id.btn_add)
//    @AfterPermissionGranted(RC_WRITE_EXTERNAL_STORAGE)
    public void openAddDialog() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            createDir();
            showAddDialog();
        } else {
            EasyPermissions.requestPermissions(this, "需要存储权限", RC_WRITE_EXTERNAL_STORAGE,
                                               Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private void createDir() {
        if (FileHelper.checkoutSDCard() && targetFile == null) {
            File parentDir = Environment.getExternalStorageDirectory();
            if (parentDir != null) {
                File childDir = new File(parentDir, "OkioTest");
                FileHelper.checkDirExists(childDir);

                long currentTime = System.currentTimeMillis();
                long today = TimeUnit.MILLISECONDS.toDays(currentTime);
                targetFile = new File(childDir, "story_" + today);
                try {
                    FileHelper.checkFileExists(targetFile);
                } catch (IOException e) {
                    Log.e(TAG, "check file exists error:" + e.getMessage());
                }
            }
        }
    }

    private void showAddDialog() {
        @SuppressLint("InflateParams")
        final View inputView = LayoutInflater.from(this).inflate(R.layout.dialog_view_input, null);
        customDialog = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog)
                .setTitle("添加内容")
                .setView(inputView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText input = ButterKnife.findById(inputView, R.id.dialog_edit_input);
                        String addContent = input.getText().toString();
                        addSomething(addContent);
                        dialog.dismiss();
                    }
                }).show();
    }

    @OnClick(R.id.btn_show)
//    @AfterPermissionGranted(RC_WRITE_EXTERNAL_STORAGE)
    public void showContent() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            createDir();
            getContent();
        } else {
            EasyPermissions.requestPermissions(this, "需要存储权限", RC_WRITE_EXTERNAL_STORAGE,
                                               Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private void getContent() {
        try {
            BufferedSource source = Okio.buffer(Okio.source(targetFile));
            String content = source.readUtf8();
            tvContent.setText(content);
            Toast.makeText(this, "读取成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addSomething(String addContent) {
        try {
            BufferedSink sink = Okio.buffer(Okio.appendingSink(targetFile));
            sink.writeUtf8(addContent);
            sink.flush();
            Toast.makeText(this, "写入成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Toast.makeText(this, "请继续操作", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        // 在这里，应该根据请求code去设定不同的标题和说明，如果存在多个权限请求和回调的话
        // 并且可以将请求code作为用户手动设置权限之后的返回code

        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle("需要存储权限")
                    .setRationale("为了写入或读取历史数据")
                    .setRequestCode(requestCode)
                    .build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 默认的code，是作为统一请求而存在，但统一请求通常会有这样的情况：
        // 用户或某些app取消权限，导致运行时无权限崩溃，因此建议在运行时检查权限比较合适。
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(this, "请继续操作", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == RC_WRITE_EXTERNAL_STORAGE) {
            if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请继续操作", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
