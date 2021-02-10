package com.app.msgalarmclock.presentation.model

enum class DayOfWeek(val shortName: String, val order: Int) {
    MONDAY("Пн", 2),
    TUESDAY("Вт", 3),
    WEDNESDAY("Ср", 4),
    THURSDAY("Чт", 5),
    FRIDAY("Пт", 6),
    SATURDAY("Сб", 7),
    SUNDAY("Вс", 1);

    companion object {
        fun fromShortName(name: String): DayOfWeek {
            return values().find {
                it.shortName == name
            } ?: MONDAY
        }

        fun fromOrder(order: Int): DayOfWeek {
            return values().find {
                it.order == order
            } ?: MONDAY
        }
    }
}