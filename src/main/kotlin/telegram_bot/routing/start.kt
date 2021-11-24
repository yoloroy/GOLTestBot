package telegram_bot.routing

import common.CATEGORIES_COMMAND
import common.CURRENT_COMMAND
import common.INFO_COMMAND
import common.models.UserData
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
suspend fun BehaviourContext.start(message: CommonMessage<TextContent>) {
    sendTextMessage(message.chat.id, "Привет, ${message.asFromUser()!!.user.firstName}!")

    val userId = message.asFromUser()!!.user.id
    if (userId !in UsersRepository.usersData) {
        UsersRepository.usersData[userId] = UserData()

        sendTextMessage(message.chat.id, "Я GOLBot — твой индивидуальный помощник в достижении личных целей")
    }
    sendStats(message, UsersRepository.usersData[userId]!!)

    var step: String? = null
    while (step == null) {
        step = waitText(
            SendTextMessage(
                message.chat.id,
                "Что вы хотите посмотреть?",
                replyMarkup = replyKeyboard {
                    row {
                        simpleButton(CATEGORIES_COMMAND)
                        simpleButton(CURRENT_COMMAND)
                        simpleButton(INFO_COMMAND)
                    }
                }
            )
        ).first().text.takeIf { stp -> stp == CATEGORIES_COMMAND || stp == CURRENT_COMMAND }

        if (step == null) {
            sendTextMessage(message.chat.id, "Вы неверно ввели номер")
        }
    }

    when (step) {
        CATEGORIES_COMMAND -> categories(message)
        CURRENT_COMMAND -> current(message)
        INFO_COMMAND -> info(message)
    }
}
