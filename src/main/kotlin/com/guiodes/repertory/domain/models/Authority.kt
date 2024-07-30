package com.guiodes.repertory.domain.models

import com.guiodes.repertory.domain.interfaces.BaseEntity
import org.springframework.security.core.GrantedAuthority

data class Authority(
    val name: String
): BaseEntity(), GrantedAuthority {
    override fun getAuthority() = name
}
