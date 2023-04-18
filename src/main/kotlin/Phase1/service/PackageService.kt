package Phase1.service

import Phase1.data.*
import Phase1.presentation.PackageClientDto
import Phase1.presentation.PackageCreateDTO
import Phase1.presentation.PackageDTO
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.annotation.security.RolesAllowed


@Suppress("UNCHECKED_CAST")
@Service
class PackageService(val packageRepository: PackageRepository, val messageRepository: MessageRepository, val messageService: MessageService, val clientRepository: ClientRepository){
    // save operation
    @RolesAllowed("CLIENT", "MANAGER")
    fun savePackage(pack: PackageCreateDTO): Phase1.data.Package? {
        val message = Message(0, "systemInitialMessage", pack.client.username, "Shipment created", "You have created the shipment successfully", LocalDateTime.now(), null, null)
        val client = clientRepository.findClientByUsername(pack.client.username) as Client
        val paczka = Phase1.data.Package(0, null, pack.description, pack.height,
            pack.width, pack.length, pack.weight, pack.origin, message, pack.destination, mutableListOf(), client, null,
            Status.AwaitingEntry)
        paczka.history?.add(PHistory(0, Status.AwaitingEntry, LocalDateTime.now()))
        paczka.updateStatusHistory(Status.AwaitingEntry)
        message.packageId = paczka.packageId
        messageRepository.save(message)
        return packageRepository.save(paczka)
    }

    // read operation
    @RolesAllowed("MANAGER")
    fun fetchPackagesList(): List<Phase1.data.Package> {
        return packageRepository.findAll() as List<Phase1.data.Package>
    }

    // get one
    fun getOnePackage(packageId: Long)= packageRepository.findById(packageId)

    fun getPackageStatusAndHistory(packageId: Long): Package {
        return packageRepository.findById(packageId).get()
    }


    fun updatePackageStatus(packageId: Long, status: Status?, auth: Authentication) {
        val packageDB: Phase1.data.Package = packageRepository.findById(packageId).get()
        if (status != null) {
            if(packageDB.history?.size  == 0) {
                packageDB.updateStatusHistory(packageDB.status)
            }
            packageDB.putStatus(status)
            packageDB.updateStatusHistory(status)
            messageService.addOne(auth.name, packageDB.client.username, "Status change",
                "The status of your shipment has changed to $status", LocalDateTime.now(), packageDB.packageId, packageDB.lastMessage?.messageId)
        }
        packageRepository.save(packageDB)
    }

    @RolesAllowed("MANAGER")
    fun updatePackageDestination(packageId: Long, destination : String?){
        val packageDB: Package = packageRepository.findById(packageId).get()

        if (destination != null) {
            packageDB.putDestination(destination)
        }

        packageRepository.save(packageDB)
        }

    // delete operation
    @RolesAllowed("CLIENT")
    fun deletePackageById(packageId: Long) {
        packageRepository.deleteById(packageId)
    }

}