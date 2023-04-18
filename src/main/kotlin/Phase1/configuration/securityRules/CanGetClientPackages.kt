package Phase1.configuration.securityRules

import org.intellij.lang.annotations.Language
import org.springframework.security.access.prepost.PreAuthorize

@PreAuthorize(CanGetClientPackages.condition)
annotation class CanGetClientPackages {
    companion object {
        @Language("SpEL")
        const val condition = "(hasRole('CLIENT') AND @mySecurityService.myPackage(principal,#id))" +
                "OR hasRole('MANAGER')"
    }
}