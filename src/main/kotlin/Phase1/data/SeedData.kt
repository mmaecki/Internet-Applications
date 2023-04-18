package Phase1.data

import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.Month
import javax.transaction.Transactional

@Component
class SeedData (
        val hubs: HubRepository,
        val packages: PackageRepository,
        val users: ClientRepository,
        val drivers: DriverRepository,
        val trucks: TruckRepository,
        val roles: RoleRepository,
        val bcrypt : PasswordEncoder,
        val messages: MessageRepository,

): CommandLineRunner {

    fun initialize() {
            val r1 = Role(0, "ROLE_CLIENT", null)
            val r2 = Role(0, "ROLE_MANAGER", null)
            val r3 = Role(0, "ROLE_HUBWORKER", null)
            val r4 = Role(0, "ROLE_DRIVER", null)

        roles.saveAll(listOf(r1,r2, r3, r4))

            val c1 = Client(0, "Antos", "aaaaa@aaa.com", bcrypt.encode("123"), r1, mutableListOf())
            val c2 = Client(0, "Ziele", "aaaaaaa@aaa.com", bcrypt.encode("123"), r1, mutableListOf())

            users.saveAll(listOf(c1,c2))
            r1.users?.add(c1)
            r1.users?.add(c2)
            roles.save(r1)

            val hb1 = Hub(0,"Poznan", "Klonowa 5", "strasznie smutne miejsce", mutableListOf())
            val hb2 = Hub(0, "Lzibona", "oceanowa 5", "costa da caparica", mutableListOf())
            hubs.saveAll(listOf(hb1,hb2))

            val h1 = HubWorker(0, "robotnik", "robotnik@a.com",bcrypt.encode("123"), r3, hb1)
            val h2 = HubWorker(0, "proletariusz","proletariusz@b.com",bcrypt.encode("123"), r3, hb2)
            users.saveAll(listOf(h1, h2) )

            val m1 = Manager(0,"franek.kimono", "menago@aaaaa.com", bcrypt.encode("123"), r2)
            val m2 = Manager(0,"krzys.menager","menagerkrzys@gmail.com", bcrypt.encode("123"), r2)
            users.saveAll(listOf(m1,m2))


            val d1 = Driver(0, "Kubica", "aaa@kubica.pl", bcrypt.encode("123"), r4, null, mutableListOf())
            val d2 = Driver(0,"Max Verstappen","namaxa@gmail.com",bcrypt.encode("123"), r4, null, mutableListOf())

            val t1 = Truck(0, d1,mutableListOf())
            val t2 = Truck(0, d2,mutableListOf())
            val msg1=Message(0,"Antos","Ziele","World Cup","Poland Won",LocalDateTime.of(2019, Month.MARCH, 28, 14, 33),0,0)
            val message = Message(0, "Antos", "robotnik", "Szybciej panie", "Mógłby się pan trochę pospieszyć? Ludzie czekają", LocalDateTime.of(2022, Month.APRIL, 30, 21, 33), 0, null)
            messages.saveAll(listOf(msg1, message))

            d1.truck = t1
            d2.truck = t2
            trucks.saveAll(listOf(t1,t2))
            users.saveAll(listOf(d1,d2))

            val paczka = Package(0, hb1, "nie wiem", 10, 10, 10, 10, "Berlin", null, "Kępno", mutableListOf(PHistory(0,Status.AwaitingEntry,
                LocalDateTime.now())), c1, t1, Status.AwaitingEntry)
            val paczka2 = Package(0, hb2, "też nie wiem", 10, 10, 10, 10, "Londyn", null, "Chrzęszczno", mutableListOf(
                PHistory(0,Status.AwaitingEntry, LocalDateTime.now())
            ), c2, t1,Status.AwaitingEntry)




            hb2.packages.add(paczka)
            hb1.packages.add(paczka2)

            packages.saveAll(listOf(paczka, paczka2))
            hubs.saveAll(listOf(hb2, hb1))
    }

    @Transactional
    override fun run(vararg args: String?) {
        initialize()

        users.findAll().forEach{
            println(it.username)
        }
        println("====Packages====")
        packages.findAll().forEach {
            println("Package(${it.packageId}, description : ${it.description},hub : ${it.hub?.name}, client : ${it.client.username},truck : ${it.truck},destination :  ${it.destination},origin : ${it.origin}, status : ${it.status}")
        }
        println("====Hubs and packages====")
        hubs.findAll().forEach{
            println("Hub : ${it.hubId}, ${it.name}")
            hubs.findPackagesByHubId(it.hubId).forEach {
                println("Package(${it.packageId}, ${it.client.username})")
            }
        }

        println("====Trucks and packages=====")
        trucks.findAll().forEach{
            println("Truck : ${it.truckId}, ${it.driver}")
            trucks.findPackagesByTruckId(it.truckId).forEach {
                println("Package : ${it.packageId}, ${it.client}")
            }
        }

        println()
    }
}

