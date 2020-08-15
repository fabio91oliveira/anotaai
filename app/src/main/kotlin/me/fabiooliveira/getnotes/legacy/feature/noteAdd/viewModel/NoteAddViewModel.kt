//package me.fabiooliveira.getnotes.legacy.feature.noteAdd.viewModel
//
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.disposables.CompositeDisposable
//import io.reactivex.schedulers.Schedulers
//import me.fabiooliveira.getnotes.legacy.model.entity.Note
//import me.fabiooliveira.getnotes.legacy.model.repository.NoteRepository
//import me.fabiooliveira.getnotes.legacy.util.DateUtil
//import me.fabiooliveira.getnotes.legacy.vo.Resource
//import java.util.Date
//
///**
// * Created by Fabio Oliveira
// * Email: fabio91oliveira@gmail.com
// * Mobile: +55 (21) 98191-4951
// * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
// */
//
//class NoteAddViewModel(private val repository: NoteRepository) : ViewModel() {
//
//    private var compositeDisposables = CompositeDisposable()
//    val resourceMutableLive = MutableLiveData<Resource<Long>>()
//
//    fun addNote(note: Note) {
//        compositeDisposables.add(repository.insertNote(note)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe ({
//                    resourceMutableLive.postValue(Resource.success(it))
//                }, {
//                    resourceMutableLive.postValue(Resource.error(it.message!!, null))
//                })
//        )
//    }
//
//    fun isEmpty(text: String): Boolean {
//        return text.isEmpty()
//    }
//
//    fun isDateBeforeToday(date: Date): Boolean {
//        val calendar = DateUtil.getCalendar()
//
//        if(DateUtil.isSameDay(date, calendar.time)) {
//            return false
//        }
//        return date.before(calendar.time)
//    }
//
//    fun onDestroy() {
//        if(!compositeDisposables.isDisposed) {
//            compositeDisposables.dispose()
//        }
//    }
//}
