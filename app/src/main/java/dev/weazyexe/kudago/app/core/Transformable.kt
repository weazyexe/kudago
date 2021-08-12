package dev.weazyexe.kudago.app.core

/**
 * Интерфейс, описывающий трансформацию объекта в тип [T]
 */
interface Transformable <T> {

    fun transform(): T
}

/**
 * Трансформировать коллекцию из [Transformable]
 */
fun <T> List<Transformable<T>>.transform() = map { it.transform() }