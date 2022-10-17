package com.vladbystrov.yandexmapkit.di

import android.content.Context
import androidx.room.Room
import com.vladbystrov.yandexmapkit.data.RepositoryImpl
import com.vladbystrov.yandexmapkit.data.database.PlaceDao
import com.vladbystrov.yandexmapkit.data.database.PlaceDatabase
import com.vladbystrov.yandexmapkit.domain.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRepository(placeDao: PlaceDao) = RepositoryImpl(placeDao)

    @Provides
    fun providePlaceDao(placeDatabase: PlaceDatabase): PlaceDao {
        return placeDatabase.placeDao()
    }

    @Provides
    @Singleton
    fun providePlaceDatabase(@ApplicationContext appContext: Context): PlaceDatabase {
        return Room.databaseBuilder(
            appContext,
            PlaceDatabase::class.java,
            "placeDatabase"
        ).build()
    }
}