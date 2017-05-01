package cn.zhang.qiang.hellgate.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * 定义提供依赖的有效范围，比如Activity生命周期
 * <p>
 * Created by mrZQ on 2017/5/1.
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {}
