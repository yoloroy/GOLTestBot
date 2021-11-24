package common.models

data class UserData(
    val currentRoadmaps: MutableList<Roadmap> = mutableListOf(),
    var currentTasks: MutableList<Task> = mutableListOf(),
    val finishedTasks: MutableList<Task> = mutableListOf()
) {
    fun getFinishingValue(roadmap: Roadmap): Double {
        val finished = finishedTasks.filter { it in roadmap.tasks }.size
        val size = roadmap.tasks.size

        return finished.toDouble() / size.toDouble()
    }
}
