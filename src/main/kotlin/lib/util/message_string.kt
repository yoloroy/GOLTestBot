package lib.util

fun List<String>.toMessageString() = (this zip indices).joinToString("\n") { (it, i) -> "${i + 1}) $it" }
