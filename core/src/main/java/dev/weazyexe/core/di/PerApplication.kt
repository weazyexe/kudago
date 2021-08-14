package dev.weazyexe.core.di

import javax.inject.Scope

/**
 * Глобальный Dagger скоуп
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PerApplication