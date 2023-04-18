package Phase1.configuration

import Phase1.data.Message
import Phase1.data.MessageRepository
import Phase1.data.PackageRepository
import Phase1.data.Status
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component

@Component("mySecurityService")
class SecurityService(
        val packages: PackageRepository,
        val messages:MessageRepository
) {

    fun myUsername(principal: User, username: String): Boolean {
        return principal.username == username
    }

    fun myPackage(principal: User, id:Long): Boolean {
        val pck = packages.findById(id)

        return pck.get().client.username == principal.username
    }

    fun packageStatusIsAwaitingEntry(principal: User, id:Long): Boolean {
        val pck = packages.findById(id)

        return pck.get().status == Status.AwaitingEntry
    }

    fun statusIsInTransitOrDelivered(principal: User, status: Status): Boolean {
        return status == Status.InTransit || status == Status.Delivered
    }

    fun statusIsInStorage(principal: User, status: Status): Boolean {
        return status == Status.InStorage
    }

    fun myMessage(principal: User, id:Long): Boolean {
        val msg = messages.findById(id)

        return msg.get().from == principal.username
    }


}
