package Phase1.data

import java.util.*
import java.time.LocalDateTime
import javax.persistence.*
@Entity
data class Truck(
    @Id @GeneratedValue val truckId: Long,
    @OneToOne
    val driver: Driver,
    @OneToMany(mappedBy="truck")
    val packages:MutableList<Package>
){
    override fun hashCode(): Int = truckId.toInt()
    override fun toString(): String ="Truck(id=${truckId})"
}

@Entity
data class Package(
        @Id @GeneratedValue val packageId:Long,
        @ManyToOne
        var hub: Hub?,
        val description: String,
        val height: Long,
        val width: Long,
        val length: Long,
        val weight:Int,
        val origin:String,
        @OneToOne
        var lastMessage: Message?,
        var destination:String,
        @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
        var history: MutableList<PHistory>?,
        @ManyToOne
        val client: Client,
        @ManyToOne
        var truck: Truck?,
        var status: Status?
) {
    fun putStatus(status: Status) {
        this.status = status
    }

    fun putDestination(destination: String) {
        this.destination = destination
    }

    fun updateStatusHistory(status: Status?) {
        val hist = PHistory(0, status, LocalDateTime.now())
        this.history?.add(hist)
    }

    override fun toString(): String = "Package(id=${packageId})"
}
@Entity
data class PHistory(
    @Id @GeneratedValue val id:Long,
    val status: Status?,
    val date: LocalDateTime
)

enum class Status{
    AwaitingEntry, InStorage, InTransit, Delivered, ReturnedToTheSource, Lost, Damaged
}

@Entity
data class Message(
    @Id @GeneratedValue val messageId:Long,
    @Column(name="fromUser") val from:String,
    @Column(name="toUser")   val to:String,
    val subject:String,
    val body:String,
    val timestamp: LocalDateTime,
    var packageId: Long?,
    var previous_messageId:Long?
    )

@Entity
data class Hub(
    @Id @GeneratedValue val hubId:Long,
    val name:String,
    val address:String,
    val description:String,
    @OneToMany(mappedBy = "hub")
    val packages: MutableList<Phase1.data.Package>)
{
    override fun toString(): String ="Hub(id=${hubId})"
}

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
data class Manager(
    override val id: Long,
    override val username: String,
    override val email: String,
    override val password: String,
    override val role: Role,
) : AppUser(id, username, email, password, role)

@Entity
data class Client(
        override val id: Long,
        override val username: String,
        override val email: String,
        override val password: String,
        override val role: Role,
    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    val packages:MutableList<Package>
) : AppUser(id, username, email, password, role)

@Entity
data class HubWorker(
    override val id: Long,
    override val username: String,
    override val email: String,
    override val password: String,
    override val role: Role,
    @ManyToOne
    val hub:Hub
)  : AppUser(id, username, email, password, role)

@Entity
data class Driver(
    override val id: Long,
    override val username: String,
    override val email: String,
    override val password: String,
    override val role: Role,
    @OneToOne
    var truck: Truck?,
    @OneToMany
    val packages:MutableList<Package>
): AppUser(id, username, email, password, role){
    override fun hashCode(): Int = id.toInt()
    override fun toString(): String ="Driver(id=${id}, name=${username})"

            }

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class AppUser(
    @Id @GeneratedValue
    open val id: Long = 0,
    open val username: String = "",
    open val email: String = "",
    open val password: String = "",
    @ManyToOne
    open val role: Role
) {}

@Entity
data class Role (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    val name: String,

    @OneToMany(mappedBy = "role")
    val users: MutableList<AppUser>?,
) {
    override fun toString(): String ="${name}"
}