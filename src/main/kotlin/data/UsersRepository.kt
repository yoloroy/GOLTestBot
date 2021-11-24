package data

import common.models.UserData
import dev.inmo.tgbotapi.types.UserId

object UsersRepository {
    var usersData: MutableMap<UserId, UserData> = mutableMapOf()
}
