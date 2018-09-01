package ru.familion.cryptocurencies.vm.factory

import android.arch.lifecycle.ViewModel
import kotlin.reflect.KClass
import dagger.MapKey

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)