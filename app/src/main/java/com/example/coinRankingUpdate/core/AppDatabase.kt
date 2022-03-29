package com.example.coinRankingUpdate.core
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.coinRankingUpdate.data.entity.Cryptocurrency
import com.example.coinRankingUpdate.data.local.CryptocurrenciesDao

@Database(
    version = 1,
    entities = [Cryptocurrency::class]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cryptocurrenciesDao(): CryptocurrenciesDao

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