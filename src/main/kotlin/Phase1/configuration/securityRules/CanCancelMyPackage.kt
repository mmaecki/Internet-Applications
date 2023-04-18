package Phase1.configuration.securityRules

import org.intellij.lang.annotations.Language
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize(CanCancelMyPackage.condition)
annotation class CanCancelMyPackage {
    companion object {
        @Language("SpEL")
        const val condition = "hasRole('CLIENT') AND @mySecurityService.myPackage(principal,#id) AND @mySecurityService.packageStatusIsAwaitingEntry(principal, #id)"
    }
}