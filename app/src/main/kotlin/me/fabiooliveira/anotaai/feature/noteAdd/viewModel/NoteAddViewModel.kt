package me.fabiooliveira.anotaai.feature.noteAdd.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import me.fabiooliveira.anotaai.model.entity.Note
import me.fabiooliveira.anotaai.model.repository.NoteRepository
import me.fabiooliveira.anotaai.util.DateUtil
import me.fabiooliveira.anotaai.vo.Resource
import java.util.Date

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

class NoteAddViewModel(private val repository: NoteRepository) : ViewModel() {

    private var compositeDisposables = CompositeDisposable()
    val resourceMutableLive = MutableLiveData<Resource<Long>>()

    fun addNote(note: Note) {
        compositeDisposables.add(repository.insertNote(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    resourceMutableLive.postValue(Resource.success(it))
                }, {
                    resourceMutableLive.postValue(Resource.error(it.message!!, null))
                })
        )
    }

    fun getRelevance(relevanceProgress: Int): Int {
        return when (relevanceProgress) {
            in 66..100 -> 1
            in 33..65 -> 2
            else -> 3
        }
    }

    fun isEmpty(text: String): Boolean {
        return text.isEmpty()
    }

    fun isDateBeforeToday(date: Date): Boolean {
        val calendar = DateUtil.getCalendar()

        if(DateUtil.isSameDay(date, calendar.time)) {
            return false
        }
        return date.before(calendar.time)
    }

    fun onDestroy() {
        if(!compositeDisposables.isDisposed) {
            compositeDisposables.dispose()
        }
    }
}
