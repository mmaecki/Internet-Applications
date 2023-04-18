package Phase1.configuration.securityRules

import org.intellij.lang.annotations.Language
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize(CanUpdatePackage.condition)
annotation class CanUpdatePackage {
    companion object {
        @Language("SpEL")
        const val condition = "(hasRole('DRIVER') AND @mySecurityService.statusIsInTransitOrDelivered(principal, #status)) OR" +
                "(hasRole('HUBWORKER') AND @mySecurityService.statusIsInStorage(principal, #status)) OR" +
                " (hasRole('MANAGER'))"
    }
}