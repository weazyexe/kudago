package exe.weazy.kudago.entities

import java.util.*


class City(override var slug: String, override var name: String, timeZone: TimeZone, currency: Currency, language: String) : Entity