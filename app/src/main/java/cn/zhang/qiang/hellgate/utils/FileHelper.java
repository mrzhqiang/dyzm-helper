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

package cn.zhang.qiang.hellgate.utils;

import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.qiang.zhang.logger.Log;


/**
 * <p>
 * Created by mrZQ on 2016/10/14.
 */
public final class FileHelper {
    private static final String TAG = "FileHelper";

    private FileHelper() {}

    public static boolean checkoutSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static void copyStream(@NonNull OutputStream outputStream,
                                  @NonNull InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
        } finally {
            outputStream.close();
            inputStream.close();
        }
    }

    public static void checkDirExists(@NonNull File dirFile) {
        if (!dirFile.exists() && !dirFile.mkdirs() && !dirFile.isDirectory()) {
            Log.e(TAG, "dir [" + dirFile.getPath() + "] not exists，make failed!");
        }
    }

    public static void checkFileExists(@NonNull File file) throws IOException {
        if (!file.exists() && !file.createNewFile() && !file.exists()) {
            Log.d(TAG, "file [" + file.getPath() + "] not exists，create failed!");
        }
    }

}
