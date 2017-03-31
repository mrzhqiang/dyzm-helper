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
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.zhang.qiang.hellgate.R;
import cn.zhang.qiang.hellgate.utils.FileHelper;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * <p>
 * Created by mrZQ on 2017/3/31.
 */

public class TestOkio extends AppCompatActivity {
    private static final String TAG = "TestOkio";

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
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            createDir();
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "没有存储权限，无法继续测试", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    createDir();
                } else {
                    Toast.makeText(this, "没有存储权限，无法继续测试", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @OnClick(R.id.btn_add)
    public void openAddDialog() {
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
    public void showContent() {
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
}
