package com.edw.controller;

import com.edw.dto.TransferDto;
import com.edw.model.Transfer;
import com.edw.service.TransferService;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

/**
 * <pre>
 *     com.edw.controller.TransferController
 * </pre>
 */
@Path("/transfer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransferController {

    @Inject
    TransferService transferService;

    @GET
    @Path("/consultar")
    @Transactional
    public Uni<Response> findAll() {
        return transferService.findAll()
                .onItem().transform(rows -> Response.ok(rows).build());
    }

    @POST
    @PermitAll
    @Path("/crear")
    //@Transactional
    public Uni<Response> create(TransferDto transferDto) {
        return transferService.createAsync(transferDto)
                .onItem().transform(dta -> Response.ok(dta).build());
    }
}