package com.guiodes.repertory.infra.configs

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey.Builder
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtEncoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder
import org.springframework.security.web.SecurityFilterChain
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    @Value("\${security.key.public}")
    private val publicKey: RSAPublicKey,
    @Value("\${security.key.private}")
    private val privateKey: RSAPrivateKey,
) {
    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain? {
        httpSecurity
            .authorizeHttpRequests {
                it
                    .requestMatchers(*WHITE_LIST)
                    .permitAll()
                    .anyRequest()
                    .authenticated()
            }.csrf {
                it.disable()
            }.oauth2ResourceServer {
                it.jwt(Customizer.withDefaults())
            }.sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }

        return httpSecurity.build()
    }

    @Bean
    fun jwtEncoder(): JwtEncoder {
        val jwk: JWK = Builder(this.publicKey).privateKey(privateKey).build()
        val jwks = ImmutableJWKSet<SecurityContext>(JWKSet(jwk))

        return NimbusJwtEncoder(jwks)
    }

    @Bean
    fun jwtDecoder(): JwtDecoder = NimbusJwtDecoder.withPublicKey(publicKey).build()

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    companion object {
        val WHITE_LIST =
            arrayOf(
                "/v3/api-docs/**",
                "/user/new",
                "/login",
                "/v3/api-docs",
                "/swagger-ui/**",
            )
    }
}
