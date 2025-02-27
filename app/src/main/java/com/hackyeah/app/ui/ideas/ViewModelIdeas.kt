package com.hackyeah.app.ui.ideas

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hackyeah.app.data.NetworkState
import com.hackyeah.app.data.Status
import com.hackyeah.app.data.model.Idea
import com.hackyeah.app.data.model.IdeaDetails
import com.hackyeah.app.data.repository.RepositoryIdeas
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ViewModelIdeas @Inject constructor(
    private var ideasRepository: RepositoryIdeas,
    var networkState: MutableLiveData<NetworkState>
) : ViewModel() {

    private var disposable: Disposable? = null

    private var ideaList: MutableLiveData<List<Idea>> = MutableLiveData()
    private var singleIdeaDetails: MutableLiveData<IdeaDetails> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getIdeas(): MutableLiveData<List<Idea>> {
        networkState.postValue(NetworkState(status = Status.LOADING))
        disposable = ideasRepository
            .getIdeas()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { ideas ->
                    if (ideas.isNullOrEmpty()) {
                        networkState.postValue(NetworkState(status = Status.FAILED))
                        return@subscribe
                    }
                    networkState.postValue(NetworkState(Status.SUCCESS))
                    ideaList.postValue(ideas)
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

        return ideaList
    }

    fun getIdeaById(ideaId: Int): MutableLiveData<IdeaDetails> {
        disposable = ideasRepository
            .getIdeaDetails(ideaId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { idea ->
                    if (idea == null) {
                        networkState.postValue(NetworkState(status = Status.FAILED))
                        return@subscribe
                    }
                    networkState.postValue(NetworkState(Status.SUCCESS))
                    singleIdeaDetails.postValue(idea)
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

        return singleIdeaDetails
    }

    fun resetNetworkState() {
        networkState.value = NetworkState(Status.RESET)
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}