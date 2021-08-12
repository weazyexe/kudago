package dev.weazyexe.kudago.app.core

/**
 * Интерфейс, описывающий трансформацию объекта в тип [T]
 */
interface Transformable <T> {

    fun transform(): T
}