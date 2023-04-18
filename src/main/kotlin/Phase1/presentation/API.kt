package Phase1.presentation

import Phase1.configuration.securityRules.*
import org.springframework.web.bind.annotation.*
import Phase1.data.*
import javax.print.attribute.standard.Destination


@RequestMapping("api/hubs")
interface HubsAPI{

    @GetMapping("")
    fun getAll():Collection<HubDTO>

//    @GetMapping("/{username}/packages")
//    fun getHubsPackages():Collection<PackageDTO>

    @GetMapping("/{id}/packages")
    fun getAllPackages(@PathVariable id:Long):Collection<PackageDTO>
}

@RequestMapping("api/trucks")
interface  TrucksAPI{

    @GetMapping("")
    fun getAll():Collection<TruckDTO>

    @GetMapping("/{id}/packages")
    fun getAllPackages(@PathVariable id:Long):Collection<PackageDTO>
}

@RequestMapping("api/packages")
interface PackagesAPI{
    @PostMapping("")
    fun savePackage(@RequestBody pack:PackageCreateDTO):Unit

    @PutMapping("/{id}/status")
    @CanUpdatePackage
    fun updateStatus(@PathVariable id:Long, @RequestParam status: Status?)

    @PutMapping("/{id}/destination")
    fun updateDestination(@PathVariable id:Long, @RequestParam destination: String?)

    @DeleteMapping("{id}")
    @CanCancelMyPackage
    fun deleteOne(@PathVariable id:Long):Unit

    @GetMapping("")
    fun getAll():Collection<PackageDTO>

    @GetMapping("/{id}")
    @CanGetClientPackages
    fun getOne(@PathVariable id:Long): PackageDTO

    @GetMapping("/{id}/statusAndHistory")
    @CanGetClientPackages
    fun getPackageStatusAndHistory(@PathVariable id:Long): PackageClientDto
}

@RequestMapping("api/user")
interface ClientsAPI {
    @GetMapping("/{username}/packages")
//    @ClientCanGetPackagesInfo
    fun getAllClientPackages(@PathVariable username: String):Collection<PackageClientDto>

    @GetMapping("/{username}/messages")
//    @ClientCanGetHisMessages
    fun getUsersMessages(@PathVariable username: String): Collection<MessageDTO>

    @GetMapping("{username}/hub")
    fun getMyHub(@PathVariable username: String): HubDTO

    @GetMapping("{username}/truck")
    fun getMyTruck(@PathVariable username: String): TruckDTO?

    @GetMapping("{username}/role")
    fun getMyRole(@PathVariable username: String): RoleDTO?
}

@RequestMapping("api/messages")
interface MessagesAPI {

    @GetMapping("")
    fun getAll():Collection<MessageDTO>

    @PostMapping("")
    fun addOne(@RequestBody message:MessageCreateDTO):Unit

    @GetMapping("{id}")
    fun getOne(@PathVariable id:Long): MessageDTO

//    @DeleteMapping("{id}")
//    fun delete(@PathVariable id:Long):Unit

}