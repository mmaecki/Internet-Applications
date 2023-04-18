package Phase1.presentation

import Phase1.Application
import Phase1.service.PackageService
import com.sun.xml.bind.v2.runtime.reflect.Lister
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class PackageController(val packageService: PackageService) : PackagesAPI {
    override fun savePackage(pack: PackageCreateDTO) {
        packageService.savePackage(pack)
    }

    override fun updateStatus(id: Long, status: Phase1.data.Status?) {
        val auth: org.springframework.security.core.Authentication = SecurityContextHolder.getContext().authentication
//        if (auth != null) {
//            println(auth.name)
//        }
        packageService.updatePackageStatus(id, status, auth)
    }

    override fun updateDestination(id: Long,destination: String?){
        packageService.updatePackageDestination(id, destination)
    }

    override fun deleteOne(id: Long) {
        packageService.deletePackageById(id)
    }

    override fun getAll(): Collection<PackageDTO> {
        return packageService.fetchPackagesList().map { PackageDTO(it) }
    }

    override fun getOne(id: Long): PackageDTO = PackageDTO(packageService.getOnePackage(id).get())

    override fun getPackageStatusAndHistory(id: Long): PackageClientDto {
        val pck = packageService.getPackageStatusAndHistory(id)
        return PackageClientDto(pck)
    }

}