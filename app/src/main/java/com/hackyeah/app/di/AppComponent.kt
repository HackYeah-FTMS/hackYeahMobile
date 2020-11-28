package com.hackyeah.app.di

import android.app.Application
import com.hackyeah.app.di.projects.ProjectsViewModelModule
import com.hackyeah.app.di.viewmodels.ViewModelFactoryModule
import com.hackyeah.app.ui.main.MainActivity
import com.hackyeah.app.ui.projects.FragmentProjects
import com.hackyeah.app.ui.projects.details.FragmentProjectDetails
import com.hackyeah.app.ui.projects.add.FragmentNewProject
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        AppModule::class,
        ProjectsViewModelModule::class,
        ViewModelFactoryModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: FragmentProjects)
    fun inject(fragment: FragmentProjectDetails)
    fun inject(fragment: FragmentNewProject)
}