package com.guiodes.repertory.infra.api.controllers

import com.guiodes.repertory.application.usecases.CreateInstrumentUseCase
import com.guiodes.repertory.application.usecases.FindInstrumentUseCase
import com.guiodes.repertory.domain.api.requests.CreateInstrumentRequest
import com.guiodes.repertory.domain.models.Instrument
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/instrument")
class InstrumentController(
    private val createInstrumentUseCase: CreateInstrumentUseCase,
    private val findInstrumentUseCase: FindInstrumentUseCase,
) {
    @PostMapping
    fun createInstrument(
        @RequestBody createInstrumentRequest: CreateInstrumentRequest,
    ) = createInstrumentUseCase.execute(createInstrumentRequest)

    @GetMapping("/list/name/{name}")
    fun listInstruments(
        @PathVariable name: String,
    ): List<Instrument> = findInstrumentUseCase.execute(name)

    @GetMapping("/list/id/{id}")
    fun listById(
        @PathVariable id: UUID,
    ): Instrument? = findInstrumentUseCase.execute(id)
}
