package com.finance.data.mapper

import java.util.ArrayList

abstract class Mapper<T, F> {

    abstract fun mapTo(item: F): T

    abstract fun mapFrom(item: T): F

    fun mapTo(items: List<F>): List<T> {
        val memberEntities: MutableList<T> = ArrayList()
        for (item in items) {
            memberEntities.add(mapTo(item))
        }
        return memberEntities
    }

    fun mapFrom(items: List<T>): List<F> {
        val member: MutableList<F> = ArrayList()
        for (item in items) {
            member.add(mapFrom(item))
        }
        return member
    }
}