package common.models

import common.ToMessageStringConvertible
import lib.util.toMessageString

data class Task(
    val name: String,
    val description: String,
    val link: String
) : ToMessageStringConvertible {
    override fun toMessageString() = listOf(name, description, link).joinToString("\n")
}

fun List<Task>.toMessageString() = map { it.name }.toMessageString()
