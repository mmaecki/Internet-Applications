package Phase1.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import java.util.*

interface DriverRepository:CrudRepository<Driver, Long>{
}
//Łukasz
interface PackageRepository:CrudRepository<Phase1.data.Package,Long>{
}
//Mati
interface TruckRepository:CrudRepository<Truck,Long>{

    fun findByTruckId(name:String):Optional<Truck>

    @Query("select distinct p from Truck t inner join t.packages p where t.truckId = :id")
    fun findPackagesByTruckId(id:Long):Collection<Phase1.data.Package>
}

interface RoleRepository:CrudRepository<Role, Long> {

}



interface ClientRepository:JpaRepository<AppUser,Long>{
    fun findClientByUsername(username:String):AppUser
    fun findDriverByUsername(username:String):Driver
//    fun findClientByClientId(clientId:Long):Optional<Client>
//    fun findClientsPackagesByClientId(id:Long):Collection<Phase1.data.Package>
}
//interface HubWorkerRepository:CrudRepository<HubWorker,Long>{
//}
//interface ManagerRepository:CrudRepository<Manager,Long>{
//}
interface MessageRepository:CrudRepository<Message,Long>{
    fun findByMessageId(messageId:Long):Message

    @Query("select distinct m from Message m where m.from = :username or m.to = :username")
    fun findAllByUserId(username: String):Collection<Message>
}

//Mati M
interface HubRepository:CrudRepository<Hub,Long>{

    fun findHubByHubId(id:Long): Optional<Hub>

    @Query("select distinct p from Hub h inner join h.packages p where h.hubId = :number")
    fun findPackagesByHubId(number:Long):Collection<Phase1.data.Package>
}
interface PHistoryRepository:CrudRepository<PHistory, Long>{}