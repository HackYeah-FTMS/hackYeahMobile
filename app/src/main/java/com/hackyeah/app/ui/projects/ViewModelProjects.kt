package com.hackyeah.app.ui.projects

import android.annotation.SuppressLint
import android.util.Log
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

class ViewModelProjects @Inject constructor(
    private var repositoryProjects: RepositoryProjects,
    var networkState: MutableLiveData<NetworkState>
) : ViewModel() {

    private var disposable: Disposable? = null

    private var projectList: MutableLiveData<List<Project>> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getProjects(): MutableLiveData<List<Project>> {
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
                    Log.e("Felipe ", "throwable " + throwable.message)
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

    fun resetNetworkState() {
        networkState.value = NetworkState(Status.RESET)

    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}