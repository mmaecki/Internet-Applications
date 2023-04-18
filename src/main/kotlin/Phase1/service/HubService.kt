package Phase1.service

import Phase1.data.HubRepository
import org.springframework.stereotype.Service
import javax.annotation.security.RolesAllowed

@Service
class HubService (val hubs:HubRepository){

    @RolesAllowed("HUBWORKER", "MANAGER")
    fun getAll()=hubs.findAll()

    @RolesAllowed("HUBWORKER", "MANAGER")
    fun getAllPackages(id:Long)=hubs.findPackagesByHubId(id)

    fun count() = hubs.count()

    fun clear(){
        hubs.deleteAll()
    }

    fun delete(id:Long){
        hubs.deleteById(id)
    }
}


