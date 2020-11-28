package com.hackyeah.app.di.ideas

import androidx.lifecycle.ViewModel
import com.hackyeah.app.di.viewmodels.ViewModelKey
import com.hackyeah.app.ui.ideas.ViewModelIdeas
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
public abstract class IdeasViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelIdeas::class)
    public abstract fun bindViewModelIdeas(viewModel: ViewModelIdeas): ViewModel

}