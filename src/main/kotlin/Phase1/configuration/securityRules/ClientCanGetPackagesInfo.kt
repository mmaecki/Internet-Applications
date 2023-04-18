package Phase1.configuration.securityRules
import org.intellij.lang.annotations.Language
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize(ClientCanGetPackagesInfo.condition)
annotation class ClientCanGetPackagesInfo{
    companion object {
        @Language("SpEL")
        const val condition = "(hasRole('CLIENT') AND @mySecurityService.myUsername(principal,#username))" +
                "OR hasRole('MANAGER')"
    }
}
