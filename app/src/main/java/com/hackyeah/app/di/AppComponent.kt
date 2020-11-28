package com.hackyeah.app.di

import android.app.Application
import com.hackyeah.app.di.ideas.IdeasViewModelModule
import com.hackyeah.app.di.projects.ProjectsViewModelModule
import com.hackyeah.app.di.viewmodels.ViewModelFactoryModule
import com.hackyeah.app.ui.ideas.FragmentIdeas
import com.hackyeah.app.ui.main.MainActivity
import com.hackyeah.app.ui.projects.FragmentProjects
import com.hackyeah.app.ui.projects.add.FragmentNewProject
import com.hackyeah.app.ui.projects.details.FragmentProjectDetails
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApiModule::class,
        AppModule::class,
        ProjectsViewModelModule::class,
        IdeasViewModelModule::class,
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
    fun inject(fragment: FragmentIdeas)
}