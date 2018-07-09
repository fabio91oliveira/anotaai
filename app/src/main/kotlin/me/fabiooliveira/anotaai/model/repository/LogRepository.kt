package me.fabiooliveira.anotaai.model.repository

import io.reactivex.Flowable
import me.fabiooliveira.anotaai.model.entity.Log
import me.fabiooliveira.anotaai.persistence.dao.LogDao
import javax.inject.Inject

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

class LogRepository @Inject constructor(private val logDao: LogDao) {

    fun insertLog(log: Log): Flowable<Long> {
        return Flowable.fromCallable {logDao.insert(log)}
    }

    fun findFirst(): Flowable<List<Log>> {
        return logDao.findAll()
    }
}