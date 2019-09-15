package ir.jmehdipour.immortalcar.data.source;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ir.jmehdipour.immortalcar.data.model.Car;

@Database(entities = {Car.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase appDatabase;

    public abstract  CarLocalDataSource getCarLocalDataSource();

    public static AppDatabase getInstance(Context context) {
        if (appDatabase == null ){
            appDatabase = Room.databaseBuilder(context, AppDatabase.class, "immortal_car")
                    .allowMainThreadQueries().build();
        }

        return appDatabase;
    }
}
