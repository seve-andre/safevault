package com.mitch.safevault.core.database

import androidx.room.RoomDatabase

// see at https://developer.android.com/training/data-storage/room#database
/*@Database(
    // put entities inside the []
    entities = [],
    version = 1
)*/
abstract class SafeVaultRoomDatabase : RoomDatabase() {
    // put DAOs here as abstract val
    // see example at: https://github.com/seve-andre/myUniBo/blob/main/app/src/main/kotlin/com/mitch/my_unibo/db/UniboDatabase.kt
}
