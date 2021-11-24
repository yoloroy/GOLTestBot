package telegram_bot.routing

import common.models.Category
import common.models.toMessageString
import data.RoadmapsRepository
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
suspend fun BehaviourContext.categories(message: CommonMessage<TextContent>) {
    val category = getCategoryOrNull(message) ?: let {
        start(message)
        return
    }
    val roadmap = getRoadmapOrNull(message, category.roadmaps) ?: let {
        start(message)
        return
    }

    val step = waitText(
        SendTextMessage(
            message.chat.id,
            "Хотите начать ваш путь по этому roadmap'у?",
            replyMarkup = replyKeyboard {
                row {
                    simpleButton("да")
                    simpleButton("нет")
                }
            }
        )
    ).first().text

    when (step) {
        "да" -> {
            val user = UsersRepository.usersData[message.asFromUser()!!.user.id]!!
            user.currentRoadmaps += roadmap
            user.currentTasks += roadmap.tasks.first()

            sendTextMessage(message.chat.id, "Поздравляю! Ты только что выбрал курс ${roadmap.name}. Твоё первое задание уже находится во вкладке “Текущие задания”")
            current(message)
        }
        "нет" -> start(message)
    }
}

suspend fun BehaviourContext.getCategoryOrNull(message: CommonMessage<TextContent>, desc: Boolean = true): Category? {
    val categories = RoadmapsRepository.categories
    if (categories.isEmpty()) {
        sendTextMessage(message.chat.id, "тут пусто(")
        return null
    }

    if (desc) {
        sendTextMessage(message.chat.id, "Вот какие категории доступны на данный момент:\n${categories.toMessageString()}")
        sendTextMessage(message.chat.id, "Введите номер категории")
    }

    var categoryNumber = waitText().first().text.toIntOrNull()

    if (categoryNumber == null) {
        sendTextMessage(message.chat.id, "Вы ввели неправильный номер категории, прошу, повторите снова")
        return getCategoryOrNull(message, false)
    }
    categoryNumber--

    return RoadmapsRepository.categories[categoryNumber]
}
