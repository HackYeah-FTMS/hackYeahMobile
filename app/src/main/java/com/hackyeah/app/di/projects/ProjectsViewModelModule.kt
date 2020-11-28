package com.hackyeah.app.di.projects

import androidx.lifecycle.ViewModel
import com.hackyeah.app.di.viewmodels.ViewModelKey
import com.hackyeah.app.ui.projects.ViewModelProjects
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
public abstract class ProjectsViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ViewModelProjects::class)
    public abstract fun bindViewModelProjects(viewModel: ViewModelProjects): ViewModel

}