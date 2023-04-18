package Phase1.presentation

import Phase1.service.ClientService
import org.springframework.web.bind.annotation.RestController

@RestController
class ClientController(val clientService: ClientService):ClientsAPI {
    override fun getAllClientPackages(username: String): Collection<PackageClientDto> {
        return clientService.getAllClientPackages(username)
    }

    override fun getUsersMessages(username: String): Collection<MessageDTO> = clientService.getUsersMessages(username).map { MessageDTO(it) }

    override fun getMyHub(username: String): HubDTO = HubDTO(clientService.getMyHub(username))
    override fun getMyTruck(username: String): TruckDTO? = clientService.getMyTruck(username)?.let { TruckDTO(it) }
    override fun getMyRole(username: String): RoleDTO? = clientService.getRole(username).let { RoleDTO(it) }

}