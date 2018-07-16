package me.fabiooliveira.getnotes.feature.notesList.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import me.fabiooliveira.getnotes.model.entity.Note
import me.fabiooliveira.getnotes.feature.notesList.viewModel.mapper.NoteMapper
import me.fabiooliveira.getnotes.model.entity.Log
import me.fabiooliveira.getnotes.model.repository.LogRepository
import me.fabiooliveira.getnotes.model.repository.NoteRepository
import me.fabiooliveira.getnotes.vo.Resource
import java.util.*

/**
 * Created by Fabio Oliveira
 * Email: fabio91oliveira@gmail.com
 * Mobile: +55 (21) 98191-4951
 * LinkedIn: https://www.linkedin.com/in/fabio91oliveira
 */

class NotesListViewModel(private val noteRepository: NoteRepository, private val logRepository: LogRepository,
                         private val noteMapper: NoteMapper) : ViewModel() {

    private var compositeDisposables = CompositeDisposable()
    val resourceLongMutableLiveData = MutableLiveData<Resource<Long>>()
    val resourceHashMapMutableLiveData = MutableLiveData<Resource<LinkedHashMap<String, MutableList<Note>>>>()
    val resourceBooleanMutableLiveData = MutableLiveData<Boolean>()
    val resourceInsertedLogMutableLiveData = MutableLiveData<Boolean>()

    fun refreshNoteList(){
        compositeDisposables.add(noteRepository.getNotes()
                .map { noteList -> noteMapper.transform(noteList) }
                .subscribeOn(Schedulers.newThread())
                .subscribe ({
                    resourceHashMapMutableLiveData.postValue(Resource.success(it))
                }, {
                    resourceHashMapMutableLiveData.postValue(Resource.error(it.message!!, null))
                }))
    }

    fun checkFirstLogin(){
        compositeDisposables.add(logRepository.findFirst()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    resourceBooleanMutableLiveData.postValue(!it.isEmpty())
                    if(it.isEmpty()) insertLog()
                }, {
                    resourceBooleanMutableLiveData.postValue(false)
                }))
    }

    fun getNotesTitles(linkedHashMap: LinkedHashMap<String, MutableList<Note>>): MutableList<String> {
        val expandableNoteTitlesList = mutableListOf<String>()

        linkedHashMap.entries.forEach {
            expandableNoteTitlesList.add(it.key)
        }

        return expandableNoteTitlesList
    }

    fun changeStatus(note: Note) {
        note.isDone = !note.isDone

        compositeDisposables.add(noteRepository.markAsDone(note)
                .subscribeOn(Schedulers.newThread())
                .subscribe ({
                    if(it.toInt() == 1) { resourceLongMutableLiveData.postValue(Resource.success(it)) }
                    else { resourceLongMutableLiveData.postValue(Resource.error("", null)) }
                }, {
                    resourceLongMutableLiveData.postValue(Resource.error(it.message!!, null))
                }))
    }

    fun deleteNote(note: Note) {
        compositeDisposables.add(noteRepository.deleteNote(note)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    if(it.toInt() == 1) { resourceLongMutableLiveData.postValue(Resource.success(it)) }
                    else { resourceLongMutableLiveData.postValue(Resource.error("", null)) }
                }, {
                    resourceLongMutableLiveData.postValue(Resource.error(it.message!!, null))
                }))
    }

    private fun insertLog(){
        compositeDisposables.add(logRepository.insertLog(Log(Date()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    when(it.toInt()){
                        0-> resourceInsertedLogMutableLiveData.postValue(false)
                        1 -> resourceInsertedLogMutableLiveData.postValue(true)
                    }
                }, {resourceInsertedLogMutableLiveData.postValue(false)}))
    }
}
