package Phase1.configuration.securityRules
import org.intellij.lang.annotations.Language
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize(ClientCanGetHisMessages.condition)
annotation class ClientCanGetHisMessages{
    companion object {
        @Language("SpEL")
        const val condition = "(@mySecurityService.myUsername(principal,#username))"
    }
}
