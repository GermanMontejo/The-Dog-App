package com.example.thedogapp.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.AutoMigrationSpec

@TypeConverters(PropertyTypeConverters::class)
@Database(
    entities = [DogEntity::class],
    version = 1,
    autoMigrations = [
//        AutoMigration(
//            from = 1,
//            to = 2,
//            spec = DogDatabase.DogMigrationSpec::class
//        )
    ]
)
abstract class DogDatabase : RoomDatabase() {
    abstract fun dogDao(): DogDao

    @RenameColumn(tableName = "dogs", fromColumnName = "image", toColumnName = "imageUrl")
    class DogMigrationSpec : AutoMigrationSpec
}
