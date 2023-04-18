package Phase1.service

import Phase1.data.*
import Phase1.presentation.PackageClientDto
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.security.RolesAllowed

@Service
class ClientService(val clientRepository: ClientRepository, val messages: MessageRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user: Phase1.data.AppUser = clientRepository.findClientByUsername(username) ?:throw UsernameNotFoundException(username)
        return User.builder()
                .username(user.username)
                .password(user.password)
                .authorities(user.role.name)
                .build()
    }

    fun addUser(user: Client): Optional<Client> {
        clientRepository.save(user)
        return Optional.of(user)
    }

    fun getRole(username: String): Role {
        val user: Phase1.data.AppUser = clientRepository.findClientByUsername(username)
        return user.role
    }

    fun getTruck(username: String): Int {
        val user: Phase1.data.Driver = clientRepository.findDriverByUsername(username)
        return user.truck.hashCode()
    }



    fun getAllClientPackages(username: String): List<PackageClientDto> {
        val client: Client = clientRepository.findClientByUsername(username) as Client
        val packages = client.packages
        return packages.map { PackageClientDto(it) }
    }

    fun getUsersMessages(username:String)=messages.findAllByUserId(username)

    @RolesAllowed("HUBWORKER")
    fun getMyHub(username: String): Hub{
        val hubWorker: HubWorker = clientRepository.findClientByUsername(username) as HubWorker
        return hubWorker.hub
    }
    @RolesAllowed("DRIVER")
    fun getMyTruck(username: String): Truck?{
        val driver: Driver = clientRepository.findClientByUsername(username) as Driver
        return driver.truck
    }
}