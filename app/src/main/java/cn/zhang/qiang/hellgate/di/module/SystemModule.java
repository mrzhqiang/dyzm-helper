package cn.zhang.qiang.hellgate.di.module;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;

import cn.zhang.qiang.hellgate.di.PerActivity;
import dagger.Module;
import dagger.Provides;

/**
 * 提供系统服务相关依赖
 * <p>
 * Created by mrZQ on 2017/5/1.
 */

@Module
public final class SystemModule {

    @Provides @PerActivity AudioManager provideAudioManager(Context context) {
        return (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    @Provides @PerActivity SensorManager provideSensorManager(Context context) {
        return (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    @Provides @PerActivity Sensor provideSensorAccelerometer(SensorManager sensorManager) {
        return sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Provides @PerActivity ConnectivityManager provideConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}
