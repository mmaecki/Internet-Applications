package Phase1.presentation

import Phase1.data.*
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*


data class PackageCreateDTO(val description:String,
                            val height: Long,
                            val width: Long,
                            val length: Long,
                            val weight:Int,
                            val origin:String,
                            val destination:String,
                            val client: ClientDTO)

data class PackageClientDto(val packageId: Long,
                            val status: Status?,
                            val history: List<PHistory>?,
                            val description: String,
                            val height: Long,
                            val width: Long,
                            val length: Long,
                            val weight:Int,
                            val origin:String,
                            val destination:String,

) {
    constructor(paczka:Package):this(paczka.packageId, paczka.status, paczka.history, paczka.description,
    paczka.height, paczka.width, paczka.length, paczka.weight, paczka.origin, paczka.destination)
}

data class ClientDTO(val username:String)

data class UserDTO(val username:String)

data class PackageDTO(val packageId:Long,
                      val description:String,
                      val height: Long,
                      val width: Long,
                      val length: Long,
                      val weight:Int,
                      val origin:String,
                      val destination:String,
                      val history: List<PHistory>?,
                      val client: ClientDTO,
                      val status: Status?){
    constructor(paczka:Package):this(paczka.packageId,  paczka.description,
        paczka.height, paczka.width, paczka.length, paczka.weight, paczka.origin, paczka.destination, paczka.history,
        ClientDTO(paczka.client.username), paczka.status)
}


data class MessageCreateDTO(val from:String,
                            val to:String,
                            val subject:String,
                            val body:String,
                            val timestamp: LocalDateTime,
                            val packageId: Long,
                            val previous_messageId:Long)

data class MessageDTO(val messageId:Long,
                      val from:String,
                      val to:String,
                      val subject:String,
                      val body:String,
                      val timestamp: LocalDateTime,
                      val packageId: Long?,
                      val previous_messageId:Long?){
    constructor(message:Message):this(message.messageId,message.from,message.to,message.subject,message.body,message.timestamp, message.packageId,message.previous_messageId)
}
data class HubDTO(
        val hubId:Long,
        val name:String,
        val address:String,
        val description:String,
        val packages: String){
    constructor(hub:Hub):this(hub.hubId,hub.name,hub.address,hub.description,hub.packages.toString())

}
data class TruckDTO(
        val truckId: Long,
        val driver: String,
        val packages:String
){
    constructor(truck:Truck):this(truck.truckId,truck.driver.username + truck.driver.id,truck.packages.toString())
}

data class RoleDTO(
        val name: String,
) {
    constructor(role:Role): this(role.name)
}