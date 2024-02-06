package com.sowhat.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.sowhat.database.common.DATABASE_FEED
import com.sowhat.database.dao.EntireFeedDao
import com.sowhat.database.dao.MyFeedDao
import com.sowhat.database.dao.NotificationDao
import com.sowhat.database.entity.EntireFeedEntity
import com.sowhat.database.entity.MyFeedEntity
import com.sowhat.database.entity.NotificationEntity

@Database(
    entities = [
        MyFeedEntity::class,
        EntireFeedEntity::class,
        NotificationEntity::class
    ],
    version = 6,
    autoMigrations = [
        AutoMigration(from = 1, to = 2), // 1 to 2 : 단순 테이블 추가이므로 Migration 클래스 만들지 않음,
        AutoMigration(from = 2, to = 3), // 2 to 3 : 전체 피드 테이블 칼럼추가(isOwner)
        AutoMigration(from = 3, to = 4), // 3 to 4 : Notification 테이블 추가
        AutoMigration(from = 4, to = 5), // 3 to 4 : Notification 테이블에 date 필드 추가
    ]
)
@TypeConverters(ListConverter::class, LongListConverter::class)
abstract class FeedDatabase : RoomDatabase() {
    abstract val myFeedDao: MyFeedDao
    abstract val entireFeedDao: EntireFeedDao
    abstract val notificationDao: NotificationDao

    companion object {
        @Volatile // makes the field immediately made visible to other threads
        private var INSTANCE: FeedDatabase? = null

//        @RenameColumn(
//            tableName = "subscriber_data_table",
//            fromColumnName = "subscriber_id",
//            toColumnName = "subscriber_id_revised"
//        )
//        class Migration1To2: AutoMigrationSpec
//
//        @RenameColumn(
//            tableName = "subscriber_data_table",
//            fromColumnName = "subscriber_name",
//            toColumnName = "subscriber_name_revised"
//        )
//        class Migration2To3: AutoMigrationSpec

        class Migration5to6 : Migration(5, 6) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE my_feed ADD COLUMN photoId TEXT")
                db.execSQL("ALTER TABLE entire_feed ADD COLUMN photoId TEXT")
            }
        }

        fun getInstance(context: Context): FeedDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        FeedDatabase::class.java,
                        DATABASE_FEED
                    ).addMigrations(Migration5to6()).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

class ListConverter {
    @TypeConverter
    fun listToJson(value: List<String>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<String>? {
        return Gson().fromJson(value,Array<String>::class.java)?.toList()
    }
}

class LongListConverter {
    @TypeConverter
    fun listToJson(value: List<Long>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Long>? {
        return Gson().fromJson(value,Array<Long>::class.java)?.toList()
    }
}