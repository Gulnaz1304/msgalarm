package com.app.msgalarmclock.presentation.viewmodeldi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.app.msgalarmclock.presentation.ui.main.alarm.AlarmViewModel
import com.app.msgalarmclock.presentation.ui.main.alarmlist.AlarmListViewModel
import com.app.msgalarmclock.presentation.ui.main.choosecontact.SelectContactViewModel
import com.app.msgalarmclock.presentation.ui.ring.RingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelBinder {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(AlarmListViewModel::class)
    abstract fun bindAlarmListViewModel(viewModel: AlarmListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AlarmViewModel::class)
    abstract fun bindNewAlarmViewModel(viewModel: AlarmViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SelectContactViewModel::class)
    abstract fun bindSelectContactViewModel(viewModel: SelectContactViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RingViewModel::class)
    abstract fun bindRingViewModel(viewModel: RingViewModel): ViewModel
}