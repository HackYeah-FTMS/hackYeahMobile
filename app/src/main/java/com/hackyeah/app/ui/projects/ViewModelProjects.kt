package com.hackyeah.app.ui.projects

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hackyeah.app.data.NetworkState
import com.hackyeah.app.data.Status
import com.hackyeah.app.data.model.Project
import com.hackyeah.app.data.repository.RepositoryProjects
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import android.util.Log
import java.io.File

class ViewModelProjects @Inject constructor(
    private var repositoryProjects: RepositoryProjects,
    var networkState: MutableLiveData<NetworkState>
) : ViewModel() {

    private var disposable: Disposable? = null

    private var projectList: MutableLiveData<List<Project>> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getProjects(): MutableLiveData<List<Project>> {
        networkState.postValue(NetworkState(status = Status.LOADING))
        disposable = repositoryProjects
            .getProjects()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { projects ->
                    if (projects.isNullOrEmpty()) {
                        networkState.postValue(NetworkState(status = Status.FAILED))
                        return@subscribe
                    }
                    networkState.postValue(NetworkState(Status.SUCCESS))
                    projectList.postValue(projects)
                },
                { throwable ->
                    networkState.postValue(
                        NetworkState(
                            status = Status.FAILED,
                            error = throwable
                        )
                    )
                }
            )

        return projectList
    }

    fun getProjectById(id: Int) = projectList.value?.firstOrNull() { it.id == id }

    @SuppressLint("CheckResult")
    fun addProject(
        title: String,
        description: String,
        ideaSolutionFile: File,
        ideaImageFile: File,
        tags: List<String>? = null,
    ) {
        networkState.postValue(NetworkState(status = Status.LOADING))
        disposable = repositoryProjects
            .addProjects(
                title,
                description,
                ideaSolutionFile,
                ideaImageFile
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    Log.e("Felipe", "success" )
                    networkState.postValue(NetworkState(Status.SUCCESS))
                },
                { throwable ->
                    Log.e("Felipe", "throwable " + throwable.message )
                    networkState.postValue(
                        NetworkState(
                            status = Status.FAILED,
                            error = throwable
                        )
                    )
                }
            )
    }

    fun resetNetworkState() {
        networkState.value = NetworkState(Status.RESET)
    }

    fun setLoading() {
        networkState.value = NetworkState(Status.LOADING)
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}