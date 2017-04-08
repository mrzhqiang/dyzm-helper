package cn.zhang.qiang.hellgate.db;

import android.util.LongSparseArray;

import java.util.List;

/**
 * <p>
 * Created by mrZQ on 2017/4/5.
 */

public interface DbTable {
    String[] getCreateSql();
    LongSparseArray<List<String>> getUpgrade();
    LongSparseArray<List<String>> getDowngrade();
}
