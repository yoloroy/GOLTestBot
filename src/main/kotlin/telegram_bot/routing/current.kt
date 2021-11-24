package telegram_bot.routing

import common.CURRENT_ROADMAPS_COMMAND
import common.CURRENT_TASKS_COMMAND
import common.models.Roadmap
import common.models.Task
import common.models.toMessageString
import data.UsersRepository
import dev.inmo.tgbotapi.extensions.api.send.sendTextMessage
import dev.inmo.tgbotapi.extensions.behaviour_builder.BehaviourContext
import dev.inmo.tgbotapi.extensions.behaviour_builder.expectations.waitText
import dev.inmo.tgbotapi.extensions.utils.asFromUser
import dev.inmo.tgbotapi.extensions.utils.types.buttons.replyKeyboard
import dev.inmo.tgbotapi.extensions.utils.types.buttons.row
import dev.inmo.tgbotapi.extensions.utils.types.buttons.simpleButton
import dev.inmo.tgbotapi.requests.send.SendTextMessage
import dev.inmo.tgbotapi.types.message.abstracts.CommonMessage
import dev.inmo.tgbotapi.types.message.content.TextContent
import dev.inmo.tgbotapi.utils.PreviewFeature

@PreviewFeature
suspend fun BehaviourContext.current(message: CommonMessage<TextContent>) {
    val userId = message.asFromUser()!!.user.id
    val data = UsersRepository.usersData[userId]!!

    val step = waitText(
        SendTextMessage(
            message.chat.id,
            "Что вы дальше хотите посмотреть?",
            replyMarkup = replyKeyboard {
                row {
                    simpleButton(CURRENT_TASKS_COMMAND)
                    simpleButton(CURRENT_ROADMAPS_COMMAND)
                }
            }
        )
    ).first().text

    when (step) {
        CURRENT_TASKS_COMMAND -> {
            val task = getTaskOrNull(message, data.currentTasks)

            if (task != null) {
                task(message, task)
            }
        }
        CURRENT_ROADMAPS_COMMAND -> {
            val roadmap = getRoadmapOrNull(message, data.currentRoadmaps)

            if (roadmap != null) {
                roadmap(message, roadmap)
            }
        }
        else -> throw Exception()
    }

    start(message)
}

suspend fun BehaviourContext.getTaskOrNull(message: CommonMessage<TextContent>, tasks: List<Task>, desc: Boolean = true): Task? {
    if (tasks.isEmpty()) {
        sendTextMessage(message.chat.id, "тут пусто(")
        return null
    }

    if (desc) {
        sendTextMessage(message.chat.id, "Ваши задачи:\n" + tasks.mapIndexed { i, it -> "$i) ${it.name}" }.joinToString("\n"))
        sendTextMessage(message.chat.id, "Введите номер интересующей вас задачи:")
    }

    var taskNumber = waitText().first().text.toIntOrNull()

    if (taskNumber == null) {
        sendTextMessage(message.chat.id, "Вы ввели неправильный номер задачи, прошу, повторите снова")
        return getTaskOrNull(message, tasks, false)
    }
    taskNumber--

    return tasks[taskNumber]
}

suspend fun BehaviourContext.getRoadmapOrNull(message: CommonMessage<TextContent>, roadmaps: List<Roadmap>, desc: Boolean = true): Roadmap? {
    if (roadmaps.isEmpty()) {
        sendTextMessage(message.chat.id, "тут пусто(")
        return null
    }

    if (desc) {
        sendTextMessage(message.chat.id, "На данный момент доступны дорожные карты:\n" + roadmaps.toMessageString())
        sendTextMessage(message.chat.id, "Введите номер дорожной карты:")
    }

    var roadmapNumber = waitText().first().text.toIntOrNull()

    if (roadmapNumber == null) {
        sendTextMessage(message.chat.id, "Вы ввели неправильный номер задачи, прошу, повторите снова")
        return getRoadmapOrNull(message, roadmaps, false)
    }
    roadmapNumber--

    return roadmaps[roadmapNumber]
}

@PreviewFeature
suspend fun BehaviourContext.task(message: CommonMessage<TextContent>, task: Task) {
    val user = UsersRepository.usersData[message.asFromUser()!!.user.id]!!

    sendTextMessage(message.chat.id, "Задача:\n"+task.toMessageString())

    val step = waitText(
        SendTextMessage(
            message.chat.id,
            "Что вы хотите с ней сделать?",
            replyMarkup = replyKeyboard {
                row {
                    simpleButton("Отметить выполненной")
                    simpleButton("Забыть")
                    simpleButton("Ничего")
                }
            }
        )
    ).first().text

    when (step) {
        "Отметить выполненной" -> user.finishedTasks += task
        "Забыть" -> user.currentTasks -= task
    }

    current(message)
}

@PreviewFeature
suspend fun BehaviourContext.roadmap(message: CommonMessage<TextContent>, roadmap: Roadmap) {
    val user = UsersRepository.usersData[message.asFromUser()!!.user.id]!!

    sendTextMessage(message.chat.id, "Roadmap:\n"+roadmap.toMessageString())

    val step = waitText(
        SendTextMessage(
            message.chat.id,
            "Что вы хотите с ним сделать?",
            replyMarkup = replyKeyboard {
                row {
                    simpleButton("Получить новую задачу")
                    simpleButton("Забыть")
                    simpleButton("Ничего")
                }
            }
        )
    ).first().text

    when (step) {
        "Получить новую задачу" -> {
            if (user.finishedTasks.any { it in roadmap.tasks }) {
                user.currentTasks += roadmap.tasks.zipWithNext().last { (f, _) -> f in user.finishedTasks }.second
                user.currentTasks = user.currentTasks.distinct().toMutableList()
                sendTextMessage(message.chat.id, "Вы получили новую задачу")
            } else if (user.currentTasks.any { it in roadmap.tasks }) {
                sendTextMessage(message.chat.id, "У вас уже есть задача по этому roadmap'у")
            } else {
                user.currentTasks += roadmap.tasks.first()
                sendTextMessage(message.chat.id, "Вы получили свою первую задачу на этом пути")
            }
            start(message)
        }
        "Забыть" -> {
            user.currentRoadmaps -= roadmap
            current(message)
        }
        else -> {
            current(message)
        }
    }
}
