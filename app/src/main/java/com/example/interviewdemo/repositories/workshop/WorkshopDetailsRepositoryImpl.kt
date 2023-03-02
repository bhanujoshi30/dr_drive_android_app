package com.example.interviewdemo.repositories.workshop


import com.example.interviewdemo.models.WorkshopDetailItem
import com.example.interviewdemo.network.ApiInterface
import com.example.interviewdemo.room.dao.WorkshopDetailsDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * Workshop Details repository
 */
class WorkshopDetailsRepositoryImpl @Inject constructor(
    private val mApi: ApiInterface,
    private val dispatcher: CoroutineDispatcher,
    private val workshopDetailDao: WorkshopDetailsDao
) : WorkshopDetailsRepository {

    override suspend fun getWorkshopList() = withContext(dispatcher) {
        mApi.getWorkshopDetailsList()
    }

    override suspend fun getWorkshopsDetails(): List<WorkshopDetailItem> {
        return workshopDetailDao.getWorkshopDetails()
    }

    override suspend fun insertAll(workshopDetailList: MutableList<WorkshopDetailItem>) {
        return workshopDetailDao.insertWorkshopDetails(workshopDetailList)
    }

    override suspend fun deleteAll() {
        return workshopDetailDao.deleteAllWorkshopDetails()
    }
}