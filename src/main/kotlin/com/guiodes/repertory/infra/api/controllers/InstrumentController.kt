package com.guiodes.repertory.infra.api.controllers

import com.guiodes.repertory.application.gateways.InstrumentGateway
import com.guiodes.repertory.domain.models.Instrument
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/instrument")
class InstrumentController(
    private val instrumentGateway: InstrumentGateway,
) {
    @PostMapping
    fun createInstrument(
        @RequestBody name: String,
    ) {
        instrumentGateway.save(Instrument(name = name))
    }

    @GetMapping
    fun listInstruments(
        @RequestParam name: String,
    ): List<Instrument> = instrumentGateway.findByNameContaining(name)

    @GetMapping
    fun listById(
        @RequestParam id: UUID,
    ): Instrument? = instrumentGateway.findById(id)

    @DeleteMapping
    fun deleteInstrument(
        @RequestBody name: String,
    ) {
        instrumentGateway.deleteByName(name)
    }

    @PutMapping
    fun updateInstrument(
        @RequestBody instrument: Instrument,
    ) {
        instrumentGateway.update(instrument)
    }
}
