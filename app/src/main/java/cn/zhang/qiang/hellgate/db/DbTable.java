package cn.zhang.qiang.hellgate.db;

/**
 * <p>
 * Created by mrZQ on 2017/4/5.
 */

public interface DbTable {
    String getCreateSql();
    String getDropSql();
    String[] getUpgrade();
}
