package com.example.coinRankingUpdate.core
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.coinRankingUpdate.data.entity.BookmarkEntity
import com.example.coinRankingUpdate.data.entity.CryptocurrencyEntity
import com.example.coinRankingUpdate.data.local.BookmarkDao
import com.example.coinRankingUpdate.data.local.CryptocurrenciesDao

@Database(
    version = 1,
    entities = [CryptocurrencyEntity::class, BookmarkEntity::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cryptocurrenciesDao(): CryptocurrenciesDao
    abstract fun bookmarkDao() : BookmarkDao

    companion object {

        private const val DATABASE_NAME = "db_coin_ranking"

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}